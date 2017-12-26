package com.tojoy.meeting.model;

import com.tojoy.meeting.report.ResponseReport;
import com.tojoy.service.wx.bean.Meeting;

import java.util.List;

public class MeetingModel extends ResponseReport
{
	private static final long serialVersionUID = 1L;

	public MeetingModel()
	{
		super();
	}

	public MeetingModel(Byte responseCode)
	{
		super(responseCode);
	}

	private  Meeting meeting;

	private Integer signupStatus;//报名状态

	private long signupCount; // 报名人数

	private Integer thumbUpStatus;//点赞状态

	private Integer thumbUpCount; //点赞人数

	private Integer attentionStatus;//关注状态

	private Integer attentionCount; //关注人数

	public Integer getThumbUpStatus() {
		return thumbUpStatus;
	}

	public void setThumbUpStatus(Integer thumbUpStatus) {
		this.thumbUpStatus = thumbUpStatus;
	}

	public Integer getThumbUpCount() {
		return thumbUpCount;
	}

	public void setThumbUpCount(Integer thumbUpCount) {
		this.thumbUpCount = thumbUpCount;
	}

	public Integer getAttentionCount() {
		return attentionCount;
	}

	public void setAttentionCount(Integer attentionCount) {
		this.attentionCount = attentionCount;
	}

	public Integer getSignupStatus() {
		return signupStatus;
	}

	public void setSignupStatus(Integer signupStatus) {
		this.signupStatus = signupStatus;
	}

	public long getSignupCount() {
		return signupCount;
	}

	public void setSignupCount(long signupCount) {
		this.signupCount = signupCount;
	}

	public Integer getAttentionStatus() {
		return attentionStatus;
	}

	public void setAttentionStatus(Integer attentionStatus) {
		this.attentionStatus = attentionStatus;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
}
