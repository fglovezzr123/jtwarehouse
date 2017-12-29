package com.tojoycloud.frame.controller;

import com.tojoycloud.frame.common.Enum_appointMode;
import com.tojoycloud.frame.report.RequestReport;
import com.tojoycloud.frame.report.ResponseReport;

public class OrderAction extends Action
{

	public OrderAction(RequestReport request)
	{
		super(request);
	}

	@Override
	public ResponseReport operation()
	{
		String fromId = _commandInfo.getFromId();
		String toId = _commandInfo.getToId();
		String mode = _commandInfo.getAppointMode();
		String durationTime = _commandInfo.getDurationTime();
		String startTime = null;
		if (durationTime != null && durationTime.equals(Enum_appointMode.IMMEDIATELY.getValue()))
			startTime = _commandInfo.getStartTime();
		// TODO:入库相应信息,作为约见记录功能
		// TODO:添加Redis中，Key为约见orderID，value为整条记录，时效为 ——
		// 1.定时：开始时间的时间戳时间+（持续时间-15）；注意：开始时间为时间戳，持续时间和15都是以（分）为单位，需要换算
		// 2.立即：发起请求时间的时间戳时间 + 配置的超时时间
		// TODO：同时发起MQ，将此条记录发送给task，待task接收，做数据监测清理工作
		sendMQ();
		return null;
	}

}
