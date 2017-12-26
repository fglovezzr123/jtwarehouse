package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_CONFIG 
 * 
 * @author gaojun
 * @date 2017-09-18 15:40:53
 * @version 1.0
 */
@Table(name = "TJY_CONFIG")
public class Config implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 分类 */
	private Integer type;

    /** 状态 */
	private Integer status;

    /** 创建时间 */
	private Date createTime;

    /** 创建用户id */
	private String createUserId;

    /** 创建用户名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deleted;

    /**  */
	private String config1;

    /**  */
	private String config2;

    /**  */
	private String config3;

    /**  */
	private String config4;

    /**  */
	private String config5;

    /**  */
	private String config6;

    /**  */
	private String remark;

	public Config(){}


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
	 * 获取咨讯分类（关联数据字典）
	 */
	public Integer getType() {
    	return type;
    }
  	
	/**
	 * 设置咨讯分类（关联数据字典）
	 */
	public void setType(Integer type) {
    	this.type = type;
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

	/**
	 * 获取
	 */
	public String getConfig1() {
    	return config1;
    }
  	
	/**
	 * 设置
	 */
	public void setConfig1(String config1) {
    	this.config1 = config1;
    }

	/**
	 * 获取
	 */
	public String getConfig2() {
    	return config2;
    }
  	
	/**
	 * 设置
	 */
	public void setConfig2(String config2) {
    	this.config2 = config2;
    }

	/**
	 * 获取
	 */
	public String getConfig3() {
    	return config3;
    }
  	
	/**
	 * 设置
	 */
	public void setConfig3(String config3) {
    	this.config3 = config3;
    }

	/**
	 * 获取
	 */
	public String getConfig4() {
    	return config4;
    }
  	
	/**
	 * 设置
	 */
	public void setConfig4(String config4) {
    	this.config4 = config4;
    }

	/**
	 * 获取
	 */
	public String getConfig5() {
    	return config5;
    }
  	
	/**
	 * 设置
	 */
	public void setConfig5(String config5) {
    	this.config5 = config5;
    }

	/**
	 * 获取
	 */
	public String getConfig6() {
    	return config6;
    }
  	
	/**
	 * 设置
	 */
	public void setConfig6(String config6) {
    	this.config6 = config6;
    }

	/**
	 * 获取
	 */
	public String getRemark() {
    	return remark;
    }
  	
	/**
	 * 设置
	 */
	public void setRemark(String remark) {
    	this.remark = remark;
    }
}
