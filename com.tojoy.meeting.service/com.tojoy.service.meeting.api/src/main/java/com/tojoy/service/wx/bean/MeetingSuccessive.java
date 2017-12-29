package com.tojoy.service.wx.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * tjy_meeting_successive 历届会议列表
 * 
 * @author wangyansheng
 * @date 2017-12-18 00:05:28
 * @version 1.0
 */
@Table(name = "tjy_meeting_successive")
public class MeetingSuccessive implements Serializable{
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	/** 会议id */
	private String fkId;

	/** 往届会议id */
	private String meetingId;

	/** 创建时间 */
	private Date createTime;

	/** 会议主题 */
	@Transient
	private String titles;

	/** 封面图片 */
	@Transient
	private String coverImg;

	/** 会议开始时间 */
	@Transient
	private Date startTime;

	/** 会议地址 */
	@Transient
	private String place;

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFkId() {
		return fkId;
	}

	public void setFkId(String fkId) {
		this.fkId = fkId;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
