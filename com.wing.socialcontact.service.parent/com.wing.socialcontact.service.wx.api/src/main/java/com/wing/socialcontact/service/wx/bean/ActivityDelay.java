package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * <p>Title:活动延期 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月14日 下午4:20:36
 */
@Table(name="tjy_activity_delay")
public class ActivityDelay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;
    /**
     * 活动id
     */
	private String activityId;
	/**
	 * 申请人
	 */
	private String userId;
	/**
	 * 延期原因
	 */
	private String description;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 报名截止时间
	 */
	private Date signupTime;
	/**
	 * 申请状态 0:申请中  1：通过  2：拒绝
	 */
	
	
	private Integer status;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getSignupTime() {
		return signupTime;
	}
	public void setSignupTime(Date signupTime) {
		this.signupTime = signupTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
