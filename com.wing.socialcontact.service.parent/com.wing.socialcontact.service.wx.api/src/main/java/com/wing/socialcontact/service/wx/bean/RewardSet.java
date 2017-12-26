package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_REWARD_SET 悬赏设置表
 * 
 * @author zhangfan
 * @date 2017-06-15 12:03:43
 * @version 1.0
 */
@Table(name = "TJY_REWARD_SET")
public class RewardSet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 悬赏最低金额 */
	private Integer minReward;

    /** 平台收费比例 */
	private Double chargePer;

    /** 创建时间 */
	private Date createTime;

    /** 发布人id */
	private String createUserId;

    /** 创建用户名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deleted;

	public RewardSet(){}


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
	 * 获取悬赏最低金额
	 */
	public Integer getMinReward() {
    	return minReward;
    }
  	
	/**
	 * 设置悬赏最低金额
	 */
	public void setMinReward(Integer minReward) {
    	this.minReward = minReward;
    }

	/**
	 * 获取平台收费比例
	 */
	public Double getChargePer() {
    	return chargePer;
    }
  	
	/**
	 * 设置平台收费比例
	 */
	public void setChargePer(Double chargePer) {
    	this.chargePer = chargePer;
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
	 * 获取发布人id
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置发布人id
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
