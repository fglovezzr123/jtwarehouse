package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_DYNAMIC_OP_LOG 用户操作记录表（点赞、转发）
 * 
 * @author xuxinyuan
 * @date 2017-04-19 14:19:54
 * @version 1.0
 */
@Table(name = "TJY_DYNAMIC_OP_LOG")
public class DynamicOpLog implements Serializable{
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

    /** 动态id */
	private String dynamicId;

    /** 操作类型（1、点赞 2、转发） */
	private Integer opType;

    /** 操作时间 */
	private Date actionTime;

	public DynamicOpLog(){}


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

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * 获取动态id
	 */
	public String getDynamicId() {
    	return dynamicId;
    }
  	
	/**
	 * 设置动态id
	 */
	public void setDynamicId(String dynamicId) {
    	this.dynamicId = dynamicId;
    }

	/**
	 * 获取操作类型（1、点赞 2、转发）
	 */
	public Integer getOpType() {
    	return opType;
    }
  	
	/**
	 * 设置操作类型（1、点赞 2、转发）
	 */
	public void setOpType(Integer opType) {
    	this.opType = opType;
    }

	/**
	 * 获取操作时间
	 */
	public Date getActionTime() {
    	return actionTime;
    }
  	
	/**
	 * 设置操作时间
	 */
	public void setActionTime(Date actionTime) {
    	this.actionTime = actionTime;
    }
}
