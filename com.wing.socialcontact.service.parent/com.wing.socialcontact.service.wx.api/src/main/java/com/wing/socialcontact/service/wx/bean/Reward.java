package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_REWARD 悬赏信息表
 * 
 * @author zhangfan
 * @date 2017-06-12 20:37:47
 * @version 1.0
 */
@Table(name = "TJY_REWARD")
public class Reward implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 问题 */
	private String question;

    /** 行业 */
	private String voType;

    /** 有效开始日期 */
	private Date startTime;

    /** 有效截至日期 */
	private Date endTime;

    /** 是否允许评论,1允许2不允许 */
	private Integer allowComment;

    /** 悬赏j币 */
	private Integer reward;

    /** 悬赏完成（1已完成，2未完成 ,3已过期） */
	private Integer rewardFinish;

    /** 好友可见 1是 2否 */
	private Integer isShow;

    /** 备注 */
	private String remark;

    /** 状态，1待审核 2审核通过 3已取消 4已完成 5已过期 */
	private Integer status;

    /** 创建时间 */
	private Date createTime;

    /** 发布人id */
	private String createUserId;

    /** 创建用户名称 */
	private String createUserName;

    /** 逻辑删除标识 */
	private Integer deleted;

	public Reward(){}


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
	 * 获取问题
	 */
	public String getQuestion() {
    	return question;
    }
  	
	/**
	 * 设置问题
	 */
	public void setQuestion(String question) {
    	this.question = question;
    }

	/**
	 * 获取行业
	 */
	public String getVoType() {
    	return voType;
    }
  	
	/**
	 * 设置行业
	 */
	public void setVoType(String voType) {
    	this.voType = voType;
    }

	/**
	 * 获取有效开始日期
	 */
	public Date getStartTime() {
    	return startTime;
    }
  	
	/**
	 * 设置有效开始日期
	 */
	public void setStartTime(Date startTime) {
    	this.startTime = startTime;
    }

	/**
	 * 获取有效截至日期
	 */
	public Date getEndTime() {
    	return endTime;
    }
  	
	/**
	 * 设置有效截至日期
	 */
	public void setEndTime(Date endTime) {
    	this.endTime = endTime;
    }

	/**
	 * 获取是否允许评论,1允许2不允许
	 */
	public Integer getAllowComment() {
    	return allowComment;
    }
  	
	/**
	 * 设置是否允许评论,1允许2不允许
	 */
	public void setAllowComment(Integer allowComment) {
    	this.allowComment = allowComment;
    }

	/**
	 * 获取悬赏j币
	 */
	public Integer getReward() {
    	return reward;
    }
  	
	/**
	 * 设置悬赏j币
	 */
	public void setReward(Integer reward) {
    	this.reward = reward;
    }

	/**
	 * 获取悬赏完成（1已完成，2未完成 ,3已过期）
	 */
	public Integer getRewardFinish() {
    	return rewardFinish;
    }
  	
	/**
	 * 设置悬赏完成（1已完成，2未完成 ,3已过期）
	 */
	public void setRewardFinish(Integer rewardFinish) {
    	this.rewardFinish = rewardFinish;
    }

	/**
	 * 获取好友可见 1是 2否
	 */
	public Integer getIsShow() {
    	return isShow;
    }
  	
	/**
	 * 设置好友可见 1是 2否
	 */
	public void setIsShow(Integer isShow) {
    	this.isShow = isShow;
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
	 * 获取发布人id
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置发布人id
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
}
