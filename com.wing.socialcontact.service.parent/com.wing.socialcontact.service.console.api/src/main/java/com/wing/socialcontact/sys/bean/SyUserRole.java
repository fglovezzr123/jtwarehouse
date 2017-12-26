package com.wing.socialcontact.sys.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 用户--角色关联表对应实体
 */
@Table(name = "sy_user_role")
public class SyUserRole implements java.io.Serializable {

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
	 * 角色id
	 */
	@Column(name = "role_id")
	private String roleId;

	// Constructors

	/** default constructor */
	public SyUserRole() {
	}

	/** full constructor */
	public SyUserRole(String userId, String roleId) {
		this.userId = userId;
		this.roleId = roleId;
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
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}