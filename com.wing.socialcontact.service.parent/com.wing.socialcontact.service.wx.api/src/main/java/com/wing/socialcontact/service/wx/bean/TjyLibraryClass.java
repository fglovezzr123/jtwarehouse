package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 文库分类
 * @author zhangzheng
 *
 */
@Table(name = "tjy_library_class")
public class TjyLibraryClass  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2788226717923355876L;
	
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
	 * 推荐
	 */
	private int recommend;
	/**
	 * 排序
	 */
	private int sort;
	/**
	 * 图片
	 */
	private String imagePath;
	/**
	 * 上级id
	 */
	private String parentId;
	/**
	 * 位置
	 */
	private int position;
	/**
	 * 等级
	 */
	private int level;
	/**
	 * 分类名加标签名
	 */
	private String content;
	
	
	
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
