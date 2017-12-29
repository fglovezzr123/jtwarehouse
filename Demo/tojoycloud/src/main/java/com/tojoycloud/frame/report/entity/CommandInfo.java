package com.tojoycloud.frame.report.entity;

public class CommandInfo
{
	private String commandName;

	private String eventName;

	// 发起方
	private String fromId;

	// 被约方
	private String toId;

	// 约见模式（1—立即 2—定时）
	private String appointMode;

	// 起始时间(时间戳格式)
	private String startTime;

	// 持续时间（分）
	private String durationTime;

	// 订单ID
	private String orderId;

	// 订单状态(0-取消 1-下单 2—同意）
	private String orderStatus;

	public String getOrderStatus()
	{
		return orderStatus;
	}

	public String getAppointMode()
	{
		return appointMode;
	}

	public void setAppointMode(String appointMode)
	{
		this.appointMode = appointMode;
	}

	public void setOrderStatus(String orderStatus)
	{
		this.orderStatus = orderStatus;
	}

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getFromId()
	{
		return fromId;
	}

	public void setFromId(String fromId)
	{
		this.fromId = fromId;
	}

	public String getToId()
	{
		return toId;
	}

	public void setToId(String toId)
	{
		this.toId = toId;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getDurationTime()
	{
		return durationTime;
	}

	public void setDurationTime(String durationTime)
	{
		this.durationTime = durationTime;
	}

	public String getCommandName()
	{
		return commandName;
	}

	public void setCommandName(String commandName)
	{
		this.commandName = commandName;
	}

	public String getEventName()
	{
		return eventName;
	}

	public void setEventName(String eventName)
	{
		this.eventName = eventName;
	}

}
