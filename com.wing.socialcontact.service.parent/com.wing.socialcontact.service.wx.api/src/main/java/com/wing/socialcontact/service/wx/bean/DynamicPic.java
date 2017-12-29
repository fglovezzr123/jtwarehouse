package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_DYNAMIC_PIC 用户动态图片
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:06
 * @version 1.0
 */
@Table(name = "TJY_DYNAMIC_PIC")
public class DynamicPic implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 动态ID */
	private String dynamicId;

    /** 用户ID */
	private String userId;

    /** 图片路径 */
	private String picUrl;

    /** 排序号 */
	private Double sortNum;

    /** 创建时间 */
	private Date createTime;

	public DynamicPic(){}


	/**
	 * 获取主键
	 */
	public String getId() {
    	return id;
    }
  	
	/**
	 * 设置主键
	 */
	public void setId(String id) {
    	this.id = id;
    }

	/**
	 * 获取动态ID
	 */
	public String getDynamicId() {
    	return dynamicId;
    }
  	
	/**
	 * 设置动态ID
	 */
	public void setDynamicId(String dynamicId) {
    	this.dynamicId = dynamicId;
    }

	/**
	 * 获取用户ID
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置用户ID
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取图片路径
	 */
	public String getPicUrl() {
    	return picUrl;
    }
  	
	/**
	 * 设置图片路径
	 */
	public void setPicUrl(String picUrl) {
    	this.picUrl = picUrl;
    }

	/**
	 * 获取排序号
	 */
	public Double getSortNum() {
    	return sortNum;
    }
  	
	/**
	 * 设置排序号
	 */
	public void setSortNum(Double sortNum) {
    	this.sortNum = sortNum;
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
}
