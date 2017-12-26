package com.wing.socialcontact.front.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoy.service.wx.api.IAppointmentPayService;
import com.tojoy.service.wx.bean.AppointmentPay;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.util.StringUtil;

/**
 * 确认约见
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
