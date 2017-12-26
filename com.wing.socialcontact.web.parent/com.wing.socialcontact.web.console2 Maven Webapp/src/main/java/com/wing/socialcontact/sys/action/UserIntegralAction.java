package com.wing.socialcontact.sys.action;


import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IHonorService;
import com.wing.socialcontact.service.wx.api.IUserIntegralService;
import com.wing.socialcontact.service.wx.bean.Honor;
import com.wing.socialcontact.service.wx.bean.UserIntegral;

/**
 *积分
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/userIntegral")
public class UserIntegralAction extends BaseAction{
	
	@Autowired
	private IUserIntegralService userIntegralService; 			
	@Autowired
	private IHonorService honorService; 			
	
	
	/**
	 * 条件查询资讯分类
	 * 
	 * @return
	 */
	@RequiresPermissions("userIntegral:read")
	@RequestMapping("list")
	public String userIntegralload(){
		
		return "system/userIntegral/userIntegral_list";
	
	}
	@RequiresPermissions("userIntegral:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,UserIntegral userIntegral){
		
		return ajaxJsonEscape(userIntegralService.selectUserIntegrals(param, userIntegral));
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequiresPermissions("userIntegral:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		UserIntegral userIntegral  = new UserIntegral();
		
		map.put("dto", userIntegral);
		return "system/userIntegral/userIntegral_edit";
	
	}
	/**
	 * 添加
	 * @param userIntegral
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("userIntegral:add")
	@RequestMapping("add")
	public ModelAndView add(UserIntegral userIntegral,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		UserIntegral u = userIntegralService.selectByCode(userIntegral.getCode());
		if(null != u){
			return  ajaxDoneTextError("该编码已存在！");
		}
		userIntegral.setCreateTime(new Date());
		userIntegral.setUpdateTime(new Date());
		return ajaxDone(userIntegralService.addUserIntegral(userIntegral));	
	}
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("userIntegral:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		UserIntegral userIntegral = userIntegralService.selectById(id);
		
		if(userIntegral==null){
			return NODATA;
		}
		map.addAttribute("dto",userIntegral);
		
		String[] userHonorIds=null;
		String titles="";
		if (userIntegral.getRemark() != null && !"".equals(userIntegral.getRemark())) {
			userHonorIds = userIntegral.getRemark().split(",");
			for(int i=0;i<userHonorIds.length;i++){
				Honor honor = honorService.selectById(userHonorIds[i]);
				if(null!=honor){
					if(StringUtils.isEmpty(titles)){
						titles+=honor.getTitle();
					}else{
						titles+=",";
						titles+=honor.getTitle();
					}
				}
			}
			
		}
		map.addAttribute("titles",titles);
		return "system/userIntegral/userIntegral_edit";
	}
	/**
	 * 修改
	 * @param userIntegral
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("userIntegral:update")
	@RequestMapping("update")
	public ModelAndView update(UserIntegral userIntegral,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		///UserIntegral u = userIntegralService.selectByCode(userIntegral.getCode());
		///if(null != u){
		///	return  ajaxDoneTextError("该编码已存在！");
		//}
		userIntegral.setUpdateTime(new Date());
		return ajaxDone(userIntegralService.updateUserIntegral(userIntegral));
		
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("userIntegral:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		
		return ajaxDone(userIntegralService.deleteUserIntegral(ids));
	
	}
	
	
	
}
