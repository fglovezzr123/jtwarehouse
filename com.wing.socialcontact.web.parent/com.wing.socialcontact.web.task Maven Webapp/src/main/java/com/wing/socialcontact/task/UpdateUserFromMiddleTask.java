package com.wing.socialcontact.task;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.funny.ali.mns.MnsCloudCreater;
import com.funny.ali.mns.commons.enums.QueueAttributeEnum;
import com.funny.ali.mns.commons.message.MnsMessageManager;
import com.funny.ali.mns.commons.queue.MnsQueueManager;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.ApplicationPath;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.StringUtil;
import com.wing.socialcontact.util.im.IMUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Devil
 * @description: 从中间库消息队列消费并更新用户信息
 * @date 2017/12/6 14:34
 */
@Component
public class UpdateUserFromMiddleTask {

    /**
     * 日志
     */
    protected final static Logger LOGGER = Logger.getLogger(UpdateUserFromMiddleTask.class);

    /**
     * 队列名称
     */
    private static String queneName = ApplicationPath.getParameter("MNS_QUENE_MIDDLE");

    /**
     * IM前缀
     */
    public static final String IM_PREFIX = "NETEASE_IM_PREFIX";
    private String imPrefix = ApplicationPath.getParameter(IM_PREFIX);

    @Autowired
    private IWxUserService wxUserService;
    @Autowired
    private ITjyUserService tjyUserService;
    @Autowired
    private IListValuesService listValuesService;
    @Autowired
    private IDistrictService districtService;

