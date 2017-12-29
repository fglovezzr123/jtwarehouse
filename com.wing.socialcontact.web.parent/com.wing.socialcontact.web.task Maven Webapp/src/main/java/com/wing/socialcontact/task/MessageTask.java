package com.wing.socialcontact.task;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ISysconfigService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.ImMsgBean;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.Sysconfig;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.DateUtils;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.WeixinUtil;
import com.wing.socialcontact.util.bean.NewsContent;
import com.wing.socialcontact.util.bean.TextContent;
import com.wing.socialcontact.util.bean.WxMsmBean;

/**
 * 消息提醒定时器
 * 
 * @ClassName: MessageTask
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年6月15日 下午4:16:46
 */
@Service
public class MessageTask {

	protected final static Logger logger = Logger.getLogger(MessageTask.class);
	@Autowired
	private IMessageInfoService messageInfoService;

	@Autowired
	private ISysconfigService sysconfigService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IWxUserService wxUserService;

	@Autowired
	private RedisCache redisCache;

	private static boolean isrun = false;

	// 任务一 更新活动状态
	@Scheduled(cron = "0/2 * * * * ?")
	public void messageTask() {
		if (!isrun) {
			System.out.println("send message task ...");
			isrun = true;
			execute();
			executeVo();
		}
		isrun = false;
	}

	/**
	 * 入口方法。
	 */
	public void execute() {
		try {
			while (true) {
				String task = redisCache.getFromQueue("sms_queue_web_online");
				if (StringUtils.isEmpty(task)) {
					return;
				}
				MessageInfo messageInfo = messageInfoService.selectByPrimaryKey(task);
				System.out.println("messageInfo+++++++++++++++++++" + JSONObject.toJSONString(messageInfo));
				executeSingleTask(messageInfo);
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * 发送单条信息
	 * 
	 * 取出任务并执行，如果失败，放回任务列表。
	 * 
	 * @param taskQueue
	 * @param task
	 */
	private void executeSingleTask(MessageInfo messageVo) {
		try {
			// do the job

			System.out.println("-------sendMessage-------");
			System.out.println("+++++++++++++++++executeSingleTask" + JSONObject.toJSONString(messageVo));

			if (messageVo.getStatus() != 0) {
				logger.info("-------消息状态不为0，不发送-------------");
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
					logger.info("用户编号为空，不发送[" + messageVo.getId() + "]");
				} else {
					TjyUser tjyUser = tjyUserService.selectByPrimaryKey(messageVo.getToUserId());
					if (null == tjyUser) {
						logger.info("获取用户失败，不发送[" + messageVo.getId() + "]");
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
								logger.info("发送服务号消息");
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
								logger.info("发送企业号消息");
								textContent = JSON.toJavaObject(JSON.parseObject(c), TextContent.class);
								xmb = new WxMsmBean();
								xmb.setMsgtype("text");
								xmb.setTouser(StringUtils.isEmpty(wxUser.getWxUserId()) ? wxUser.getId() + ""
										: wxUser.getWxUserId());
								xmb.setText(textContent);
								xmb.setAgentid(Integer.valueOf(sysconfig.getWeixinAppno()));
								result = JSON.toJSONString(xmb);
								sendSucc = WeixinUtil.sendMsg(sysconfig.getWeixinAppidQyh(),
										sysconfig.getWeixinAppsecretQyh(), result);
								break;
							default:
								sendSucc = false;
								logger.info("用户状态无效，不发送[" + messageVo.getId() + "]");
							}
						} else if (messageVo.getWxMsgType() == 2) {// 图文消息
							switch (status) {
							case 1:// 服务号
									// 组装消息
								newsContent = JSON.toJavaObject(JSON.parseObject(c), NewsContent.class);
								xmb = new WxMsmBean();
								xmb.setMsgtype("news");
								xmb.setTouser(wxUser.getQqOpenid());
								xmb.setNews(newsContent);
								result = JSON.toJSONString(xmb);
								sendSucc = WeixinUtil.sendMsgFwh(sysconfig.getWeixinAppid(),
										sysconfig.getWeixinAppsecret(), result);
								break;
							case 2:// 企业号
									// 组装消息
								newsContent = JSON.toJavaObject(JSON.parseObject(c), NewsContent.class);
								xmb = new WxMsmBean();
								xmb.setMsgtype("news");
								xmb.setTouser(StringUtils.isEmpty(wxUser.getWxUserId()) ? wxUser.getId() + ""
										: wxUser.getWxUserId());
								xmb.setNews(newsContent);
								xmb.setAgentid(Integer.valueOf(sysconfig.getWeixinAppno()));
								result = JSON.toJSONString(xmb);
								sendSucc = WeixinUtil.sendMsg(sysconfig.getWeixinAppidQyh(),
										sysconfig.getWeixinAppsecretQyh(), result);
								break;
							default:
								sendSucc = false;
								logger.info("用户状态无效，不发送[" + messageVo.getId() + "]");
							}
						} else {
							sendSucc = false;
							logger.info("微信消息类型[" + messageVo.getWxMsgType() + "]无效，不发送");
						}
					}
				}
			}
			messageVo.setSendTime(new Date());
			if (sendSucc) {
				messageVo.setStatus(1);
			} else {
				messageVo.setStatus(2);
			}
			messageInfoService.updateMessageInfo(messageVo);
		} catch (Throwable e) {
			logger.error(String.format("任务%s执行失败：%s，重新放回队列", messageVo.getId(), e.getMessage()));
			e.printStackTrace();
		}
	}

