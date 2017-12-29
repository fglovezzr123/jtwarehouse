package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_REPORT 举报信息表
 * 
 * @author zhangfan
 * @date 2017-03-30 15:05:41
 * @version 1.0
 */
@Table(name = "TJY_REPORT")
public class Report implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 举报人id */
	private String userId;

    /** 举报类别对应表的主键(如话题表id) */
	private String fkId;

    /** 举报类别(如话题pk) */
	private String rtType;

    /** 创建时间 */
	private Date createTime;

    /** 逻辑删除标识 */
	private Integer deletd;
	
	/** 举报内容*/
	private String content;
	
	/** 话题状态 1显示 2不显示 */
	private Integer isShow;

	public Report(){}


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
	 * 获取举报人id
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置举报人id
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取举报类别对应表的主键(如话题表id)
	 */
	public String getFkId() {
    	return fkId;
    }
  	
	/**
	 * 设置举报类别对应表的主键(如话题表id)
	 */
	public void setFkId(String fkId) {
    	this.fkId = fkId;
    }

	/**
	 * 获取举报类别(如话题pk)
	 */
	public String getRtType() {
    	return rtType;
    }
  	
	/**
	 * 设置举报类别(如话题pk)
	 */
	public void setRtType(String rtType) {
    	this.rtType = rtType;
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
	 * 获取逻辑删除标识
	 */
	public Integer getDeletd() {
    	return deletd;
    }
  	
	/**
	 * 设置逻辑删除标识
	 */
	public void setDeletd(Integer deletd) {
    	this.deletd = deletd;
    }


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Integer getIsShow() {
		return isShow;
	}


	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
}
