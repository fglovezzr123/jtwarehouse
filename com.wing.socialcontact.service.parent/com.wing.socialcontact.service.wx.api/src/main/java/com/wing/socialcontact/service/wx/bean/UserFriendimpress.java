package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_USER_FRIENDIMPRESS 用户好友印象
 * 
 * @author gaojun
 * @date 2017-04-14 22:34:36
 * @version 1.0
 */
@Table(name = "TJY_USER_FRIENDIMPRESS")
public class UserFriendimpress implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 用户主键 */
	private String userId;

    /** 喜好编码(关联字典表) */
	private String impressId;

    /** 印象数量 */
	private Integer impressCount;

    /** 排序 */
	private Integer sortno;

    /** 印象类型：0：朋友印象 1：我需要  2:我能提供的 */
	private Integer type;

    /** 创建时间 */
	private Date createTime;

    /** 创建用户id */
	private String createUserId;

    /** 创建用户名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deleted;

	public UserFriendimpress(){}


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
	 * 获取用户主键
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置用户主键
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取喜好编码(关联字典表)
	 */
	public String getImpressId() {
    	return impressId;
    }
  	
	/**
	 * 设置喜好编码(关联字典表)
	 */
	public void setImpressId(String impressId) {
    	this.impressId = impressId;
    }

	/**
	 * 获取印象数量
	 */
	public Integer getImpressCount() {
    	return impressCount;
    }
  	
	/**
	 * 设置印象数量
	 */
	public void setImpressCount(Integer impressCount) {
    	this.impressCount = impressCount;
    }

	/**
	 * 获取排序
	 */
	public Integer getSortno() {
    	return sortno;
    }
  	
	/**
	 * 设置排序
	 */
	public void setSortno(Integer sortno) {
    	this.sortno = sortno;
    }

	/**
	 * 获取印象类型：0：朋友印象 1：我需要  2:我能提供的
	 */
	public Integer getType() {
    	return type;
    }
  	
	/**
	 * 设置印象类型：0：朋友印象 1：我需要  2:我能提供的
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
	 * 获取创建用户id
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置创建用户id
	 */
	public void setCreateUserId(String createUserId) {
    	this.createUserId = createUserId;
    }

	/**
	 * 获取创建用户名称
	 */
	public String getCreateUserName() {
    	return createUserName;
    }
  	
	/**
	 * 设置创建用户名称
	 */
	public void setCreateUserName(String createUserName) {
    	this.createUserName = createUserName;
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
