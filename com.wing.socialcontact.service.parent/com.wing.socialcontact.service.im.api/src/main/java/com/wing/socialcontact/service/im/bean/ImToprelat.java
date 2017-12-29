package com.wing.socialcontact.service.im.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_IM_TOPRELAT 置顶关系表
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:12:38
 * @version 1.0
 */
@Table(name = "TJY_IM_TOPRELAT")
public class ImToprelat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 主体ID（群id或用户id） */
	private String subjectId;

    /** 主体类型（1、用户、2、群） */
	private Integer subjectType;

    /** 关系用户ID */
	private String relatUser;

    /** 置顶时间 */
	private Date topTime;

	public ImToprelat(){}


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
	 * 获取主体ID（群id或用户id）
	 */
	public String getSubjectId() {
    	return subjectId;
    }
  	
	/**
	 * 设置主体ID（群id或用户id）
	 */
	public void setSubjectId(String subjectId) {
    	this.subjectId = subjectId;
    }

	/**
	 * 获取主体类型（1、用户、2、群）
	 */
	public Integer getSubjectType() {
    	return subjectType;
    }
  	
	/**
	 * 设置主体类型（1、用户、2、群）
	 */
	public void setSubjectType(Integer subjectType) {
    	this.subjectType = subjectType;
    }

	/**
	 * 获取关系用户ID
	 */
	public String getRelatUser() {
    	return relatUser;
    }
  	
	/**
	 * 设置关系用户ID
	 */
	public void setRelatUser(String relatUser) {
    	this.relatUser = relatUser;
    }

	/**
	 * 获取置顶时间
	 */
	public Date getTopTime() {
    	return topTime;
    }
  	
	/**
	 * 设置置顶时间
	 */
	public void setTopTime(Date topTime) {
    	this.topTime = topTime;
    }
}
