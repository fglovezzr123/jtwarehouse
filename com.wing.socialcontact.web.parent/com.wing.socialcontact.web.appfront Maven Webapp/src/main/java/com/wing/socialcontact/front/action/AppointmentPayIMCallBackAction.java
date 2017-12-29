package com.wing.socialcontact.front.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.tojoy.service.wx.api.IAppointmentPayService;
import com.tojoy.service.wx.api.IOrderItemService;
import com.tojoy.service.wx.api.IOrderService;
import com.tojoy.service.wx.bean.AppointmentPay;
import com.tojoy.service.wx.bean.Order;
import com.tojoy.service.wx.bean.OrderItem;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.wing.socialcontact.service.wx.api.IChargeSwitchService;
import com.wing.socialcontact.service.wx.bean.ChargeSwitch;

/**
 * im回调
 */
@Controller
@RequestMapping("m/app/iMCallBack")
public class AppointmentPayIMCallBackAction extends BaseAppAction
{

	@Autowired
	private IAppointmentPayService appointmentPayService;
	@Autowired
	private IChargeSwitchService chargeSwitchService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderItemService orderItemService;

	@RequestMapping("callBack")
	@ResponseBody
	public String callBack(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			InputStream is = request.getInputStream();
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String s = "";
			while ((s = br.readLine()) != null)
			{
				sb.append(s);
			}
			if (sb.toString().length() > 0)
			{
				Gson gson = new Gson();
				Map<String, Object> map = new HashMap<String, Object>();
				map = gson.fromJson(sb.toString(), map.getClass());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// 开始时间
				String start_time = map.get("startTime") == null ? "" : map.get("startTime").toString();
				// 时长（秒）
				String duration = map.get("duration") == null ? "" : map.get("duration").toString();
				// 结束时间
				String actualEnd_time = Long.parseLong(start_time)+Long.parseLong(duration)*1000+"";
				// 约见id
				String id = map.get("id") == null ? "" : map.get("id").toString();

				double sePlatFee = 0;
				double countPlatJB = 0;
				double miToJB = 0;
				double secondToFee = 0;
				double countToJB = 0;
				double unitPriceJB = 0;
				double countJB = 0;
				AppointmentPay appointmentPay = new AppointmentPay();
				// 判断平台是否收费
				ChargeSwitch chargeSwitch = chargeSwitchService.getChargeSwitchBySign("appointment");
				if (1==chargeSwitch.getStatus())
				{// 平台收费开关打开
					// 获取平台每分钟收取的费用
					double miPlatFee = chargeSwitch.getPlatformFee();
					if (miPlatFee > 0)
					{
						// 换算平台每秒收费
						sePlatFee = new BigDecimal(miPlatFee).divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
						// 平台实际收取总JB
						countPlatJB = new BigDecimal(duration).multiply(new BigDecimal(sePlatFee)).doubleValue();
						// 判断被约见人每分钟JB
						miToJB = 0.0;
						// 换算被约见人每秒收取JB
						secondToFee = new BigDecimal(miToJB).divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
						// 被约人实际收取总JB
						countToJB = new BigDecimal(duration).multiply(new BigDecimal(secondToFee)).doubleValue();
						// 单价
						unitPriceJB = new BigDecimal(sePlatFee).add(new BigDecimal(countToJB)).doubleValue();
						// 总JB(平台JB+被约人JB)
						countJB = new BigDecimal(countPlatJB).add(new BigDecimal(countToJB)).doubleValue();
						// 修改约见状态、时间、JB
					}
					else
					{
						// 判断被约见人每分钟JB
						miToJB = 0.0;
						// 换算被约见人每秒收取JB
						secondToFee = new BigDecimal(miToJB).divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
						// 被约人实际收取总JB
						countToJB = new BigDecimal(duration).multiply(new BigDecimal(secondToFee)).doubleValue();
						// 单价
						unitPriceJB = secondToFee;
						// 总JB(平台JB+被约人JB)
						countJB = countToJB;
					}
				}
				else
				{// 平台不收取费用
					// 判断被约见人每分钟JB
					miToJB = 0.0;
					// 换算被约见人每秒收取JB
					secondToFee = new BigDecimal(miToJB).divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
					// 被约人实际收取总JB
					countToJB = new BigDecimal(duration).multiply(new BigDecimal(secondToFee)).doubleValue();
					// 单价
					unitPriceJB = secondToFee;
					// 总JB(平台JB+被约人JB)
					countJB = countToJB;

				}
				appointmentPay.setStart_Time(new Date(Long.parseLong(start_time)));
				appointmentPay.setDuration(Long.parseLong(duration));
				appointmentPay.setActualEnd_Time(new Date(Long.parseLong(actualEnd_time)));
				if(countJB>0)
					appointmentPay.setStatus("5");
				else
					appointmentPay.setStatus("3");
				appointmentPay.setId(Long.parseLong(id));
				int count = appointmentPayService.updateAppointmentPay(appointmentPay);
				if (count > 0)
				{
					// 更新订单

					Order order = new Order();
					order.setItem_Id(Long.parseLong(id));
					order.setPayment(countJB);
					orderService.updateOrder(order);
					// 更新商品表
					OrderItem orderItem = new OrderItem();
					orderItem.setItem_Id(Long.parseLong(id));
					orderItem = orderItemService.getOrderItem(orderItem);
					orderItem.setNum(Long.parseLong(duration));
					orderItem.setPrice(unitPriceJB);
					orderItem.setTotal_Fee(countJB);
					orderItemService.updateOrderItem(orderItem);
					// 操作日志
					return "成功";
				}
				else
				{
					return "失败！";
				}
			}
			else
			{
				return "失败！";
			}
		}
		catch (IOException e1)
		{
			return "失败！";
		}
	}
}
