package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_HZB_MANAGER_LOG
 * 
 * @author liangwj
 * @date 2017-07-22 18:21:15
 * @version 1.0
 */
@Table(name = "TJY_HZB_MANAGER_LOG")
public class HzbManagerLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	/** 操作类型（1开通2停用3启用4增加余额5减少余额6修改等级7用户充值8用户消费9订单退款）增加金额算累充，减少金额不算累消 */
	private Integer type;

	/** 记录类型（1：收入2：消费3：黄金级4：白金级5：钻石级） */
	private Integer pdType;

	/** 操作金额 */
	private Double managerMoney;
	/** 当前余额 */
	private Double currYe;

	/** 所属人 */
	private String userId;

	/** 操作人 */
	private String managerUser;

	/** 操作时间 */
	private Date managerTime;

	/** 操作说明 */
	private String remark;
	
	public HzbManagerLog() {
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
	 * 获取操作类型（1开通2停用3启用4增加余额5减少余额6修改等级7用户充值8用户消费）
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 设置操作类型（1开通2停用3启用4增加余额5减少余额6修改等级7用户充值8用户消费）
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 获取所属人
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置所属人
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取操作人
	 */
	public String getManagerUser() {
		return managerUser;
	}

	/**
	 * 设置操作人
	 */
	public void setManagerUser(String managerUser) {
		this.managerUser = managerUser;
	}

	/**
	 * 获取操作时间
	 */
	public Date getManagerTime() {
		return managerTime;
	}

	/**
	 * 设置操作时间
	 */
	public void setManagerTime(Date managerTime) {
		this.managerTime = managerTime;
	}

	/**
	 * 获取操作说明
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置操作说明
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPdType() {
		return pdType;
	}

	public void setPdType(Integer pdType) {
		this.pdType = pdType;
	}

	public Double getManagerMoney() {
		return managerMoney;
	}

	public void setManagerMoney(Double managerMoney) {
		this.managerMoney = managerMoney;
	}

	public Double getCurrYe() {
		return currYe;
	}

	public void setCurrYe(Double currYe) {
		this.currYe = currYe;
	}

}
