package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_DYNAMIC_PAY_LOG 用户支付记录
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:05
 * @version 1.0
 */
@Table(name = "TJY_DYNAMIC_PAY_LOG")
public class DynamicPayLog implements Serializable{
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

    /** 语音ID */
	private String mediaId;

    /** 打赏金额 */
	private Long payAmount;

    /** 操作时间 */
	private Date actionTime;
	
	/** 1、打赏 2语音支付*/
	private Integer actionType;

    /** 状态 0、失败 1、成功 */
	private Integer status;

	public DynamicPayLog(){}


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
	 * 获取语音ID
	 */
	public String getMediaId() {
    	return mediaId;
    }
  	
	/**
	 * 设置语音ID
	 */
	public void setMediaId(String mediaId) {
    	this.mediaId = mediaId;
    }

	/**
	 * 获取打赏金额
	 */
	public Long getPayAmount() {
    	return payAmount;
    }
  	
	/**
	 * 设置打赏金额
	 */
	public void setPayAmount(Long payAmount) {
    	this.payAmount = payAmount;
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

	public Integer getActionType() {
		return actionType;
	}


	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}


	/**
	 * 获取状态 0、失败 1、成功
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置状态 0、失败 1、成功
	 */
	public void setStatus(Integer status) {
    	this.status = status;
    }
}
