package com.tojoy.meeting.report;


import com.tojoy.meeting.common.report.BaseReport;
import com.tojoy.meeting.common.report.base.AppInfo;
import com.tojoy.meeting.common.report.base.CommandInfo;
import com.tojoy.meeting.common.report.base.DeviceInfo;
import com.tojoy.meeting.common.report.base.UserProperty;

@SuppressWarnings({"serial"})
public class RequestReport extends BaseReport
{
	private AppInfo appInfo;

	private UserProperty userProperty;

	private DeviceInfo deviceInfo;

	private CommandInfo commandInfo;

	public RequestReport()
	{
		super();
	}

	public AppInfo getAppInfo()
	{
		return appInfo;
	}

	public void setAppInfo(AppInfo appInfo)
	{
		this.appInfo = appInfo;
	}

	public UserProperty getUserProperty()
	{
		return userProperty;
	}

	public void setUserProperty(UserProperty userProperty)
	{
		this.userProperty = userProperty;
	}

	public DeviceInfo getDeviceInfo()
	{
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo)
	{
		this.deviceInfo = deviceInfo;
	}

	public CommandInfo getCommandInfo()
	{
		return commandInfo;
	}

	public void setCommandInfo(CommandInfo commandInfo)
	{
		this.commandInfo = commandInfo;
	}

}
