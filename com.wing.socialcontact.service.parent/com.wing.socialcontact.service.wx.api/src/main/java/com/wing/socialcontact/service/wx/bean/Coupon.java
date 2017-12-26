package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_COUPON 优惠券表
 * 
 * @author zhangfan
 * @date 2017-07-20 10:24:15
 * @version 1.0
 */
@Table(name = "TJY_COUPON")
public class Coupon implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 优惠券名称 */
	private String couponName;

    /** 优惠券类型(1代金券，2满减券，3兑换券) */
	private Integer couponType;

    /** 优惠券币种(1j币，2rmb) */
	private Integer couponCoinType;

    /** 优惠券金额 */
	private Double couponAmount;

    /** 最低消费 */
	private Double orderMinBuy;

    /** 使用范围 */
	private String useRange;

    /** 指定店铺 */
	private String useStore;

    /** 状态，1审核中2审核通过3审核不通过 */
	private Integer status;

    /** 创建时间 */
	private Date createTime;

    /** 发布人id */
	private String createUserId;

    /** 创建用户名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deleted;

	public Coupon(){}


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
	 * 获取优惠券名称
	 */
	public String getCouponName() {
    	return couponName;
    }
  	
	/**
	 * 设置优惠券名称
	 */
	public void setCouponName(String couponName) {
    	this.couponName = couponName;
    }

	/**
	 * 获取优惠券类型(1代金券，2满减券，3兑换券)
	 */
	public Integer getCouponType() {
    	return couponType;
    }
  	
	/**
	 * 设置优惠券类型(1代金券，2满减券，3兑换券)
	 */
	public void setCouponType(Integer couponType) {
    	this.couponType = couponType;
    }

	/**
	 * 获取优惠券币种(1j币，2rmb)
	 */
	public Integer getCouponCoinType() {
    	return couponCoinType;
    }
  	
	/**
	 * 设置优惠券币种(1j币，2rmb)
	 */
	public void setCouponCoinType(Integer couponCoinType) {
    	this.couponCoinType = couponCoinType;
    }

	/**
	 * 获取优惠券金额
	 */
	public Double getCouponAmount() {
    	return couponAmount;
    }
  	
	/**
	 * 设置优惠券金额
	 */
	public void setCouponAmount(Double couponAmount) {
    	this.couponAmount = couponAmount;
    }

	/**
	 * 获取最低消费
	 */
	public Double getOrderMinBuy() {
    	return orderMinBuy;
    }
  	
	/**
	 * 设置最低消费
	 */
	public void setOrderMinBuy(Double orderMinBuy) {
    	this.orderMinBuy = orderMinBuy;
    }

	/**
	 * 获取使用范围
	 */
	public String getUseRange() {
    	return useRange;
    }
  	
	/**
	 * 设置使用范围
	 */
	public void setUseRange(String useRange) {
    	this.useRange = useRange;
    }

	/**
	 * 获取指定店铺
	 */
	public String getUseStore() {
    	return useStore;
    }
  	
	/**
	 * 设置指定店铺
	 */
	public void setUseStore(String useStore) {
    	this.useStore = useStore;
    }

	/**
	 * 获取状态，1审核中2审核通过3审核不通过
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置状态，1审核中2审核通过3审核不通过
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
	 * 获取发布人id
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置发布人id
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
