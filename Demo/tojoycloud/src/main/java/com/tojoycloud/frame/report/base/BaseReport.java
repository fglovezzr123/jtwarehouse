package com.tojoycloud.frame.report.base;

@SuppressWarnings("serial")
public class BaseReport extends BaseObject
{
	private String protocolType = "Request";

	private Byte responseCode;

	private String responseTips;

	public String getProtocolType()
	{
		return protocolType;
	}

	public BaseReport()
	{
		super();
		this.responseCode = ResponseCode.OK;
		this.protocolType = ReportType.RESPONSE;
	}

	public BaseReport(String reportType)
	{
		super();
		this.protocolType = reportType;
	}

	public BaseReport(Byte responseCode)
	{
		super();
		this.responseCode = responseCode;
		this.protocolType = ReportType.RESPONSE;
	}

	public void setProtocolType(String protocolType)
	{
		this.protocolType = protocolType;
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
