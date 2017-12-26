package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_NEWS_PAY_LOG 新闻支付日志
 * 
 * @author zhangfan
 * @date 2017-06-28 09:31:35
 * @version 1.0
 */
@Table(name = "TJY_NEWS_PAY_LOG")
public class NewsPayLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 新闻表id */
	private String fkId;

    /** 支付j币 */
	private Long payAmount;

    /** 用户id */
	private String userId;

    /** 状态 0、失败 1、成功 */
	private Integer status;

    /** 创建时间 */
	private Date createTime;

    /** 创建用户id */
	private String createUserId;

    /** 创建用户名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deleted;

	public NewsPayLog(){}


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
	 * 获取新闻表id
	 */
	public String getFkId() {
    	return fkId;
    }
  	
	/**
	 * 设置新闻表id
	 */
	public void setFkId(String fkId) {
    	this.fkId = fkId;
    }

	/**
	 * 获取支付j币
	 */
	public Long getPayAmount() {
    	return payAmount;
    }
  	
	/**
	 * 设置支付j币
	 */
	public void setPayAmount(Long payAmount) {
    	this.payAmount = payAmount;
    }

	/**
	 * 获取用户id
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置用户id
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
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
	 * 获取创建用户id
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置创建用户id
	 */
	public void setCreateUserId(String createUserId) {
    	this.createUserId = createUserId;
    }

	/**
	 * 获取创建用户名称
	 */
	public String getCreateUserName() {
    	return createUserName;
    }
  	
	/**
	 * 设置创建用户名称
	 */
	public void setCreateUserName(String createUserName) {
    	this.createUserName = createUserName;
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
}
