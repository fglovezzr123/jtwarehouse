package com.tojoy.meeting.model;

import com.tojoy.service.wx.bean.Meeting;

import java.util.List;

public class MeetingListModel
{
	private static final long serialVersionUID = 1L;


	private List<Meeting> list;

	public List<Meeting> getList() {
		return list;
	}

	public void setList(List<Meeting> list) {
		this.list = list;
	}
}
