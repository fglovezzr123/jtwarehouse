package com.wing.socialcontact.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IHzbManagerLogService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ISysconfigService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.HzbManagerLog;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;

/**
 * 互助宝升降级定时器
 * 
 * @ClassName: HzbTask
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年7月27日 上午9:28:38
 */
@Service
public class HzbTask {

	protected final static Logger logger = Logger.getLogger(HzbTask.class);
	@Autowired
	private IMessageInfoService messageInfoService;

	@Autowired
	private ISysconfigService sysconfigService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IWxUserService wxUserService;

	@Autowired
	private IHzbManagerLogService hzbManagerLogService;

	private static boolean isrun = false;

	/**
	 * 每天0:30点执行
	 * 
	 * @Title: messageTask
	 * @Description: TODO
	 * @return: void
	 * @author: zengmin
	 * @date: 2017年7月27日 上午9:30:40
	 */
	//@Scheduled(cron = "0/2 * * * * ?")
	@Scheduled(cron = "0 30 0 * * ?")
	public void hzbTask() {
		if (!isrun) {
			System.out.println("send hzb task ...");
			isrun = true;
			// 获取已开通互助宝的用户列表，不包含已过期的
			List<Map<String, Object>> userList = wxUserService.selectTaskHzbWxUser();
			if (null != userList && !userList.isEmpty()) {
				// executeSj(userList);
				executeJj(userList);
			}
		}
		isrun = false;
	}

