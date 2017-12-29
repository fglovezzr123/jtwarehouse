package com.wing.socialcontact.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.api.ILibraryLiveService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IliveSignupService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyLibraryLive;
import com.wing.socialcontact.service.wx.bean.TjyLiveSignup;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.ConfigUtil;
import com.wing.socialcontact.util.DateUtils;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;
@Service
public class LibraryLiveTask {

	@Autowired
	private ILibraryLiveService libraryLiveService;
	@Autowired
	private IMessageInfoService messageInfoService;
	@Autowired
	private IliveSignupService liveSignupService;
	@Autowired
	private ITjyUserService tjyUserService;
	
	@Scheduled(cron = "0 0/1 * * * ?")
	public void updateLibraryLiveStatus() {
		  System.out.println("----------------------直播状态修改任务开始-----------------------");
		/**
		 * 当前时间大于开始时间 ，状态未未开始
		 */
		List<TjyLibraryLive> wkslist =  libraryLiveService.selectWksLives();
		for(TjyLibraryLive l :wkslist){
			libraryLiveService.updatestatusbyid(2,l.getId());
		}
		/**
		 * 当前时间大于结束时间  状态为进行中
		 */
		List<TjyLibraryLive> jxzlist =  libraryLiveService.selectJxzLives();
		for(TjyLibraryLive l :jxzlist){
			libraryLiveService.updatestatusbyid(3,l.getId());
		}
		
		 System.out.println("----------------------直播状态修改任务结束-----------------------");
	}
	
	/**
	 * 直播开始前一天提醒   (条件 ：   未提醒，前一天)
	 */
	@Scheduled(cron = "0 0/1 8-23 * * ?")
	public void liveremind(){
		try{
			System.out.println("直播提醒任务开始--------------------------------------------------------------------------------------");
			List<TjyLiveSignup> list = liveSignupService.getunremindsignups();
			
			for(TjyLiveSignup s:list){
				TjyLibraryLive live = libraryLiveService.getLibraryLive(s.getLiveid());
				String userid=s.getUserid();
				TjyUser tjyUser = tjyUserService.selectById(userid);
				//短信
				String content1="{name:\"" + tjyUser.getNickname() + "\",livename:\"" + live.getTitle() + "\",starttime:\"" + DateUtils.datetimeToString(live.getStartTime()) + "\"}";
				sendmessage(tjyUser.getId(),content1,1,AldyMessageUtil.MsmTemplateId.LIVE_START,tjyUser.getMobile());
				String content=AldyMessageUtil.liveStartRemind(tjyUser.getNickname(), DateUtils.datetimeToString(live.getStartTime()), live.getTitle(), s.getLiveid());
				sendmessage(userid,content,2,"LIVE_REMIND",null);
				sendmessage(userid,content,4,"LIVE_REMIND",null);
				
				s.setIsremind(1);
				liveSignupService.update(s);
			}
			System.out.println("直播提醒任务结束--------------------------------------------------------------------------------------");
		}catch(Exception e){
			
		}
	}
	/**
	 * 发消息方法  1 短信  2 微信    4活动消息
	 */
	public void sendmessage(String touser, String content, int type, String templateid,String mobile) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setId(UUIDGenerator.getUUID());
		messageInfo.setDeleted(0);
		messageInfo.setType(type);// 微信
		messageInfo.setToUserId(touser);
		messageInfo.setCreateTime(new Date());
		// 组装内容
		if(type==2){
			String con = WxMsmUtil.getTextMessageContent(content);
			messageInfo.setContent(con);
		}else{
			messageInfo.setContent(content);
		}
		messageInfo.setTemplateId(templateid);
		messageInfo.setMobile(mobile);
		messageInfo.setStatus(0);// 未发送
		messageInfo.setWxMsgType(1);///** 微信消息类型（1：文本消息2：图文消息） */
		messageInfoService.addMessageInfo(messageInfo);
	}
}
