package com.tojoy.service.console.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 角色表对应实体
 * 
 */
@Table(name = "sy_role")
public class SyRole implements java.io.Serializable{

	/**
	 * @Fields serialVersionUID : 
	 */

	@Transient
	private static final long serialVersionUID = 1L;
	// Fields

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;
	/**
	 * 角色名称
	 * 角色名称不能为空
	 * 角色名称长度必须在1-20之间
	 */
	@Column(name = "role_name")
	private String roleName;
	/**
	 * 角色说明
	 */
	@Column(name = "role_desc")
	private String roleDesc;
	

	// Constructors

	/** default constructor */
	public SyRole() {
	}

	/** minimal constructor */
	public SyRole(String roleName) {
		this.roleName = roleName;
	}
	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	

}