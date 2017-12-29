package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_ACCESSORY 附件表
 * 
 * @author liangwj
 * @date 2017-03-27 11:24:18
 * @version 1.0
 */
@Table(name = "TJY_ACCESSORY")
public class Accessory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 上传人ID */
	private String userId;

    /** 附件主题ID */
	private String superId;

    /** 后缀名 */
	private String ext;

    /** 图片高 */
	private Integer height;

    /** 图片宽 */
	private Integer width;

    /** 大小 */
	private Integer size;

    /** 附件原名 */
	private String originalName;

    /** 附件描述 */
	private String info;

    /** 附件新名 */
	private String newName;

    /** 附件url */
	private String path;

    /** 状态 */
	private Integer status;

    /** 创建时间 */
	private Date createTime;

    /** 创建人ID */
	private String createUserId;

    /** 创建Name */
	private String createUserName;

    /** 修改时间 */
	private Date updateTime;

    /** 修改人ID */
	private String updateUserId;

    /** 修改Name */
	private String updateUserName;

    /** 逻辑删除标识 */
	private Integer deleted;

	public Accessory(){}


	/**
	 * 获取
	 */
	public String getId() {
    	return id;
    }
  	
	/**
	 * 设置
	 */
	public void setId(String id) {
    	this.id = id;
    }

	/**
	 * 获取上传人ID
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置上传人ID
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取附件主题ID
	 */
	public String getSuperId() {
    	return superId;
    }
  	
	/**
	 * 设置附件主题ID
	 */
	public void setSuperId(String superId) {
    	this.superId = superId;
    }

	/**
	 * 获取后缀名
	 */
	public String getExt() {
    	return ext;
    }
  	
	/**
	 * 设置后缀名
	 */
	public void setExt(String ext) {
    	this.ext = ext;
    }

	/**
	 * 获取图片高
	 */
	public Integer getHeight() {
    	return height;
    }
  	
	/**
	 * 设置图片高
	 */
	public void setHeight(Integer height) {
    	this.height = height;
    }

	/**
	 * 获取图片宽
	 */
	public Integer getWidth() {
    	return width;
    }
  	
	/**
	 * 设置图片宽
	 */
	public void setWidth(Integer width) {
    	this.width = width;
    }

	/**
	 * 获取大小
	 */
	public Integer getSize() {
    	return size;
    }
  	
	/**
	 * 设置大小
	 */
	public void setSize(Integer size) {
    	this.size = size;
    }

	/**
	 * 获取附件原名
	 */
	public String getOriginalName() {
    	return originalName;
    }
  	
	/**
	 * 设置附件原名
	 */
	public void setOriginalName(String originalName) {
    	this.originalName = originalName;
    }

	/**
	 * 获取附件描述
	 */
	public String getInfo() {
    	return info;
    }
  	
	/**
	 * 设置附件描述
	 */
	public void setInfo(String info) {
    	this.info = info;
    }

	/**
	 * 获取附件新名
	 */
	public String getNewName() {
    	return newName;
    }
  	
	/**
	 * 设置附件新名
	 */
	public void setNewName(String newName) {
    	this.newName = newName;
    }

	/**
	 * 获取附件url
	 */
	public String getPath() {
    	return path;
    }
  	
	/**
	 * 设置附件url
	 */
	public void setPath(String path) {
    	this.path = path;
    }

	/**
	 * 获取状态
	 */
	public Integer getStatus() {
    	return status;
    }
  	
	/**
	 * 设置状态
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
	 * 获取创建人ID
	 */
	public String getCreateUserId() {
    	return createUserId;
    }
  	
	/**
	 * 设置创建人ID
	 */
	public void setCreateUserId(String createUserId) {
    	this.createUserId = createUserId;
    }

	/**
	 * 获取创建Name
	 */
	public String getCreateUserName() {
    	return createUserName;
    }
  	
	/**
	 * 设置创建Name
	 */
	public void setCreateUserName(String createUserName) {
    	this.createUserName = createUserName;
    }

	/**
	 * 获取修改时间
	 */
	public Date getUpdateTime() {
    	return updateTime;
    }
  	
	/**
	 * 设置修改时间
	 */
	public void setUpdateTime(Date updateTime) {
    	this.updateTime = updateTime;
    }

	/**
	 * 获取修改人ID
	 */
	public String getUpdateUserId() {
    	return updateUserId;
    }
  	
	/**
	 * 设置修改人ID
	 */
	public void setUpdateUserId(String updateUserId) {
    	this.updateUserId = updateUserId;
    }

	/**
	 * 获取修改Name
	 */
	public String getUpdateUserName() {
    	return updateUserName;
    }
  	
	/**
	 * 设置修改Name
	 */
	public void setUpdateUserName(String updateUserName) {
    	this.updateUserName = updateUserName;
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
