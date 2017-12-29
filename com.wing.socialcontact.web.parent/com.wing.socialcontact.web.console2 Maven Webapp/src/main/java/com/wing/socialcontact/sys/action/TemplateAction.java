package com.wing.socialcontact.sys.action;


import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.ITemplateService;
import com.wing.socialcontact.service.wx.bean.Template;

/**
 *消息模板
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/template")
public class TemplateAction extends BaseAction{
	
	@Autowired
	private ITemplateService templateService; 			
	
	
	/**
	 * 条件查询资讯分类
	 * 
	 * @return
	 */
	@RequiresPermissions("template:read")
	@RequestMapping("list")
	public String templateload(){
		
		return "system/template/template_list";
	
	}
	@RequiresPermissions("template:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,Template template){
		
		return ajaxJsonEscape(templateService.selectTemplates(param, template));
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequiresPermissions("template:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		Template template  = new Template();
		
		map.put("dto", template);
		return "system/template/template_edit";
	
	}
	/**
	 * 添加
	 * @param template
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("template:add")
	@RequestMapping("add")
	public ModelAndView add(Template template,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		template.setCreateTime(new Date());
		template.setUpdateTime(new Date());
		return ajaxDone(templateService.addTemplate(template));	
	}
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("template:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		Template template = templateService.selectById(id);
		
		if(template==null){
			return NODATA;
		}
		map.addAttribute("dto",template);
		
		return "system/template/template_edit";
	}
	/**
	 * 修改
	 * @param template
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("template:update")
	@RequestMapping("update")
	public ModelAndView update(Template template,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		template.setUpdateTime(new Date());
		return ajaxDone(templateService.updateTemplate(template));
		
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("template:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		
		return ajaxDone(templateService.deleteTemplates(ids));
	
	}
	
	
	@RequestMapping("isOpen")
	public ModelAndView isOpen(String[] ids) {
		return ajaxDone(templateService.updateTemplateByIsopen(ids, 0));
	}
	
	@RequestMapping("isNotOpen")
	public ModelAndView isNotOpen(String[] ids) {
		return ajaxDone(templateService.updateTemplateByIsopen(ids, 1));
	}
	
}
