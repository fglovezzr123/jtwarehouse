package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * <p>Title:活动取消申请 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月14日 下午4:17:31
 */
@Table(name="tjy_activity_cancel")
public class ActivityCancel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;
    /*活动id*/
	private String activityId;
	/*取消说明*/
	private String description;
	/**
	 * 申请人
	 */
	private String userId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 申请状态  0：申请中  1：申请通过 2：申请被拒
	 */
	private Integer status;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
