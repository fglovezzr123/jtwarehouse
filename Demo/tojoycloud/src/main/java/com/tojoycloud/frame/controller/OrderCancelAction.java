package com.tojoycloud.frame.controller;

import com.tojoycloud.frame.report.RequestReport;
import com.tojoycloud.frame.report.ResponseReport;

public class OrderCancelAction extends Action
{

	public OrderCancelAction(RequestReport request)
	{
		super(request);
	}

	@Override
	public ResponseReport operation()
	{
		String orderID = _commandInfo.getOrderId();
		// TODO:变更约见记录为取消状态
		// TODO:从Redis中删除掉对应的orderID
		return null;
	}

}
