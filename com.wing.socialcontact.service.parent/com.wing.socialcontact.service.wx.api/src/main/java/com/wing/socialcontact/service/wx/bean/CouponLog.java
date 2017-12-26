package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_COUPON_LOG 优惠券领取记录表
 * 
 * @author zhangfan
 * @date 2017-07-20 11:03:44
 * @version 1.0
 */
@Table(name = "TJY_COUPON_LOG")
public class CouponLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 优惠券生成记录表id */
	private String fkId;

    /** 优惠券编码 */
	private String couponCode;

    /** 领取人id */
	private String userId;

    /** 领取时间 */
	private Date receiveTime;

    /** 使用时间 */
	private Date useTime;

    /** 使用状态 1已使用2未使用 */
	private Integer useStatus;

    /** 订单id */
	private String orderId;

    /** 创建时间 */
	private Date createTime;

    /** 创建人id */
	private String createUserId;

    /** 创建用户名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deleted;

	public CouponLog(){}


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
	 * 获取优惠券生成记录表id
	 */
	public String getFkId() {
    	return fkId;
    }
  	
	/**
	 * 设置优惠券生成记录表id
	 */
	public void setFkId(String fkId) {
    	this.fkId = fkId;
    }

	/**
	 * 获取优惠券编码
	 */
	public String getCouponCode() {
    	return couponCode;
    }
  	
	/**
	 * 设置优惠券编码
	 */
	public void setCouponCode(String couponCode) {
    	this.couponCode = couponCode;
    }

	/**
	 * 获取领取人id
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置领取人id
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取领取时间
	 */
	public Date getReceiveTime() {
    	return receiveTime;
    }
  	
	/**
	 * 设置领取时间
	 */
	public void setReceiveTime(Date receiveTime) {
    	this.receiveTime = receiveTime;
    }

	/**
	 * 获取使用时间
	 */
	public Date getUseTime() {
    	return useTime;
    }
  	
	/**
	 * 设置使用时间
	 */
	public void setUseTime(Date useTime) {
    	this.useTime = useTime;
    }

	/**
	 * 获取使用状态 1已使用2未使用
	 */
	public Integer getUseStatus() {
    	return useStatus;
    }
  	
	/**
	 * 设置使用状态 1已使用2未使用
	 */
	public void setUseStatus(Integer useStatus) {
    	this.useStatus = useStatus;
    }

	/**
	 * 获取订单id
	 */
	public String getOrderId() {
    	return orderId;
    }
  	
	/**
	 * 设置订单id
	 */
	public void setOrderId(String orderId) {
    	this.orderId = orderId;
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
	 * 获取创建人id
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置创建人id
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
