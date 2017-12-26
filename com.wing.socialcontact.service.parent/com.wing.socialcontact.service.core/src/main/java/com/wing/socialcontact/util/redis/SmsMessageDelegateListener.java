package com.wing.socialcontact.util.redis;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ISysconfigService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.Sysconfig;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.WeixinUtil;
import com.wing.socialcontact.util.bean.NewsContent;
import com.wing.socialcontact.util.bean.TextContent;
import com.wing.socialcontact.util.bean.WxMsmBean;

@Component("smsMessageDelegateListener")
public class SmsMessageDelegateListener {

	@Resource
	private IMessageInfoService messageInfoService;

	@Resource
	private ISysconfigService sysconfigService;

	@Resource
	private ITjyUserService tjyUserService;

	@Resource
	private IWxUserService wxUserService;

	// 监听Redis消息
	public void handleMessage(Serializable message) {
		if (message instanceof MessageInfo) {
			try {
				MessageInfo messageVo = (MessageInfo) message;
				System.out.println("-------sendMessage--send_msg_id:" + messageVo.getId() + "-----");
				/**
				 * 如果消息状态不为0，则不发送
				 */
				if (messageVo.getStatus() != 0) {
					System.out.println("-------消息状态不为0，不发送-------------");
					return;
				}
				boolean sendSucc = false;
				if (messageVo.getType() == 1) {
					// 发送短信
					sendSucc = AldyMessageUtil.sendSms(messageVo.getTemplateId(), messageVo.getMobile(),
							messageVo.getContent());
				} else if (messageVo.getType() == 2) {// 发送微信
					Sysconfig sysconfig = sysconfigService.getSysconfig();
					if (StringUtils.isEmpty(messageVo.getToUserId())) {
						sendSucc = false;
						System.out.println("用户编号为空，不发送[" + messageVo.getId() + "]");
					} else {
						TjyUser tjyUser = tjyUserService.selectByPrimaryKey(messageVo.getToUserId());
						if (null == tjyUser) {
							System.out.println("获取用户失败，不发送[" + messageVo.getId() + "]");
							sendSucc = false;
						} else {
							WxUser wxUser = wxUserService.selectByPrimaryKey(messageVo.getToUserId());
							int status = null == tjyUser.getStatus() ? 0 : tjyUser.getStatus();
							String c = messageVo.getContent();
							WxMsmBean xmb = null;
							TextContent textContent = null;
							NewsContent newsContent = null;
							String result = "";
							// 文本消息
							if (messageVo.getWxMsgType() == 1) {
								switch (status) {
								case 1:// 服务号
										// 组装消息
									textContent = JSON.toJavaObject(JSON.parseObject(c), TextContent.class);
									xmb = new WxMsmBean();
									xmb.setMsgtype("text");
									xmb.setTouser(tjyUser.getOpenId());
									xmb.setText(textContent);
									result = JSON.toJSONString(xmb);
									sendSucc = WeixinUtil.sendKfMsgFwh(sysconfig.getWeixinAppid(),
											sysconfig.getWeixinAppsecret(), result);
									break;
								case 2:// 企业号
										// 组装消息
									textContent = JSON.toJavaObject(JSON.parseObject(c), TextContent.class);
									xmb = new WxMsmBean();
									xmb.setMsgtype("text");
									xmb.setTouser(wxUser.getWxUserId());
									xmb.setText(textContent);
									xmb.setAgentid(Integer.valueOf(sysconfig.getWeixinAppno()));
									result = JSON.toJSONString(xmb);
									sendSucc = WeixinUtil.sendMsg(sysconfig.getWeixinAppidQyh(),
											sysconfig.getWeixinAppsecretQyh(), result);
									break;
								default:
									sendSucc = false;
									System.out.println("用户状态无效，不发送[" + messageVo.getId() + "]");
								}
							} else if (messageVo.getWxMsgType() == 2) {// 图文消息
								switch (status) {
								case 1:// 服务号
									System.out.println("服务号消息暂时不发送");
									// 组装消息
									newsContent = JSON.toJavaObject(JSON.parseObject(c), NewsContent.class);
									xmb = new WxMsmBean();
									xmb.setMsgtype("news");
									xmb.setTouser(wxUser.getQqOpenid());
									xmb.setNews(newsContent);
									result = JSON.toJSONString(xmb);
									// sendSucc =
									// WeixinUtil.sendMsgFwh(sysconfig.getWeixinAppid(),
									// sysconfig.getWeixinAppsecret(),result);
									break;
								case 2:// 企业号
										// 组装消息
									newsContent = JSON.toJavaObject(JSON.parseObject(c), NewsContent.class);
									xmb = new WxMsmBean();
									xmb.setMsgtype("news");
									xmb.setTouser(wxUser.getWxUserId());
									xmb.setNews(newsContent);
									xmb.setAgentid(Integer.valueOf(sysconfig.getWeixinAppno()));
									result = JSON.toJSONString(xmb);
									sendSucc = WeixinUtil.sendMsg(sysconfig.getWeixinAppidQyh(),
											sysconfig.getWeixinAppsecretQyh(), result);
									break;
								default:
									sendSucc = false;
									System.out.println("用户状态无效，不发送[" + messageVo.getId() + "]");
								}
							} else {
								sendSucc = false;
								System.out.println("微信消息类型[" + messageVo.getWxMsgType() + "]无效，不发送");
							}
						}
					}
				}
				final boolean result = sendSucc;
				// 异步更新短信表状态为发送成功
				final String smsId = messageVo.getId();
				Executor executor = Executors.newSingleThreadExecutor();
				executor.execute(new Runnable() {
					public void run() {
						MessageInfo mi = new MessageInfo();
						mi.setId(smsId);
						mi.setSendTime(new Date());
						if (result) {
							mi.setStatus(1);
						} else {
							mi.setStatus(2);
						}
						messageInfoService.updateMessageInfo(mi);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("消息发送出错了");
			}
		}
	}
}