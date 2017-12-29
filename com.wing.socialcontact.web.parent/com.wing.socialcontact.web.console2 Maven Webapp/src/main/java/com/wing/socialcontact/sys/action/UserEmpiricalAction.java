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
import com.wing.socialcontact.service.wx.api.IUserEmpiricalService;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;

/**
 *经验值等级
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/userEmpirical")
public class UserEmpiricalAction extends BaseAction{
	
	@Autowired
	private IUserEmpiricalService userEmpiricalService; 			
	
	
	/**
	 * 条件查询
	 * 
	 * @return
	 */
	@RequiresPermissions("userEmpirical:read")
	@RequestMapping("list")
	public String userEmpiricalload(){
		
		return "system/userEmpirical/userEmpirical_list";
	
	}
	@RequestMapping("list2")
	public String userEmpiricalload2(){
		
		return "system/userEmpirical/userEmpirical_list2";
		
	}
	@RequiresPermissions("userEmpirical:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,UserEmpirical userEmpirical){
		
		return ajaxJsonEscape(userEmpiricalService.selectUserEmpiricals(param, userEmpirical));
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequiresPermissions("userEmpirical:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		UserEmpirical userEmpirical  = new UserEmpirical();
		
		map.put("dto", userEmpirical);
		return "system/userEmpirical/userEmpirical_edit";
	
	}
	/**
	 * 添加
	 * @param userEmpirical
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("userEmpirical:add")
	@RequestMapping("add")
	public ModelAndView add(UserEmpirical userEmpirical,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		userEmpirical.setCreateTime(new Date());
		userEmpirical.setUpdateTime(new Date());
		return ajaxDone(userEmpiricalService.addUserEmpirical(userEmpirical));	
	}
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("userEmpirical:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		UserEmpirical userEmpirical = userEmpiricalService.selectById(id);
		
		if(userEmpirical==null){
			return NODATA;
		}
		map.addAttribute("dto",userEmpirical);
		
		return "system/userEmpirical/userEmpirical_edit";
	}
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("userEmpirical:update")
	@RequestMapping("updatePage2")
	public String updatePage2(String id,ModelMap map){
		UserEmpirical userEmpirical = userEmpiricalService.selectById(id);
		
		if(userEmpirical==null){
			return NODATA;
		}
		map.addAttribute("dto",userEmpirical);
		
		return "system/userEmpirical/userEmpirical_edit2";
	}
	/**
	 * 修改
	 * @param userEmpirical
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("userEmpirical:update")
	@RequestMapping("update")
	public ModelAndView update(UserEmpirical userEmpirical,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		userEmpirical.setUpdateTime(new Date());
		return ajaxDone(userEmpiricalService.updateUserEmpirical(userEmpirical));
		
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("userEmpirical:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		
		return ajaxDone(userEmpiricalService.deleteUserEmpirical(ids));
	
	}
	
	
	
}
