package com.wing.socialcontact.sys.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 操作表对应实体
 */
@Table(name = "sy_action")
public class SyAction implements java.io.Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	// Fields

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;
	/**
	 * 关联菜单id
	 */
	@Column(name = "menu_id")
	private String menuId;
	/**
	 * 排序号
	 */
	@Column(name = "action_sort")
	private Short actionSort;
	/**
	 * 操作名称
	 */
	@Column(name = "action_name")
	private String actionName;
	/**
	 * 操作url，多个用，连接
	 */
	@Column(name = "action_url")
	private String actionUrl;

	// Constructors

	/** default constructor */
	public SyAction() {
	}

	/** minimal constructor */
	public SyAction(String menuId, Short actionSort, String actionName) {
		this.menuId = menuId;
		this.actionSort = actionSort;
		this.actionName = actionName;
	}

	/** full constructor */
	public SyAction(String menuId, Short actionSort, String actionName,
			String actionUrl) {
		this.menuId = menuId;
		this.actionSort = actionSort;
		this.actionName = actionName;
		this.actionUrl = actionUrl;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public Short getActionSort() {
		return this.actionSort;
	}

	public void setActionSort(Short actionSort) {
		this.actionSort = actionSort;
	}
	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionUrl() {
		return this.actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

}