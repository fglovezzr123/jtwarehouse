package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 聚合页面-栏目表
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:28
 * @version 1.0
 */
@Table(name = "TJY_PAGE_COLUMN")
public class PageColumn implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	@Column(name = "id")
	// @GeneratedValue(generator = "UUID")
	private String id;

	/** 栏目名称 */
	private String columnName;

	/** 展现形式 */
	private Integer showStyle;

	/** 排序 */
	private Integer orderNum;

	/** 所属页面 */
	private String pageId;

	/** 更多链接地址 */
	private String moreUrl;

	/** 1：普通栏目、2：动态栏目 */
	private Integer columnType;

	/** 动态栏目使用，栏目数量 */
	private Integer columnNum;

	/** 动态栏目使用，栏目键 */
	private String elementKey;

	/** 动态栏目使用，栏目值 */
	private String elementValue;

	/** 是否导航 */
	private int columnStatus;

	/** 动态栏目使用，栏目说明 */
	private String remark;

	@Transient
	private List<PageElement> elementList;

	public PageColumn() {
	}

	/**
	 * 获取
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取栏目名称
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * 设置栏目名称
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * 获取展现形式
	 */
	public Integer getShowStyle() {
		return showStyle;
	}

	/**
	 * 设置展现形式
	 */
	public void setShowStyle(Integer showStyle) {
		this.showStyle = showStyle;
	}

	/**
	 * 获取排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	/**
	 * 设置排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * 获取所属页面
	 */
	public String getPageId() {
		return pageId;
	}

	/**
	 * 设置所属页面
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	/**
	 * 获取更多链接地址
	 */
	public String getMoreUrl() {
		return moreUrl;
	}

	/**
	 * 设置更多链接地址
	 */
	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
	}

	public List<PageElement> getElementList() {
		return elementList;
	}

	public void setElementList(List<PageElement> elementList) {
		this.elementList = elementList;
	}

	public Integer getColumnType() {
		return columnType;
	}

	public void setColumnType(Integer columnType) {
		this.columnType = columnType;
	}

	public Integer getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}

	public String getElementKey() {
		return elementKey;
	}

	public void setElementKey(String elementKey) {
		this.elementKey = elementKey;
	}

	public String getElementValue() {
		return elementValue;
	}

	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getColumnStatus() {
		return columnStatus;
	}

	public void setColumnStatus(int columnStatus) {
		this.columnStatus = columnStatus;
	}
}
