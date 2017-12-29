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

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
}
