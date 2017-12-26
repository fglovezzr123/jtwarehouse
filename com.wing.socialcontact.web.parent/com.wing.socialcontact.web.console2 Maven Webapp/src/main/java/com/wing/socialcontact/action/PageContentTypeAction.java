package com.wing.socialcontact.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IPageContentTypeService;
import com.wing.socialcontact.service.wx.bean.PageContentType;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * pageContentType管理
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/pageContentType")
public class PageContentTypeAction extends BaseAction{
	
	@Autowired
	private IPageContentTypeService pageContentTypeService;
	@Autowired
	private IListValuesService listValuesService; 
	/**
	 * 条件查询话题
	 * 
	 * @return
	 */
	///@RequiresPermissions("pageContentType:read")
	@RequestMapping("list")
	public String load(ModelMap map){
		map.addAttribute("type", 802);
		return "pageContentType/pageContentType_list";
	
	}
	///@RequiresPermissions("pageContentType:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,PageContentType pageContentType){
		
		return ajaxJsonEscape(pageContentTypeService.selectAllPageContentType(param, pageContentType));
	}

	@RequestMapping("queryAll")
	@ResponseBody
	public DataGrid queryAll(PageParam param, PageContentType pageContentType){
		return pageContentTypeService.selectAllPageContentType(param, pageContentType);
	}

	/**
	 * 跳转到pageContentType添加页面
	 * @return
	 */
	//@RequiresPermissions("pageContentType:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		return "pageContentType/pageContentType_add";
	
	}
	/**
	 * 添加pageContentType
	 * @param pageContentType
	 * @param errors
	 * @return
	 */
	//@RequiresPermissions("pageContentType:add")
	@RequestMapping("add")
	public ModelAndView add(PageContentType pageContentType,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(pageContentTypeService.addPageContentType(pageContentType));	
	}
	/**
	 * 跳转到pageContentType修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	//@RequiresPermissions("pageContentType:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		PageContentType pageContentType = pageContentTypeService.selectById(id);
		if(pageContentType==null){
			return NODATA;
		}
		map.addAttribute("b",pageContentType);
		
		return "pageContentType/pageContentType_update";
	}
	/**
	 * 修改pageContentType
	 * @param pageContentType
	 * @param errors
	 * @return
	 */
	//@RequiresPermissions("pageContentType:update")
	@RequestMapping("update")
	public ModelAndView update(PageContentType pageContentType,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(pageContentTypeService.updatePageContentType(pageContentType));
		
	}
	/**
	 * 删除pageContentType
	 * @param ids
	 * @return
	 */
	//@RequiresPermissions("pageContentType:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(pageContentTypeService.deletePageContentType(ids));
	}

	@RequestMapping("lookUpPage")
	public String lookUp(){
		return "pageContentType/lookup";
	}
}
