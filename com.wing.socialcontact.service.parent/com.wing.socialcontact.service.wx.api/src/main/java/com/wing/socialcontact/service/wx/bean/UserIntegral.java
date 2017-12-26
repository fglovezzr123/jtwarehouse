package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_USER_INTEGRAL 用户积分表
 * 
 * @author gaojun
 * @date 2017-07-04 15:30:54
 * @version 1.0
 */
@Table(name = "TJY_USER_INTEGRAL")
public class UserIntegral implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 任务编码 */
	private String code;

    /** 任务体系 */
	private String taskSystem;

    /** 经验值 */
	private Integer empiricalTotal;

    /** 积分 */
	private Integer integralTotal;

    /** 说明 */
	private String integralExplain;

    /** 任务规则说明 */
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

    /** 任务状态（预留） */
	private String taskStatus;

	public UserIntegral(){}


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
	 * 获取任务编码
	 */
	public String getCode() {
    	return code;
    }
  	
	/**
	 * 设置任务编码
	 */
	public void setCode(String code) {
    	this.code = code;
    }

	/**
	 * 获取任务体系
	 */
	public String getTaskSystem() {
    	return taskSystem;
    }
  	
	/**
	 * 设置任务体系
	 */
	public void setTaskSystem(String taskSystem) {
    	this.taskSystem = taskSystem;
    }

	/**
	 * 获取经验值
	 */
	public Integer getEmpiricalTotal() {
    	return empiricalTotal;
    }
  	
	/**
	 * 设置经验值
	 */
	public void setEmpiricalTotal(Integer empiricalTotal) {
    	this.empiricalTotal = empiricalTotal;
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
	 * 获取说明
	 */
	public String getIntegralExplain() {
    	return integralExplain;
    }
  	
	/**
	 * 设置说明
	 */
	public void setIntegralExplain(String integralExplain) {
    	this.integralExplain = integralExplain;
    }

	/**
	 * 获取任务规则说明
	 */
	public String getRemark() {
    	return remark;
    }
  	
	/**
	 * 设置任务规则说明
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
	 * 获取任务状态（预留）
	 */
	public String getTaskStatus() {
    	return taskStatus;
    }
  	
	/**
	 * 设置任务状态（预留）
	 */
	public void setTaskStatus(String taskStatus) {
    	this.taskStatus = taskStatus;
    }
}
