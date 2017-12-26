package com.wing.socialcontact.service.wx.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.gexin.GeTuiModel;
import com.wing.socialcontact.gexin.GetuiAndroidPushUtil;
import com.wing.socialcontact.gexin.GetuiIosPushUtil;
import com.wing.socialcontact.sensitive.conf.Config;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.ImMsgBean;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.Template;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.dao.MessageInfoDao;
import com.wing.socialcontact.service.wx.dao.TemplateDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.im.IMUtil;

/**
 * 
 * @author gaojun
 * @date 2017-03-28 15:00:44
 * @version 1.0
 */
@Service
public class MessageInfoServiceImpl extends BaseServiceImpl<MessageInfo> implements IMessageInfoService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:MessageInfo:\" + ";

	@Resource
	private MessageInfoDao messageInfoDao;

	@Resource
	private TemplateDao templateDao;
	@Resource
	private ITjyUserService tjyUserService;
	@Resource
	private RedisCache redisCache;

	@Override
	public List selectAllMessageInfo() {
		return messageInfoDao.selectAllMessageInfoMap();
	}

	@Override
	public DataGrid selectMessageInfos(PageParam param, MessageInfo messageInfo) {
		DataGrid data = new DataGrid();

		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		/// parm.put("tagName", messageInfo.getTagName());
		parm.put("orderStr", orderStr);
		List lst = messageInfoDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());

		return data;
	}
	@Override
	public List selectMessageInfos(MessageInfo messageInfo) {
		Map parm = new HashMap();
		parm.put("status", messageInfo.getStatus());
		parm.put("type", messageInfo.getType());
		parm.put("toUserId", messageInfo.getToUserId());
		List lst = messageInfoDao.selectByParam(parm);
		return lst;
	}

	@Override
	public String addMessageInfo(MessageInfo messageInfo) {
		// TODO 为测试方便，临时屏蔽短信
		//messageInfo.setStatus(1);
		// TODO 为测试方便，临时屏蔽短信END
		
		
		int res = messageInfoDao.insert(messageInfo);
		if (res > 0) {
			if (messageInfo.getStatus() == 0 && messageInfo.getType() < 3) {
				redisCache.putToQueue("sms_queue_web_online", messageInfo.getId());
			}else if(messageInfo.getStatus() == 0 && messageInfo.getType() > 2){
				
				IMUtil.pushContentUtil(Config.setConfFileName("dubbo.properties").getString("NETEASE_IM_APPKEY").trim(),
						Config.setConfFileName("dubbo.properties").getString("NETEASE_IM_APPSECRET").trim(),
						Config.setConfFileName("dubbo.properties").getString("NETEASE_IM_PREFIX").trim(),
						messageInfo.getId(),"系统通知", messageInfo.getContent(),messageInfo.getType()+"", messageInfo.getTemplateId(),
						messageInfo.getCreateTime().getTime()+"", Constants.IM_MESSAGE_PUSH_ACCOUNT, messageInfo.getToUserId(), messageInfo.getContent(), "");
			}
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}
	/*public void pushContentUtil(String id,String title,String content ,String type,
			String template,String timestamp,String from,String to,String contentDetail,String status ){
		String url = Config.setConfFileName("dubbo.properties").getString("SYSDOMAIN")+"appfront/im/m/notify/sendAttachMsg";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/x-json");
		try
		{
			RequestReport report = new RequestReport();
			
			Map map = new HashMap();
			Map extra = new HashMap();
			map.put("id",id );
			map.put("title",title );
			map.put("content",content );
			map.put("type",type );
			map.put("template",template );
			map.put("timestamp",timestamp );
			extra.put("from", from);
			extra.put("to", to);
			extra.put("content", contentDetail);
			extra.put("status", status);
			map.put("extra",extra );
			JSONObject jsonObject = JSONObject.fromObject(map);
			System.out.println(jsonObject);
			Map data = new HashMap();
			data.put("from", from);
			data.put("msgType", "0");
			data.put("to", to);
			data.put("attach", jsonObject.toString());
			data.put("pushContent", jsonObject.toString());
			CommandInfo userCommand = new CommandInfo();
			userCommand.setCommandName("sss");
			userCommand.setData(data);
			report.setCommandInfo(userCommand);
			// 加载appinfo
			AppInfo appInfo = new AppInfo();
			appInfo.setProductId("tojoycloud_app");
			appInfo.setVersionCode("1.0.0");
			appInfo.setVisitDevice("1");
			report.setAppInfo(appInfo);
			// 加载userProperty
			UserProperty userProperty = new UserProperty();
			userProperty.setUserId("1");
			report.setUserProperty(userProperty);
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setImei("000000");
			deviceInfo.setImsi("");
			deviceInfo.setLanguage("CN");
			deviceInfo.setModel("MHA");
			deviceInfo.setIsEmulator("false");
			deviceInfo.setSerialNo("3HX5T17726004042000000000000000");
			deviceInfo.setManufacturer("HUAWEI");
			deviceInfo.setSdkLevel("24");
			deviceInfo.setScreenHeight("1340");
			deviceInfo.setScreenWidth("750");
			deviceInfo.setDeviceName("HUAWEIMHA-AL00");
			report.setDeviceInfo(deviceInfo);

			byte[] bResponseToClient = new byte[0];
			String request = JSON.toJSONString(report);
			byte[] bufCompressed = ReportUtil.compressData(request.getBytes("utf-8")); // 压缩
			bResponseToClient = ReportUtil.ecrypt(bufCompressed); // 加密

			ByteArrayEntity reqEntity = new ByteArrayEntity(bResponseToClient);
			httpPost.setEntity(reqEntity);

			HttpResponse response = httpClient.execute(httpPost);
			System.out.println(response.getStatusLine());
			InputStream in = response.getEntity().getContent();
			String responseReport = ReportUtil.getRequestStr(in);//
			System.out.println(responseReport);
			ResponseReport rr = JSON.parseObject(responseReport, ResponseReport.class);
			System.out.println("--------" + rr.getResponseTips() + "---------");
			System.out.println("--------" + rr.getResponseCode() + "---------");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/
	@Override
	public List selectMessageInfoPageByUserIdAndType(String userId, int type, int page, int pageSize) {
		PageHelper.startPage(page, pageSize);// 分页
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("toUserId", userId);
		param.put("type", type);
		return messageInfoDao.selectByParam(param);
	}

	@Override
	public boolean updateStatusByUserIdAndType(String userId, int type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("toUserId", userId);
		param.put("type", type);
		return messageInfoDao.updateStatusByUserIdAndType(param);
	}
	
	@Override
	public Integer countMessage(String userId, Integer type, int status) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("toUserId", userId);
		param.put("type", type);
		param.put("status", status);
		return messageInfoDao.count(param);
	}
	
	@Override
	public void sendImAppMessage(ImMsgBean imMsgBean) throws IOException{
	    // 企服云app推送
	    Map map = this.selToken(imMsgBean.getToUserId());
	    if(map != null){
            List<Map> lstDnd = this.selOneToOneDnd(imMsgBean.getFormUserId(), imMsgBean.getToUserId());
            // 如果没有点对点免打扰
            if (CollectionUtils.isEmpty(lstDnd)) {
                //Map map = this.selToken(imMsgBean.getToUserId());
    
                TjyUser u = tjyUserService.selectByPrimaryKey(imMsgBean.getFormUserId());
                String content = imMsgBean.getContent();
                if (content != null && !"".equals(content)) {
                    if (content.startsWith("http:")) {
                        if (u != null) {
                            content = "你收到来自" + u.getNickname() + "的消息";
                        } else {
                            content = "您收到一条企服联盟消息";
                        }
                    } else if(content.contains("&quot;emotioneee")){
                        content = "您收到一条企服联盟消息";
                    }else {
                        if (u != null) {
                            content = u.getNickname() + ":" + content;
                        }
                    }
                }
    
                String device = (String) map.get("device");
                String token = (String) map.get("token");
                String dnd = (String) map.get("dnd");
    
                GeTuiModel model = new GeTuiModel();
                model.setContent(content);
                model.setCustomMsgKey("type");
                model.setCustomMsgValue("7");
    
                // 消息通知全局配置 F：开启，T：关闭
                if ("F".equals(dnd)) {
                    if ("I".equals(device)) {
                        // ios
                        GetuiIosPushUtil.iosSinglePush(token, model);
                    } else {
                        // android
                        model.setTitle("企服联盟");
                        GetuiAndroidPushUtil.androidSinglePush(token, model);
                    }
                }
            }
	    }
	}
	@Override
	public void sendImMessage(ImMsgBean imMsgBean) throws IOException {
		// 只有短信及微信需要推送消息
		redisCache.putToQueue("sms_queue_web_online_im", JSON.toJSONString(imMsgBean));
	}

	@Override
	public String updateMessageInfo(MessageInfo messageInfo) {
		MessageInfo old = selectByPrimaryKey(messageInfo.getId());
		old.setSendTime(messageInfo.getSendTime());
		old.setStatus(messageInfo.getStatus());
		if (super.updateByPrimaryKeyCache(old, old.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteMessageInfos(String[] ids) {
		// 等待删除的对象集合
		int count = 0;
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				String[] myids = id.split(",");
				for (String string : myids) {
					MessageInfo r = selectByPrimaryKey(string);
					if (r != null) {

						if (super.deleteByPrimaryKeyCache(string, MessageInfo.class))
							count++;
					}
				}
			}
		}
		return count > 0;
	}

	@Override
	public MessageInfo selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, MessageInfo.class);
	}

	@Override
	public MessageInfo selectById(String id) {
		return messageInfoDao.selectByPrimaryKey(id);
	}

	/**
	 * 
	 * 
	 * @title getMsgContent 获取短信内容字符串
	 * @param request
	 * @param mark
	 *            短信模版表示符
	 * @param Map<Object,Object>
	 *            map key("user","order"),value(User,Order)
	 * @return
	 * @author gaojun
	 * @date 2015 2015年12月31日 下午8:36:43
	 */
	@Override
	public String getMsgContent(String mark, Map<String, Object> map) {
		Template param = new Template();
		param.setMark(mark);
		Template template = templateDao.selectOne(param);
		String content = null;
		if ((template != null) && (template.getIsOpen() == 0)) {
			String path = TemplateServiceImpl.class.getResource("msg.vm").getPath().replace("file:", "");
			System.out.println("+++++++++msg.vm path:" + path);
			System.out.println("+++++++++msg.vm path:" + path.substring(0, path.length() - 6));
			PrintWriter pwrite = null;
			try {
				pwrite = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path, false), "UTF-8"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pwrite.print(template.getContent());// 向msg.vm写入短信模板
			pwrite.flush();
			pwrite.close();
			Properties p = new Properties();
			p.setProperty("file.resource.loader.path", path.substring(0, path.length() - 6));
			p.setProperty("input.encoding", "UTF-8");
			p.setProperty("output.encoding", "UTF-8");
			Velocity.init(p);
			org.apache.velocity.Template blank = Velocity.getTemplate("msg.vm", "UTF-8");
			VelocityContext context = new VelocityContext();
			context.put("send_time", CommUtil.formatLongDate(new Date()));

			String domain = Constants.domain;

			// 设置短信信息
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if ("url".equals(entry.getKey().toString())) {
					if (entry.getValue().toString().indexOf("http://") == -1) {
						context.put(entry.getKey().toString(), domain + entry.getValue());
					}
				} else {
					if (entry.getValue() instanceof Date) {
						Date d = (Date) entry.getValue();
						if (d.getHours() > 0) {
							context.put(entry.getKey().toString(), CommUtil.formatLongDate(entry.getValue()));
						} else {
							context.put(entry.getKey().toString(), CommUtil.formatShortDate(entry.getValue()));
						}
					} else {
						context.put(entry.getKey().toString(), entry.getValue());
					}
				}
			}

			// context.put("webPath", CommUtil.getURL(request));
			StringWriter writer = new StringWriter();
			blank.merge(context, writer);
			content = writer.toString();
			/// System.out.println("+++++++++msg.vm content:"+content);
		}
		return content;
	}

	public Map selToken(String toUserId) {
		Map parm = new HashMap();
		parm.put("toUserId", toUserId);
		return messageInfoDao.selToken(parm);
	}

	public List<Map> selOneToOneDnd(String fromUserId, String toUserId) {
		Map parm = new HashMap();
		parm.put("fromUserId", fromUserId);
		parm.put("toUserId", toUserId);
		List lst = messageInfoDao.selOneToOneDnd(parm);
		return lst;
	}
}