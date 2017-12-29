package com.wing.socialcontact.front.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoy.service.wx.api.IOrderItemService;
import com.tojoy.service.wx.api.IOrderService;
import com.tojoy.service.wx.bean.Order;
import com.tojoy.service.wx.bean.OrderItem;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.tojoycloud.wechat.entity.request.WxPayUnifiedOrderRequest;
import com.tojoycloud.wechat.entity.result.WxPayAppOrderResult;
import com.tojoycloud.wechat.exception.WxPayException;
import com.tojoycloud.wechat.service.WxPayService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.DoubleUtil;
import com.wing.socialcontact.util.StringUtil;
/**
 * 充值
 * 
 * @author 刘涛
 * 
 */
@Controller
@RequestMapping("m/app/order")
public class AppointmentPayOrderStart extends BaseAppAction
{
	@Autowired
	private WxPayService wxPayService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderItemService orderItemService;
	@Autowired
	private IListValuesService listValuesService;
	@Autowired
	private IWxUserService wxUserService;
	
	@RequestMapping("startOrder")
	public @ResponseBody ResponseReport generatingOrder(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登陆", null);
		}
		//充值金额
		String rechargeAmount = rr.getDataValue("rechargeAmount");
		if(StringUtil.isEmpty(rechargeAmount)) {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "充值金额为空", null);
		}
		//充值类型1金额，2JB
		String rechargeType = rr.getDataValue("rechargeType");
		if(StringUtil.isEmpty(rechargeType)) {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "充值类型不能为空", null);
		}
		if (Double.parseDouble(rechargeAmount) <= 0) {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "充值金额必须大于0元", null);
		}
		double rechargeJB = 0;
		if("2".equals(rechargeType)) {//JB充值
			// J币人民币换算
			ListValues listValue = listValuesService.selectByPrimaryKey("fbc8733aafed4fd9ac4f341b155b5311");
			if (StringUtils.isEmpty(listValue.getListValue())) {
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "当前J币兑换比例尚未设置，不能充值，请联系客服人员", null);
			} else {
				double bl = Double.valueOf(listValue.getListValue());
				rechargeJB = new BigDecimal(Double.parseDouble(rechargeAmount)).multiply(new BigDecimal(bl)).doubleValue();
			}
		}
		//ip
		String spbillCreateIp = rr.getDataValue("spbillCreateIp");
		if (StringUtil.isEmpty(spbillCreateIp))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		//生成充值订单
		Order order = new Order();
		//生成订单号时间戳加6位随机码
		String timesTamp = new Date().getTime()+"";
		String randomCode = (int) ((Math.random() * 9 + 1) * 100000) + "";
		//订单号
		String order_id = timesTamp+randomCode;
		//未支付
		String order_status = "0";
		String user_id = userId;
		//订单所属业务模块0普通约见、1普通充值、2业务模块、3业务模块
		String order_type = "1";
		//订单类型0业务订单、1充值订单
		String type = "1";
		
		order.setOrder_Id(order_id);
		order.setStatus(order_status);
		order.setUser_Id(Long.parseLong(user_id));
		order.setType(order_type);
		order.setOrder_Type(type);
		order = orderService.insertOrder(order);
		
		//支付
		WxPayUnifiedOrderRequest payRequest = WxPayUnifiedOrderRequest.newBuilder().spbillCreateIp(spbillCreateIp)
				.body("").totalFee(WxPayUnifiedOrderRequest.yuanToFee(rechargeAmount)).outTradeNo(order_id).build();
		try
		{
			WxPayAppOrderResult result = wxPayService.createClientOrder(payRequest);
			Map map = new HashMap();
			map.put("wxPayAppOrderResult", result);
			
			
			orderService.updateOrder(order);
			//更新用户JB
			
			WxUser user = wxUserService.selectById(userId);
			Double doub = DoubleUtil.add(user.getJbAmount()!= null ? user.getJbAmount():0, rechargeJB);
			user.setJbAmount(doub);
			wxUserService.updateWxUser(user);
			
			
			
			return super.getSuccessAjaxResult(rr, "成功", map);
		}
		catch (WxPayException e)
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "失败", null);
		}
		
	}
}
