package com.wing.socialcontact.action;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.SysAppointment;
import com.wing.socialcontact.sys.service.SysIAppointmentService;




@Controller
@RequestMapping("/appointment")
public class AppointmentAction extends BaseAction
{
	@Autowired
	private SysIAppointmentService sysIAppointmentService; 
	
	
	
	@RequiresPermissions("appointment:read")
	@RequestMapping("load")
	public String load(ModelMap map,String fromName,String fromTel,String toName,String toTel,String status){
		//List<SysAppointment> values = sysIAppointmentService.selectListByParam(fromName, fromTel, toName, toTel, status);
		//map.addAttribute("list", values);
		return "appointment/load";
		
	}
	@RequiresPermissions("appointment:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,String fromName,String fromTel, String toName,String toTel,String status){
		return ajaxJsonEscape(sysIAppointmentService.selectListByParam(param,fromName, fromTel,  toName, toTel, status));
	}
	@RequiresPermissions("daka:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(sysIAppointmentService.deleteAppointments(ids));
	}
}
