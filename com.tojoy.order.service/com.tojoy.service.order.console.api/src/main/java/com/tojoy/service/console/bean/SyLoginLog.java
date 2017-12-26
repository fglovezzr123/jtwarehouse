package com.tojoy.service.console.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 登录日志对应实体
 */
@Table(name = "sy_login_log")
public class SyLoginLog implements java.io.Serializable {

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
	 * 用户id
	 */
	@Column(name = "user_id")
	private String userId;

	/**
	 * 登录时间
	 */
	@Column(name = "login_time")
	private Timestamp loginTime;
	/**
	 * 登录ip
	 */
	@Column(name = "login_ip")
	private String loginIp;
	/**
	 * ip信息：国家
	 */
	@Column(name = "ip_info_country")
	private String ipInfoCountry;
	/**
	 * ip信息：省份
	 */
	@Column(name = "ip_info_region")
	private String ipInfoRegion;
	/**
	 * ip信息：城市
	 */
	@Column(name = "ip_info_city")
	private String ipInfoCity;
	/**
	 * ip信息：运营商
	 */
	@Column(name = "ip_info_isp")
	private String ipInfoIsp;
	/**
	 * 登录来源方式，1：web,2:android
	 */
	@Column(name = "login_type")
	private Short loginType;
	/**
	 * 日志 备注
	 */
	@Column(name = "login_desc")
	private String loginDesc;
	
	// Constructors

	/** default constructor */
	public SyLoginLog() {
	}

	/** minimal constructor */
	public SyLoginLog(String userId, Timestamp loginTime) {
		this.userId = userId;
		this.loginTime = loginTime;
	}

	/** full constructor */
	public SyLoginLog(String userId, Timestamp loginTime, String loginIp,
			String ipInfoCountry, String ipInfoRegion, String ipInfoCity,
			String ipInfoIsp, Short loginType,String loginDesc) {
		this.userId = userId;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.ipInfoCountry = ipInfoCountry;
		this.ipInfoRegion = ipInfoRegion;
		this.ipInfoCity = ipInfoCity;
		this.ipInfoIsp = ipInfoIsp;
		this.loginType = loginType;
		this.loginDesc=loginDesc;
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
	public Timestamp getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getIpInfoCountry() {
		return this.ipInfoCountry;
	}
	
	public void setIpInfoCountry(String ipInfoCountry) {
		this.ipInfoCountry = ipInfoCountry;
	}
	public String getIpInfoRegion() {
		return this.ipInfoRegion;
	}

	public void setIpInfoRegion(String ipInfoRegion) {
		this.ipInfoRegion = ipInfoRegion;
	}
	public String getIpInfoCity() {
		return this.ipInfoCity;
	}

	public void setIpInfoCity(String ipInfoCity) {
		this.ipInfoCity = ipInfoCity;
	}
	public String getIpInfoIsp() {
		return this.ipInfoIsp;
	}

	public void setIpInfoIsp(String ipInfoIsp) {
		this.ipInfoIsp = ipInfoIsp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Short getLoginType() {
		return loginType;
	}

	public void setLoginType(Short loginType) {
		this.loginType = loginType;
	}
	public String getLoginDesc() {
		return loginDesc;
	}

	public void setLoginDesc(String loginDesc) {
		this.loginDesc = loginDesc;
	}

}