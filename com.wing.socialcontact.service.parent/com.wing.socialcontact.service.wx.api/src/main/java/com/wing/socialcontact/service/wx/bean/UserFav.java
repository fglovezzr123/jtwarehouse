package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_USER_FAV 用户爱好信息
 * 
 * @author gaojun
 * @date 2017-04-11 15:34:07
 * @version 1.0
 */
@Table(name = "TJY_USER_FAV")
public class UserFav implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 用户主键 */
	private String userId;

    /** 喜好编码(关联字典表) */
	private String favId;

    /** 排序 */
	private Integer sortno;

	public UserFav(){}


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
	 * 获取用户主键
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置用户主键
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取喜好编码(关联字典表)
	 */
	public String getFavId() {
    	return favId;
    }
  	
	/**
	 * 设置喜好编码(关联字典表)
	 */
	public void setFavId(String favId) {
    	this.favId = favId;
    }

	/**
	 * 获取排序
	 */
	public Integer getSortno() {
    	return sortno;
    }
  	
	/**
	 * 设置排序
	 */
	public void setSortno(Integer sortno) {
    	this.sortno = sortno;
    }
}
