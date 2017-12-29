package com.wing.socialcontact.front.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.util.StringUtil;

/**
 * 约见业务入口
 * 
 * @author 刘涛
 * 
 */
@Controller
@RequestMapping("m/app/appointment")
public class AppointmentPayAction extends BaseAppAction
{

	

	
	
	@RequestMapping("start")
	public @ResponseBody ResponseReport sendAppointment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String start = rr.getDataValue("start");
		if (StringUtil.isEmpty(start))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		
		return super.getAjaxResult(rr, ResponseCode.NotSupport, "取消约见失败", null);
	}
}
