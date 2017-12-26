package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TJY_LEAVE_MSG
 * 
 * @author zengmin
 * @date 2017-04-28 23:41:19
 * @version 1.0
 */
@Table(name = "TJY_LEAVE_MSG")
public class LeaveMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	/** 留言人 */
	private String userId;

	/**  */
	private String content;

	/** 留言时间 */
	private Date createTime;

	/** 删除标记(0未删除1已删除) */
	private Integer deleted;

	/** 反馈类型 (1:im 2:个人中心3：企服云app) */
	private String type;

	/** 来源（1：微信企业号 2：企服云app） */
	private String source;

	public LeaveMsg() {
	}

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
	 * 获取留言人
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置留言人
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取留言时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置留言时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取删除标记(0未删除1已删除)
	 */
	public Integer getDeleted() {
		return deleted;
	}

	/**
	 * 设置删除标记(0未删除1已删除)
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getType() {
		return type;
	}

	/** 反馈类型 (1:im 2:个人中心3：企服云app) */
	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	/** 来源（1：微信企业号 2：企服云app） */
	public void setSource(String source) {
		this.source = source;
	}

}
