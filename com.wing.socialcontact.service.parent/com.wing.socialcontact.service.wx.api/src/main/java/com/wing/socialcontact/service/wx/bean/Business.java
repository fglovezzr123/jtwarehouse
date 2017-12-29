package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_BUSINESS 合作信息表
 * 
 * @author zhangfan
 * @date 2017-04-18 12:01:49
 * @version 1.0
 */
@Table(name = "TJY_BUSINESS")
public class Business implements Serializable{
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
	private String titles;

    /** 合作分类（关联数据字典） */
	private String bizType;

    /** 诉求类别，1供给2需求 */
	private Integer appealType;

    /** 合作诉求 */
	private String appealSummary;

    /** 备注 */
	private String appealRemark;

    /** 有效开始日期 */
	private Date startTime;

    /** 有效截至日期 */
	private Date endTime;

    /** 是否允许评论,1允许2不允许 */
	private Integer allowComment;

    /** 悬赏j币 */
	private Integer reward;

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

    /** 合作说明 */
	private String appealDesc;
	
	/** 是否推荐，1推荐 2不推荐 */
	private Integer isRecommend;
	
	private Integer lookCount;
	
	private Integer rewardFinish;
	
	private Integer isShow;

	public Business(){}


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
	public String getTitles() {
    	return titles;
    }
  	
	/**
	 * 设置标题
	 */
	public void setTitles(String titles) {
    	this.titles = titles;
    }

	/**
	 * 获取合作分类（关联数据字典）
	 */
	public String getBizType() {
    	return bizType;
    }
  	
	/**
	 * 设置合作分类（关联数据字典）
	 */
	public void setBizType(String bizType) {
    	this.bizType = bizType;
    }

	/**
	 * 获取诉求类别，1供给2需求
	 */
	public Integer getAppealType() {
    	return appealType;
    }
  	
	/**
	 * 设置诉求类别，1供给2需求
	 */
	public void setAppealType(Integer appealType) {
    	this.appealType = appealType;
    }

	/**
	 * 获取合作诉求
	 */
	public String getAppealSummary() {
    	return appealSummary;
    }
  	
	/**
	 * 设置合作诉求
	 */
	public void setAppealSummary(String appealSummary) {
    	this.appealSummary = appealSummary;
    }

	/**
	 * 获取备注
	 */
	public String getAppealRemark() {
    	return appealRemark;
    }
  	
	/**
	 * 设置备注
	 */
	public void setAppealRemark(String appealRemark) {
    	this.appealRemark = appealRemark;
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
	 * 获取合作说明
	 */
	public String getAppealDesc() {
    	return appealDesc;
    }
  	
	/**
	 * 设置合作说明
	 */
	public void setAppealDesc(String appealDesc) {
    	this.appealDesc = appealDesc;
    }


	public Integer getIsRecommend() {
		return isRecommend;
	}


	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}


	public Integer getLookCount() {
		return lookCount;
	}


	public void setLookCount(Integer lookCount) {
		this.lookCount = lookCount;
	}


	public Integer getRewardFinish() {
		return rewardFinish;
	}


	public void setRewardFinish(Integer rewardFinish) {
		this.rewardFinish = rewardFinish;
	}


	public Integer getIsShow() {
		return isShow;
	}


	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	
}
