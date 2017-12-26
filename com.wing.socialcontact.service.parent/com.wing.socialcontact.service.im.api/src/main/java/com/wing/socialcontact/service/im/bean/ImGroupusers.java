package com.wing.socialcontact.service.im.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_IM_GROUPUSERS 群成员
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:12:38
 * @version 1.0
 */
@Table(name = "TJY_IM_GROUPUSERS")
public class ImGroupusers implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 群id */
	private String groupId;

    /** 用户id */
	private String userId;

    /** 群昵称 */
	private String nickname;

    /** 职责(owner/member) */
	private String affiliations;

    /** 消息免打扰(0、可打扰 1、免打扰) */
	private Integer msgDisturb;

    /** 创建时间 */
	private Date createTime;

    /** 更新时间 */
	private Date updateTime;

	public ImGroupusers(){}


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
	 * 获取群id
	 */
	public String getGroupId() {
    	return groupId;
    }
  	
	/**
	 * 设置群id
	 */
	public void setGroupId(String groupId) {
    	this.groupId = groupId;
    }

	/**
	 * 获取用户id
	 */
	public String getUserId() {
    	return userId;
    }
  	
	/**
	 * 设置用户id
	 */
	public void setUserId(String userId) {
    	this.userId = userId;
    }

	/**
	 * 获取群昵称
	 */
	public String getNickname() {
    	return nickname;
    }
  	
	/**
	 * 设置群昵称
	 */
	public void setNickname(String nickname) {
    	this.nickname = nickname;
    }

	/**
	 * 获取职责(owner/member)
	 */
	public String getAffiliations() {
    	return affiliations;
    }
  	
	/**
	 * 设置职责(owner/member)
	 */
	public void setAffiliations(String affiliations) {
    	this.affiliations = affiliations;
    }

	/**
	 * 获取消息免打扰(0、可打扰 1、免打扰)
	 */
	public Integer getMsgDisturb() {
    	return msgDisturb;
    }
  	
	/**
	 * 设置消息免打扰(0、可打扰 1、免打扰)
	 */
	public void setMsgDisturb(Integer msgDisturb) {
    	this.msgDisturb = msgDisturb;
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
}