    /**
     * 执行方法---每10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void execute() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("==========执行从中间库消息队列同步====开始");
        MNSClient mnsClient = null;
        try {
            //创建mnsClient
            mnsClient = MnsCloudCreater.getClientInstance();
            Map<String, Object> pushMap = new HashMap<>(2);
            pushMap.put(QueueAttributeEnum.QueueName.getAttrKey(), queneName);
            //根据队列名称获取队列，没有就创建
            CloudQueue cloudQueue = MnsQueueManager.getQueue(mnsClient, pushMap);
            if (cloudQueue == null) {
                MnsQueueManager.createMnsQueue(mnsClient, pushMap);
            }
            Map<String, Object> paraMap = new HashMap<>(2);
            //消息读取超时时间
            paraMap.put("waitSeconds", 2);
            while (true) {
                Message message = MnsMessageManager.popMessage(cloudQueue, paraMap);
                if (message == null) {
                    //为空跳出循环
                    break;
                } else {
                    System.out.println(message.getMessageBody());
                    JSONObject jsonObject = JSONObject.fromObject(message.getMessageBody());
                    //获取json对象手机号
                    String mobile = jsonObject.get("mobile") != null ? (String) jsonObject.get("mobile") : null;
                    if (!StringUtil.isEmpty(mobile)) {
                        //根据手机号查询user用户和tjy_user用户
                        WxUser wxUser = wxUserService.selectByMobile(mobile);
                        if (wxUser != null) {
                            TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId().toString());
                            //塞入数据
                            setUserInfoFromJson(wxUser, tjyUser, jsonObject);
                            //更新user和tjy_user
                            LOGGER.info("========开始更新天九云用户：" + mobile);
                            tjyUserService.updateTjyUser(tjyUser);
                            wxUserService.updateWxUser(wxUser);
                            LOGGER.info("------------------结束更新：" + mobile);
                        }
                    }
                    //消费完删除消息
                    MnsMessageManager.deleteMessage(cloudQueue, message);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("==========执行从中间库消息队列同步====结束=======执行时间： " + (endTime - startTime) + "ms");
    }

    /**
     * 塞入用户信息
     * @param wxUser user表用户
     * @param tjyUser 天九云用户
     * @param jsonObject 获取的json对象
     */
    private void setUserInfoFromJson(WxUser wxUser, TjyUser tjyUser, JSONObject jsonObject) {
        try {
            //手机号
            String mobile = jsonObject.get("mobile") != null ? (String) jsonObject.get("mobile") : null;
            String newMobile = jsonObject.get("newMobile") != null ? (String) jsonObject.get("newMobile") : null;
            if (!StringUtil.isEmpty(newMobile) && !newMobile.equals(mobile)) {
                wxUser.setMobile(newMobile);
                tjyUser.setMobile(newMobile);
                tjyUser.setBindPhone(newMobile);
            }
            //用户名
            String customerName = jsonObject.get("userName") != null ? (String) jsonObject.get("userName") : null;
            if (!StringUtil.isEmpty(customerName)) {
                wxUser.setTruename(customerName);
                tjyUser.setNickname(customerName);
                tjyUser.setTrueName(customerName);
                wxUser.setUsername(customerName);
                wxUser.setNickName(customerName);
                // 更新im用户头像
                IMUtil.updateUserOne(imPrefix + tjyUser.getId(), tjyUser.getNickname(), tjyUser.getHeadPortrait());
            }
            //性别
            String sexString = jsonObject.get("sexId") != null ? (String) jsonObject.get("sexId") : null;
            if (!StringUtil.isEmpty(sexString)) {
                if ("女".equals(sexString)) {
                    wxUser.setSex(Integer.valueOf("2"));
                } else if ("男".equals(sexString)) {
                    wxUser.setSex(Integer.valueOf("1"));
                }
            }
            //激活时间
            String reconDate = jsonObject.get("reconDate") != null ? (String) jsonObject.get("reconDate") : null;
            if (!StringUtil.isEmpty(reconDate)) {
                tjyUser.setReconDate(new Date(Long.parseLong(reconDate)));
            }
            //公司名称
            String company = jsonObject.get("company") != null ? (String) jsonObject.get("company") : null;
            if (!StringUtil.isEmpty(company)) {
                tjyUser.setComName(company.split(",")[0]);
            }
            //职位
            String job = jsonObject.get("job") != null ? (String) jsonObject.get("job") : null;
            if (!StringUtil.isEmpty(job)) {
                List<Map<String, Object>> listValues = listValuesService.selectListByType(12, job);
                if (null != listValues && !listValues.isEmpty()) {
                    Map lv = (Map) listValues.get(0);
                    tjyUser.setJob((String) lv.get("id"));
                }
            }
            //行业
            String customerUnitIndustry = jsonObject.get("industry") != null ? (String) jsonObject.get("industry") : null;
            if (!StringUtil.isEmpty(customerUnitIndustry)) {
                List<Map<String, Object>> listValues = listValuesService.selectListByType(8001, customerUnitIndustry);
                if (null != listValues && !listValues.isEmpty()) {
                    Map lv = (Map) listValues.get(0);
                    tjyUser.setIndustry((String) lv.get("id"));
                } else {
                    tjyUser.setIndustry("cf1b1378048c4c9a90298c847ad594ba");
                }
            }
            //认证状态
            String reconStatus = jsonObject.get("reconStatus") != null ? (String) jsonObject.get("reconStatus") : null;
            if (!StringUtil.isEmpty(reconStatus)) {
                if ("问题客户".equals(reconStatus)) {
                    tjyUser.setIsRealname(0);
                    tjyUser.setReconStatus(3);
                } else {
                    tjyUser.setIsRealname(1);
                    tjyUser.setReconStatus(2);
                }
            }
            //省份
            String province = jsonObject.get("province") != null ? (String) jsonObject.get("province") : null;
            if (!StringUtil.isEmpty(province)) {
                Map param = new HashMap(2);
                param.put("disName", province);
                List lst = districtService.selectByParam(param);
                if (lst != null && lst.size() > 0) {
                    Map d = (Map) lst.get(0);
                    tjyUser.setProvince((String) d.get("id"));
                }
            }
            //最后注册时间
            String lastRegDate = jsonObject.get("lastRegDate") != null ? (String) jsonObject.get("lastRegDate") : null;
            if (!StringUtil.isEmpty(lastRegDate)) {
                long d = CommUtil.null2Long(lastRegDate);
                tjyUser.setLastRegDate(new Date(d));
            }
            //客服电话
            String kfTelephone = jsonObject.get("kfTelephone") != null ? (String) jsonObject.get("kfTelephone") : null;
            if (!StringUtil.isEmpty(kfTelephone)) {
                tjyUser.setKfTelephone(kfTelephone);
            }
            //头衔
            String honorTitle = jsonObject.get("honorTitle") != null ? (String) jsonObject.get("honorTitle") : null;
            if (!StringUtil.isEmpty(honorTitle)) {
                if ("天九家人".equals(honorTitle)) {
                    tjyUser.setHonorTitle("家人");
                    tjyUser.setHonorFlag("honor_001");
                } else if ("天九云亲".equals(honorTitle)) {
                    tjyUser.setHonorTitle("云亲");
                    tjyUser.setHonorFlag("honor_002");
                } else if ("天九伙伴".equals(honorTitle)) {
                    tjyUser.setHonorTitle("伙伴");
                    tjyUser.setHonorFlag("honor_003");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
