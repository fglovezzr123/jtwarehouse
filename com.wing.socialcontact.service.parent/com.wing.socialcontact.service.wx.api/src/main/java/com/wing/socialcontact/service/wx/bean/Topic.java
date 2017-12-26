package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TJY_TOPIC 话题pk表
 * 
 * @author zhangfan
 * @date 2017-03-29 11:32:11
 * @version 1.0
 */
@Table(name = "TJY_TOPIC")
public class Topic implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	/** pk话题 */
	private String topicDesc;

	/** 红方观点 */
	private String redPoint;

	/** 红方支持人数 */
	private Integer redCount;

	/** 蓝方观点 */
	private String bluePoint;

	/** 蓝方支持人数 */
	private Integer blueCount;

	/** 是否运行评论,1允许2不允许 */
	private Integer allowComment;

	/** 是否推荐，1推荐 2不推荐 */
	private Integer isRecommend;

	/** 话题状态 1显示 2不显示 */
	private Integer isShow;

	/** 备注 */
	private String remark;

	/** 状态，1审核中2审核通过3审核不通过 */
	private Integer status;

	/** 创建时间 */
	private Date createTime;

	/** 创建人id */
	private String createUserId;

	/** 创建人电话 */
	private String createUserName;

	/** 创建人名称 */
	@Transient
	private String userName;
	
	/** 创建人手机号 */
	@Transient
	private String mobile;

	/** 逻辑删除标识 */
	private Integer deletd;

	private String topicExplain;

	private Integer sort;

	private String url;

	private String imagePath;

	private Integer isAd;

	public Topic() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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
	 * 获取pk话题
	 */
	public String getTopicDesc() {
		return topicDesc;
	}

	/**
	 * 设置pk话题
	 */
	public void setTopicDesc(String topicDesc) {
		this.topicDesc = topicDesc;
	}

	/**
	 * 获取红方观点
	 */
	public String getRedPoint() {
		return redPoint;
	}

	/**
	 * 设置红方观点
	 */
	public void setRedPoint(String redPoint) {
		this.redPoint = redPoint;
	}

	/**
	 * 获取红方支持人数
	 */
	public Integer getRedCount() {
		return redCount;
	}

	/**
	 * 设置红方支持人数
	 */
	public void setRedCount(Integer redCount) {
		this.redCount = redCount;
	}

	/**
	 * 获取蓝方观点
	 */
	public String getBluePoint() {
		return bluePoint;
	}

	/**
	 * 设置蓝方观点
	 */
	public void setBluePoint(String bluePoint) {
		this.bluePoint = bluePoint;
	}

	/**
	 * 获取蓝方支持人数
	 */
	public Integer getBlueCount() {
		return blueCount;
	}

	/**
	 * 设置蓝方支持人数
	 */
	public void setBlueCount(Integer blueCount) {
		this.blueCount = blueCount;
	}

	/**
	 * 获取是否运行评论,1允许2不允许
	 */
	public Integer getAllowComment() {
		return allowComment;
	}

	/**
	 * 设置是否运行评论,1允许2不允许
	 */
	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}

	/**
	 * 获取是否推荐，0否，1是
	 */
	public Integer getIsRecommend() {
		return isRecommend;
	}

	/**
	 * 设置是否推荐，0否，1是
	 */
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	/**
	 * 获取话题状态 1显示 2不显示
	 */
	public Integer getIsShow() {
		return isShow;
	}

	/**
	 * 设置话题状态 1显示 2不显示
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
	 * 获取创建人id
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 * 设置创建人id
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * 获取创建人名称
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置创建人名称
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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

	public String getTopicExplain() {
		return topicExplain;
	}

	public void setTopicExplain(String topicExplain) {
		this.topicExplain = topicExplain;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getIsAd() {
		return isAd;
	}

	public void setIsAd(Integer isAd) {
		this.isAd = isAd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
