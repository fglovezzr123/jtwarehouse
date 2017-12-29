package com.wing.socialcontact.sys.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 角色--菜单关联
 */
@Table(name = "sy_role_menu")
public class SyRoleMenu implements java.io.Serializable {

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
	 * 菜单id
	 */
	@Column(name = "menu_id")
	private String menuId;

	// Constructors

	/** default constructor */
	public SyRoleMenu() {
	}

	/** full constructor */
	public SyRoleMenu(String roleId, String menuId) {
		this.roleId = roleId;
		this.menuId = menuId;
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
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}