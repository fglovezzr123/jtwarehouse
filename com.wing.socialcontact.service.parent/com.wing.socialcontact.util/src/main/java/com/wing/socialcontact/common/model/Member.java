/**  
 * @Project: tjy
 * @Title: Member.java
 * @Package com.wing.socialcontact.commons.model
 * @date 2016-4-1 上午9:31:14
 * @Copyright: 2016 
 */
package com.wing.socialcontact.common.model;

import java.sql.Timestamp;

/**
 * 
 * 类名：Member 功能：session中存储用户信息model 详细：保存用户常用信息 放入session 作者：dijuli 版本：1.0
 * 日期：2016-4-1 上午9:31:14
 *
 */

public class Member implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID :
	 */

	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	private String id;
	/**
	 * 登录信息
	 * 
	 * @return
	 */
	private IpInfo ipInfo;

	private String userName;

	private String wxUserId;
	
	private String openId;
	
	/**
	 * 客服电话
	 */
	private String kfTelephone;
	
	/**
	 * 登录时间
	 */
	private Timestamp loginTime;
	/**
	 * 部门id
	 */
	private String deptId;
	
	/**
	 * 是否实名认证
	 */
	private String isRealname;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IpInfo getIpInfo() {
		return ipInfo;
	}

	public void setIpInfo(IpInfo ipInfo) {
		this.ipInfo = ipInfo;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public String getIsRealname() {
		return isRealname;
	}

	public void setIsRealname(String isRealname) {
		this.isRealname = isRealname;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getKfTelephone() {
		return kfTelephone;
	}

	public void setKfTelephone(String kfTelephone) {
		this.kfTelephone = kfTelephone;
	}

}
