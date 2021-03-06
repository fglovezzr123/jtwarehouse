/**  
 * @Project: tjy
 * @Title: DistrictAction.java
 * @Package com.oa.manager.system.action
 * @date 2016-6-19 下午2:01:55
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.action;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.bean.SyDistrict;
import com.wing.socialcontact.sys.service.IDistrictService;

/**
 * 
 * 类名：DistrictAction
 * 功能：地区管理
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-6-19 下午2:01:55
 *
 */
@Controller
@RequestMapping("/district")
public class DistrictAction extends BaseAction{
	
	@Autowired
	private IDistrictService service; 
	
	
	/**
	 * 地区管理 条件分页查询
	 * @return
	 */
	@RequiresPermissions("district:read")
	@RequestMapping("load")
	public String load(String superId,ModelMap map){
		
		if(StringUtils.isNotBlank(superId)){
			SyDistrict superDis=service.selectByPrimaryKey(superId);
			if(superDis!=null&&superDis.getSuperId()!=null){
				map.addAttribute("spId",superDis.getSuperId());
				map.addAttribute("spName",superDis.getDisName());
			}else{
				map.addAttribute("spName","地区管理");
			}
		}else{
			map.addAttribute("spName","地区管理");
		}
		return "system/district/load";
	
	}
	/**
	 * 地区管理 条件分页查询
	 * @param param
	 * @param dis
	 * @return
	 */
	@RequiresPermissions("district:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,SyDistrict dis){
		
		return ajaxJsonEscape(service.selectDistrict(param, dis));
		
	
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequiresPermissions("district:add")
	@RequestMapping("addPage")
	public String addPage(){
		
		return "system/district/add";
	
	}
	/**
	 * 添加地区
	 * @param dis
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("district:add")
	@RequestMapping("add")
	public ModelAndView add(SyDistrict dis,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(service.addDistrict(dis));
		
	}
	/**
	 * 跳转到自定义设置修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("district:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		SyDistrict dis=service.selectByPrimaryKey(id);
		if(dis==null){
			return NODATA;
		}
		map.addAttribute("d",dis);
		return "system/district/update";
	}
	/**
	 * 修改
	 * @param dis
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("district:update")
	@RequestMapping("update")
	public ModelAndView update(SyDistrict dis,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(service.updateDistrict(dis));
		
	}
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("district:delete")
	@RequestMapping("del")
	public ModelAndView delete(String[] ids){
		
		return ajaxDone(service.deleteDistrict(ids));
	}
	
	/**
	 * 地区查找带回
	 * @param map
	 * @return
	 */
	@RequestMapping("lookUpPage")
	public String lookUpPage(ModelMap map){
		
		map.addAttribute("dis",service.selectAllDistrict());
		
		return "system/district/lookup";
	}
	/**
	 * 地区查找带回
	 * @param map
	 * @return
	 */
	@RequestMapping("lookUp")
	public ModelAndView lookUp(){
		
		return ajaxJsonEscape(service.selectAllDistrict());
	}
	
	
	
	
}
