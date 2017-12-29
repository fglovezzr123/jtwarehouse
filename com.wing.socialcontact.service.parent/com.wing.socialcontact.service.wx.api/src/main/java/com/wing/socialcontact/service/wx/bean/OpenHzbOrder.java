package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TJY_OPEN_HZB_ORDER
 * 
 * @author liangwj
 * @date 2017-07-20 20:28:03
 * @version 1.0
 */
@Table(name = "TJY_OPEN_HZB_ORDER")
public class OpenHzbOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	//@Column(name = "id")
	//@GeneratedValue(generator = "UUID")
	private String id;

	/** 互助宝开通订单所属人 */
	private String userId;

	/** 申请开通级别（1：黄金级2：白金级3：钻石级） */
	private Short level;

	/** 状态（1：待支付2：待支付尾款3：支付完成（已付尾款）4：已过期） */
	private Integer status;

	/** 申请时间 */
	private Date createTime;

	/** 最后操作时间（针对后台管理人员的操作） */
	private Date lastTime;
	
	private String lastUserId;

	@Transient
	private List<OpenHzbPayLog> payLogList;

	@Transient
	private double yfk;

	@Transient
	private double dfk;
	
	@Transient
	private String keyword;

	public OpenHzbOrder() {
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
	 * 获取互助宝开通订单所属人
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置互助宝开通订单所属人
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取申请开通级别（1：黄金级2：白金级3：钻石级）
	 */
	public Short getLevel() {
		return level;
	}

	/**
	 * 设置申请开通级别（1：黄金级2：白金级3：钻石级）
	 */
	public void setLevel(Short level) {
		this.level = level;
	}

	/**
	 * 获取状态（1：待支付2：待支付尾款3：支付完成（已付尾款）4：已过期）
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置状态（1：待支付2：待支付尾款3：支付完成（已付尾款）4：已过期）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取申请时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置申请时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取最后操作时间（针对后台管理人员的操作）
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * 设置最后操作时间（针对后台管理人员的操作）
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public List<OpenHzbPayLog> getPayLogList() {
		return payLogList;
	}

	public void setPayLogList(List<OpenHzbPayLog> payLogList) {
		this.payLogList = payLogList;
	}

	public double getYfk() {
		return yfk;
	}

	public void setYfk(double yfk) {
		this.yfk = yfk;
	}

	public double getDfk() {
		return dfk;
	}

	public void setDfk(double dfk) {
		this.dfk = dfk;
	}

	public String getLastUserId() {
		return lastUserId;
	}

	public void setLastUserId(String lastUserId) {
		this.lastUserId = lastUserId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
