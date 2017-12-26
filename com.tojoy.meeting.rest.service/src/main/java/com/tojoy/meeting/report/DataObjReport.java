package com.tojoy.meeting.report;

import com.tojoy.service.wx.bean.Meeting;

public class DataObjReport extends ResponseReport
{
	private static final long serialVersionUID = 1L;

	public DataObjReport()
	{
		super();
	}

	public DataObjReport(Byte responseCode)
	{
		super(responseCode);
	}

	private Object dataObj;

	public Object getDataObj() {
		return dataObj;
	}

	public void setDataObj(Object dataObj) {
		this.dataObj = dataObj;
	}
}
