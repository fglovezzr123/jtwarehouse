package com.wing.socialcontact.front.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoy.service.wx.api.IAppointmentPayService;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.util.StringUtil;
/**
 * 获取代约见，已约见
 * */
@Controller
@RequestMapping("m/app/getAppointmentList")
public class AppointmentPayAll extends BaseAppAction
{
	@Autowired
	private IAppointmentPayService appointmentPayService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IMessageInfoService messageInfoService;
	/**
	 * 获取约见列表
	 * 
	 */
	@RequestMapping("getAppointment")
	public @ResponseBody ResponseReport getAppointmentList(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String bs = rr.getDataValue("bs");
		if (StringUtil.isEmpty(bs))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		String pageSize = rr.getDataValue("pageSize");
		String pageNum = rr.getDataValue("pageNum");
		if (StringUtil.isEmpty(pageNum))
		{
			pageNum = "1";// 默认第一页
		}
		if (StringUtil.isEmpty(pageSize))
		{
			pageSize = "10"; // 默认10条
		}
		//开关：0显示全部，1只显示作为被约见人记录
		String derail = rr.getDataValue("derail");
		if(StringUtil.isEmpty(derail)) {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		List list = null;
		if ("dyj".equals(bs))
			if("0".equals(derail)) {
				list = appointmentPayService.getDyjAllList(Long.parseLong(userId),Integer.parseInt(pageNum),Integer.parseInt(pageSize));
			}else if("1".equals(derail)) {
				list = appointmentPayService.getDyjList(Long.parseLong(userId),Integer.parseInt(pageNum),Integer.parseInt(pageSize));
			}else {
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
			}
		if ("yyj".equals(bs))
			if("0".equals(derail)) {
				list = appointmentPayService.getYyjAllList(Integer.parseInt(userId), Integer.parseInt(pageNum), Integer.parseInt(pageSize));
			}else if("1".equals(derail)) {
				list = appointmentPayService.getYyjList(Long.parseLong(userId),Integer.parseInt(pageNum),Integer.parseInt(pageSize));
			}
			
		return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", list);
	}
}
