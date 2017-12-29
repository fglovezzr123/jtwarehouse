package com.wing.socialcontact.front.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoy.service.wx.api.IOrderService;
import com.tojoy.service.wx.bean.Order;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.util.StringUtil;

/**
 * 视频挂断返回金额、时长
 * @author 刘涛
 * 
 */

@Controller
@RequestMapping("m/app/end")
public class AppointmentPayEndVideo extends BaseAppAction
{
	@Autowired
	private IOrderService orderService;
	@RequestMapping("endVideo")
	public @ResponseBody ResponseReport endVideo(@RequestBody RequestReport rr)
	{
		
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登陆", null);
		}
		//约见记录id
		String id = rr.getDataValue("id");
		
		Order order = new Order();
		order.setItem_Id(Long.parseLong(id));
		List orderList = orderService.queryOrder(order);
		if(orderList.size()>0) {
			Map orderMap = (HashMap)orderList.get(0);
			return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", orderMap);
		}else {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "获取失败", null);
		}
		
		//}
	}
}
