package com.tojoycloud.frame.controller;

import com.tojoycloud.frame.report.RequestReport;
import com.tojoycloud.frame.report.ResponseReport;

public class OrderAgreeAction extends Action
{

	public OrderAgreeAction(RequestReport request)
	{
		super(request);
	}

	@Override
	public ResponseReport operation()
	{
		String orderID = _commandInfo.getOrderId();
		// TODO:变更约见记录为同意状态
		// TODO:从Redis中删除掉对应的orderID
		// TODO:调用订单dubbo服务接口，生成对应订单（只记录对应约见ID即可，时间等待结束时IM回调，价格待结束时需要判断当前约见收费的总开关是否开启，并根据全局配置回写价格做结算）
		return null;
	}

}
