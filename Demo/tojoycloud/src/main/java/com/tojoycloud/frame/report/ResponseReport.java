package com.tojoycloud.frame.report;

import com.tojoycloud.frame.report.base.BaseReport;

@SuppressWarnings({"serial"})
public class ResponseReport extends BaseReport
{
	private Byte responseCode;

	private String responseTips;

	public ResponseReport()
	{
		super();
	}

	public ResponseReport(Byte responseCode)
	{
		super();
		this.responseCode = responseCode;
	}

	public Byte getResponseCode()
	{
		return responseCode;
	}

	public void setResponseCode(Byte responseCode)
	{
		this.responseCode = responseCode;
	}

	public String getResponseTips()
	{
		return responseTips;
	}

	public void setResponseTips(String responseTips)
	{
		this.responseTips = responseTips;
	}

}
