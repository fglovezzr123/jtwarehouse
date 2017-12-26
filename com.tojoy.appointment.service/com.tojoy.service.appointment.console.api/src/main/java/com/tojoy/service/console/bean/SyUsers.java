package com.tojoy.service.console.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户表对应实体
 */
@Table(name = "sy_users")
public class SyUsers implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue(generator = "UUID")
	@Id
	private String id;
	/**
	 * 用户登录名称
	 * 账号不能为空
	 * 账号只能由数字、字母或者下划线组成，长度在3-15之间。
	 */
	@Column(name = "user_name")
	private String userName;
	/**
	 * 用户密码
	 */
	@Column(name = "user_password")
	private String userPassword;
	/**
	 * 注册时间
	 */
	@Column(name = "register_time")
	private Timestamp registerTime;
	/**
	 * 最后登录时间
	 */
	@Column(name = "last_login_time")
	private Timestamp lastLoginTime;
	/**
	 * 最后登录ip
	 */
	@Column(name = "last_login_ip")
	private String lastLoginIp;
	/**
	 * 登录时密码输入错误时间(最后一次)
	 */
	@Column(name = "error_time")
	private Timestamp errorTime;
	/**
	 * 密码输入错误次数，当输入正确一次时清零
	 */
	@Column(name = "error_count")
	private Short errorCount;
	/**
	 * 用户状态，1：可用，0：不可用，禁止登录系统
	 */
	@Column(name = "user_status")
	private Short userStatus;
	/**
	 * 用户所属部门id
	 */
	@Column(name = "dept_id")
	private String deptId;
	/**
	 * 用户真实姓名
	 * 真实姓名只能由汉字、数字、字母或者下划线组成，长度在2-10之间。
	 */
	@Column(name = "true_name")
	private String trueName;
	/**
	 * 备注
	 * 用户备注长度不能大于50。
	 */
	@Column(name = "user_desc")
	private String userDesc;
	/**
	 * 性别,1:男，0：女
	 */
	@Column(name = "user_sex")
	private Short userSex;
	/**
	 * 注册用户人id
	 */
	@Column(name = "register_uid")
	private String registerUid;
	/**
	 * 手机号
	 */
	@Column(name = "mobile_phone_number")
	private String mobilePhoneNumber;

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Timestamp getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}
	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}
	
	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return this.lastLoginIp;
	}
	
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Timestamp getErrorTime() {
		return this.errorTime;
	}

	public void setErrorTime(Timestamp errorTime) {
		this.errorTime = errorTime;
	}
	public Short getErrorCount() {
		return this.errorCount;
	}

	public void setErrorCount(Short errorCount) {
		this.errorCount = errorCount;
	}
	public Short getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Short userStatus) {
		this.userStatus = userStatus;
	}
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getUserDesc() {
		return this.userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	public Short getUserSex() {
		return this.userSex;
	}

	public void setUserSex(Short userSex) {
		this.userSex = userSex;
	}
	public String getRegisterUid() {
		return this.registerUid;
	}

	public void setRegisterUid(String registerUid) {
		this.registerUid = registerUid;
	}
	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}




}