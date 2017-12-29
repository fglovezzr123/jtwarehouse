package com.tojoy.service.console.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 菜单表对应实体
 */
@Table(name = "sy_menu")
public class SyMenu implements java.io.Serializable {

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
	 * 上级菜单id
	 */
	@Column(name = "menu_super_id")
	private String menuSuperId;
	/**
	 * 菜单状态 是否可用，1:可用，0：不可用
	 */
	@Column(name = "menu_status")
	private Short menuStatus;
	/**
	 * 排序号
	 */
	@Column(name = "menu_sort")
	private Short menuSort;
	/**
	 * 菜单名称
	 */
	@Column(name = "menu_name")
	private String menuName;
	/**
	 * 菜单图片url
	 */
	@Column(name = "menu_icon")
	private String menuIcon;
	/**
	 * 菜单url
	 */
	@Column(name = "menu_url")
	private String menuUrl;
	/**
	 * 页面打开区域，dwz参数：navTab(在标签中),dialog(弹出窗口);_blank(在浏览器新窗口)
	 */
	@Column(name = "menu_target")
	private String menuTarget;
	/**
	 * dwz参数,rel为打开页面所在标签或弹出窗口的id，如果重复，将在同一标签打开
	 */
	@Column(name = "menu_rel")
	private String menuRel;
	/**
	 * 默认是否打开，true或false
	 */
	@Column(name = "menu_open")
	private String menuOpen;
	/**
	 * dwz参数,为external="true"或者href是外网连接时，以iframe方式打开navTab页面
	 */
	@Column(name = "menu_external")
	private String menuExternal;
	/**
	 * dwz参数,表示重复打开navTab时是否重新加载数据
	 */
	@Column(name = "menu_fresh")
	private String menuFresh;

	// Constructors

	/** default constructor */
	public SyMenu() {
	}

	/** full constructor */
	public SyMenu(String menuSuperId, Short menuStatus, Short menuSort,
			String menuName, String menuIcon, String menuUrl,
			String menuTarget, String menuRel, String menuOpen,
			String menuExternal, String menuFresh) {
		this.menuSuperId = menuSuperId;
		this.menuStatus = menuStatus;
		this.menuSort = menuSort;
		this.menuName = menuName;
		this.menuIcon = menuIcon;
		this.menuUrl = menuUrl;
		this.menuTarget = menuTarget;
		this.menuRel = menuRel;
		this.menuOpen = menuOpen;
		this.menuExternal = menuExternal;
		this.menuFresh = menuFresh;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getMenuSuperId() {
		return this.menuSuperId;
	}

	public void setMenuSuperId(String menuSuperId) {
		this.menuSuperId = menuSuperId;
	}
	public Short getMenuStatus() {
		return this.menuStatus;
	}

	public void setMenuStatus(Short menuStatus) {
		this.menuStatus = menuStatus;
	}
	public Short getMenuSort() {
		return this.menuSort;
	}

	public void setMenuSort(Short menuSort) {
		this.menuSort = menuSort;
	}
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuIcon() {
		return this.menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuTarget() {
		return this.menuTarget;
	}

	public void setMenuTarget(String menuTarget) {
		this.menuTarget = menuTarget;
	}
	public String getMenuRel() {
		return this.menuRel;
	}

	public void setMenuRel(String menuRel) {
		this.menuRel = menuRel;
	}
	public String getMenuOpen() {
		return this.menuOpen;
	}

	public void setMenuOpen(String menuOpen) {
		this.menuOpen = menuOpen;
	}
	public String getMenuExternal() {
		return this.menuExternal;
	}

	public void setMenuExternal(String menuExternal) {
		this.menuExternal = menuExternal;
	}
	public String getMenuFresh() {
		return this.menuFresh;
	}

	public void setMenuFresh(String menuFresh) {
		this.menuFresh = menuFresh;
	}

}