package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_BUSINESS_DISSCUSS 合作商洽表
 * 
 * @author zhangfan
 * @date 2017-04-21 10:59:49
 * @version 1.0
 */
@Table(name = "TJY_BUSINESS_DISSCUSS")
public class BusinessDisscuss implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 商洽表id */
	private String fkId;

    /** 状态 1显示 2不显示 */
	private Integer isShow;

    /** 内容 */
	private String content;

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
	
	private Integer isAccept;
	
	private String parentId;

	public BusinessDisscuss(){}


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
	 * 获取商洽表id
	 */
	public String getFkId() {
    	return fkId;
    }
  	
	/**
	 * 设置商洽表id
	 */
	public void setFkId(String fkId) {
    	this.fkId = fkId;
    }

	/**
	 * 获取状态 1显示 2不显示
	 */
	public Integer getIsShow() {
    	return isShow;
    }
  	
	/**
	 * 设置状态 1显示 2不显示
	 */
	public void setIsShow(Integer isShow) {
    	this.isShow = isShow;
    }

	/**
	 * 获取内容
	 */
	public String getContent() {
    	return content;
    }
  	
	/**
	 * 设置内容
	 */
	public void setContent(String content) {
    	this.content = content;
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


	public Integer getIsAccept() {
		return isAccept;
	}


	public void setIsAccept(Integer isAccept) {
		this.isAccept = isAccept;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
