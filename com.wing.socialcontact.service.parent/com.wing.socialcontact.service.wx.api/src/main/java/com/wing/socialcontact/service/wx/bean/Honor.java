package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_HONOR 郧章
 * 
 * @author gaojun
 * @date 2017-04-14 22:34:35
 * @version 1.0
 */
@Table(name = "TJY_HONOR")
public class Honor implements Serializable{
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

    /** 排序 */
	private Integer orderNum;

    /** 图片地址 */
	private String picPath;
	
	/** 图片地址 */
	private String picPath2;

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
	
	/** 详情 */
	private String remark;
	
	/** 勋章类型（1：认证勋章 2：互助宝勋章3：平台活动勋章）*/
	private String hornorType;
	
	/** 勋章定义（触发条件） */
	private String honorConfig;
	
	/**勋章编码*/
	private String honorCode;

	public Honor(){}


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
	
	
	

	public String getPicPath2() {
		return picPath2;
	}


	public void setPicPath2(String picPath2) {
		this.picPath2 = picPath2;
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
	

    public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getHornorType() {
		return hornorType;
	}


	public void setHornorType(String hornorType) {
		this.hornorType = hornorType;
	}


	public String getHonorConfig() {
		return honorConfig;
	}


	public void setHonorConfig(String honorConfig) {
		this.honorConfig = honorConfig;
	}


	public String getHonorCode() {
		return honorCode;
	}


	public void setHonorCode(String honorCode) {
		this.honorCode = honorCode;
	}
	
	
}
