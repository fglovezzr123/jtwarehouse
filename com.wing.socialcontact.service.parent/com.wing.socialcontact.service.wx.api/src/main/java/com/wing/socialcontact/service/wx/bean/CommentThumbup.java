package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_COMMENT_THUMBUP 评论点赞表
 * 
 * @author liangwj
 * @date 2017-04-04 15:00:56
 * @version 1.0
 */
@Table(name = "TJY_COMMENT_THUMBUP")
public class CommentThumbup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** wxopenid  */
	private String openId;

    /** 点赞人  */
	private String userId;

    /** 主题id  */
	private String pId;

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

	public CommentThumbup(){}


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
	 * 获取wxopenid 
	 */
	public String getOpenId() {
    	return openId;
    }
  	
	/**
	 * 设置wxopenid 
	 */
	public void setOpenId(String openId) {
    	this.openId = openId;
    }

	/**
	 * 获取点赞人 
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置点赞人 
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取主题id 
	 */
	public String getPId() {
    	return pId;
    }
  	
	/**
	 * 设置主题id 
	 */
	public void setPId(String pId) {
    	this.pId = pId;
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
