package com.wing.socialcontact.service.wx.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户拍照照片
 * 
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
@Table(name = "tjy_user_photo")
public class UserPhoto implements Serializable{
	private static final long serialVersionUID = 1L;

    /**主键*/
    @Id
	@Column(name = "id")
	private String id;

    /** 用户id */
	private String userId;

	/** 用户名称 */
	@Transient
	private String trueName;

	/**照片Url*/
    private String photoUrl;

	/**审核状态*/
	private Integer status;

	/**创建时间*/
	private Date createTime;

	/**创建人id*/
	private String createUserId;

	/**更新时间*/
	private Date updateTime;

	/**更新人id*/
	private String updateUserId;

	/**删除标识*/
	private Integer deleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
}
