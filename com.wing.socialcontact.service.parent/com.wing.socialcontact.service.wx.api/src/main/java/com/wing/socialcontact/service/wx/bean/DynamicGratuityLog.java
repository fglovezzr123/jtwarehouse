package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_DYNAMIC_GRATUITY_LOG 用户打赏记录
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:05
 * @version 1.0
 */
@Table(name = "TJY_DYNAMIC_GRATUITY_LOG")
public class DynamicGratuityLog implements Serializable{
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
	private String useId;

    /** 动态id */
	private String dynamicId;

    /** 打赏金额 */
	private Long qratuityAmount;

    /** 操作时间 */
	private Date actionTime;

    /** 状态 0、失败 1、成功 */
	private Integer status;

	public DynamicGratuityLog(){}


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
	public String getUseId() {
    	return useId;
    }
  	
	/**
	 * 设置用户ID
	 */
	public void setUseId(String useId) {
    	this.useId = useId;
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
	 * 获取打赏金额
	 */
	public Long getQratuityAmount() {
    	return qratuityAmount;
    }
  	
	/**
	 * 设置打赏金额
	 */
	public void setQratuityAmount(Long qratuityAmount) {
    	this.qratuityAmount = qratuityAmount;
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
