package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_VOTE 投票记录表
 * 
 * @author zhangfan
 * @date 2017-04-13 10:46:03
 * @version 1.0
 */
@Table(name = "TJY_VOTE")
public class Vote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 话题表id */
	private String fkId;

    /** 投票人id */
	private String userId;

    /** 支持类别（1红方/2蓝方） */
	private String voteType;

    /** 创建时间 */
	private Date createTime;

    /** 逻辑删除标识 */
	private Integer deletd;

	public Vote(){}


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
	 * 获取话题表id
	 */
	public String getFkId() {
    	return fkId;
    }
  	
	/**
	 * 设置话题表id
	 */
	public void setFkId(String fkId) {
    	this.fkId = fkId;
    }

	/**
	 * 获取投票人id
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置投票人id
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取支持类别（1红方/2蓝方）
	 */
	public String getVoteType() {
    	return voteType;
    }
  	
	/**
	 * 设置支持类别（1红方/2蓝方）
	 */
	public void setVoteType(String voteType) {
    	this.voteType = voteType;
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
}
