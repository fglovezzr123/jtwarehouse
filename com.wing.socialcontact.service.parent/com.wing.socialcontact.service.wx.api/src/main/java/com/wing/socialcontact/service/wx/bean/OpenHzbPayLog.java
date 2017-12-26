package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TJY_OPEN_HZB_PAY_LOG
 * 
 * @author liangwj
 * @date 2017-07-20 20:28:03
 * @version 1.0
 */
@Table(name = "TJY_OPEN_HZB_PAY_LOG")
public class OpenHzbPayLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	// @Column(name = "id")
	// @GeneratedValue(generator = "UUID")
	private String id;

	/** 互助宝订单外键 */
	private String orderId;

	/** 付款类型（1：线上、2：线下） */
	private Short fkType;

	/** 线上付款方式（1：微信2：财付通） */
	private Short xsFkType;

	/** 付款时间 */
	private Date fkTime;

	/** 付款金额 */
	private Double fkMoney;

	/** 付款状态（0:待支付1：成功2：失败） */
	private Short fkStatus;

	/** 审核状态 （0：待审核1：审核通过2：审核不通过） */
	private Short shStatus;

	/** 审核时间 */
	private Date shTime;

	/** 审核人（后台用户） */
	private String shUserId;

	/** 付款凭证（线下支付图片） */
	private String fkPz;

	/**
	 * 线上支付流水号
	 */
	private String fkSn;

	private String remark;

	/** 日志类型1：开通日志2：充值日志 */
	private Short logType;

	/** 日志所属人，充值日志使用 */
	private String userId;
	
	@Transient
	private String keyword;

	public OpenHzbPayLog() {
	}

	/**
	 * 获取
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取互助宝订单外键
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 设置互助宝订单外键
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 获取付款类型（1：线上、2：线下）
	 */
	public Short getFkType() {
		return fkType;
	}

	/**
	 * 设置付款类型（1：线上、2：线下）
	 */
	public void setFkType(Short fkType) {
		this.fkType = fkType;
	}

	/**
	 * 获取线上付款方式（1：微信2：财付通）
	 */
	public Short getXsFkType() {
		return xsFkType;
	}

	/**
	 * 设置线上付款方式（1：微信2：财付通）
	 */
	public void setXsFkType(Short xsFkType) {
		this.xsFkType = xsFkType;
	}

	/**
	 * 获取付款时间
	 */
	public Date getFkTime() {
		return fkTime;
	}

	/**
	 * 设置付款时间
	 */
	public void setFkTime(Date fkTime) {
		this.fkTime = fkTime;
	}

	/**
	 * 获取付款金额
	 */
	public Double getFkMoney() {
		return fkMoney;
	}

	/**
	 * 设置付款金额
	 */
	public void setFkMoney(Double fkMoney) {
		this.fkMoney = fkMoney;
	}

	/**
	 * 获取付款状态（1：成功2：失败）
	 */
	public Short getFkStatus() {
		return fkStatus;
	}

	/**
	 * 设置付款状态（1：成功2：失败）
	 */
	public void setFkStatus(Short fkStatus) {
		this.fkStatus = fkStatus;
	}

	/**
	 * 获取审核状态（0：待审核1：审核通过2：审核不通过）
	 */
	public Short getShStatus() {
		return shStatus;
	}

	/**
	 * 设置审核状态（0：待审核1：审核通过2：审核不通过）
	 */
	public void setShStatus(Short shStatus) {
		this.shStatus = shStatus;
	}

	/**
	 * 获取审核时间
	 */
	public Date getShTime() {
		return shTime;
	}

	/**
	 * 设置审核时间
	 */
	public void setShTime(Date shTime) {
		this.shTime = shTime;
	}

	/**
	 * 获取审核人（后台用户）
	 */
	public String getShUserId() {
		return shUserId;
	}

	/**
	 * 设置审核人（后台用户）
	 */
	public void setShUserId(String shUserId) {
		this.shUserId = shUserId;
	}

	/**
	 * 获取付款凭证（线下支付图片）
	 */
	public String getFkPz() {
		return fkPz;
	}

	/**
	 * 设置付款凭证（线下支付图片）
	 */
	public void setFkPz(String fkPz) {
		this.fkPz = fkPz;
	}

	public String getFkSn() {
		return fkSn;
	}

	public void setFkSn(String fkSn) {
		this.fkSn = fkSn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getLogType() {
		return logType;
	}

	public void setLogType(Short logType) {
		this.logType = logType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
