package com.wing.socialcontact.service.im.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_IM_FRIEND 我的好友
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:37:16
 * @version 1.0
 */
@Table(name = "TJY_IM_FRIEND")
public class ImFriend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 用户ID */
	private String userId;

    /** 好友ID */
	private String friendUser;

    /** 好友备注 */
	private String friendMemo;

    /** 创建时间 */
	private Date createTime;

    /** 更新时间 */
	private Date updateTime;
	
	/** 消息免打扰*/
	private Integer msgDisturb;
	

    /** 星标好友标识  */
	private String starFlag;
	
	/** 星标操作时间	 */
	private Date starFlagDate;




	public ImFriend(){}


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
	 * 获取用户ID
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置用户ID
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取好友ID
	 */
	public String getFriendUser() {
    	return friendUser;
    }
  	
	/**
	 * 设置好友ID
	 */
	public void setFriendUser(String friendUser) {
    	this.friendUser = friendUser;
    }

	/**
	 * 获取好友备注
	 */
	public String getFriendMemo() {
    	return friendMemo;
    }
  	
	/**
	 * 设置好友备注
	 */
	public void setFriendMemo(String friendMemo) {
    	this.friendMemo = friendMemo;
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
	 * 获取更新时间
	 */
	public Date getUpdateTime() {
    	return updateTime;
    }
  	
	/**
	 * 设置更新时间
	 */
	public void setUpdateTime(Date updateTime) {
    	this.updateTime = updateTime;
    }


	public Integer getMsgDisturb() {
		return msgDisturb;
	}


	public void setMsgDisturb(Integer msgDisturb) {
		this.msgDisturb = msgDisturb;
	}
	
	public String getStarFlag() {
		return starFlag;
	}


	public void setStarFlag(String starFlag) {
		this.starFlag = starFlag;
	}


	public Date getStarFlagDate() {
		return starFlagDate;
	}


	public void setStarFlagDate(Date starFlagDate) {
		this.starFlagDate = starFlagDate;
	}
	
	
}
