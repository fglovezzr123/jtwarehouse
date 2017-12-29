package com.wing.socialcontact.front.action;

import java.util.Date;
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
 * 确认约见
 * 
 * @author 刘涛
 * 
 */
@Controller
@RequestMapping("m/app/appointmentConfirm")
public class AppointmentPayConfirm extends BaseAppAction
{
	@Autowired
	private IAppointmentPayService appointmentPayService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IMessageInfoService messageInfoService;
	// 确认约见系统通知
	private static final String confirmContent = ApplicationPath.getParameter("confirm.message.content");

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
		// 约见记录的id
		String id = rr.getDataValue("id");
		if (StringUtil.isEmpty(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "id为空", null);
		}
		
		// 更新状态
		String status = "2";
		AppointmentPay appointmentPay = appointmentPayService.getAppointmentPay(id);
		//二次判断是否超时task将状态置成取消
		if("0".equals(appointmentPay.getStatus())) {//没有超时，将状态置成确认状态
			appointmentPay.setStatus(status);
			int relCount = appointmentPayService.updateAppointmentPay(appointmentPay);
			if (relCount > 0)
			{
				//从redis中删除记录
				RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
				redisCache.evict(id);
				
				
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
		}else {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "已超时，确认约见失败", null);
		}
		
		
	}


}
