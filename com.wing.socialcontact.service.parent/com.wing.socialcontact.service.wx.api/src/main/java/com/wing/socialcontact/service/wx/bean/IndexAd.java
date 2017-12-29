package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_INDEX_AD 
 * 
 * @author zengmin
 * @date 2017-07-07 15:45:48
 * @version 1.0
 */
@Table(name = "TJY_INDEX_AD")
public class IndexAd implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 类型 */
	private Integer adType;

    /** 用户范围（1全部用户2指定用户） */
	private Integer userRange;

    /** 用户等级（指定用户使用） */
	private String userLevel;
	
	private String userLevelName;

    /** 认证用户是否可见（1：可见2：不可见） */
	private Integer reconUserFlag;

    /** 注册用户是否可见（1：可见2：不可见） */
	private Integer regUserFlag;

    /** 幻灯片图片地址 */
	private String imgUrl;

    /** 幻灯片图片名称 */
	private String imgName;

    /** 幻灯片链接 */
	private String imgLink;

    /** 排序 */
	private Integer orderNum;

    /** 状态（1有效0无效） */
	private Integer status;

    /** 创建人 */
	private String createUserId;

    /** 创建时间 */
	private Date createTime;

	public IndexAd(){}


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
	 * 获取类型
	 */
	public Integer getAdType() {
    	return adType;
    }
  	
	/**
	 * 设置类型
	 */
	public void setAdType(Integer adType) {
    	this.adType = adType;
    }

	/**
	 * 获取用户范围（1全部用户2指定用户）
	 */
	public Integer getUserRange() {
    	return userRange;
    }
  	
	/**
	 * 设置用户范围（1全部用户2指定用户）
	 */
	public void setUserRange(Integer userRange) {
    	this.userRange = userRange;
    }

	/**
	 * 获取用户等级（指定用户使用）
	 */
	public String getUserLevel() {
    	return userLevel;
    }
  	
	/**
	 * 设置用户等级（指定用户使用）
	 */
	public void setUserLevel(String userLevel) {
    	this.userLevel = userLevel;
    }

	/**
	 * 获取认证用户是否可见（1：可见2：不可见）
	 */
	public Integer getReconUserFlag() {
    	return reconUserFlag;
    }
  	
	/**
	 * 设置认证用户是否可见（1：可见2：不可见）
	 */
	public void setReconUserFlag(Integer reconUserFlag) {
    	this.reconUserFlag = reconUserFlag;
    }

	/**
	 * 获取注册用户是否可见（1：可见2：不可见）
	 */
	public Integer getRegUserFlag() {
    	return regUserFlag;
    }
  	
	/**
	 * 设置注册用户是否可见（1：可见2：不可见）
	 */
	public void setRegUserFlag(Integer regUserFlag) {
    	this.regUserFlag = regUserFlag;
    }

	/**
	 * 获取幻灯片图片地址
	 */
	public String getImgUrl() {
    	return imgUrl;
    }
  	
	/**
	 * 设置幻灯片图片地址
	 */
	public void setImgUrl(String imgUrl) {
    	this.imgUrl = imgUrl;
    }

	/**
	 * 获取幻灯片图片名称
	 */
	public String getImgName() {
    	return imgName;
    }
  	
	/**
	 * 设置幻灯片图片名称
	 */
	public void setImgName(String imgName) {
    	this.imgName = imgName;
    }

	/**
	 * 获取幻灯片链接
	 */
	public String getImgLink() {
    	return imgLink;
    }
  	
	/**
	 * 设置幻灯片链接
	 */
	public void setImgLink(String imgLink) {
    	this.imgLink = imgLink;
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
	 * 获取状态（1有效0无效）
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置状态（1有效0无效）
	 */
	public void setStatus(Integer status) {
    	this.status = status;
    }

	/**
	 * 获取创建人
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置创建人
	 */
	public void setCreateUserId(String createUserId) {
    	this.createUserId = createUserId;
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


	public String getUserLevelName() {
		return userLevelName;
	}

	public void setUserLevelName(String userLevelName) {
		this.userLevelName = userLevelName;
	}
}
