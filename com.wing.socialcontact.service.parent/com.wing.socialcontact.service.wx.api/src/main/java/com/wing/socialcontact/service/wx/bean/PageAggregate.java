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
 * 聚合页面-主表
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:28
 * @version 1.0
 */
@Table(name = "TJY_PAGE_AGGREGATE")
public class PageAggregate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	/** 页面名称 */
	private String pageName;

	/** 页面访问地址 */
	private String pageUrl;

	/** 是否有轮播图 */
	private Integer lbtFlag;

	/** 轮播图关联编号 */
	private String lbtId;

	/** 是否有快捷入口 */
	private Integer kjrkFlag;

	/** 状态（1启用0停用） */
	private Integer status;

	/** 停用跳转url */
	private String tyUrl;

	/** 创建时间 */
	private Date createTime;

	/** 创建人 */
	private String createUserId;

	/** 最后修改时间 */
	private Date updateTime;

	/** 最后修改人 */
	private String updateUserId;
	
	/**
	 * 使用类型，1：微信端使用 2：app使用 
	 */
	private Integer pageType;
	
	@Transient
	private List<Banner> bannerList;


	@Transient
	private List<PageQuickEntry> pageQuickEntryList;

	@Transient
	private List<PageColumn> pageColumnList;
	
	@Transient
	private String pageColumnJsonStr;

	public PageAggregate() {
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
	 * 获取页面名称
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * 设置页面名称
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * 获取页面访问地址
	 */
	public String getPageUrl() {
		return pageUrl;
	}

	/**
	 * 设置页面访问地址
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	/**
	 * 获取是否有轮播图
	 */
	public Integer getLbtFlag() {
		return lbtFlag;
	}

	/**
	 * 设置是否有轮播图
	 */
	public void setLbtFlag(Integer lbtFlag) {
		this.lbtFlag = lbtFlag;
	}

	/**
	 * 获取轮播图关联编号
	 */
	public String getLbtId() {
		return lbtId;
	}

	/**
	 * 设置轮播图关联编号
	 */
	public void setLbtId(String lbtId) {
		this.lbtId = lbtId;
	}

	/**
	 * 获取是否有快捷入口
	 */
	public Integer getKjrkFlag() {
		return kjrkFlag;
	}

	/**
	 * 设置是否有快捷入口
	 */
	public void setKjrkFlag(Integer kjrkFlag) {
		this.kjrkFlag = kjrkFlag;
	}

	/**
	 * 获取状态（1启用0停用）
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置状态（1启用0停用）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取停用跳转url
	 */
	public String getTyUrl() {
		return tyUrl;
	}

	/**
	 * 设置停用跳转url
	 */
	public void setTyUrl(String tyUrl) {
		this.tyUrl = tyUrl;
	}

	/**
	 * 获取创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取创建人
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 * 设置创建人
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * 获取最后修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置最后修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取最后修改人
	 */
	public String getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * 设置最后修改人
	 */
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public List<PageQuickEntry> getPageQuickEntryList() {
		return pageQuickEntryList;
	}

	public void setPageQuickEntryList(List<PageQuickEntry> pageQuickEntryList) {
		this.pageQuickEntryList = pageQuickEntryList;
	}

	public List<PageColumn> getPageColumnList() {
		return pageColumnList;
	}

	public void setPageColumnList(List<PageColumn> pageColumnList) {
		this.pageColumnList = pageColumnList;
	}

	public List<Banner> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<Banner> bannerList) {
		this.bannerList = bannerList;
	}

	public String getPageColumnJsonStr() {
		return pageColumnJsonStr;
	}

	public void setPageColumnJsonStr(String pageColumnJsonStr) {
		this.pageColumnJsonStr = pageColumnJsonStr;
	}

	public Integer getPageType() {
		return pageType;
	}

	public void setPageType(Integer pageType) {
		this.pageType = pageType;
	}
}
