package com.tojoy.meeting.report;


import com.tojoy.meeting.common.report.BaseReport;
import com.tojoy.meeting.common.report.base.CommandInfo;

@SuppressWarnings({"serial"})
public class ResponseReport extends BaseReport
{
	private Byte responseCode;

	private String responseTips;

	private CommandInfo commandInfo;

	private Object dataObj;

	public ResponseReport()
	{
		super();
	}

	public ResponseReport(Byte responseCode)
	{
		super();
		this.responseCode = responseCode;
	}

	public CommandInfo getCommandInfo() {
		return commandInfo;
	}

	public void setCommandInfo(CommandInfo commandInfo) {
		this.commandInfo = commandInfo;
	}

	public Object getDataObj() {
		return dataObj;
	}

	public void setDataObj(Object dataObj) {
		this.dataObj = dataObj;
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
