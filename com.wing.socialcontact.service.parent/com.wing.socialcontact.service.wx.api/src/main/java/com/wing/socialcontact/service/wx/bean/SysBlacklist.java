package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TJY_SYS_BLACKLIST
 * 
 * @author zengmin
 * @date 2017-08-08 08:56:15
 * @version 1.0
 */
@Table(name = "TJY_SYS_BLACKLIST")
public class SysBlacklist implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	/** 所属人 */
	private String userId;

	/** 开始时间 */
	private Date beginTime;

	/** 结束时间 */
	private Date endTime;

	/** 操作人 */
	private String managerUser;

	/** 操作时间 */
	private Date managerTime;

	/** 最后操作人 */
	private String lastUser;

	/** 最后操作时间 */
	private Date lastTime;

	@Transient
	private TjyUser tjyUser;

	@Transient
	private String managerUserName;

	public SysBlacklist() {
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
	 * 获取开始时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 设置开始时间
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 获取结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 设置结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	 * 获取最后操作人
	 */
	public String getLastUser() {
		return lastUser;
	}

	/**
	 * 设置最后操作人
	 */
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	/**
	 * 获取最后操作时间
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * 设置最后操作时间
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public TjyUser getTjyUser() {
		return tjyUser;
	}

	public void setTjyUser(TjyUser tjyUser) {
		this.tjyUser = tjyUser;
	}

	public String getManagerUserName() {
		return managerUserName;
	}

	public void setManagerUserName(String managerUserName) {
		this.managerUserName = managerUserName;
	}

}
