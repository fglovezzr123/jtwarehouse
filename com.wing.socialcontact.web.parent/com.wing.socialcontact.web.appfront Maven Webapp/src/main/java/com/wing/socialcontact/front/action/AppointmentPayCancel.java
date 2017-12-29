package com.wing.socialcontact.front.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoy.service.wx.api.IAppointmentPayService;
import com.tojoy.service.wx.api.IOrderItemService;
import com.tojoy.service.wx.api.IOrderService;
import com.tojoy.service.wx.bean.AppointmentPay;
import com.tojoy.service.wx.bean.Order;
import com.tojoy.service.wx.bean.OrderItem;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.StringUtil;

/**
 * 取消约见
 * 
 * @author 刘涛
 * 
 */
@Controller
@RequestMapping("m/app/appointmentCancel")
public class AppointmentPayCancel extends BaseAppAction
{
	@Autowired
	private IAppointmentPayService appointmentPayService;
	@Autowired
	private IOrderService orderService;
	
	/**
	 * 取消约见
	 * 
	 * @author 刘涛
	 * @param id
	 */
	@RequestMapping("cancelAppointment")
	public @ResponseBody ResponseReport cancelAppointment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登陆", null);
		}
		// 获取约见id
		String id = rr.getDataValue("id");
		if (StringUtil.isEmpty(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "id为空", null);
		}
		AppointmentPay updateAppointmentPay = appointmentPayService.getAppointmentPay(id);
		String ystatus = updateAppointmentPay.getStatus();
		boolean bl = false;
		//如果是在确认约见后取消订单，需要将订单状态置为取消状态
		if("2".equals(ystatus)) {//确认约见
			bl = true;
		}
		// 更新状态
		String status = "4";
		updateAppointmentPay.setStatus(status);
		int relCount = appointmentPayService.updateAppointmentPay(updateAppointmentPay);
		if (relCount > 0) {
			if(!bl) {
				//从redis中删除对应记录
				RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
				redisCache.evict(id);
			}
			if(bl) {//修改订单为取消状态
				Order order = new Order();
				order.setItem_Id(Long.parseLong(id));
				order = orderService.getOrder(order);
				order.setStatus("3");
				orderService.updateOrder(order);
			}
			return super.getAjaxResult(rr, ResponseCode.OK, "取消约见成功", null);
		}else {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "取消约见失败", null);
	
		}
	}
}
