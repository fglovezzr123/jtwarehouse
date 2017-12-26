package com.wing.socialcontact.front.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import com.tojoy.util.ApplicationPath;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.util.StringUtil;
import com.wing.socialcontact.util.UUIDGenerator;

/**
 * 约见付费版
 * 
 * @author 刘涛
 * 
 */
@Controller
@RequestMapping("m/app/appointmentPay")
public class AppointmentPayAction extends BaseAppAction
{

	@Autowired
	private IAppointmentPayService appointmentPayService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IMessageInfoService messageInfoService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderItemService orderItemService;

	// 发起约见系统通知
	private static final String content = ApplicationPath.getParameter("appointment.message.content");
	// 确认约见系统通知
	private static final String confirmContent = ApplicationPath.getParameter("confirm.message.content");

	/**
	 * 发起约见
	 * 
	 * @author 刘涛
	 * @param toId被约见人id(必选)
	 * @param expectTime期望约见时间
	 *            (必选，格式：yyyy-MM-dd HH:mm:ss) estimateTimeLength 预计约见时长(必选，单位：秒)
	 *            msg 约见理由(可选) appointmentStatus 约见分类(必选)0定时、1立即 type
	 *            0普通约见、1同城约见、2同行约见、3同趣约见、4大卡约见、5网红约见、6名医约见
	 */
	@RequestMapping("sendAppointment")
	public @ResponseBody ResponseReport sendAppointment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登陆", null);
		}
		// 判断上次约见是否付款
		Order vorder = new Order();
		vorder.setStatus("1");
		vorder.setUser_Id(Long.parseLong(userId));
		List<Order> orderList = orderService.queryOrder(vorder);
		if(orderList.size()>0) {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "有未知付订单", orderList);
		}
		try
		{
			// 获取数据
			// 约见人
			String fromId = userId;
			if (StringUtil.isEmpty(fromId))
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "约见人为空，发起约见失败", null);
			// 被约见人
			String toId = rr.getDataValue("toId");
			if (StringUtil.isEmpty(toId))
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "被约见人为空，发起约见失败", null);
			// 状态0待约见、1已约见、2确认约见、3完成、4取消
			String status = "0";
			// 约见类型0定时、1立即
			String appointmentStatus = rr.getDataValue("appointmentStatus");
			if (StringUtil.isEmpty(appointmentStatus))
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "约见类型为空，发起约见失败", null);
			String expectTime = "";
			String estimateTimeLength = rr.getDataValue("estimateTimeLength");
			String estimateEndTime = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if("0".equals(appointmentStatus)) {//定时
				// 判断期望约见时间是否有冲突（如果被约见人已经已经有确认约见的记录，则不能发起约见）
				AppointmentPay queryAppointmentPay = new AppointmentPay();
				queryAppointmentPay.setStatus("2");
				queryAppointmentPay.setFrom_Id(Long.parseLong(userId));
				List<AppointmentPay> listAppointmentPay = appointmentPayService.queryAppointmentPay(queryAppointmentPay);
				if (listAppointmentPay.size() == 0) {
					//时间不冲突
					// 期望约见时间
					expectTime = rr.getDataValue("expectTime");
					if (StringUtil.isEmpty(expectTime))
						return super.getAjaxResult(rr, ResponseCode.NotSupport, "期望约见时间为空，发起约见失败", null);
					// 预计约见时长
					estimateTimeLength = rr.getDataValue("estimateTimeLength");
					if (StringUtil.isEmpty(estimateTimeLength))
						return super.getAjaxResult(rr, ResponseCode.NotSupport, "预计约见时长为空，发起约见失败", null);
					// 预计结束时间
					long eet = sdf.parse(expectTime).getTime() + Long.parseLong(estimateTimeLength);
					Date date = new Date(eet);
					estimateEndTime = sdf.format(date);
				}else {
					return super.getAjaxResult(rr, ResponseCode.NotSupport, "约见时间冲突", null);
				}
				
			}
			// 约见理由
			String msg = rr.getDataValue("msg");
			// 类型0普通约见、1同城约见、2同行约见、3同趣约见、4大卡约见、5网红约见、6名医约见
			String type = rr.getDataValue("type");
			if (StringUtil.isEmpty(type))
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "类型为空，发起约见失败", null);
			AppointmentPay insertAppointmentPay = new AppointmentPay();
			insertAppointmentPay.setFrom_Id(Long.parseLong(fromId));
			insertAppointmentPay.setTo_Id(Long.parseLong(toId));
			insertAppointmentPay.setStatus(status);
			//insertAppointmentPay.setExpect_Time(sdf.parse(expectTime));
			if(!StringUtil.isEmpty(estimateTimeLength)) {
				insertAppointmentPay.setEstimateTimeLength(Long.parseLong(estimateTimeLength));
			}
			//insertAppointmentPay.setEstimateEnd_Time(sdf.parse(estimateEndTime));
			insertAppointmentPay.setMsg(msg);
			insertAppointmentPay.setAppointmentStatus(appointmentStatus);
			insertAppointmentPay.setType(type);
			// 发起约见
			insertAppointmentPay = appointmentPayService.insertAppointmentPay(insertAppointmentPay);
			if (0 != insertAppointmentPay.getId())
			{
				//生成订单
				Order order = new Order();
				//生成订单号时间戳加6位随机码
				String timesTamp = new Date().getTime()+"";
				String randomCode = (int) ((Math.random() * 9 + 1) * 100000) + "";
				//订单号
				String order_id = timesTamp+randomCode;
				String order_status = "0";
				String user_id = userId;
				String order_type = type;
				order.setOrder_Id(order_id);
				order.setStatus(order_status);
				order.setUser_Id(Long.parseLong(user_id));
				order.setType(order_type);
				order = orderService.insertOrder(order);
				//生成商品详情
				OrderItem orderItem = new OrderItem();
				orderItem.setItem_Id(insertAppointmentPay.getId());
				orderItem.setOrder_Id(order_id);
				orderItemService.insertOrderItem(orderItem);
				// 给被约见人发送系统通知
				TjyUser fromUser = tjyUserService.selectByPrimaryKey(fromId);
				TjyUser toUser = tjyUserService.selectByPrimaryKey(toId);
				String fromName = fromUser.getTrueName();
				String toName = toUser.getTrueName();
				if (StringUtil.isEmpty(fromName))
					fromName = fromUser.getNickname();
				if (StringUtil.isEmpty(toName))
					toName = toUser.getNickname();
				String str[] = content.split("XX");
				String content = str[0] + toName + str[1] + fromName + str[2];
				MessageInfo mi = new MessageInfo();
				mi.setId(UUIDGenerator.getUUID());
				mi.setContent(content);
				mi.setType(5);
				mi.setTemplateId("");
				mi.setStatus(0);
				mi.setCreateTime(new Date());
				mi.setToUserId(toId);
				String mess = messageInfoService.addMessageInfo(mi);
				if ("msg.operation.success".equals(mess))
					return super.getAjaxResult(rr, ResponseCode.OK, "发起约见成功", insertAppointmentPay);
				else
					return super.getAjaxResult(rr, ResponseCode.OK, "发起约见成功,系统消息发送失败", insertAppointmentPay);
			}
			else
			{
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "发起约见失败", null);
			}

		}
		catch (ParseException e)
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "ParseException，发起失败", null);
		}
	}

	/**
	 * 确认约见
	 * 
	 * @author 刘涛
	 * @param id
	 */
	@RequestMapping("confirmAppointment")
	public @ResponseBody ResponseReport confirmAppointment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登陆", null);
		}
		// 获取id
		String id = rr.getDataValue("id");
		if (StringUtil.isEmpty(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "id为空", null);
		}
		// 更新状态
		String status = "2";
		AppointmentPay updateAppointmentPay = new AppointmentPay();
		updateAppointmentPay.setId(Long.parseLong(id));
		updateAppointmentPay.setStatus(status);
		int relCount = appointmentPayService.updateAppointmentPay(updateAppointmentPay);
		if (relCount > 0)
		{
			// 给被约见人发送系统通知
			AppointmentPay getAppointmentPay = appointmentPayService.getAppointmentPay(id);
			TjyUser fromUser = tjyUserService.selectByPrimaryKey(getAppointmentPay.getFrom_Id() + "");
			TjyUser toUser = tjyUserService.selectByPrimaryKey(getAppointmentPay.getTo_Id() + "");
			String fromName = fromUser.getTrueName();
			String toName = toUser.getTrueName();
			if (StringUtil.isEmpty(fromName))
				fromName = fromUser.getNickname();
			if (StringUtil.isEmpty(toName))
				toName = toUser.getNickname();
			String str[] = confirmContent.split("XX");
			String content = str[0] + fromName + str[1] + toName + str[2];
			MessageInfo mi = new MessageInfo();
			mi.setId(UUIDGenerator.getUUID());
			mi.setContent(content);
			mi.setType(5);
			mi.setTemplateId("");
			mi.setStatus(0);
			mi.setCreateTime(new Date());
			mi.setToUserId(toUser.getId());
			String mess = messageInfoService.addMessageInfo(mi);
			if ("msg.operation.success".equals(mess))
				return super.getAjaxResult(rr, ResponseCode.OK, "确认约见成功", null);
			else
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "确认约见成功,系统消息发送失败", null);
		}
		else
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "确认约见失败", null);
		}
	}

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
		// 获取id
		String id = rr.getDataValue("id");
		if (StringUtil.isEmpty(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "id为空", null);
		}
		// 更新状态
		String status = "4";
		AppointmentPay updateAppointmentPay = new AppointmentPay();
		updateAppointmentPay.setId(Long.parseLong(id));
		updateAppointmentPay.setStatus(status);
		int relCount = appointmentPayService.updateAppointmentPay(updateAppointmentPay);
		if (relCount > 0)
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "取消约见成功", null);
		else
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "取消约见失败", null);
	}
}
