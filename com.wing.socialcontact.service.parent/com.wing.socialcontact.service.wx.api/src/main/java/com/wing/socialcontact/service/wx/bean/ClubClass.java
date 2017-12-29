package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author zhangzheng
 *会所分类
 */
@Table(name = "tjy_club_class")
public class ClubClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3957331991600372298L;

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
	 * 名称
	 */
	private String name;
	/**
	 * 推荐 1：是  0：否
	 */
	private int recommend;
	/**
	 * 排序
	 */
	private int sort;
	/**
	 * 父级id
	 */
	private String parentId;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}
