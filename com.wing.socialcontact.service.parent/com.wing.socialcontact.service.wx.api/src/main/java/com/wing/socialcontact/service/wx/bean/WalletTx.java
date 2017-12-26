package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_WALLET_TX 钱包提现记录
 * 
 * @author zengmin
 * @date 2017-05-04 16:15:41
 * @version 1.0
 */
@Table(name = "TJY_WALLET_TX")
public class WalletTx implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	/** 用户账号 */
	private String userid;

	/** 用户昵称 */
	private String username;

	/** 微信号 */
	private String wxh;

	/** 提现金额 */
	private Double txje;

	/** 剩余金额 */
	private Double syje;

	/** 操作时间 */
	private Date createTime;

	/** 0：待申请；1：成功；2：失败 */
	private String state;

	/** 确认人 */
	private String opUser;

	/** 确认时间 */
	private Date opTime;

	/** 支付序号 */
	private String paySn;

	/** 支付状态 */
	private String payStatus;

	public WalletTx() {
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
	 * 获取用户账号
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * 设置用户账号
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * 获取用户昵称
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户昵称
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取微信号
	 */
	public String getWxh() {
		return wxh;
	}

	/**
	 * 设置微信号
	 */
	public void setWxh(String wxh) {
		this.wxh = wxh;
	}

	/**
	 * 获取提现金额
	 */
	public Double getTxje() {
		return txje;
	}

	/**
	 * 设置提现金额
	 */
	public void setTxje(Double txje) {
		this.txje = txje;
	}

	/**
	 * 获取剩余金额
	 */
	public Double getSyje() {
		return syje;
	}

	/**
	 * 设置剩余金额
	 */
	public void setSyje(Double syje) {
		this.syje = syje;
	}

	/**
	 * 获取操作时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置操作时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取0：待申请；1：成功；2：失败
	 */
	public String getState() {
		return state;
	}

	/**
	 * 设置0：待申请；1：成功；2：失败
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 获取确认人
	 */
	public String getOpUser() {
		return opUser;
	}

	/**
	 * 设置确认人
	 */
	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	/**
	 * 获取确认时间
	 */
	public Date getOpTime() {
		return opTime;
	}

	/**
	 * 设置确认时间
	 */
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getPaySn() {
		return paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

}