	/**
	 * 入口方法。
	 */
	public void executeVo() {
		try {
			while (true) {
				String imMsgBeanStr = redisCache.getFromQueue("sms_queue_web_online_im");
				if (StringUtils.isEmpty(imMsgBeanStr)) {
					return;
				}
				JSONObject jo = JSONObject.parseObject(imMsgBeanStr);
				ImMsgBean imMsgBean = JSON.toJavaObject(jo, ImMsgBean.class);
				executeSingleTaskVo(imMsgBean);
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}

	}
	// {"content":"离线5","contentType":"text","formGroupId":"",
	// "formUserId":"8499","msgType":"chat","sendTime":"1501742015996","toUser
	// Id":"8464","wxMsgType":"1"}

	/**
	 * 发送单条信息
	 * 
	 * 取出任务并执行，如果失败，放回任务列表。
	 * 
	 * @param taskQueue
	 * @param task
	 */
	private void executeSingleTaskVo(ImMsgBean messageVo) {
		try {
			System.out.println("-------sendMessageIm-------");
			System.out.println("+++++++++++++++++executeSingleTaskVo" + JSONObject.toJSONString(messageVo));
			boolean sendSucc = false;
			Sysconfig sysconfig = sysconfigService.getSysconfig();
			if (StringUtils.isEmpty(messageVo.getToUserId())) {
				sendSucc = false;
				System.out.println("用户编号为空，不发送");
			} else {
				WxUser wxUser = wxUserService.selectByPrimaryKey(messageVo.getToUserId());
				if (null != wxUser) {
					TjyUser tjyUser = tjyUserService.selectByPrimaryKey(messageVo.getToUserId());
					TjyUser fromtjyUser = tjyUserService.selectByPrimaryKey(messageVo.getFormUserId());
					int status = null == tjyUser.getStatus() ? 0 : tjyUser.getStatus();
					String c = messageVo.getContent();
					WxMsmBean xmb = null;
					TextContent textContent = null;
					NewsContent newsContent = null;
					String result = "";
					// 文本消息
					if ("1".equals(messageVo.getWxMsgType())) {
						/*
						 * if ("image".equals(messageVo.getContentType()) || (c
						 * != null && c.indexOf("oss-cn-beijing.aliyuncs.com")
						 * != -1)) { c = "您的好友" + fromtjyUser.getNickname() +
						 * "给您发来了一张图片，请查看。"; } else if
						 * ("audio".equals(messageVo.getContentType())) { c =
						 * "您的好友" + fromtjyUser.getNickname() +
						 * "给您发来了一段语音，请查看。"; } else { if (c.indexOf("&lt;div")
						 * != -1) { c = c.substring(0, c.indexOf("&lt;div")); }
						 * c = fromtjyUser.getNickname() + ":" + c; }
						 */

						System.out.println("C++++++++++++++++++++++++++++++++++++" + c.toString());
						switch (status) {
						case 1:// 服务号
								// textContent =
								// JSON.toJavaObject(JSON.parseObject(c),
								// TextContent.class);
							textContent = new TextContent();
							textContent.setContent(c);
							xmb = new WxMsmBean();
							xmb.setMsgtype("text");
							xmb.setTouser(wxUser.getQqOpenid());
							xmb.setText(textContent);
							result = JSON.toJSONString(xmb);
							sendSucc = WeixinUtil.sendKfMsgFwh(sysconfig.getWeixinAppid(),
									sysconfig.getWeixinAppsecret(), result);
							break;
						case 2:// 企业号
								// textContent =
								// JSON.toJavaObject(JSON.parseObject(c),
								// TextContent.class);
							textContent = new TextContent();
							textContent.setContent(c);
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
							sendSucc = WeixinUtil.sendMsgFwh(sysconfig.getWeixinAppid(), sysconfig.getWeixinAppsecret(),
									result);
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
			if (sendSucc) {
				System.out.println("im消息发送成功");
			} else {
				System.out.println("im消息发送失败");
			}
		} catch (Throwable e) {
			logger.error(String.format("任务%s执行失败：%s，重新放回队列", messageVo.getToUserId(), e.getMessage()));
			e.printStackTrace();
		}
	}
}
