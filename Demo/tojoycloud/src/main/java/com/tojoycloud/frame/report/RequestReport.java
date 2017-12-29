package com.tojoycloud.frame.report;

import com.tojoycloud.frame.report.base.BaseReport;
import com.tojoycloud.frame.report.base.ReportType;
import com.tojoycloud.frame.report.entity.AppInfo;
import com.tojoycloud.frame.report.entity.CommandInfo;
import com.tojoycloud.frame.report.entity.DeviceInfo;
import com.tojoycloud.frame.report.entity.UserProperty;

@SuppressWarnings({"serial"})
public class RequestReport extends BaseReport
{
	private AppInfo appInfo;

	private UserProperty userProperty;

	private DeviceInfo deviceInfo;

	private CommandInfo commandInfo;

	public RequestReport()
	{
		super(ReportType.REQUEST);
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
