package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_USER_EMPIRICAL 用户经验等级表
 * 
 * @author gaojun
 * @date 2017-07-05 10:32:01
 * @version 1.0
 */
@Table(name = "TJY_USER_EMPIRICAL")
public class UserEmpirical implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 等级 */
	private String level;

    /** 等级数 */
	private Integer levelNo;

    /** 最低经验值 */
	private Integer empiricalLow;

    /** 最高经验值 */
	private Integer empiricalHigh;

    /** 积分 */
	private Integer integralTotal;

    /** 等级规则说明 */
	private String remark;

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

    /** 等级状态（预留） */
	private String empiricalStatus;

    /** 打招呼数量限制 */
	private Integer greetingsCount;

	public UserEmpirical(){}


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
	 * 获取等级
	 */
	public String getLevel() {
    	return level;
    }
  	
	/**
	 * 设置等级
	 */
	public void setLevel(String level) {
    	this.level = level;
    }

	/**
	 * 获取等级数
	 */
	public Integer getLevelNo() {
    	return levelNo;
    }
  	
	/**
	 * 设置等级数
	 */
	public void setLevelNo(Integer levelNo) {
    	this.levelNo = levelNo;
    }

	/**
	 * 获取最低经验值
	 */
	public Integer getEmpiricalLow() {
    	return empiricalLow;
    }
  	
	/**
	 * 设置最低经验值
	 */
	public void setEmpiricalLow(Integer empiricalLow) {
    	this.empiricalLow = empiricalLow;
    }

	/**
	 * 获取最高经验值
	 */
	public Integer getEmpiricalHigh() {
    	return empiricalHigh;
    }
  	
	/**
	 * 设置最高经验值
	 */
	public void setEmpiricalHigh(Integer empiricalHigh) {
    	this.empiricalHigh = empiricalHigh;
    }

	/**
	 * 获取积分
	 */
	public Integer getIntegralTotal() {
    	return integralTotal;
    }
  	
	/**
	 * 设置积分
	 */
	public void setIntegralTotal(Integer integralTotal) {
    	this.integralTotal = integralTotal;
    }

	/**
	 * 获取等级规则说明
	 */
	public String getRemark() {
    	return remark;
    }
  	
	/**
	 * 设置等级规则说明
	 */
	public void setRemark(String remark) {
    	this.remark = remark;
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

	/**
	 * 获取等级状态（预留）
	 */
	public String getEmpiricalStatus() {
    	return empiricalStatus;
    }
  	
	/**
	 * 设置等级状态（预留）
	 */
	public void setEmpiricalStatus(String empiricalStatus) {
    	this.empiricalStatus = empiricalStatus;
    }

	/**
	 * 获取打招呼数量限制
	 */
	public Integer getGreetingsCount() {
    	return greetingsCount;
    }
  	
	/**
	 * 设置打招呼数量限制
	 */
	public void setGreetingsCount(Integer greetingsCount) {
    	this.greetingsCount = greetingsCount;
    }
}
