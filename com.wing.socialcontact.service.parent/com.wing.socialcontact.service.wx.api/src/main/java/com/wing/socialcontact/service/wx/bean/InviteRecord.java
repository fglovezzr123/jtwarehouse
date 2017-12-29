package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_INVITE_RECORD 
 * 
 * @author zengmin
 * @date 2017-07-13 09:51:06
 * @version 1.0
 */
@Table(name = "TJY_INVITE_RECORD")
public class InviteRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**  */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 邀请人 */
	private String userId;

    /** 被邀请人微信openid */
	private String byqOpenId;

    /** 创建时间 */
	private Date createTime;
	
	 /** 被邀请人微信昵称 */
	private String byqNickName;
	
	 /** 是否送积分（0：未送 1：已送） */
	private String isIntegral;

	public InviteRecord(){}


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
	 * 获取邀请人
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置邀请人
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取被邀请人微信openid
	 */
	public String getByqOpenId() {
    	return byqOpenId;
    }
  	
	/**
	 * 设置被邀请人微信openid
	 */
	public void setByqOpenId(String byqOpenId) {
    	this.byqOpenId = byqOpenId;
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


	public String getByqNickName() {
		return byqNickName;
	}


	public void setByqNickName(String byqNickName) {
		this.byqNickName = byqNickName;
	}


	public String getIsIntegral() {
		return isIntegral;
	}


	public void setIsIntegral(String isIntegral) {
		this.isIntegral = isIntegral;
	}
	
	
}
