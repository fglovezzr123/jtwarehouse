package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 聚合页面-快捷入口
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:29
 * @version 1.0
 */
@Table(name = "TJY_PAGE_QUICK_ENTRY")
public class PageQuickEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	/** 名称 */
	private String name;

	/** 链接地址 */
	private String linkUrl;

	/** 图片地址 */
	private String imgUrl;

	/** 排序 */
	private Integer orderNum;

	/** 所属页面编号 */
	private String pageId;

	public PageQuickEntry() {
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
	 * 获取名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取链接地址
	 */
	public String getLinkUrl() {
		return linkUrl;
	}

	/**
	 * 设置链接地址
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
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
	 * 获取所属页面编号
	 */
	public String getPageId() {
		return pageId;
	}

	/**
	 * 设置所属页面编号
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
}
