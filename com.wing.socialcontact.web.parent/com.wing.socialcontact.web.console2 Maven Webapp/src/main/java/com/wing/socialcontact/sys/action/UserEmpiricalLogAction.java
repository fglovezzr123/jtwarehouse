package com.wing.socialcontact.sys.action;


import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserEmpiricalLogService;
import com.wing.socialcontact.service.wx.api.IUserEmpiricalService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.service.wx.bean.UserEmpiricalLog;
import com.wing.socialcontact.service.wx.bean.WxUser;

/**
 *经验值等级明细
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/userEmpiricalLog")
public class UserEmpiricalLogAction extends BaseAction{
	
	@Autowired
	private IUserEmpiricalLogService userEmpiricalLogService; 			
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IWxUserService wxUserService;

	@Autowired
	private IUserEmpiricalService userEmpiricalService;
	
	
	/**
	 * 条件查询资讯分类
	 * 
	 * @return
	 */
	@RequiresPermissions("userEmpiricalLog:read")
	@RequestMapping("list")
	public String userEmpiricalLogload(){
		
		return "system/userEmpiricalLog/userEmpiricalLog_list";
	
	}
	@RequiresPermissions("userEmpiricalLog:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,UserEmpiricalLog userEmpiricalLog,String nickname,String mobile,String userId){
		
		return ajaxJsonEscape(userEmpiricalLogService.selectUserEmpiricalLogs(param, userEmpiricalLog, nickname, mobile,userId));
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequiresPermissions("userEmpiricalLog:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		UserEmpiricalLog userEmpiricalLog  = new UserEmpiricalLog();
		
		map.put("dto", userEmpiricalLog);
		return "system/userEmpiricalLog/userEmpiricalLog_edit";
	
	}
	
	@RequestMapping("addPage2")
	public String addPage2(ModelMap map) {
		return "system/userEmpiricalLog/userEmpiricalLog_add";
	}
	@RequestMapping("addPage3")
	public String addPage3(ModelMap map,String userId) {
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		map.addAttribute("user", user);
		return "system/userEmpiricalLog/userEmpiricalLog_add3";
	}
	/***修改等级****/
	@RequestMapping("addPage4")
	public String addPage4(ModelMap map,String userId) {
		List<UserEmpirical> userLevelList = userEmpiricalService.selectAllUserEmpirical();
		map.addAttribute("userLevelList", userLevelList);
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		String level = user.getLevel();
		String levelId = "";
		if(!StringUtils.isEmpty(level)){
			UserEmpirical ue= userEmpiricalService.selectBylevel(level);
			if(null!=ue){
				levelId =  ue.getId();
			}
		}
		map.addAttribute("levelId", levelId);
		
		
		map.addAttribute("user", user);
		return "system/userEmpiricalLog/userEmpiricalLog_add4";
	}
	@RequestMapping("add2")
	public ModelAndView add(UserEmpiricalLog userEmpiricalLog,String mobile,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		WxUser user = wxUserService.selectByMobile(mobile);
		if(null == user){
			return ajaxDoneTextError("该手机用户未在本系统中注册！");
		}
		//TjyUser tjyuser = tjyUserService.selectByPrimaryKey(user.getId().toString());
		userEmpiricalLog.setUserId(user.getId().toString());
		userEmpiricalLog.setCreateTime(new Date());
		int empirical=0;
			if(null != user.getEmpiricalTotal()){
				//1:+ 2:-
				if("1".equals(userEmpiricalLog.getEmpiricalType())){
					empirical= user.getEmpiricalTotal()+userEmpiricalLog.getEmpirical();
				}else if("2".equals(userEmpiricalLog.getEmpiricalType())){
					empirical= user.getEmpiricalTotal()-userEmpiricalLog.getEmpirical();
				}
			}else{
				if("1".equals(userEmpiricalLog.getEmpiricalType())){
					empirical= empirical+userEmpiricalLog.getEmpirical();
				}else if("2".equals(userEmpiricalLog.getEmpiricalType())){
					empirical= empirical-userEmpiricalLog.getEmpirical();
				}
			}
			if(empirical<0.0){
				return ajaxDoneTextError("该手机用户经验值余额不足！");
			}else{
				userEmpiricalLog.setYeEmpirical(empirical);;
				userEmpiricalLogService.addUserEmpiricalLog(userEmpiricalLog);
				
				//用户等级
				UserEmpirical userEmpirical_temp = userEmpiricalService.selectByEmpirical(empirical);
				user.setEmpiricalTotal(empirical);
				if(null!=userEmpirical_temp){
					user.setLevel(userEmpirical_temp.getLevel());
				}
				wxUserService.updateWxUser(user);
			}
		
		return  ajaxDone(MsgConfig.MSG_KEY_SUCCESS);	
	}
	@RequestMapping("add3")
	public ModelAndView add3(UserEmpiricalLog userEmpiricalLog,String userId,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
		}
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		
		userEmpiricalLog.setUserId(userId);
		userEmpiricalLog.setCreateTime(new Date());
		int empirical=0;
		if(null != user.getEmpiricalTotal()){
			//1:+ 2:-
			if("1".equals(userEmpiricalLog.getEmpiricalType())){
				empirical= user.getEmpiricalTotal()+userEmpiricalLog.getEmpirical();
			}else if("2".equals(userEmpiricalLog.getEmpiricalType())){
				empirical= user.getEmpiricalTotal()-userEmpiricalLog.getEmpirical();
			}
		}else{
			if("1".equals(userEmpiricalLog.getEmpiricalType())){
				empirical= empirical+userEmpiricalLog.getEmpirical();
			}else if("2".equals(userEmpiricalLog.getEmpiricalType())){
				empirical= empirical-userEmpiricalLog.getEmpirical();
			}
		}
		if(empirical<0.0){
			return ajaxDoneTextError("该手机用户经验值余额不足！");
		}else{
			userEmpiricalLog.setYeEmpirical(empirical);;
			userEmpiricalLogService.addUserEmpiricalLog(userEmpiricalLog);
			user.setEmpiricalTotal(empirical);
			wxUserService.updateWxUser(user);
		}
		
		return  ajaxDone(MsgConfig.MSG_KEY_SUCCESS);	
	}
	
	@RequestMapping("add4")
	public ModelAndView add4(WxUser user,String userId,String levelId,String remark,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
		}
		WxUser _user = wxUserService.selectByPrimaryKey(userId);
		UserEmpirical userEmpirical = userEmpiricalService.selectById(levelId);
		int old_empirical  = _user.getEmpiricalTotal();
		_user.setLevel(userEmpirical.getLevel());
		_user.setEmpiricalTotal(userEmpirical.getEmpiricalLow());
		wxUserService.updateWxUser(_user);
		
		UserEmpiricalLog userEmpiricalLog = new UserEmpiricalLog();
		if(old_empirical>=userEmpirical.getEmpiricalLow()){
			userEmpiricalLog.setEmpirical(old_empirical-userEmpirical.getEmpiricalLow());
			userEmpiricalLog.setEmpiricalType("2");
		}else{
			userEmpiricalLog.setEmpirical(userEmpirical.getEmpiricalLow()-old_empirical);
			userEmpiricalLog.setEmpiricalType("1");
		}
		
		userEmpiricalLog.setUserId(userId);
		userEmpiricalLog.setRemark("后台修改等级");
		userEmpiricalLog.setYeEmpirical(userEmpirical.getEmpiricalLow());
		
		userEmpiricalLog.setCreateTime(new Date());
		userEmpiricalLogService.addUserEmpiricalLog(userEmpiricalLog);
		return  ajaxDone(MsgConfig.MSG_KEY_SUCCESS);	
	}
	
	/**
	 * 添加
	 * @param userEmpiricalLog
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("userEmpiricalLog:add")
	@RequestMapping("add")
	public ModelAndView add(UserEmpiricalLog userEmpiricalLog,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		userEmpiricalLog.setCreateTime(new Date());
		userEmpiricalLog.setUpdateTime(new Date());
		return ajaxDone(userEmpiricalLogService.addUserEmpiricalLog(userEmpiricalLog));	
	}
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("userEmpiricalLog:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		UserEmpiricalLog userEmpiricalLog = userEmpiricalLogService.selectById(id);
		
		if(userEmpiricalLog==null){
			return NODATA;
		}
		map.addAttribute("dto",userEmpiricalLog);
		
		return "system/userEmpiricalLog/userEmpiricalLog_edit";
	}
	/**
	 * 修改
	 * @param userEmpiricalLog
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("userEmpiricalLog:update")
	@RequestMapping("update")
	public ModelAndView update(UserEmpiricalLog userEmpiricalLog,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		userEmpiricalLog.setUpdateTime(new Date());
		return ajaxDone(userEmpiricalLogService.updateUserEmpiricalLog(userEmpiricalLog));
		
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("userEmpiricalLog:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		
		return ajaxDone(userEmpiricalLogService.deleteUserEmpiricalLog(ids));
	
	}
	
	
	
}
