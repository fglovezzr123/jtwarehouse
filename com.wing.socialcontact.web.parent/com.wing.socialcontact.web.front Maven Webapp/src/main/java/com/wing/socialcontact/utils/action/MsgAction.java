package com.wing.socialcontact.utils.action;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.service.im.api.IImFriendService;
import com.wing.socialcontact.service.im.api.IImGroupusersService;
import com.wing.socialcontact.service.im.bean.ImFriend;
import com.wing.socialcontact.service.im.bean.ImGroupusers;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.ImMsgBean;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.HttpClientUtil;
import com.wing.socialcontact.util.MD5Util;

/**
 * 消息发送控制器
 * 
 * @ClassName: MsgAction
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年5月16日 上午9:37:33
 */
@Controller
@RequestMapping("/m/message")
public class MsgAction extends BaseAction {

	@Resource
	private IMessageInfoService messageInfoService;

	@Resource
	private IImFriendService imFriendService;

	@Resource
	private IImGroupusersService imGroupusersService;
	@Resource
	private ITjyUserService tjyUserService;

	/**
	 * 发送消息
	 * 
	 * @Title: send
	 * @Description: TODO
	 * @param messageInfo
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月16日 上午10:44:02
	 */
	@RequestMapping("send")
	public @ResponseBody Map send(ImMsgBean imMsgBean, String sinstr, String timestamp) {
		System.out.println("=================================开始添加离线推送========================================");
		if (StringUtils.isEmpty(imMsgBean.getMsgType())) {
			return super.getAjaxResult("401", "参数错误[消息类型]", null);
		}
		if (StringUtils.isEmpty(imMsgBean.getToUserId())) {
			return super.getAjaxResult("401", "参数错误[接收人]", null);
		}
		if (StringUtils.isEmpty(imMsgBean.getFormUserId())) {
			return super.getAjaxResult("401", "参数错误[消息内容]", null);
		}
		if (StringUtils.isEmpty(imMsgBean.getContentType())) {
			return super.getAjaxResult("401", "参数错误[消息内容类型]", null);
		}
		if (StringUtils.isEmpty(imMsgBean.getContent())) {
			return super.getAjaxResult("401", "参数错误[消息内容]", null);
		}
		long now = new Date().getTime();
		long ts = Long.parseLong(timestamp);
		// 签名时间戳差1分钟则校验失败
		if (Math.abs(now - ts) > 60000) {
			return super.getAjaxResult("999", "签名失败！", null);
		}
		// System.out.println(imMsgBean.toString());
		// System.out.println("+++++++++++sinstr:"+sinstr);
		// System.out.println("+++++++++++timestamp:"+timestamp);
		String sstr = timestamp + imMsgBean.getFormUserId() + imMsgBean.getToUserId() + Constants.PRIVATE_KEY;
		// System.out.println("+++++++++++sstr:"+sstr);
		sstr = MD5Util.to_MD5(sstr);
		if (sstr.equals(sinstr)) {
			try {
			    messageInfoService.sendImAppMessage(imMsgBean);
				// 如果接收人屏蔽了发送人的消息，则不进行消息推送
				if ("1".equals(imMsgBean.getMsgType())) {// 群消息
					ImGroupusers imGroupusers = imGroupusersService.findByUserAndGroupId(imMsgBean.getToUserId(),
							imMsgBean.getFormGroupId());
					if (null == imGroupusers) {
						return super.getAjaxResult("401", "参数错误[接收人不在群内]", null);
					}
					// 免打扰
					if (imGroupusers.getMsgDisturb()!=null && imGroupusers.getMsgDisturb() == 1) {
						return super.getSuccessAjaxResult("接收人设置了群免打扰");
					}
				} else {// 私聊
					ImFriend imFriend = imFriendService.findByUserAndFriend(imMsgBean.getToUserId(),
							imMsgBean.getFormUserId());
					if (null == imFriend) {
						return super.getAjaxResult("401", "参数错误[未找到好友]", null);
					}
					// 免打扰
					if (imFriend.getMsgDisturb()!=null && imFriend.getMsgDisturb() == 1) {
						return super.getSuccessAjaxResult("接收人设置了免打扰");
					}
				}
				imMsgBean.setWxMsgType("1");
				
				String c = imMsgBean.getContent();
				TjyUser fromUser = tjyUserService.selectByPrimaryKey(imMsgBean.getFormUserId());
				if("image".equals(imMsgBean.getContentType())){
					c="您的好友"+fromUser.getNickname()+"给您发来了一张图片，请查看。";
				}else if("audio".equals(imMsgBean.getContentType())){
					c="您的好友"+fromUser.getNickname()+"给您发来了一段语音，请查看。";
				}else{
					if(c.indexOf("&lt;div")!=-1){
						c=c.substring(0,c.indexOf("&lt;div"));
					}
					if(c.indexOf("&lt;img")!=-1){
						c=c.substring(0,c.indexOf("&lt;img"))+"[表情]";
					}
					c=fromUser.getNickname()+"："+c;
				}
				imMsgBean.setContent(c);
				System.out.println(JSONObject.toJSONString(imMsgBean));
				messageInfoService.sendImMessage(imMsgBean);
			} catch (IOException e) {
				e.printStackTrace();
				return super.getAjaxResult("999", "推送失败！", null);
			}

			System.out.println("=================================结束添加离线推送========================================");
			return super.getSuccessAjaxResult();
		} else {
			return super.getAjaxResult("999", "推送失败！", null);
		}
	}

	public static void main(String[] args) {
		String fromuserid = "8442";
		String touserid = "8464";
		// String touserid="6273";
		// String fromuserid="6294";
		String formGroupId = "";
		String msgType = "chat";
		// msgType="groupchat";
		long timestamp = new Date().getTime();
		String sinstr = MD5Util.to_MD5(timestamp + fromuserid + touserid + Constants.PRIVATE_KEY);
		String reqURL = "http://localhost/wxfront/m/message/send.do?" + "sinstr=" + sinstr + "&timestamp=" + timestamp
				+ "&toUserId=" + touserid + "&formUserId=" + fromuserid + "&" + "formGroupId=" + formGroupId
				+ "&msgType=" + msgType + "&contentType=msg&content=sssfsdfsdf&sendTime=" + timestamp;
		String rs = HttpClientUtil.sendGetRequest(reqURL, null);
		System.out.println(reqURL);
		System.out.println(rs);
	}
}