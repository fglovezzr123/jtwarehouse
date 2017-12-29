package com.tojoy.service.console.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 角色--操作关联
 */
@Table(name = "sy_role_action")
public class SyRoleAction implements java.io.Serializable {

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
	 * 角色id
	 */
	@Column(name = "role_id")
	private String roleId;
	/**
	 * 操作id
	 */
	@Column(name = "action_id")
	private String actionId;

	// Constructors

	/** default constructor */
	public SyRoleAction() {
	}

	/** full constructor */
	public SyRoleAction(String roleId, String actionId) {
		this.roleId = roleId;
		this.actionId = actionId;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getActionId() {
		return this.actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

}