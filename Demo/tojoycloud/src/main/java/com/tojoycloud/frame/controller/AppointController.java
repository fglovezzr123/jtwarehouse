package com.tojoycloud.frame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoycloud.frame.common.Enum_order;
import com.tojoycloud.frame.report.RequestReport;
import com.tojoycloud.frame.report.ResponseReport;
import com.tojoycloud.frame.report.base.ResponseCode;

/**
 * 约见基本接口
 * 
 * @author qiaoweiran
 * 
 */
@Controller
@RequestMapping("/appoint")
public class AppointController
{
	/**
	 * 订单接口（下单、同意、取消） orderStatus：0：取消 1：下单 2：同意
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public @ResponseBody
	ResponseReport sendSns(@RequestBody RequestReport user)
	{

		String orderStatus = user.getCommandInfo().getOrderStatus();
		Enum_order orderEnum = Enum_order.getOptType(orderStatus);
		Action action = null;
		switch (orderEnum)
		{
			case ORDER:
				action = new OrderAction(user);
			break;
			case AGREE:
				action = new OrderAgreeAction(user);
			break;
			case CANCEL:
				action = new OrderCancelAction(user);
			break;
			default:
			break;
		}
		if (action != null)
			return action.operation();
		ResponseReport br = new ResponseReport();
		br.setResponseCode(ResponseCode.Error);
		br.setResponseTips("参数错误!");
		return br;
	}
}
