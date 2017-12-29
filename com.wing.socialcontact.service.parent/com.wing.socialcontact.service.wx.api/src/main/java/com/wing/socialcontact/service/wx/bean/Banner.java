package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_BANNER 轮播图
 * 
 * @author zhangfan
 * @date 2017-03-31 17:43:02
 * @version 1.0
 */
@Table(name = "TJY_BANNER")
public class Banner implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 标题 */
	private String title;

    /** 跳转页面 */
	private String jumpUrl;

    /** 排序 */
	private Integer orderNum;

    /** 图片地址 */
	private String picPath;

    /** 展示位置 */
	private String columnType;

	/** 跳转类型（1：H5 2：native） */
	private String jumpType;

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
	
	 /** 用户范围（1全部用户2指定用户） */
	private Integer userRange;

    /** 用户等级（指定用户使用） */
	private String userLevel;
	
	private String userLevelName;

    /** 认证用户是否可见（1：可见2：不可见） */
	private Integer reconUserFlag;

    /** 注册用户是否可见（1：可见2：不可见） */
	private Integer regUserFlag;
	
	/** 是否为新手引导页   0：否      1：是    */
	private int isguide;
	
	

	public int getIsguide() {
		return isguide;
	}


	public void setIsguide(int isguide) {
		this.isguide = isguide;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Banner(){}


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
	 * 获取标题
	 */
	public String getTitle() {
    	return title;
    }
  	
	/**
	 * 设置标题
	 */
	public void setTitle(String title) {
    	this.title = title;
    }

	/**
	 * 获取跳转页面
	 */
	public String getJumpUrl() {
    	return jumpUrl;
    }
  	
	/**
	 * 设置跳转页面
	 */
	public void setJumpUrl(String jumpUrl) {
    	this.jumpUrl = jumpUrl;
    }

	/**
	 * 获取排序
	 */
	public Integer getOrderNum() {
    	return orderNum;
    }
  	
	/**
	 * 设置排序
	 */
	public void setOrderNum(Integer orderNum) {
    	this.orderNum = orderNum;
    }

	/**
	 * 获取图片地址
	 */
	public String getPicPath() {
    	return picPath;
    }
  	
	/**
	 * 设置图片地址
	 */
	public void setPicPath(String picPath) {
    	this.picPath = picPath;
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


	public String getColumnType() {
		return columnType;
	}


	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}


	public Integer getUserRange() {
		return userRange;
	}


	public void setUserRange(Integer userRange) {
		this.userRange = userRange;
	}


	public String getUserLevel() {
		return userLevel;
	}


	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}


	public String getUserLevelName() {
		return userLevelName;
	}


	public void setUserLevelName(String userLevelName) {
		this.userLevelName = userLevelName;
	}


	public Integer getReconUserFlag() {
		return reconUserFlag;
	}


	public void setReconUserFlag(Integer reconUserFlag) {
		this.reconUserFlag = reconUserFlag;
	}


	public Integer getRegUserFlag() {
		return regUserFlag;
	}


	public void setRegUserFlag(Integer regUserFlag) {
		this.regUserFlag = regUserFlag;
	}


	public String getJumpType() {
		return jumpType;
	}

	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}
}
