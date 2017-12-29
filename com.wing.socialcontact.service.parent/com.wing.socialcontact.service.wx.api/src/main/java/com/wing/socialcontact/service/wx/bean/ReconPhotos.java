package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_RECON_PHOTOS 用户认证图片表
 * 
 * @author zengmin
 * @date 2017-04-07 01:41:58
 * @version 1.0
 */
@Table(name = "TJY_RECON_PHOTOS")
public class ReconPhotos implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 类型 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 类型（1：营业执照2：银联认证） */
	private Integer type;

    /** 所属用户tjy_user */
	private String userId;

    /** 图片地址 */
	private String imgUrl;

    /** 图片名称 */
	private String imgName;

	public ReconPhotos(){}


	/**
	 * 获取类型
	 */
	public String getId() {
    	return id;
    }
  	
	/**
	 * 设置类型
	 */
	public void setId(String id) {
    	this.id = id;
    }

	/**
	 * 获取类型（1：营业执照2：银联认证）
	 */
	public Integer getType() {
    	return type;
    }
  	
	/**
	 * 设置类型（1：营业执照2：银联认证）
	 */
	public void setType(Integer type) {
    	this.type = type;
    }

	/**
	 * 获取所属用户tjy_user
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置所属用户tjy_user
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取图片地址
	 */
	public String getImgUrl() {
    	return imgUrl;
    }
  	
	/**
	 * 设置图片地址
	 */
	public void setImgUrl(String imgUrl) {
    	this.imgUrl = imgUrl;
    }

	/**
	 * 获取图片名称
	 */
	public String getImgName() {
    	return imgName;
    }
  	
	/**
	 * 设置图片名称
	 */
	public void setImgName(String imgName) {
    	this.imgName = imgName;
    }
}
