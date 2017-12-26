package com.tojoy.service.wx.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.deserializer.IntegerDeserializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * tjy_meeting_relation 选集视频列表
 * 
 * @author wangyansheng
 * @date 2017-12-18 00:05:28
 * @version 1.0
 */
@Table(name = "tjy_meeting_relation")
public class MeetingRelation implements Serializable{
	private static final long serialVersionUID = 1L;
    /** 主键 */
    @Id
	@Column(name = "id")
	private Long id;

	/** 会议id */
	private String meetingId;

	/** 微吼会议id */
	private String webinarId;

	/** 微吼会议主题 */
	private String webinarSubject;

	/** 微吼会议状态 */
	private Integer webinarStatus;

	/** 微吼会议图片地址 */
	private String webinarThumb;

	/** 微吼会议开始时间 */
	private Date webinarStartTime;

	/** 创建时间 */
	private Date creatTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getWebinarId() {
		return webinarId;
	}

	public void setWebinarId(String webinarId) {
		this.webinarId = webinarId;
	}

	public String getWebinarSubject() {
		return webinarSubject;
	}

	public void setWebinarSubject(String webinarSubject) {
		this.webinarSubject = webinarSubject;
	}

	public Integer getWebinarStatus() {
		return webinarStatus;
	}

	public void setWebinarStatus(Integer webinarStatus) {
		this.webinarStatus = webinarStatus;
	}

	public String getWebinarThumb() {
		return webinarThumb;
	}

	public void setWebinarThumb(String webinarThumb) {
		this.webinarThumb = webinarThumb;
	}

	public Date getWebinarStartTime() {
		return webinarStartTime;
	}

	public void setWebinarStartTime(Date webinarStartTime) {
		this.webinarStartTime = webinarStartTime;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
}
