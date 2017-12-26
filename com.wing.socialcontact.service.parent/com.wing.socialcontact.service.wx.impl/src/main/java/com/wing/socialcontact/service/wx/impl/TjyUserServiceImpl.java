package com.wing.socialcontact.service.wx.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.annotation.Resource;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
//import com.funny.ali.mns.MnsCloudCreater;
//import com.funny.ali.mns.commons.enums.QueueAttributeEnum;
//import com.funny.ali.mns.commons.message.MnsMessageManager;
//import com.funny.ali.mns.commons.queue.MnsQueueManager;
import com.github.pagehelper.PageHelper;
import com.wing.socialcontact.sensitive.conf.Config;
import com.wing.socialcontact.util.*;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.service.wx.dao.SysconfigDao;
import com.wing.socialcontact.service.wx.dao.TjyUserDao;
import com.wing.socialcontact.service.wx.dao.WxUserDao;
import com.wing.socialcontact.synchronization.TjySign;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.bean.SyDistrict;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author zengmin
 * @date 2017-04-07 01:41:07
 * @version 1.0
 */
@Service
public class TjyUserServiceImpl extends BaseServiceImpl<TjyUser> implements ITjyUserService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:TjyUser:\" + ";

	@Resource
	private TjyUserDao tjyUserDao;
	@Resource
	private WxUserDao wxUserDao;
	@Resource
	private SysconfigDao sysconfigDao;
	@Resource
	private IListValuesService listValuesService;
	@Resource
	private IDistrictService districtService;
	@Resource
	private IWxUserService wxUserService;
	@Autowired
	private CustomizedPropertyConfigurer customizedPropertyConfigurer;

	@Override
	public boolean addTjyUser(TjyUser tjyUser) {
		if (null == tjyUser.getReconStatus()) {
			tjyUser.setReconStatus(0);
		}
		if (null == tjyUser.getAppSynMsgToTjy()) {
			tjyUser.setAppSynMsgToTjy(0);
		}
		if (null == tjyUser.getIsdk()) {
			tjyUser.setIsdk(0);
		}
		if (null == tjyUser.getIsztdh()) {
			tjyUser.setIsztdh(0);
		}
		if (null == tjyUser.getZtdhsort()) {
			tjyUser.setZtdhsort(0);
		}
		int res = tjyUserDao.insert(tjyUser);
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TjyUser selectById(String id) {
		return tjyUserDao.selectByPrimaryKey(id);
	}

	@Override
	public TjyUser selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, TjyUser.class);
	}

	@Override
	public boolean updateTjyUser(TjyUser tjyUser) {
		return super.updateByPrimaryKeyCache(tjyUser, tjyUser.getId());
	}

	@Override
	public TjyUser selectByWxUniqueId(String wxUniqueId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("wxUniqueId", wxUniqueId);
		return tjyUserDao.load(param);
	}

	public List selAllUserForApp(String uids) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uids", uids);
		return tjyUserDao.selAllUserForApp(param);
	}

	@Override
	public List<TjyUser> selectbymobile(String phone, int recon_status) {

		TjyUser user = new TjyUser();
		user.setMobile(phone);
		user.setReconStatus(recon_status);
		return tjyUserDao.select(user);
	}

	@Override
	public List<Map<String, Object>> selectTaskRecon() {
		return tjyUserDao.selectTaskRecon();
	}

	// 同步远程用户域名
	private static String remotingDomain;

	private String queneName;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean remotingUpdateTjyUser(TjyUser tjyUser, String mobile) throws Exception {
		int remotingflag = sysconfigDao.remoteEnable();
		// 同步接口开关：0 close, 1 open
		if (remotingflag == 0) {return true;}

		queneName = (String) customizedPropertyConfigurer.getProperty("queueName");
		if (StringUtil.isEmpty(queneName)) {
			return false;
		}

		try {
			WxUser u = wxUserService.selectById(tjyUser.getId());
			Map queries = new HashMap();
			queries.put("timestamp", System.currentTimeMillis() + "");
			// 唯一标示
			queries.put("phone", mobile);
			// 性别 0男 1女
			queries.put("sex", "男");
			if (u.getSex() != null && u.getSex() == 2) {
				queries.put("sex", "女");
			}
			// 客户名称
			if (!StringUtil.isEmpty(tjyUser.getNickname())) {
				queries.put("customerName", tjyUser.getNickname());
			}
			// 手机号变动
			if (!StringUtil.isEmpty(tjyUser.getMobile())) {
				// 电话号码相关 b
				queries.put("customerNumber", tjyUser.getMobile());
				if (!mobile.equals(tjyUser.getMobile())) {
					queries.put("newMobile", tjyUser.getMobile());
				}
			}
			// 公司
			if (!StringUtil.isEmpty(tjyUser.getComName())) {
				queries.put("customerCompany", tjyUser.getComName());
			}
			// 客户来源
			queries.put("customerDataSources", "1");
			// 服务秘书电话
			if (!StringUtil.isEmpty(tjyUser.getKfTelephone())) {
				queries.put("servantNumber", tjyUser.getKfTelephone());
			}
			// 省份
			if (!StringUtil.isEmpty(tjyUser.getProvince())) {
				SyDistrict d = districtService.selectByPrimaryKey(tjyUser.getProvince());
				if (d != null) {
					queries.put("customerProvince", d.getDisName());
				}
			}
			// 工作(职位)
			if (!StringUtil.isEmpty(tjyUser.getJob())) {
				ListValues lv = listValuesService.selectByPrimaryKey(tjyUser.getJob());
				if (lv != null) {
					queries.put("customerTitle", lv.getListValue());
				}
			}
			// 行业
			if (!StringUtil.isEmpty(tjyUser.getIndustry())) {
				ListValues lv = listValuesService.selectByPrimaryKey(tjyUser.getIndustry());
				if (lv != null) {
					queries.put("customerUnitIndustry", lv.getListValue());
				}
			}
			if (tjyUser.getReconStatus() != null && tjyUser.getReconStatus() == 2) {
				queries.put("customerType", "认证客户");
			} else {
				queries.put("customerType", "普通客户");
			}
			if (tjyUser.getReconDate() != null) {
				queries.put("customerActivationDate", tjyUser.getReconDate().getTime() + "");
			}
			if (!StringUtil.isEmpty(tjyUser.getKfTelephone())) {
				queries.put("servantNumber", tjyUser.getKfTelephone());
			}
			if (tjyUser.getLastRegDate() != null) {
				queries.put("customerEndDate", tjyUser.getLastRegDate().getTime() + "");
			}
			if (!StringUtil.isEmpty(tjyUser.getHonorTitle())) {
				queries.put("customerEffectiveLevel", tjyUser.getHonorTitle());
				if ("honor_001".equals(tjyUser.getHonorFlag())) {
					queries.put("customerEffectiveLevel", "天九家人");
				} else if ("honor_002".equals(tjyUser.getHonorFlag())) {
					queries.put("customerEffectiveLevel", "天九云亲");
				} else if ("honor_003".equals(tjyUser.getHonorFlag())) {
					queries.put("customerEffectiveLevel", "天九伙伴");
				} else if ("honor_004".equals(tjyUser.getHonorFlag())) {
					// queries.put("customerEffectiveLevel", "天九服务商");
				}
			}
			JSONObject jsonObject = JSONObject.fromObject(queries);
//			//mns发送
//			MNSClient mnsClient = MnsCloudCreater.getClientInstance();
//			Map<String, Object> pushMap = new HashMap<>(2);
//			pushMap.put(QueueAttributeEnum.QueueName.getAttrKey(), queneName);
//			CloudQueue cloudQueue = MnsQueueManager.getQueue(mnsClient, pushMap);
//			Map<String, Object> paraMap = new HashMap<>(2);
//			paraMap.put("messageBody", jsonObject);
//			Message message = MnsMessageManager.putMessage(cloudQueue, paraMap);
//			return message != null ? true : false;
return false;
//			System.out.println(jsonObject1.toString());
//			queries = TjySign.sign(queries);
//			String res = HttpClientUtil.sendPostRequest(remotingDomain + "dubbo-customer-admin/cusinterface/insert",
//					queries, new HashMap());
//			System.out.println("httpRequest:" + res);
//			System.out.println("++++++end++remotingUpdateTjyUser");
//			JSONObject jsonObject = JSONObject.fromObject(res);
//			if (jsonObject == null || jsonObject.isNullObject()) {
//				return false;
//			}
//			Integer ret = jsonObject.getInt("ret");
//			if (ret == 0) {
//				return true;
//			} else {
//				return false;
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Map remotingGetUser(String mobile) throws Exception {

		int remotingflag = sysconfigDao.remoteEnable();
		// 同步接口开关：0 close, 1 open
		if (remotingflag == 0)
			return null;

		remotingDomain = (String) customizedPropertyConfigurer.getProperty("remotingDomain");
		System.out.println("++++++start++remotingGetUser");
		try {
			Map<String, String> queries = new HashMap<String, String>();
			queries.put("timestamp", TjySign.getISO8601Time());
			queries.put("phone", mobile);
			String perToSign = TjySign.composeStringToSign(queries);
			byte[] sha1 = null;
			try {
				sha1 = TjySign.hmacSHA1Signature("ljWPzf0qA5I9OhBsTo5DsQEFkc2CxG&", perToSign);
				System.out.println(sha1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String sign = new String(new BASE64Encoder().encode(sha1));
			queries.put("signature", sign);

			String res = HttpClientUtil.sendPostRequest(
					remotingDomain + "dubbo-customer-admin/cusinterface/getCustomerInfoByPhone", queries,
					new HashMap());
			System.out.println("httpRequest:" + res);
			JSONObject jsonObject = JSONObject.fromObject(res);
			if (jsonObject == null || jsonObject.isNullObject()) {
				return null;
			}
			Integer ret = jsonObject.getInt("ret");
			if (ret == 0) {
				String s = jsonObject.getJSONObject("data").toString();
				Map usermap = JsonUtil.parseJSON2Map(s);
				System.out.println("+++++end+++remotingGetUser:" + usermap.toString());
				return usermap;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List getUserListById(String id)
	{
		Map param = new HashMap();
		param.put("id", id);
		return tjyUserDao.getUserListById(param);
	}

	@Override
	public List selectByMobile(String mobile)
	{
		Map param = new HashMap();
		param.put("mobile", mobile);
		return tjyUserDao.selectByMobile(param);
	}
	/**
	 * @see ITjyUserService#getReconUserList(Map)
	 * @param paraMap
	 * @return
	 */
	@Override
	public List getReconUserList(Map paraMap) {
		Integer pageNum = paraMap.get("pageNum") != null ? Integer.valueOf(paraMap.get("pageNum").toString()) : null;
		Integer pageSize = paraMap.get("pageSize") != null ? Integer.valueOf(paraMap.get("pageSize").toString()) : null;
		if (pageNum == null || pageSize == null) {
			return null;
		} else {
			PageHelper.startPage(pageNum, pageSize);
			return tjyUserDao.getShUserListByParam(paraMap);
		}
	}

}