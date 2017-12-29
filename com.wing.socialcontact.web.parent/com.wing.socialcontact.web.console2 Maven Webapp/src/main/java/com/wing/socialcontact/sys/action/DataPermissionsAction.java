/**  
 * @Title: DataPermissionsAction.java
 * @date 2016-10-18 下午4:05:33
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.action;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.sys.bean.SyDataPermissions;
import com.wing.socialcontact.sys.service.IDataPermissionsService;

/**
 * 数据权限管理
 * @author	dijuli
 * @version	 1.0
 *
 */
@Controller
@RequestMapping("/dataPer")
public class DataPermissionsAction extends BaseAction{

	@Autowired
	private IDataPermissionsService dataPermissionsService; 
	
	/**
	 * 管理页面
	 * @return
	 */
	@RequiresPermissions("dataPer:read")
	@RequestMapping("load")
	public String load(){
		
		return "system/data_permissions/load";
	}
	/**
	 * 修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("dataPer:read")
	@RequestMapping("updatePage")
	public String updatePage(SyDataPermissions per,ModelMap map){
		
		map.addAttribute("per",dataPermissionsService.updateSelectOne(per));
		
		return "system/data_permissions/update/"+per.getType();
	}
	/**
	 * 修改
	 * @param per
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("dataPer:update")
	@RequestMapping("update")
	public ModelAndView update(SyDataPermissions per,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(dataPermissionsService.updateSyDataPermissions(per));
		
	}
	
	
	
	
}
