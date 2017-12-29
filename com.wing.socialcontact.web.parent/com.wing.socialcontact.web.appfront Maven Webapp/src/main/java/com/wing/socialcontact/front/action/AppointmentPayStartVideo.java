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
 * 接听视频验证是否有未知付订单
 * @author 刘涛
 * 
 */

@Controller
@RequestMapping("m/app/video")
public class AppointmentPayStartVideo extends BaseAppAction
{
	
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping("valvideo")
	public @ResponseBody ResponseReport valvideo(@RequestBody RequestReport rr)
	{
		
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登陆", null);
		}
		Order vorder = new Order();
		//status=0未知付
		vorder.setStatus("0");
		//用户id
		vorder.setUser_Id(Long.parseLong(userId));
		//type=0约见业务模块
		vorder.setType("0");
		List orderList = orderService.queryOrder(vorder);
		double sum = 0;
		//if(orderList.size()>0) {
		for(int i=0;i<orderList.size();i++) {
			Map orderMap = (HashMap)orderList.get(i);
			
			String payment = (String) (orderMap.get("payment")==null?"0":orderMap.get("payment").toString());
			new BigDecimal(sum).add(new BigDecimal(Double.parseDouble(payment)));
		}
		if(sum>0)
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "有未知付订单,请完成订单", orderList);
		else
			return super.getAjaxResult(rr, ResponseCode.OK,"已支付" , null);
		//}
	}
}
