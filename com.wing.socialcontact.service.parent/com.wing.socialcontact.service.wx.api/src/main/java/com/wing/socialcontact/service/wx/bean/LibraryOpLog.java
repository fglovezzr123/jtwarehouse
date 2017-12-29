package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_LIBRARY_OP_LOG 文章用户操作记录表（点赞、转发）
 * 
 * @author zhangfan
 * @date 
 * @version 1.0
 */
@Table(name = "TJY_LIBRARY_OP_LOG")
public class LibraryOpLog implements Serializable{
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

    /** fkid */
	private String fkId;

    /** 操作类型（1、点赞 2、转发） */
	private Integer opType;

    /** 操作时间 */
	private Date actionTime;
	
	private Integer type;

	public LibraryOpLog(){}


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


	public String getFkId() {
		return fkId;
	}


	public void setFkId(String fkId) {
		this.fkId = fkId;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
