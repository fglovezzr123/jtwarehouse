package com.wing.socialcontact.sys.action;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IUserIntegralEmpiricalService;
import com.wing.socialcontact.service.wx.bean.UserIntegralEmpirical;

/**
 *积分经验规则
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/userIntegralEmpirical")
public class UserIntegralEmpiricalAction extends BaseAction{
	
	@Autowired
	private IUserIntegralEmpiricalService userIntegralEmpiricalService; 			
	
	
	/**
	 * 条件查询资讯分类
	 * 
	 * @return
	 */
	//@RequiresPermissions("userIntegralEmpirical:read")
	@RequestMapping("list")
	public String userIntegralEmpiricalload(){
		
		return "system/userIntegralEmpirical/userIntegralEmpirical_list";
	
	}
	//@RequiresPermissions("userIntegralEmpirical:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,UserIntegralEmpirical userIntegralEmpirical){
		
		return ajaxJsonEscape(userIntegralEmpiricalService.selectUserIntegralEmpiricals(param, userIntegralEmpirical));
	}
	/**
	 * 积分规则
	 * @return
	 */
	//@RequiresPermissions("userIntegralEmpirical:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		 UserIntegralEmpirical userIntegralEmpirical = userIntegralEmpiricalService.selectByIeType("1");
			
			if(userIntegralEmpirical==null){
				UserIntegralEmpirical userIntegralEmpirical2  = new UserIntegralEmpirical();
				map.put("dto",userIntegralEmpirical2);
			}else{
				map.put("dto", userIntegralEmpirical);
			}
		return "system/userIntegralEmpirical/userIntegralEmpirical_edit";
	
	}
	/**
	 * 等级规则
	 * @return
	 */
	//@RequiresPermissions("userIntegralEmpirical:add")
	@RequestMapping("addPage2")
	public String addPage2(ModelMap map){
       UserIntegralEmpirical userIntegralEmpirical = userIntegralEmpiricalService.selectByIeType("2");
		
		if(userIntegralEmpirical==null){
			UserIntegralEmpirical userIntegralEmpirical2  = new UserIntegralEmpirical();
			map.put("dto",userIntegralEmpirical2);
		}else{
			map.put("dto", userIntegralEmpirical);
		}
		
		return "system/userIntegralEmpirical/userIntegralEmpirical_edit2";
		
	}
	/**
	 * 等级规则
	 * @return
	 */
	//@RequiresPermissions("userIntegralEmpirical:add")
	@RequestMapping("addPage3")
	public String addPage3(ModelMap map){
		UserIntegralEmpirical userIntegralEmpirical = userIntegralEmpiricalService.selectByIeType("3");
		
		if(userIntegralEmpirical==null){
			UserIntegralEmpirical userIntegralEmpirical2  = new UserIntegralEmpirical();
			map.put("dto",userIntegralEmpirical2);
		}else{
			map.put("dto", userIntegralEmpirical);
		}
		
		return "system/userIntegralEmpirical/userIntegralEmpirical_edit3";
		
	}
	/**
	 * 添加
	 * @param userIntegralEmpirical
	 * @param errors
	 * @return
	 */
	//@RequiresPermissions("userIntegralEmpirical:add")
	@RequestMapping("add")
	public ModelAndView add(UserIntegralEmpirical userIntegralEmpirical,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		userIntegralEmpirical.setCreateTime(new Date());
		userIntegralEmpirical.setUpdateTime(new Date());
		return ajaxDone(userIntegralEmpiricalService.addUserIntegralEmpirical(userIntegralEmpirical));	
	}
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	//@RequiresPermissions("userIntegralEmpirical:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		UserIntegralEmpirical userIntegralEmpirical = userIntegralEmpiricalService.selectById(id);
		
		if(userIntegralEmpirical==null){
			return NODATA;
		}
		map.addAttribute("dto",userIntegralEmpirical);
		
		return "system/userIntegralEmpirical/userIntegralEmpirical_edit";
	}
	/**
	 * 修改
	 * @param userIntegralEmpirical
	 * @param errors
	 * @return
	 */
	//@RequiresPermissions("userIntegralEmpirical:update")
	@RequestMapping("update")
	public ModelAndView update(UserIntegralEmpirical userIntegralEmpirical,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		userIntegralEmpirical.setUpdateTime(new Date());
		return ajaxDone(userIntegralEmpiricalService.updateUserIntegralEmpirical(userIntegralEmpirical));
		
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	//@RequiresPermissions("userIntegralEmpirical:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		
		return ajaxDone(userIntegralEmpiricalService.deleteUserIntegralEmpirical(ids));
	
	}
	
	
	
}
