package com.wing.socialcontact.sys.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用户自定义快捷菜单
 */
@Table(name = "sy_menu_my")
public class SyMenuMy implements java.io.Serializable {

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
	 * 关联的用户id 
	 */
	@Column(name = "_user_id")
	private String userId;
	/**
	 * 关联的菜单id
	 */
	@Column(name = "_menu_id")
	private String menuId;
	/**
	 * 排序号
	 */
	@Column(name = "_sort")
	private Short sort;

	// Constructors

	/** default constructor */
	public SyMenuMy() {
	}

	/** full constructor */
	public SyMenuMy(String userId, String menuId, Short sort) {
		this.userId = userId;
		this.menuId = menuId;
		this.sort = sort;
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

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Short getSort() {
		return this.sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

}