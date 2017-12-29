package com.tojoycloud.frame.controller;

import com.tojoycloud.frame.report.RequestReport;
import com.tojoycloud.frame.report.ResponseReport;
import com.tojoycloud.frame.report.entity.CommandInfo;

public abstract class Action
{
	protected CommandInfo _commandInfo;

	public Action(RequestReport request)
	{
		this._commandInfo = request.getCommandInfo();
	}

	public CommandInfo getCommandInfo()
	{
		return _commandInfo;
	}

	public abstract ResponseReport operation();

	protected void sendMQ()
	{
		// TODO:发送MQ方法
	}

}
