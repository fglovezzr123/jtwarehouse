package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_TEMPLATE 消息模块表
 * 
 * @author liangwj
 * @date 2017-03-27 11:22:42
 * @version 1.0
 */
@Table(name = "TJY_TEMPLATE")
public class Template implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
    
	private String id;

    /** 消息内容 */
	private String content;

    /** 消息描述 */
	private String info;

    /** 模板标识 */
	private String mark;

    /** 模板标题 */
	private String title;

    /** 是否禁用 1：为禁用 0为可用 */
	private Integer isOpen;

    /** 消息类型 1：短信 2：微信 */
	private Integer type;

    /** 创建时间 */
	private Date createTime;

    /** 创建人ID */
	private String createUserId;

    /** 创建Name */
	private String createUserName;

    /** 修改时间 */
	private Date updateTime;

    /** 修改人ID */
	private String updateUserId;

    /** 修改Name */
	private String updateUserName;

    /** 逻辑删除标识 */
	private Integer deleted;

	public Template(){}


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
	 * 获取消息内容
	 */
	public String getContent() {
    	return content;
    }
  	
	/**
	 * 设置消息内容
	 */
	public void setContent(String content) {
    	this.content = content;
    }

	/**
	 * 获取消息描述
	 */
	public String getInfo() {
    	return info;
    }
  	
	/**
	 * 设置消息描述
	 */
	public void setInfo(String info) {
    	this.info = info;
    }

	/**
	 * 获取模板标识
	 */
	public String getMark() {
    	return mark;
    }
  	
	/**
	 * 设置模板标识
	 */
	public void setMark(String mark) {
    	this.mark = mark;
    }

	/**
	 * 获取模板标题
	 */
	public String getTitle() {
    	return title;
    }
  	
	/**
	 * 设置模板标题
	 */
	public void setTitle(String title) {
    	this.title = title;
    }

	/**
	 * 获取是否禁用 1：为禁用 0为可用
	 */
	public Integer getIsOpen() {
    	return isOpen;
    }
  	
	/**
	 * 设置是否禁用 1：为禁用 0为可用
	 */
	public void setIsOpen(Integer isOpen) {
    	this.isOpen = isOpen;
    }

	/**
	 * 获取消息类型 1：短信 2：微信
	 */
	public Integer getType() {
    	return type;
    }
  	
	/**
	 * 设置消息类型 1：短信 2：微信
	 */
	public void setType(Integer type) {
    	this.type = type;
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
	 * 获取创建人ID
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置创建人ID
	 */
	public void setCreateUserId(String createUserId) {
    	this.createUserId = createUserId;
    }

	/**
	 * 获取创建Name
	 */
	public String getCreateUserName() {
    	return createUserName;
    }
  	
	/**
	 * 设置创建Name
	 */
	public void setCreateUserName(String createUserName) {
    	this.createUserName = createUserName;
    }

	/**
	 * 获取修改时间
	 */
	public Date getUpdateTime() {
    	return updateTime;
    }
  	
	/**
	 * 设置修改时间
	 */
	public void setUpdateTime(Date updateTime) {
    	this.updateTime = updateTime;
    }

	/**
	 * 获取修改人ID
	 */
	public String getUpdateUserId() {
    	return updateUserId;
    }
  	
	/**
	 * 设置修改人ID
	 */
	public void setUpdateUserId(String updateUserId) {
    	this.updateUserId = updateUserId;
    }

	/**
	 * 获取修改Name
	 */
	public String getUpdateUserName() {
    	return updateUserName;
    }
  	
	/**
	 * 设置修改Name
	 */
	public void setUpdateUserName(String updateUserName) {
    	this.updateUserName = updateUserName;
    }

	/**
	 * 获取逻辑删除标识
	 */
	public Integer getDeleted() {
    	return deleted;
    }
  	
	/**
	 * 设置逻辑删除标识
	 */
	public void setDeleted(Integer deleted) {
    	this.deleted = deleted;
    }
}
