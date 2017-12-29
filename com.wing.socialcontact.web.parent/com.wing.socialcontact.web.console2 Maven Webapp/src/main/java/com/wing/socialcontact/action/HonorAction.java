package com.wing.socialcontact.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IHonorService;
import com.wing.socialcontact.service.wx.bean.Honor;
import com.wing.socialcontact.service.wx.bean.UserIntegral;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * honor管理
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/honor")
public class HonorAction extends BaseAction{
	
	@Autowired
	private IHonorService honorService;
	@Autowired
	private IListValuesService listValuesService; 
	/**
	 * 条件查询话题
	 * 
	 * @return
	 */
	@RequiresPermissions("honor:read")
	@RequestMapping("load")
	public String load(ModelMap map){
		map.addAttribute("type", 802);
		return "honor/honor_load";
	
	}
	@RequiresPermissions("honor:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,Honor honor,String startTimef,String endTimef){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//获取Calendar实例
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		if(!StringUtils.isEmpty(startTimef)&&!StringUtils.isEmpty(endTimef))
	    	//把字符串转成日期类型
		try {
			startTime.setTime(df.parse(startTimef));
			endTime.setTime(df.parse(endTimef));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	    if (startTime.compareTo(endTime) > 0) {
	    	return  ajaxDoneTextError("查询开始时间不能大于结束时间！");
		}
		return ajaxJsonEscape(honorService.selectAllHonor(param, honor, startTimef, endTimef));
	}
	/**
	 * 跳转到honor添加页面
	 * @return
	 */
	@RequiresPermissions("honor:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		List<ListValues> values = listValuesService.selectListByType(802);
		map.addAttribute("values", values);
		return "honor/honor_add";
	
	}
	/**
	 * 添加honor
	 * @param honor
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("honor:add")
	@RequestMapping("add")
	public ModelAndView add(Honor honor,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		Honor u_code = new Honor();
		u_code.setHonorCode(honor.getHonorCode());
		List l_code = honorService.selectAllHonor(u_code);
		if(null != l_code && l_code.size()>0){
			return  ajaxDoneTextError("该勋章编码已存在！");
		}
		Honor u_title = new Honor();
		u_title.setTitle(honor.getTitle());
		List l_title = honorService.selectAllHonor(u_title);
		if(null != l_title && l_title.size()>0){
			return  ajaxDoneTextError("该勋章名称已存在！");
		}
		honor.setCreateTime(new Date());
		honor.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(honorService.addHonor(honor));	
	}
	/**
	 * 跳转到honor修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("honor:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		Honor honor = honorService.selectById(id);
		if(honor==null){
			return NODATA;
		}
		map.addAttribute("b",honor);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		List<ListValues> values = listValuesService.selectListByType(802);
		map.addAttribute("values", values);
		return "honor/honor_update";
	}
	
	@RequestMapping("viewPage")
	public String viewPage(String id,ModelMap map){
		Honor honor = honorService.selectById(id);
		if(honor==null){
			return NODATA;
		}
		map.addAttribute("b",honor);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		List<ListValues> values = listValuesService.selectListByType(802);
		map.addAttribute("values", values);
		return "honor/honor_view";
	}
	/**
	 * 修改honor
	 * @param honor
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("honor:update")
	@RequestMapping("update")
	public ModelAndView update(Honor honor,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		Honor u_code = new Honor();
		/*u_code.setHonorCode(honor.getHonorCode());
		List l_code = honorService.selectAllHonor(u_code);
		if(null != l_code && l_code.size()>0){
			return  ajaxDoneTextError("该勋章编码已存在！");
		}
		Honor u_title = new Honor();
		u_title.setTitle(honor.getTitle());
		List l_title = honorService.selectAllHonor(u_title);
		if(null != l_title && l_title.size()>0){
			return  ajaxDoneTextError("该勋章名称已存在！");
		}*/
		return ajaxDone(honorService.updateHonor(honor));
		
	}
	/**
	 * 删除honor
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("honor:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(honorService.deleteHonor(ids));
	}
}
