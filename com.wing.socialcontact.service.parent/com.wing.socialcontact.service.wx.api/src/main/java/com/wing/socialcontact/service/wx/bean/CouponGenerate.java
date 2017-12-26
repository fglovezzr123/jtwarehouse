package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_COUPON_GENERATE 优惠券生成记录表
 * 
 * @author zhangfan
 * @date 2017-07-20 11:00:18
 * @version 1.0
 */
@Table(name = "TJY_COUPON_GENERATE")
public class CouponGenerate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 优惠券id */
	private String fkId;

    /** 优惠券批号 */
	private String batchNum;

    /** 优惠券名称 */
	private String couponName;

    /** 有效开始日期 */
	private Date startTime;

    /** 有效截至日期 */
	private Date endTime;

    /** 优惠券数量 */
	private Integer couponNum;

    /** 领取数量 */
	private Integer receiveNum;

    /** 剩余数量 */
	private Integer remainNum;

    /** 创建时间 */
	private Date createTime;

    /** 发布人id */
	private String createUserId;

    /** 创建用户名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deleted;
	
	private String storeMobile;

	public CouponGenerate(){}


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
	 * 获取优惠券id
	 */
	public String getFkId() {
    	return fkId;
    }
  	
	/**
	 * 设置优惠券id
	 */
	public void setFkId(String fkId) {
    	this.fkId = fkId;
    }

	/**
	 * 获取优惠券批号
	 */
	public String getBatchNum() {
    	return batchNum;
    }
  	
	/**
	 * 设置优惠券批号
	 */
	public void setBatchNum(String batchNum) {
    	this.batchNum = batchNum;
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
	 * 获取有效开始日期
	 */
	public Date getStartTime() {
    	return startTime;
    }
  	
	/**
	 * 设置有效开始日期
	 */
	public void setStartTime(Date startTime) {
    	this.startTime = startTime;
    }

	/**
	 * 获取有效截至日期
	 */
	public Date getEndTime() {
    	return endTime;
    }
  	
	/**
	 * 设置有效截至日期
	 */
	public void setEndTime(Date endTime) {
    	this.endTime = endTime;
    }

	/**
	 * 获取优惠券数量
	 */
	public Integer getCouponNum() {
    	return couponNum;
    }
  	
	/**
	 * 设置优惠券数量
	 */
	public void setCouponNum(Integer couponNum) {
    	this.couponNum = couponNum;
    }

	/**
	 * 获取领取数量
	 */
	public Integer getReceiveNum() {
    	return receiveNum;
    }
  	
	/**
	 * 设置领取数量
	 */
	public void setReceiveNum(Integer receiveNum) {
    	this.receiveNum = receiveNum;
    }

	/**
	 * 获取剩余数量
	 */
	public Integer getRemainNum() {
    	return remainNum;
    }
  	
	/**
	 * 设置剩余数量
	 */
	public void setRemainNum(Integer remainNum) {
    	this.remainNum = remainNum;
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


	public String getStoreMobile() {
		return storeMobile;
	}


	public void setStoreMobile(String storeMobile) {
		this.storeMobile = storeMobile;
	}
	
}
