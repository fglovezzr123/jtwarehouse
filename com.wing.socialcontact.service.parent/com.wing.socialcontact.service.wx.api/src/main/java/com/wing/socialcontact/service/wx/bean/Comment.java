package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * TJY_COMMENT 评论信息表
 * 
 * @author liangwj
 * @date 2017-03-27 09:01:08
 * @version 1.0
 */
@Table(name = "TJY_COMMENT")
public class Comment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 上一级评论ID */
	private String parentId;

    /** 评论人ID */
	private String userId;

    /** 评论类别对应表的主键(如话题表ID) */
	private String fkId;

    /** 评论类别(如话题PK) */
	private String cmeType;//1:资讯   2：合作 3：话题  4：活动 5：动态  6悬赏 7文章

    /** 评论内容 */
	private String cmeDesc;

    /** 是否匿名评论，0否1是 */
	private Integer anonymous;

    /** 图片url */
	private String imgUrl;

    /** 状态 */
	private Integer status;

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

	/** 点赞数 */
	@Transient
	private Integer ThumbUpCount;

	/** 是否点赞 */
	@Transient
	private Integer isThumbUp;

	public Comment(){}

	public Integer getThumbUpCount() {
		return ThumbUpCount;
	}

	public void setThumbUpCount(Integer thumbUpCount) {
		ThumbUpCount = thumbUpCount;
	}

	public Integer getIsThumbUp() {
		return isThumbUp;
	}

	public void setIsThumbUp(Integer isThumbUp) {
		this.isThumbUp = isThumbUp;
	}

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
	 * 获取上一级评论ID
	 */
	public String getParentId() {
    	return parentId;
    }
  	
	/**
	 * 设置上一级评论ID
	 */
	public void setParentId(String parentId) {
    	this.parentId = parentId;
    }

	/**
	 * 获取评论人ID
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置评论人ID
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取评论类别对应表的主键(如话题表ID)
	 */
	public String getFkId() {
    	return fkId;
    }
  	
	/**
	 * 设置评论类别对应表的主键(如话题表ID)
	 */
	public void setFkId(String fkId) {
    	this.fkId = fkId;
    }

	/**
	 * 获取评论类别(如话题PK)
	 */
	public String getCmeType() {
    	return cmeType;
    }
  	
	/**
	 * 设置评论类别(如话题PK)
	 */
	public void setCmeType(String cmeType) {
    	this.cmeType = cmeType;
    }

	/**
	 * 获取评论内容
	 */
	public String getCmeDesc() {
    	return cmeDesc;
    }
  	
	/**
	 * 设置评论内容
	 */
	public void setCmeDesc(String cmeDesc) {
    	this.cmeDesc = cmeDesc;
    }

	/**
	 * 获取是否匿名评论，0否1是
	 */
	public Integer getAnonymous() {
    	return anonymous;
    }
  	
	/**
	 * 设置是否匿名评论，0否1是
	 */
	public void setAnonymous(Integer anonymous) {
    	this.anonymous = anonymous;
    }

	/**
	 * 获取图片url
	 */
	public String getImgUrl() {
    	return imgUrl;
    }
  	
	/**
	 * 设置图片url
	 */
	public void setImgUrl(String imgUrl) {
    	this.imgUrl = imgUrl;
    }

	/**
	 * 获取状态
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置状态
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
}
