package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_MESSAGE_INFO 消息模块表
 * 
 * @author liangwj
 * @date 2017-03-28 15:00:44
 * @version 1.0
 */
@Table(name = "TJY_MESSAGE_INFO")
public class MessageInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	// @Column(name = "id")
	// @GeneratedValue(generator = "UUID")
	private String id;

	/** 消息内容 */
	private String content;

	/** 手机 */
	private String mobile;

	/** 发送时间 */
	private Date sendTime;

	/** 发送状态 （0未发送1已发送；） */
	private Integer status;

	/** 消息类型 1：短信 2：微信 3：系统消息 4：活动消息 */
	private Integer type;

	/** wxopenid */
	private String wxOpenId;

	/** 发送人 */
	private String fromUserId;

	/** 接收人 */
	private String toUserId;

	/** 模板id */
	private String templateId;

	/** 创建时间 */
	private Date createTime;

	/** 创建人ID */
	private String createUserId;

	/** 创建Name */
	private String createUserName;

	/** 修改时间 */
	private Date updateTime;

	/** 修改人ID */
	private String updateUserId;

	/** 修改Name */
	private String updateUserName;

	/** 逻辑删除标识 */
	private Integer deleted;

	/** 服务号模板消息参数---废弃 */
	private String paramFwh;

	/** 微信消息类型（1：文本消息2：图文消息） */
	private Integer wxMsgType;

	public MessageInfo() {
	}

	/**
	 * 获取
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取消息内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置消息内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取发送时间
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * 设置发送时间
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 获取发送状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置发送状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取消息类型 1：短信 2：微信 3：系统 4：活动
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 设置消息类型 1：短信 2：微信 3：系统 4：活动
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 获取wxopenid
	 */
	public String getWxOpenId() {
		return wxOpenId;
	}

	/**
	 * 设置wxopenid
	 */
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	/**
	 * 获取发送人
	 */
	public String getFromUserId() {
		return fromUserId;
	}

	/**
	 * 设置发送人
	 */
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	/**
	 * 获取接收人
	 */
	public String getToUserId() {
		return toUserId;
	}

	/**
	 * 设置接收人
	 */
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	/**
	 * 获取模板id
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * 设置模板id
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * 获取创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取创建人ID
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 * 设置创建人ID
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * 获取创建Name
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置创建Name
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * 获取修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取修改人ID
	 */
	public String getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * 设置修改人ID
	 */
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * 获取修改Name
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}

	/**
	 * 设置修改Name
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	/**
	 * 获取逻辑删除标识
	 */
	public Integer getDeleted() {
		return deleted;
	}

	/**
	 * 设置逻辑删除标识
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	/**
	 * 获取服务号模板消息参数---废弃
	 */
	public String getParamFwh() {
		return paramFwh;
	}

	/**
	 * 设置服务号模板消息参数---废弃
	 */
	public void setParamFwh(String paramFwh) {
		this.paramFwh = paramFwh;
	}

	/** 微信消息类型（1：文本消息2：图文消息） */
	public Integer getWxMsgType() {
		return wxMsgType;
	}

	/** 微信消息类型（1：文本消息2：图文消息） */
	public void setWxMsgType(Integer wxMsgType) {
		this.wxMsgType = wxMsgType;
	}

}
