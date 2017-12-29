package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_MESSAGE_BULK 消息模块表
 * 
 * @author gaojun
 * @date 2017-07-06 16:30:22
 * @version 1.0
 */
@Table(name = "TJY_MESSAGE_BULK")
public class MessageBulk implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 消息内容 */
	private String content;

    /** 接收用户ids */
	private String toUserIds;

    /**  接收人名称串 */
	private String names;

    /** 接收人手机号串“,”分隔 */
	private String mobiles;

    /** 推送用户类型（0：全部用户;1:认证用户;2:注册用户3：企服云用户） */
	private String sendType;

    /** 消息类型 1：短信 2：微信 3：系统消息 */
	private String msgType;

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

    /** 发送人id */
	private String fromUserId;

    /** 发送时间 */
	private Date sendTime;

    /** 发送状态 */
	private String sendStatus;

    /** 模板id */
	private String templateId;

	public MessageBulk(){}


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
	 * 获取接收用户ids
	 */
	public String getToUserIds() {
    	return toUserIds;
    }
  	
	/**
	 * 设置接收用户ids
	 */
	public void setToUserIds(String toUserIds) {
    	this.toUserIds = toUserIds;
    }

	/**
	 * 获取 接收人名称串
	 */
	public String getNames() {
    	return names;
    }
  	
	/**
	 * 设置 接收人名称串
	 */
	public void setNames(String names) {
    	this.names = names;
    }

	/**
	 * 获取接收人手机号串“,”分隔
	 */
	public String getMobiles() {
    	return mobiles;
    }
  	
	/**
	 * 设置接收人手机号串“,”分隔
	 */
	public void setMobiles(String mobiles) {
    	this.mobiles = mobiles;
    }

	/**
	 * 获取推送用户类型（0：全部用户;1:认证用户;2:注册用户3：企服云用户）
	 */
	public String getSendType() {
    	return sendType;
    }
  	
	/**
	 * 设置推送用户类型（0：全部用户;1:认证用户;2:注册用户3：企服云用户）
	 */
	public void setSendType(String sendType) {
    	this.sendType = sendType;
    }

	/**
	 * 获取消息类型 1：短信 2：微信 3：系统消息
	 */
	public String getMsgType() {
    	return msgType;
    }
  	
	/**
	 * 设置消息类型 1：短信 2：微信 3：系统消息
	 */
	public void setMsgType(String msgType) {
    	this.msgType = msgType;
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
	 * 获取发送人id
	 */
	public String getFromUserId() {
    	return fromUserId;
    }
  	
	/**
	 * 设置发送人id
	 */
	public void setFromUserId(String fromUserId) {
    	this.fromUserId = fromUserId;
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
	public String getSendStatus() {
    	return sendStatus;
    }
  	
	/**
	 * 设置发送状态
	 */
	public void setSendStatus(String sendStatus) {
    	this.sendStatus = sendStatus;
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
}
