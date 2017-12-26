package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;

/**
 * im消息实体类
 * 
 * @ClassName: ImMsgBean
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年5月16日 下午2:19:49
 */
public class ImMsgBean implements Serializable {
	/**
	 * 接收人
	 */
	private String toUserId;
	/**
	 * 发送人
	 */
	private String formUserId;
	/**
	 * 群id
	 */
	private String formGroupId;
	/**
	 * 消息类型1：群聊2：私聊
	 */
	private String msgType;
	/**
	 * 消息内容类型text：文本 imgage：图片 audio：语音
	 */
	private String contentType;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 发送时间
	 */
	private String sendTime;
	
	/**
	 * 微信消息类型(1:文本2:图文)
	 */
	private String wxMsgType;
	
	public String getFormGroupId() {
		return formGroupId;
	}

	public void setFormGroupId(String formGroupId) {
		this.formGroupId = formGroupId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getFormUserId() {
		return formUserId;
	}

	public void setFormUserId(String formUserId) {
		this.formUserId = formUserId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWxMsgType() {
		return wxMsgType;
	}

	public void setWxMsgType(String wxMsgType) {
		this.wxMsgType = wxMsgType;
	}
}
