package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_USER_INTEGRAL_LOG 用户积分明细表
 * 
 * @author gaojun
 * @date 2017-07-04 15:30:55
 * @version 1.0
 */
@Table(name = "TJY_USER_INTEGRAL_LOG")
public class UserIntegralLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 任务执行人 */
	private String userId;

    /** 任务编码 */
	private String taskCode;

    /** 任务体系 */
	private String taskName;

    /** 保分值类型（1：+ 2：-） */
	private String integralType;

    /** 积分 */
	private Integer integral;

    /** 用户积分 */
	private Integer yeIntegral;

    /** 说明 */
	private String integralExplain;

    /** 备注 */
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

	public UserIntegralLog(){}


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
	 * 获取任务执行人
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置任务执行人
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取任务编码
	 */
	public String getTaskCode() {
    	return taskCode;
    }
  	
	/**
	 * 设置任务编码
	 */
	public void setTaskCode(String taskCode) {
    	this.taskCode = taskCode;
    }

	/**
	 * 获取任务体系
	 */
	public String getTaskName() {
    	return taskName;
    }
  	
	/**
	 * 设置任务体系
	 */
	public void setTaskName(String taskName) {
    	this.taskName = taskName;
    }

	/**
	 * 获取保分值类型（1：+ 2：-）
	 */
	public String getIntegralType() {
    	return integralType;
    }
  	
	/**
	 * 设置保分值类型（1：+ 2：-）
	 */
	public void setIntegralType(String integralType) {
    	this.integralType = integralType;
    }

	/**
	 * 获取积分
	 */
	public Integer getIntegral() {
    	return integral;
    }
  	
	/**
	 * 设置积分
	 */
	public void setIntegral(Integer integral) {
    	this.integral = integral;
    }

	/**
	 * 获取用户积分
	 */
	public Integer getYeIntegral() {
    	return yeIntegral;
    }
  	
	/**
	 * 设置用户积分
	 */
	public void setYeIntegral(Integer yeIntegral) {
    	this.yeIntegral = yeIntegral;
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
	 * 获取备注
	 */
	public String getRemark() {
    	return remark;
    }
  	
	/**
	 * 设置备注
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
