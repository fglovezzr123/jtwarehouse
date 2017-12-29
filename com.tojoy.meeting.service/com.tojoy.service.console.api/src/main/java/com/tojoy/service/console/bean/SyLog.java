package com.tojoy.service.console.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 操作日志对应实体
 */
@Table(name = "sy_log")
public class SyLog implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID : 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;
	/**
	 * 操作用户id
	 */
	@Column(name = "user_id")
	private String userId;
	/**
	 * 操作时间
	 */
	@Column(name = "action_time")
	private Timestamp actionTime;
	/**
	 * 操作内容
	 */
	@Column(name = "action_content")
	private String actionContent;
	/**
	 * 操作ip
	 */
	@Column(name = "action_ip")
	private String actionIp;
	/**
	 * ip信息
	 */
	@Column(name = "action_ip_info")
	private String actionIpInfo;
	/**
	 * 备注
	 */
	@Column(name = "action_desc")
	private String actionDesc;

	// Constructors

	/** default constructor */
	public SyLog() {
	}

	/** minimal constructor */
	public SyLog(String userId, Timestamp actionTime) {
		this.userId = userId;
		this.actionTime = actionTime;
	}

	/** full constructor */
	public SyLog(String userId, Timestamp actionTime, String actionContent,
			String actionIp, String actionIpInfo, String actionDesc) {
		this.userId = userId;
		this.actionTime = actionTime;
		this.actionContent = actionContent;
		this.actionIp = actionIp;
		this.actionIpInfo = actionIpInfo;
		this.actionDesc = actionDesc;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getActionTime() {
		return this.actionTime;
	}

	public void setActionTime(Timestamp actionTime) {
		this.actionTime = actionTime;
	}
	public String getActionContent() {
		return this.actionContent;
	}

	public void setActionContent(String actionContent) {
		this.actionContent = actionContent;
	}
	public String getActionIp() {
		return this.actionIp;
	}

	public void setActionIp(String actionIp) {
		this.actionIp = actionIp;
	}
	public String getActionIpInfo() {
		return this.actionIpInfo;
	}

	public void setActionIpInfo(String actionIpInfo) {
		this.actionIpInfo = actionIpInfo;
	}
	public String getActionDesc() {
		return this.actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

}