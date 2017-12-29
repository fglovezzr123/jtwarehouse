package com.wing.socialcontact.util.redis;

import java.io.Serializable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.wing.socialcontact.service.wx.api.ISysconfigService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.ImMsgBean;
import com.wing.socialcontact.service.wx.bean.Sysconfig;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.WeixinUtil;
import com.wing.socialcontact.util.bean.NewsContent;
import com.wing.socialcontact.util.bean.TextContent;
import com.wing.socialcontact.util.bean.WxMsmBean;

@Component("smsMessageDelegateListenerIm")
public class SmsMessageDelegateListenerIm {

	@Resource
	private ISysconfigService sysconfigService;

	@Resource
	private ITjyUserService tjyUserService;

	@Resource
	private IWxUserService wxUserService;

	// 监听Redis消息
	public void handleMessage(Serializable message) {
		try {

			if (message instanceof ImMsgBean) {
				ImMsgBean messageVo = (ImMsgBean) message;
				System.out.println("-------sendMessageIm-------");
				boolean sendSucc = false;
				Sysconfig sysconfig = sysconfigService.getSysconfig();
				if (StringUtils.isEmpty(messageVo.getToUserId())) {
					sendSucc = false;
					System.out.println("用户编号为空，不发送");
				} else {
					WxUser wxUser = wxUserService.selectByPrimaryKey(messageVo.getToUserId());
					if (null != wxUser) {
						TjyUser tjyUser = tjyUserService.selectByPrimaryKey(messageVo.getToUserId());
						int status = null == tjyUser.getStatus() ? 0 : tjyUser.getStatus();
						String c = messageVo.getContent();
						if(c==null)c="";
						WxMsmBean xmb = null;
						TextContent textContent = null;
						NewsContent newsContent = null;
						String result = "";
						// 文本消息
						if ("1".equals(messageVo.getWxMsgType())) {
							TjyUser fromUser = tjyUserService.selectByPrimaryKey(messageVo.getFormUserId());
							if("image".equals(messageVo.getContentType()) || (c!=null && c.indexOf("oss-cn-beijing.aliyuncs.com")!=-1)){
								c="您的好友"+fromUser.getNickname()+"给您发来了一张图片，请查看。";
							}else if("audio".equals(messageVo.getContentType())){
								c="您的好友"+fromUser.getNickname()+"给您发来了一段语音，请查看。";
							}else{
								if(c.indexOf("&lt;div")!=-1){
									c=c.substring(0,c.indexOf("&lt;div"));
								}
								c=fromUser.getNickname()+"："+c;
							}
							switch (status) {
							case 1:// 服务号
								textContent = JSON.toJavaObject(JSON.parseObject(c), TextContent.class);
								xmb = new WxMsmBean();
								xmb.setMsgtype("text");
								xmb.setTouser(wxUser.getQqOpenid());
								xmb.setText(textContent);
								result = JSON.toJSONString(xmb);
								sendSucc = WeixinUtil.sendKfMsgFwh(sysconfig.getWeixinAppid(),
										sysconfig.getWeixinAppsecret(), result);
								break;
							case 2:// 企业号
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
								System.out.println("用户状态无效，不发送");
							}
						} else if ("2".equals(messageVo.getWxMsgType())) {
							// 图文消息
							switch (status) {
							case 1:// 服务号
								System.out.println("服务号消息暂时不发送");
								newsContent = JSON.toJavaObject(JSON.parseObject(c), NewsContent.class);
								xmb = new WxMsmBean();
								xmb.setMsgtype("news");
								xmb.setTouser(wxUser.getQqOpenid());
								xmb.setNews(newsContent);
								result = JSON.toJSONString(xmb);
								// sendSucc =
								// WeixinUtil.sendMsgFwh(sysconfig.getWeixinAppid(),
								// sysconfig.getWeixinAppsecret(), result);
								break;
							case 2:// 企业号
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
								System.out.println("用户状态无效，不发送");
							}
						} else {
							sendSucc = false;
							System.out.println("微信消息类型[" + messageVo.getWxMsgType() + "]无效，不发送");
						}
					} else {
						System.out.println("获取用户失败，不发送");
						sendSucc = false;
					}
				}
				final boolean result = sendSucc;
				Executor executor = Executors.newSingleThreadExecutor();
				executor.execute(new Runnable() {
					public void run() {
						if (result) {
							System.out.println("im消息发送成功");
						} else {
							System.out.println("im消息发送失败");
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("消息发送出错了");
		}
	}
}