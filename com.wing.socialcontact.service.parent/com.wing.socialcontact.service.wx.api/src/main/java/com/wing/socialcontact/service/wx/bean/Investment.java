package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "tjy_investment")
/**
 * 投资
 * @author zhangzheng
 *
 */
public class Investment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 919436575629921090L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;
	
	private int deleted;
	
	private String createUserId;
	
	private String updateUserId;
	
	private Date updateTime;

	private Date createTime;
	/**
	 * 
	 */
	private String imagePath;
	/**
	 * 业务介绍
	 */
	private String introduce;
	/**
	 * 产品特色
	 */
	private String feature;
	/**
	 * 分类id
	 */
	private String classId;
	
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
