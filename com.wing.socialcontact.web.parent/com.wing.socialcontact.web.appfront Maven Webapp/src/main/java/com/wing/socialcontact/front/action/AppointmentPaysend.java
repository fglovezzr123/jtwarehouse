package com.wing.socialcontact.front.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.StringUtil;
import com.wing.socialcontact.util.UUIDGenerator;

/**
 * 发起约见
 * @author 刘涛
 * 
 */
@Controller
@RequestMapping("m/app/appointmentSend")
public class AppointmentPaysend extends BaseAppAction
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
	//quenName
	private String queneName = ApplicationPath.getParameter("queueName");
	/**
	 * 发起约见
	 * 
	 * @author 刘涛
	 * @param toId被约见人id(必选)
	 * @param expectTime期望约见时间
	 *            (格式：yyyy-MM-dd HH:mm:ss) estimateTimeLength 预计约见时长(单位：秒)
	 *            msg 约见理由 appointmentStatus 约见分类0定时、1立即 type
	 *            0普通约见、1其他
	 */
	@RequestMapping("sendAppointment")
	public @ResponseBody ResponseReport sendAppointment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登陆", null);
		}
		// 判断此用户约见业务模块是否有未完成的订单
		Order vorder = new Order();
		//status=0未知付
		vorder.setStatus("0");
		//用户id
		vorder.setUser_Id(Long.parseLong(userId));
		//type=0约见业务模块
		vorder.setType("0");
		List orderList = orderService.queryOrder(vorder);
		double sum = 0;
		for(int i=0;i<orderList.size();i++) {
			Map orderMap = (HashMap)orderList.get(i);
			
			String payment = (String) (orderMap.get("payment")==null?"0":orderMap.get("payment").toString());
			new BigDecimal(sum).add(new BigDecimal(Double.parseDouble(payment)));
		}
		if(sum>0)
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "有未知付订单,请完成订单", orderList);
		try
		{
			// 约见人
			String fromId = userId;
			if (StringUtil.isEmpty(fromId))
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "约见人为空，发起约见失败", null);
			// 被约见人
			String toId = rr.getDataValue("toId");
			if (StringUtil.isEmpty(toId))
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "被约见人为空，发起约见失败", null);
			// 状态0待约见、1已约见、2确认约见、3完成、4取消、5未支付，6支付完成
			String status = "0";
			// 约见类型0定时、1立即
			String appointmentStatus = rr.getDataValue("appointmentStatus");
			if (StringUtil.isEmpty(appointmentStatus))
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "约见类型为空，发起约见失败", null);
			//期望约见时间
			String expectTime = "";
			//期望约见时长
			String estimateTimeLength = "";
			//预计结束时间
			String estimateEndTime = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if("0".equals(appointmentStatus)) {//定时
				// 期望约见时间
				expectTime = rr.getDataValue("expectTime");
				if (StringUtil.isEmpty(expectTime))
					return super.getAjaxResult(rr, ResponseCode.NotSupport, "期望约见时间为空，发起约见失败", null);
				// 预计约见时长（秒）
				estimateTimeLength = rr.getDataValue("estimateTimeLength");
				if (StringUtil.isEmpty(estimateTimeLength))
					return super.getAjaxResult(rr, ResponseCode.NotSupport, "预计约见时长为空，发起约见失败", null);
				// 预计结束时间
				long eet = sdf.parse(expectTime).getTime()/1000 + Long.parseLong(estimateTimeLength);
				Date date = new Date(eet);
				estimateEndTime = sdf.format(date);
				// 判断期望约见时间是否有冲突（如果被约见人已经已经有确认约见的记录，则不能发起约见）
				AppointmentPay queryAppointmentPay = new AppointmentPay();
				queryAppointmentPay.setStatus("2");
				queryAppointmentPay.setFrom_Id(Long.parseLong(userId));
				queryAppointmentPay.setExpect_Time(sdf.parse(expectTime));
				List<AppointmentPay> listAppointmentPay = appointmentPayService.queryConfirmList(queryAppointmentPay);
				if (listAppointmentPay.size() > 0) {//时间冲突
					return super.getAjaxResult(rr, ResponseCode.NotSupport, "约见时间冲突", null);
				}
			}
			//约见时长（秒）
			estimateTimeLength = rr.getDataValue("estimateTimeLength");
			// 约见理由
			String msg = rr.getDataValue("msg");
			// 类型0普通约见、1其他
			String type = rr.getDataValue("type");
			if (StringUtil.isEmpty(type))
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "类型为空，发起约见失败", null);
			AppointmentPay insertAppointmentPay = new AppointmentPay();
			insertAppointmentPay.setFrom_Id(Long.parseLong(fromId));
			insertAppointmentPay.setTo_Id(Long.parseLong(toId));
			insertAppointmentPay.setStatus(status);
			if(!StringUtil.isEmpty(expectTime)) {
				insertAppointmentPay.setExpect_Time(sdf.parse(expectTime));
			}
			if(!StringUtil.isEmpty(estimateTimeLength)) {
				insertAppointmentPay.setEstimateTimeLength(Long.parseLong(estimateTimeLength));
			}
			if(!StringUtil.isEmpty(estimateEndTime)) {
				insertAppointmentPay.setEstimateEnd_Time(sdf.parse(estimateEndTime));
			}
			insertAppointmentPay.setMsg(msg);
			insertAppointmentPay.setAppointmentStatus(appointmentStatus);
			insertAppointmentPay.setType(type);
			// 发起约见
			insertAppointmentPay = appointmentPayService.insertAppointmentPay(insertAppointmentPay);
			if (0 != insertAppointmentPay.getId())
			{
				//生成约见业务订单
				Order order = new Order();
				//生成订单号时间戳加6位随机码
				String timesTamp = new Date().getTime()+"";
				String randomCode = (int) ((Math.random() * 9 + 1) * 100000) + "";
				//订单号
				String order_id = timesTamp+randomCode;
				//未支付
				String order_status = "0";
				String user_id = userId;
				//订单所属业务模块0普通约见、1业务模块、2业务模块、3业务模块
				String order_type = "0";
				//订单类型0业务订单、1充值订单
				String otype = "0";
				//约见id
				String item_id = insertAppointmentPay.getId()+"";
				
				order.setOrder_Id(order_id);
				order.setStatus(order_status);
				order.setUser_Id(Long.parseLong(user_id));
				order.setType(order_type);
				order.setOrder_Type(otype);
				order.setItem_Id(Long.parseLong(item_id));
				order = orderService.insertOrder(order);
				//约见详情
				OrderItem orderItem = new OrderItem();
				orderItem.setItem_Id(Long.parseLong(insertAppointmentPay.getId()+""));
				orderItem.setOrder_Id(order_id);
				orderItem.setType("0");
				orderItemService.insertOrderItem(orderItem);
				
				Date createTime = appointmentPayService.getAppointmentPay(insertAppointmentPay.getId()+"").getCreate_Time();
				insertAppointmentPay.setCreate_Time(createTime);
				//添加缓存
				RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
				if("0".equals(appointmentStatus)) {
					//设置超时时间1.定时：开始时间的时间戳时间+（持续时间-9000000）
					redisCache.put(insertAppointmentPay.getId()+"", insertAppointmentPay,(sdf.parse(expectTime).getTime()-900000)/1000);
				}else {
					//设置超时时间2.立即：发起请求时间的时间戳时间 + 配置的超时时间
					long mi = (createTime.getTime()+900000)/1000;
					redisCache.put(insertAppointmentPay.getId()+"",insertAppointmentPay, mi);
				}
				//发起MQ,将此条记录发送给task，待task接收，做数据监测清理工作
				//mns发送
//				Map<Long,AppointmentPay> map = new HashMap();
//				map.put(insertAppointmentPay.getId(), insertAppointmentPay);
//				JSONObject jsonObject = JSONObject.fromObject(map);
//				MNSClient mnsClient = MnsCloudCreater.getClientInstance();
//				Map<String, Object> pushMap = new HashMap<>(2);
//				pushMap.put(QueueAttributeEnum.QueueName.getAttrKey(), queneName);
//				CloudQueue cloudQueue = MnsQueueManager.getQueue(mnsClient, pushMap);
//				Map<String, Object> paraMap = new HashMap<>(2);
//				paraMap.put("messageBody", jsonObject);
//				MnsMessageManager.putMessage(cloudQueue, paraMap);
				//redisCache.putToQueue(insertAppointmentPay, value)

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
	
}
