package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_USER_LATESTVISTOR 最近访客
 * 
 * @author gaojun
 * @date 2017-05-02 11:14:41
 * @version 1.0
 */
@Table(name = "TJY_USER_LATESTVISTOR")
public class UserLatestvistor implements Serializable{
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

    /** 访客ID */
	private String vistorUserId;

    /** 创建时间 */
	private Date createTime;

    /** 更新时间 */
	private Date updateTime;
	
    /** 状态，0:未读 1：已读 */
	private Integer status;
	/** 状态， 1：已删除   其它：未删除 */
	private Integer deleted;


	public UserLatestvistor(){}

	
	

	public Integer getDeleted() {
		return deleted;
	}




	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}




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
	 * 获取访客ID
	 */
	public String getVistorUserId() {
    	return vistorUserId;
    }
  	
	/**
	 * 设置访客ID
	 */
	public void setVistorUserId(String vistorUserId) {
    	this.vistorUserId = vistorUserId;
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
	
	/**
	 * 获取状态，0:未读 1：已读
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置状态，0:未读 1：已读
	 */
	public void setStatus(Integer status) {
    	this.status = status;
    }
}
