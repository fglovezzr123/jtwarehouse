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
import org.springframework.util.StringUtils;

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
 * 认证用户到期定时器
 * 
 * @ClassName: HzbTask
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年7月27日 上午9:28:38
 */
@Service
public class ReconTask {

	protected final static Logger logger = Logger.getLogger(ReconTask.class);
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
	 * 每天0:10点执行
	 * 
	 * @Title: reconTask
	 * @Description: TODO
	 * @return: void
	 * @author: gaojun
	 * @date: 2017年10月12日 上午9:30:40
	 */
	//@Scheduled(cron = "0/2 * * * * ?")
	@Scheduled(cron = "0 10 0 * * ?")
	public void reconTask() {
		if (!isrun) {
			System.out.println("send recon task ...");
			isrun = true;
			// 获取有认证期限的认证用户
			List<Map<String, Object>> userList = tjyUserService.selectTaskRecon();
			if (null != userList && !userList.isEmpty()) {
				// executeSj(userList);
				executeRecon(userList);
			}
		}
		isrun = false;
	}

	/**
	 * 
	 * @Title: executeSj
	 * @Description: TODO
	 * @return: void
	 * @author: gaojun
	 * @date:
	 */
	public void executeRecon(List<Map<String, Object>> userList) {
		try {
			for (Map<String, Object> map : userList) {
				String userId = (String)map.get("id") ;
				TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
				
				
				//认证到期时间
				Date lastRegDate = (Date) map.get("last_reg_date");
				
				//当前时间
				Date currentTime = new Date();// 当前时间
				
				Date Date_30 = null, Date_7 = null;
				Calendar s_30 = Calendar.getInstance();
				s_30.setTime(currentTime);
				s_30.add(Calendar.DATE, 30);
				Date_30 = s_30.getTime();
					
					
				Calendar s_7 = Calendar.getInstance();
				s_7.setTime(currentTime);
				s_7.add(Calendar.DATE, 7);
				Date_7 = s_7.getTime();
				
				logger.info("Date_7:" + DateUtil.date2String(Date_7, "yyyy-MM-dd HH:mm:ss"));
				logger.info("Date_30:" + DateUtil.date2String(Date_30, "yyyy-MM-dd HH:mm:ss"));
				
				if (datediff(lastRegDate, Date_30)) {
					// 推送消息
					MessageInfo messageInfo = new MessageInfo();
					// 用户推送消息
					String templateStr = AldyMessageUtil.MsmTemplateId.RECON_DQ_30;
					String sms_contentStr = "{name:\"" + tjyUser.getNickname() + "\"}";
					messageInfo.setId(UUIDGenerator.getUUID());
					messageInfo.setDeleted(0);
					messageInfo.setMobile(tjyUser.getMobile());
					messageInfo.setType(1);// 短信
					messageInfo.setCreateTime(new Date());
					messageInfo.setContent(sms_contentStr);
					messageInfo.setStatus(0);// 未发送
					messageInfo.setTemplateId(templateStr);
					messageInfoService.addMessageInfo(messageInfo);
					// 秘书推送消息
					if(!StringUtils.isEmpty(tjyUser.getKfTelephone())){
						String templateStr2 = AldyMessageUtil.MsmTemplateId.RECON_MS_30;
						String sms_contentStr2 = "{msname:\"" + tjyUser.getKfTelephone() + "\",name:\"" + tjyUser.getNickname() 
								+ "\"}";
						messageInfo.setId(UUIDGenerator.getUUID());
						messageInfo.setDeleted(0);
						messageInfo.setMobile(tjyUser.getKfTelephone());
						messageInfo.setType(1);// 短信
						messageInfo.setCreateTime(new Date());
						messageInfo.setContent(sms_contentStr2);
						messageInfo.setStatus(0);// 未发送
						messageInfo.setTemplateId(templateStr2);
						messageInfoService.addMessageInfo(messageInfo);
					}
				
				}
				if (datediff(lastRegDate, Date_7)) {
					// 推送消息
					MessageInfo messageInfo = new MessageInfo();
					// 用户推送消息
					String templateStr = AldyMessageUtil.MsmTemplateId.RECON_DQ_7;
					String sms_contentStr = "{name:\"" + tjyUser.getNickname() + "\"}";
					messageInfo.setId(UUIDGenerator.getUUID());
					messageInfo.setDeleted(0);
					messageInfo.setMobile(tjyUser.getMobile());
					messageInfo.setType(1);// 短信
					messageInfo.setCreateTime(new Date());
					messageInfo.setContent(sms_contentStr);
					messageInfo.setStatus(0);// 未发送
					messageInfo.setTemplateId(templateStr);
					messageInfoService.addMessageInfo(messageInfo);
					
					// 秘书推送消息
					if(!StringUtils.isEmpty(tjyUser.getKfTelephone())){
						String templateStr2 = AldyMessageUtil.MsmTemplateId.RECON_MS_7;
						String sms_contentStr2 = "{msname:\"" + tjyUser.getKfTelephone() + "\",name:\"" + tjyUser.getNickname() 
								+ "\"}";;
						messageInfo.setId(UUIDGenerator.getUUID());
						messageInfo.setDeleted(0);
						messageInfo.setMobile(tjyUser.getKfTelephone());
						messageInfo.setType(1);// 短信
						messageInfo.setCreateTime(new Date());
						messageInfo.setContent(sms_contentStr2);
						messageInfo.setStatus(0);// 未发送
						messageInfo.setTemplateId(templateStr2);
						messageInfoService.addMessageInfo(messageInfo);
					}
				}
				if (datediff(currentTime, lastRegDate)) {
					// 推送消息 test
					MessageInfo messageInfo = new MessageInfo();
					messageInfo.setId(UUIDGenerator.getUUID());
					messageInfo.setDeleted(0);
					messageInfo.setMobile(tjyUser.getKfTelephone());
					messageInfo.setType(0);
					messageInfo.setCreateTime(new Date());
					messageInfo.setContent("到期记录");
					messageInfo.setStatus(1);
					messageInfoService.addMessageInfo(messageInfo);
					
					tjyUser.setReconStatus(1);
					tjyUser.setIsRealname(0);
					tjyUser.setLastRegDate(null);
					tjyUserService.updateTjyUser(tjyUser);
					tjyUserService.remotingUpdateTjyUser(tjyUser, tjyUser.getMobile());
					
				}
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
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
	private static boolean datediff(Date d1, Date d2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s1 = sdf.format(d1);
		String s2 = sdf.format(d2);
		return s1.equals(s2);
	}
	/**
	 * 比较两个时间大小
	 * 
	 * @Title: datediff
	 * @Description: TODO
	 * @param d1
	 * @param d2 dt1>=dt2 返回true
	 * @return
	 * @return: boolean
	 * @author:
	 * @date: 2017年7月27日 上午11:30:42
	 */
	private static   boolean datediff2(Date dt1, Date dt2) {
		  try {
	            if (dt1.getTime() >= dt2.getTime()) {
	                System.out.println("dt1 在dt2前");
	                return true;
	            } else if (dt1.getTime() < dt2.getTime()) {
	                System.out.println("dt1在dt2后");
	                return false;
	            } else {
	                return false;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
		  return false;
	}
	
	
	public static void main(String[] args) {  
		//当前时间
		Date currentTime = new Date();// 当前时间
		Date Date_30 = null, Date_7 = null;
		
		Calendar s_30 = Calendar.getInstance();
		s_30.setTime(currentTime);
		s_30.add(Calendar.DATE, 30);
		Date_30 = s_30.getTime();
			
			
		Calendar s_7 = Calendar.getInstance();
		s_7.setTime(currentTime);
		s_7.add(Calendar.DATE, 7);
		Date_7 = s_7.getTime();
		
		System.out.println("Date_7:" + DateUtil.date2String(Date_7, "yyyy-MM-dd HH:mm:ss"));
		System.out.println("Date_30:" + DateUtil.date2String(Date_30, "yyyy-MM-dd HH:mm:ss"));
		
		//datediff(currentTime,Date_7); 
   
    }  
}
