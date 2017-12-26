package com.wing.socialcontact.service.im.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_IM_GROUPINFO 群组
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:12:38
 * @version 1.0
 */
@Table(name = "TJY_IM_GROUPINFO")
public class ImGroupinfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

    /** 群号 */
	private String groupNo;

    /** 群名称 */
	private String groupName;

    /** 群头像 */
	private String headPortrait;

    /** 创建人 */
	private String creator;

    /** 群主 */
	private String mainUser;

    /** 群简介 */
	private String groupDesc;

    /** 成员上限 */
	private Integer membersMax;

    /** 群类型 1 普通群（有群号，用户可以搜索加入）2、为群聊不能被搜索加入 */
	private Integer groupType;
	
	/** 权重 */
	private Integer groupPower;

    /** 创建时间 */
	private Date createTime;

    /** 更新时间 */
	private Date updateTime;

    /** 逻辑删除标识 */
	private Integer deleted;

    /** 删除日期 */
	private Date deleteDate;
	
	private String tid;
	

	public ImGroupinfo(){}


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
	 * 获取群号
	 */
	public String getGroupNo() {
    	return groupNo;
    }
  	
	/**
	 * 设置群号
	 */
	public void setGroupNo(String groupNo) {
    	this.groupNo = groupNo;
    }

	/**
	 * 获取群名称
	 */
	public String getGroupName() {
    	return groupName;
    }
  	
	/**
	 * 设置群名称
	 */
	public void setGroupName(String groupName) {
    	this.groupName = groupName;
    }

	/**
	 * 获取群头像
	 */
	public String getHeadPortrait() {
    	return headPortrait;
    }
  	
	/**
	 * 设置群头像
	 */
	public void setHeadPortrait(String headPortrait) {
    	this.headPortrait = headPortrait;
    }

	/**
	 * 获取创建人
	 */
	public String getCreator() {
    	return creator;
    }
  	
	/**
	 * 设置创建人
	 */
	public void setCreator(String creator) {
    	this.creator = creator;
    }

	/**
	 * 获取群主
	 */
	public String getMainUser() {
    	return mainUser;
    }
  	
	/**
	 * 设置群主
	 */
	public void setMainUser(String mainUser) {
    	this.mainUser = mainUser;
    }

	/**
	 * 获取群简介
	 */
	public String getGroupDesc() {
    	return groupDesc;
    }
  	
	/**
	 * 设置群简介
	 */
	public void setGroupDesc(String groupDesc) {
    	this.groupDesc = groupDesc;
    }

	/**
	 * 获取成员上限
	 */
	public Integer getMembersMax() {
    	return membersMax;
    }
  	
	/**
	 * 设置成员上限
	 */
	public void setMembersMax(Integer membersMax) {
    	this.membersMax = membersMax;
    }

	/**
	 * 获取群类型 1 普通群（有群号，用户可以搜索加入）2、为群聊不能被搜索加入
	 */
	public Integer getGroupType() {
    	return groupType;
    }
  	
	/**
	 * 设置群类型 1 普通群（有群号，用户可以搜索加入）2、为群聊不能被搜索加入
	 */
	public void setGroupType(Integer groupType) {
    	this.groupType = groupType;
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

	/**
	 * 获取删除日期
	 */
	public Date getDeleteDate() {
    	return deleteDate;
    }
  	
	/**
	 * 设置删除日期
	 */
	public void setDeleteDate(Date deleteDate) {
    	this.deleteDate = deleteDate;
    }


	public Integer getGroupPower() {
		return groupPower;
	}


	public void setGroupPower(Integer groupPower) {
		this.groupPower = groupPower;
	}


	public String getTid() {
		return tid;
	}


	public void setTid(String tid) {
		this.tid = tid;
	}
	
}