	/**
	 * 升级
	 * 
	 * @Title: executeSj
	 * @Description: TODO
	 * @return: void
	 * @author: zengmin
	 * @date: 2017年7月27日 上午9:32:03
	 */
	public void executeSj(List<Map<String, Object>> userList) {
		try {
			for (Map<String, Object> map : userList) {
				int hzbLevel = (int) map.get("hzbLevel");
				String userId = (long) map.get("id") + "";
				// 最高级钻石级
				if (hzbLevel == 3) {
					continue;
				}
				Long ndc = (long) map.get("ndc");
				if (ndc == -1) {
					logger.info("用户开通时间为空，跳过[" + userId + "]");
					continue;
				}
				Date hzbOpenTime = (Date) map.get("hzbOpenTime");
				Date startDate = null, endDate = null;
				if (ndc > 0) {
					Calendar s1 = Calendar.getInstance();
					s1.setTime(hzbOpenTime);
					s1.set(Calendar.YEAR, s1.get(Calendar.YEAR) + ndc.intValue());
					startDate = s1.getTime();
				} else {
					startDate = hzbOpenTime;
				}
				Calendar s2 = Calendar.getInstance();
				s2.setTime(hzbOpenTime);
				s2.set(Calendar.YEAR, s2.get(Calendar.YEAR) + ndc.intValue() + 1);
				s2.set(Calendar.SECOND, s2.get(Calendar.SECOND) - 1);
				endDate = s2.getTime();
				logger.info("ndc:" + ndc);
				logger.info("start:" + DateUtil.date2String(startDate, "yyyy-MM-dd HH:mm:ss"));
				logger.info("end:" + DateUtil.date2String(endDate, "yyyy-MM-dd HH:mm:ss"));
				sj(map, startDate, endDate);
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private void sj(Map<String, Object> userMap, Date startDate, Date endDate) {
		String userId = (long) userMap.get("id") + "";
		double lc = hzbManagerLogService.selectHzbLcjeByUserIdAndTime(userId, startDate, endDate);
		int hzbLevel = (int) userMap.get("hzbLevel");
		double sj_money = 0;
		int level = 0;
		if (hzbLevel == 1) {
			sj_money = 500000;
			if (lc >= sj_money) {
				// 达到白金等级升级条件
				level = 2;
			}
			sj_money = 1000000;
			if (lc >= sj_money) {
				// 达到钻石等级升级条件
				level = 3;
			}
		} else if (hzbLevel == 2) {
			sj_money = 1000000;
			if (lc >= sj_money) {
				// 达到钻石等级升级条件
				level = 3;
			}
		}
		if (level != 0) {
			HzbManagerLog hzbManagerLog = new HzbManagerLog();
			hzbManagerLog.setType(6);// 调整等级
			hzbManagerLog.setManagerTime(new Date());
			hzbManagerLog.setPdType(level + 2);
			if (level == 2) {
				hzbManagerLog.setRemark("达到升级条件自动调整等级至：白金级");
			} else {
				hzbManagerLog.setRemark("达到升级条件自动调整等级至：钻石级");
			}
			hzbManagerLog.setUserId(userId);
			String result = hzbManagerLogService.addHzbManagerLog(hzbManagerLog);
			if (MsgConfig.MSG_KEY_SUCCESS.equals(result)) {
				WxUser user = wxUserService.selectByPrimaryKey(userId);
				user.setHzbLevel(level);
				wxUserService.updateWxUser(user);
				// 推送消息
			}
		}
	}

	/**
	 * 降级
	 * 
	 * @Title: executeJj
	 * @Description: TODO
	 * @return: void
	 * @author: zengmin
	 * @date: 2017年7月27日 上午9:32:08
	 */
	public void executeJj(List<Map<String, Object>> userList) {
		try {
			for (Map<String, Object> map : userList) {
				Long ndc = (long) map.get("ndc");
				String userId = (long) map.get("id") + "";
				Date hzbOpenTime = (Date) map.get("hzbOpenTime");
				Date startDate = null, endDate = null;
				if (ndc == -1) {
					logger.info("用户开通时间为空，跳过自动降级[" + userId + "]");
					continue;
				}
				if (ndc > 0) {
					Calendar s1 = Calendar.getInstance();
					s1.setTime(hzbOpenTime);
					s1.set(Calendar.YEAR, s1.get(Calendar.YEAR) + ndc.intValue());
					startDate = s1.getTime();
				} else {
					startDate = hzbOpenTime;
				}
				Calendar s2 = Calendar.getInstance();
				s2.setTime(hzbOpenTime);
				s2.set(Calendar.YEAR, s2.get(Calendar.YEAR) + ndc.intValue() + 1);
				s2.set(Calendar.SECOND, s2.get(Calendar.SECOND) - 1);
				endDate = s2.getTime();
				logger.info("ndc:" + ndc);
				logger.info("start:" + DateUtil.date2String(startDate, "yyyy-MM-dd HH:mm:ss"));
				logger.info("end:" + DateUtil.date2String(endDate, "yyyy-MM-dd HH:mm:ss"));
				// 还没到一个年度
				Calendar s3 = Calendar.getInstance();
				//由于降级调度推迟一天执行，故比较时间时，当前时间减一天
				s3.set(Calendar.DATE, s3.get(Calendar.DATE) - 1);
				if (!datediff(s3.getTime(), endDate)) {
					continue;
				}
				jj(map, startDate, endDate);
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private void jj(Map<String, Object> userMap, Date startDate, Date endDate) {
		String userId = (long) userMap.get("id") + "";
		int hzbLevel = (int) userMap.get("hzbLevel");
		double lx = hzbManagerLogService.selectHzbLxjeByUserIdAndTime(userId, startDate, endDate);
		long jj_money = 0;
		String moneyStr = "";
		int level = 0;
		if (hzbLevel == 1) {
			jj_money = 200000;
			moneyStr = "20W";
			if (lx < jj_money) {
				// 黄金级未达到20万-过期
				level = -1;
			}
		} else if (hzbLevel == 2) {
			jj_money = 400000;
			moneyStr = "40W";
			if (lx < jj_money) {
				level = 1;
			}
		} else if (hzbLevel == 3) {
			jj_money = 800000;
			moneyStr = "80W";
			if (lx < jj_money) {
				level = 2;
			}
		}
		if (level != 0) {
			HzbManagerLog hzbManagerLog = new HzbManagerLog();
			hzbManagerLog.setType(6);// 调整等级
			hzbManagerLog.setManagerTime(new Date());
			hzbManagerLog.setPdType(level + 2);
			WxUser user = wxUserService.selectByPrimaryKey(userId);
			TjyUser tjyUser = tjyUserService.selectByPrimaryKey(user.getId() + "");
			String templateStr = "";
			String sms_contentStr = "";
			String wx_contentStr = "";
			String name = tjyUser.getNickname();
			if (level == -1) {
				// 状态设为过期
				user.setHzbOpenFlag(3);
				hzbManagerLog.setRemark("年度消费未达标互助宝自动过期");
				templateStr = AldyMessageUtil.MsmTemplateId.HZB_DQ;
				sms_contentStr = "{name:\"" + name + "\",type:\"互助宝\",type2:\"互助宝，\"}";
				wx_contentStr = AldyMessageUtil.userhzbdq(name);
			} else if (level == 1) {
				hzbManagerLog.setRemark("年度消费未达标自动调整等级至:黄金级");
				user.setHzbLevel(1);
				templateStr = AldyMessageUtil.MsmTemplateId.HZB_JJ;
				sms_contentStr = "{name:\"" + name + "\",type:\"互助宝\",money:\"" + moneyStr
						+ "\",type2:\"互助宝\",level:\"黄金级。互助宝\"}";
				wx_contentStr = AldyMessageUtil.userhzbjj(name, moneyStr, "黄金级");
			} else if (level == 2) {
				hzbManagerLog.setRemark("年度消费未达标自动调整等级至:白金级");
				user.setHzbLevel(2);
				templateStr = AldyMessageUtil.MsmTemplateId.HZB_JJ;
				sms_contentStr = "{name:\"" + name + "\",type:\"互助宝\",money:\"" + moneyStr
						+ "元\",type2:\"互助宝\",level:\"白金级。互助宝\"}";
				wx_contentStr = AldyMessageUtil.userhzbjj(name, moneyStr, "白金级");
			}
			hzbManagerLog.setUserId(userId);
			String result = hzbManagerLogService.addHzbManagerLog(hzbManagerLog);
			if (MsgConfig.MSG_KEY_SUCCESS.equals(result)) {
				wxUserService.updateWxUser(user);
				// 推送消息
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(user.getMobile());
				messageInfo.setType(1);// 短信
				messageInfo.setCreateTime(new Date());
				messageInfo.setContent(sms_contentStr);
				messageInfo.setStatus(0);// 未发送
				messageInfo.setTemplateId(templateStr);
				messageInfoService.addMessageInfo(messageInfo);
				// 发送微信--给用户发送微信消息提醒，告知支付成功
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(user.getId() + "");
				messageInfo.setCreateTime(new Date());
				String con = WxMsmUtil.getTextMessageContent(wx_contentStr);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("cztx");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息）
				// */
				messageInfoService.addMessageInfo(messageInfo);
				/**
				 * 发送系统消息
				 */
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(3);// 系统消息
				messageInfo.setToUserId(user.getId() + "");
				messageInfo.setCreateTime(new Date());
				messageInfo.setContent(wx_contentStr);
				messageInfo.setStatus(0);// 不需要发送，未读
				messageInfoService.addMessageInfo(messageInfo);
			}
		}
	}

	/**
	 * 比较是否是同一天
	 * 
	 * @Title: datediff
	 * @Description: TODO
	 * @param d1
	 * @param d2
	 * @return
	 * @return: boolean
	 * @author: zengmin
	 * @date: 2017年7月27日 上午11:30:42
	 */
	private boolean datediff(Date d1, Date d2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s1 = sdf.format(d1);
		String s2 = sdf.format(d2);
		return s1.equals(s2);
	}
}
