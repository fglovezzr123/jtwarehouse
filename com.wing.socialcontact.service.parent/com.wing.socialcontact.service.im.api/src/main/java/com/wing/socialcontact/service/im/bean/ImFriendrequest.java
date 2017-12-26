package com.wing.socialcontact.service.im.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_IM_FRIENDREQUEST 好友请求
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:12:38
 * @version 1.0
 */
@Table(name = "TJY_IM_FRIENDREQUEST")
public class ImFriendrequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 用户ID */
	private String userId;

    /** 好友ID */
	private String friendUser;

    /** 请求信息 */
	private String askmessage;

    /** 添加状态，1待确认2已同意3已拒绝 */
	private Integer status;

    /** 创建时间 */
	private Date createTime;

    /** 更新时间 */
	private Date updateTime;

	public ImFriendrequest(){}


	/**
	 * 获取主键
	 */
	public String getId() {
    	return id;
    }
  	
	/**
	 * 设置主键
	 */
	public void setId(String id) {
    	this.id = id;
    }

	/**
	 * 获取用户ID
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置用户ID
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取好友ID
	 */
	public String getFriendUser() {
    	return friendUser;
    }
  	
	/**
	 * 设置好友ID
	 */
	public void setFriendUser(String friendUser) {
    	this.friendUser = friendUser;
    }

	/**
	 * 获取请求信息
	 */
	public String getAskmessage() {
    	return askmessage;
    }
  	
	/**
	 * 设置请求信息
	 */
	public void setAskmessage(String askmessage) {
    	this.askmessage = askmessage;
    }

	/**
	 * 获取添加状态，1待确认2已同意3已拒绝
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置添加状态，1待确认2已同意3已拒绝
	 */
	public void setStatus(Integer status) {
    	this.status = status;
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
	 * 获取更新时间
	 */
	public Date getUpdateTime() {
    	return updateTime;
    }
  	
	/**
	 * 设置更新时间
	 */
	public void setUpdateTime(Date updateTime) {
    	this.updateTime = updateTime;
    }
}
