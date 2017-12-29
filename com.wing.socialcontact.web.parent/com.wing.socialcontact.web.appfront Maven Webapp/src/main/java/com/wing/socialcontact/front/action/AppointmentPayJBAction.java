package com.wing.socialcontact.front.action;

import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoy.service.wx.api.IOrderItemService;
import com.tojoy.service.wx.api.IOrderService;
import com.tojoy.service.wx.bean.AppointmentPay;
import com.tojoy.service.wx.bean.Order;
import com.tojoy.service.wx.bean.OrderItem;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.tojoycloud.wechat.service.WxPayService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.DynamicPayLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.DoubleUtil;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.StringUtil;

/**
 * JB支付
 * */
@Controller
@RequestMapping("m/app/pay")
public class AppointmentPayJBAction extends BaseAppAction
{
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IWxUserService wxUserService;
	
	@RequestMapping("jbPayment")
	public @ResponseBody ResponseReport jbPayment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登陆", null);
		}
		// 约见id
		String id = rr.getDataValue("id");
		if (StringUtil.isEmpty(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "id为空", null);
		}
		//获取支付总JB
		Order order = new Order();
		order.setItem_Id(Long.parseLong(id));
		order = orderService.getOrder(order);
		double countFee = order.getPayment();
		//先减去自己的j币
		WxUser user = wxUserService.selectById(userId);
		Double doub = DoubleUtil.sub(user.getJbAmount()!= null ? user.getJbAmount():0, Double.valueOf(countFee));
		if(doub<=0){
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "J币不足，请充值之后再支付！", null);
		}else{
			user.setJbAmount(doub);
			wxUserService.updateWxUser(user);
			//更新订单日志表
//			DynamicPayLog dynamicPayLog = new DynamicPayLog();
//			dynamicPayLog.setActionTime(new Date());
//			dynamicPayLog.setActionType(2);
//			dynamicPayLog.setDynamicId(dynamicId);
//			dynamicPayLog.setId(UUID.randomUUID().toString());
//			dynamicPayLog.setPayAmount(Long.valueOf(jcount));
//			dynamicPayLog.setUserId(userId);
//			dynamicPayLog.setStatus(1);
//			dynamicPayLog.setMediaId(dynamic.getMediaId());
//			dynamicPayLogDao.insertSelective(dynamicPayLog);
			return super.getAjaxResult(rr, ResponseCode.OK, "付款成功", null);
		}
		
	}
}
