package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_REWARD_CLASS 悬赏类别信息表
 * 
 * @author zhangfan
 * @date 2017-06-12 13:25:02
 * @version 1.0
 */
@Table(name = "TJY_REWARD_CLASS")
public class RewardClass implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 行业名称 */
	private String className;

    /** 序号 */
	private Integer classNum;

    /** 是否推荐，1推荐 2不推荐 */
	private Integer isRecommend;

    /** 状态，1审核中2审核通过3审核不通过 */
	private Integer status;

    /** 创建时间 */
	private Date createTime;

    /** 创建用户id */
	private String createUserId;

    /** 创建用户名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deleted;
	
	private String imagePath;

	public RewardClass(){}


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
	 * 获取行业名称
	 */
	public String getClassName() {
    	return className;
    }
  	
	/**
	 * 设置行业名称
	 */
	public void setClassName(String className) {
    	this.className = className;
    }

	/**
	 * 获取序号
	 */
	public Integer getClassNum() {
    	return classNum;
    }
  	
	/**
	 * 设置序号
	 */
	public void setClassNum(Integer classNum) {
    	this.classNum = classNum;
    }

	/**
	 * 获取是否推荐，1推荐 2不推荐
	 */
	public Integer getIsRecommend() {
    	return isRecommend;
    }
  	
	/**
	 * 设置是否推荐，1推荐 2不推荐
	 */
	public void setIsRecommend(Integer isRecommend) {
    	this.isRecommend = isRecommend;
    }

	/**
	 * 获取状态，1审核中2审核通过3审核不通过
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置状态，1审核中2审核通过3审核不通过
	 */
	public void setStatus(Integer status) {
    	this.status = status;
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


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
