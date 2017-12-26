package com.wing.socialcontact.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IMeetingService;
import com.wing.socialcontact.service.wx.api.IMeetingSignupService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.Meeting;
import com.wing.socialcontact.service.wx.bean.MeetingSignup;
import com.wing.socialcontact.service.wx.bean.MeetingSignupRemind;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.ConfigUtil;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.DateUtils;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;

/**
 * 会议相关定时任务
 * 
 * @author liangwj
 */
@Service
public class MeetingTask {
	private static final Logger logger = LoggerFactory.getLogger(MeetingTask.class);
	@Autowired
	private IMeetingSignupService meetingSignupService;
	@Autowired
	private IMessageInfoService messageInfoService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IMeetingService meetingService;

	/**
	 * 会议预报名发送给微信消息提醒任务【只发活动消息】
	 */
	@Scheduled(cron = "0 0/1 8-23 * * ?")
	public void sendWxMessageForMeetingSignupRemindJob() {
		boolean isTrue = true;
		logger.debug("会议预报名发送给微信消息提醒任务");
		while (isTrue) {
			DataGrid grid = null;
			try {
				grid = meetingSignupService.selectAllMeetingSignupRemind(new PageParam(1, 100));
			} catch (Throwable e) {
				logger.error("", e);
				isTrue = false;
				break;
			}

			@SuppressWarnings("unchecked")
			List<MeetingSignupRemind> list = grid.getRows();
			if (grid == null || list == null || list.size() == 0) {
				isTrue = false;
				break;
			}

			for (MeetingSignupRemind msr : list) {
				try {
					TjyUser tjyUser = tjyUserService.selectById(msr.getUserId());
					//【AldyMessageUtil.SMSPRE】尊敬的***，您所收藏的****会议于2016-03-01开始报名。立即前往
					String message =AldyMessageUtil.meetingysignupremind(tjyUser.getNickname(),msr.getTitles(),DateUtils.dateToString(msr.getStartSignupTime(), "yyyy-MM-dd"),msr.getFkId());
					doSendActiveMsg(msr.getUserId(), message);

					msr.setIsRemind(1);
					msr.setTitles(message);
					msr.setRemindTime(DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
					meetingSignupService.updateMeetingSignupRemind(msr);
				} catch (Exception e) {
					logger.error("会议预报名发送给微信消息提醒任务",e);
					msr.setIsRemind(-1);
					msr.setRemindTime(DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
					meetingSignupService.updateMeetingSignupRemind(msr);
				}
			}
		}
	}
	/**
	 * 会议开始提醒【活动消息】+【短信】
	 */
	@Scheduled(cron = "0 0/1 8-23 * * ?")
	public void sendMsgForMeetingStartJob(){
		try {
			List<MeetingSignup> list = meetingSignupService.selectUnRemind();
			for (MeetingSignup ms : list) {
				try {
					TjyUser tjyUser = tjyUserService.selectById(ms.getUserId());
					String message =AldyMessageUtil.meetingystartremind(tjyUser.getNickname(), DateUtils.dateToString(ms.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ms.getTitles(), ms.getMeetingId());
//					String message ="【"+AldyMessageUtil.SMSPRE+"】尊敬的"+tjyUser.getNickname()+"，您所报名的"+ms.getTitles()
//							+"会议于"+DateUtils.dateToString(ms.getStartTime(), "yyyy-MM-dd HH:mm:ss")+"正式开始，"
//							+ "请您提前持入场凭证参加会议，并将手机调至震动模式。<a href='"+ConfigUtil.DOMAIN
//							+"/wxfront/m/meeting/detail/index.do?id="+ms.getMeetingId()+"'>立即前往</a>";
					doSendActiveMsg(ms.getUserId(), message);

					Map<String,String> params = Maps.newHashMap();
					params.put("name", tjyUser.getNickname());
					params.put("time", DateUtils.dateToString(ms.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
					params.put("hyname", ms.getTitles());
					
					if(ms.getMobile()==null){
						logger.error("手机号为空");
					}else if(ms.getMobile().length()!=11){
						logger.error("手机号错误");
					}else{
						doSendSmsMsg(ms.getMobile(),AldyMessageUtil.MsmTemplateId.MEETING_START,
								JSON.toJSONString(params));
					}
					
					ms.setId(ms.getId());
					ms.setRemindTime(new Date());
					meetingSignupService.updateMeetingSignup(ms);
				} catch (Exception e) {
					logger.error("会议开始发送给微信或短信消息提醒任务",e);
				}
			}
			
		} catch (Exception e) {
			logger.error("会议开始微信和短信提醒失败", e);
		}
	}
	/**
	 * 活动消息发送
	 */
	public void doSendActiveMsg(String userId,String message){
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setId(UUIDGenerator.getUUID());
		messageInfo.setDeleted(0);
		messageInfo.setType(4);
		messageInfo.setToUserId(userId);
		messageInfo.setCreateTime(new Date());
		messageInfo.setContent(message);
		messageInfo.setTemplateId("ACTIVITY_BM_TX");
		messageInfo.setStatus(0);
		messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息） */
		messageInfoService.addMessageInfo(messageInfo);
	}
	/**
	 * 微信发送
	 */
	public void doSendWxMsg(String userId,String message){
		String con = WxMsmUtil.getTextMessageContent(message);
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setId(UUIDGenerator.getUUID());
		messageInfo.setDeleted(0);
		messageInfo.setType(2);
		messageInfo.setToUserId(userId);
		messageInfo.setCreateTime(new Date());
		messageInfo.setContent(con);
		messageInfo.setTemplateId("ACTIVITY_BM_TX");
		messageInfo.setStatus(0);
		messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息） */
		messageInfoService.addMessageInfo(messageInfo);
	}
	/**
	 * 短信发送
	 * @param mobile 手机号
	 * @param templateId 模板Id
	 * @param jsonContent 内容json字符串
	 */
	public void doSendSmsMsg(String mobile,String templateId,String jsonContent){
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setId(UUIDGenerator.getUUID());
		messageInfo.setDeleted(0);
		messageInfo.setMobile(mobile);
		messageInfo.setType(1);// 短信
		messageInfo.setCreateTime(new Date());
		messageInfo.setContent(jsonContent);
		messageInfo.setStatus(0);// 未发送
		messageInfo.setTemplateId(templateId);
		messageInfoService.addMessageInfo(messageInfo);
	}
}
