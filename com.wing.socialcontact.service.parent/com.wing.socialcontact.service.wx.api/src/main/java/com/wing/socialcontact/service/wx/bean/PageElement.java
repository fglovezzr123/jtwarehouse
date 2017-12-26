package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 聚合页面-元素表
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:29
 * @version 1.0
 */
@Table(name = "TJY_PAGE_ELEMENT")
public class PageElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	/** 标题1 */
	private String titleOne;

	/** 标题2 */
	private String titleTwo;

	/** 图片地址 */
	private String imgUrl;

	/** 显示内容类型编号 */
	private String contentTypeId;

	/** 详情页面地址 */
	private String detailUrl;

	/** 内容对应模块编号 */
	private String contentId;

	/** 跳转类型 **/
	private String jumpType;
	
	/** 内容对应模块名称 */
	private String contentName;
	
	/** 所属栏目编号 */
	private String columnId;

	/** 排序 */
	private Integer orderNum;

	/**
	 * 元素key
	 */
	@Transient
	private String elementKey;

	public PageElement() {
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
	 * 获取标题1
	 */
	public String getTitleOne() {
		return titleOne;
	}

	/**
	 * 设置标题1
	 */
	public void setTitleOne(String titleOne) {
		this.titleOne = titleOne;
	}

	/**
	 * 获取标题2
	 */
	public String getTitleTwo() {
		return titleTwo;
	}

	/**
	 * 设置标题2
	 */
	public void setTitleTwo(String titleTwo) {
		this.titleTwo = titleTwo;
	}

	/**
	 * 获取图片地址
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * 设置图片地址
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * 获取显示内容类型编号
	 */
	public String getContentTypeId() {
		return contentTypeId;
	}

	/**
	 * 设置显示内容类型编号
	 */
	public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	/**
	 * 获取内容对应模块编号
	 */
	public String getContentId() {
		return contentId;
	}

	/**
	 * 设置内容对应模块编号
	 */
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	/**
	 * 获取所属栏目编号
	 */
	public String getColumnId() {
		return columnId;
	}

	/**
	 * 设置所属栏目编号
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
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

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getJumpType() {
		return jumpType;
	}

	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}

	public String getElementKey() {
		return elementKey;
	}

	public void setElementKey(String elementKey) {
		this.elementKey = elementKey;
	}
}
