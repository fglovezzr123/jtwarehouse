package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 聚合页面-内容类型表
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:28
 * @version 1.0
 */
@Table(name = "TJY_PAGE_CONTENT_TYPE")
public class PageContentType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /**  */
	private String name;

    /** 列表页地址 */
	private String listUrl;

    /** 详情页面url(示例：xxxxx/detialHy?id=:id) */
	private String detailUrl;

	/** key **/
	private String contentKey;

    /** 排序 */
	private Integer orderNum;

	public PageContentType(){}

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
	 * 获取
	 */
	public String getName() {
    	return name;
    }
  	
	/**
	 * 设置
	 */
	public void setName(String name) {
    	this.name = name;
    }

	/**
	 * 获取列表页地址
	 */
	public String getListUrl() {
    	return listUrl;
    }
  	
	/**
	 * 设置列表页地址
	 */
	public void setListUrl(String listUrl) {
    	this.listUrl = listUrl;
    }

	/**
	 * 获取详情页面url(示例：xxxxx/detialHy?id=:id)
	 */
	public String getDetailUrl() {
    	return detailUrl;
    }
  	
	/**
	 * 设置详情页面url(示例：xxxxx/detialHy?id=:id)
	 */
	public void setDetailUrl(String detailUrl) {
    	this.detailUrl = detailUrl;
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

	public String getContentKey() {
		return contentKey;
	}

	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}
}
