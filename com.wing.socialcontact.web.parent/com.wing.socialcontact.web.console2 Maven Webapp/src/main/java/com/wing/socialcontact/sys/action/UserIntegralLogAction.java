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
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserEmpiricalLogService;
import com.wing.socialcontact.service.wx.api.IUserEmpiricalService;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
import com.wing.socialcontact.service.wx.api.IUserIntegralService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.service.wx.bean.UserEmpiricalLog;
import com.wing.socialcontact.service.wx.bean.UserIntegral;
import com.wing.socialcontact.service.wx.bean.UserIntegralLog;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;

/**
 *积分明细
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/userIntegralLog")
public class UserIntegralLogAction extends BaseAction{
	
	@Autowired
	private IUserIntegralLogService userIntegralLogService;
	@Autowired
	private IUserIntegralService userIntegralService;
	@Autowired
	private IUserEmpiricalService userEmpiricalService;
	@Autowired
	private IUserEmpiricalLogService userEmpiricalLogService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IWxUserService wxUserService;
	
	
	
	
	/**
	 * 条件查询资讯分类
	 * 
	 * @return
	 */
	@RequiresPermissions("userIntegralLog:read")
	@RequestMapping("list")
	public String userIntegralLogload(){
		
		return "system/userIntegralLog/userIntegralLog_list";
	
	}
	@RequiresPermissions("userIntegralLog:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,UserIntegralLog userIntegralLog,String nickname,String mobile,String userId){
		
		return ajaxJsonEscape(userIntegralLogService.selectUserIntegralLogs(param, userIntegralLog, nickname, mobile,userId));
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequiresPermissions("userIntegralLog:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		UserIntegralLog userIntegralLog  = new UserIntegralLog();
		
		map.put("dto", userIntegralLog);
		return "system/userIntegralLog/userIntegralLog_edit";
	
	}
	
	@RequestMapping("addPage2")
	public String addPage2(ModelMap map) {
		return "system/userIntegralLog/userIntegralLog_add";
	}
	@RequestMapping("addPage3")
	public String addPage3(ModelMap map ,String userId) {
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		map.addAttribute("user", user);
		return "system/userIntegralLog/userIntegralLog_add3";
	}
	

	@RequestMapping("add2")
	public ModelAndView add2(UserIntegralLog userIntegralLog,String mobile,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		WxUser user = wxUserService.selectByMobile(mobile);
		if(null == user){
			return ajaxDoneTextError("该手机用户未在本系统中注册！");
		}
		//TjyUser tjyuser = tjyUserService.selectByPrimaryKey(user.getId().toString());
		userIntegralLog.setUserId(user.getId().toString());
		userIntegralLog.setCreateTime(new Date());
		int integral=0;
			if(null != user.getIntegralTotal()){
				//1:+ 2:-
				if("1".equals(userIntegralLog.getIntegralType())){
					integral= user.getIntegralTotal()+userIntegralLog.getIntegral();
				}else if("2".equals(userIntegralLog.getIntegralType())){
					integral= user.getIntegralTotal()-userIntegralLog.getIntegral();
				}
			}else{
				if("1".equals(userIntegralLog.getIntegralType())){
					integral= integral+userIntegralLog.getIntegral();
				}else if("2".equals(userIntegralLog.getIntegralType())){
					integral= integral-userIntegralLog.getIntegral();
				}
			}
			if(integral<0.0){
				return ajaxDoneTextError("该手机积分值余额不足！");
			}else{
				userIntegralLog.setYeIntegral(integral);;
				userIntegralLogService.addUserIntegralLog(userIntegralLog);
				user.setIntegralTotal(integral);
				wxUserService.updateWxUser(user);
			}
		return  ajaxDone(MsgConfig.MSG_KEY_SUCCESS);	
	}
	@RequestMapping("add3")
	public ModelAndView add3(UserIntegralLog userIntegralLog,String userId,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
		}
		WxUser user = wxUserService.selectByPrimaryKey(userId);
		userIntegralLog.setUserId(userId);
		userIntegralLog.setCreateTime(new Date());
		int integral=0;
		if(null != user.getIntegralTotal()){
			//1:+ 2:-
			if("1".equals(userIntegralLog.getIntegralType())){
				integral= user.getIntegralTotal()+userIntegralLog.getIntegral();
			}else if("2".equals(userIntegralLog.getIntegralType())){
				integral= user.getIntegralTotal()-userIntegralLog.getIntegral();
			}
		}else{
			if("1".equals(userIntegralLog.getIntegralType())){
				integral= integral+userIntegralLog.getIntegral();
			}else if("2".equals(userIntegralLog.getIntegralType())){
				integral= integral-userIntegralLog.getIntegral();
			}
		}
		if(integral<0.0){
			return ajaxDoneTextError("该手机积分值余额不足！");
		}else{
			userIntegralLog.setYeIntegral(integral);;
			userIntegralLogService.addUserIntegralLog(userIntegralLog);
			user.setIntegralTotal(integral);
			wxUserService.updateWxUser(user);
		}
		return  ajaxDone(MsgConfig.MSG_KEY_SUCCESS);	
	}
	/**
	 * 添加
	 * @param userIntegralLog
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("userIntegralLog:add")
	@RequestMapping("add")
	public ModelAndView add(UserIntegralLog userIntegralLog,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		userIntegralLog.setCreateTime(new Date());
		userIntegralLog.setUpdateTime(new Date());
		return ajaxDone(userIntegralLogService.addUserIntegralLog(userIntegralLog));	
	}
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("userIntegralLog:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		UserIntegralLog userIntegralLog = userIntegralLogService.selectById(id);
		
		if(userIntegralLog==null){
			return NODATA;
		}
		map.addAttribute("dto",userIntegralLog);
		
		return "system/userIntegralLog/userIntegralLog_edit";
	}
	/**
	 * 修改
	 * @param userIntegralLog
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("userIntegralLog:update")
	@RequestMapping("update")
	public ModelAndView update(UserIntegralLog userIntegralLog,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		userIntegralLog.setUpdateTime(new Date());
		return ajaxDone(userIntegralLogService.updateUserIntegralLog(userIntegralLog));
		
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("userIntegralLog:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		
		return ajaxDone(userIntegralLogService.deleteUserIntegralLog(ids));
	
	}
	
	
	/**
	 * 根据任务编码和用户修积分和经验值
	 * @param 
	 * @return
	 */
	/*
	public boolean getLntAndEmp(String userId,String code) {
		if("task_0001".equals(code)){//首次登录:task_0001
			UserIntegralLog userIntegralLog = userIntegralLogService.selectOneTimes(userId, code);
			if(null !=userIntegralLog){
				return false;
			}
		}
		//等级信息
		UserIntegral userIntegral = userIntegralService.selectByCode(code);
		if(null == userIntegral){
			return false;
		}
		int integral = userIntegral.getIntegralTotal();//积分
		int empirical = userIntegral.getEmpiricalTotal();//经验值
		String taskSystem = userIntegral.getTaskSystem();//任务名
		//用户信息
		WxUser user = wxUserService.selectByPrimaryKey(Long.parseLong(userId));
		if(null == user){
			return false;
		}
		int integralTotal = 0 ;//积分
		if(null != user.getEmpiricalTotal()){
			integralTotal = user.getIntegralTotal();
		}
		int empiricalTotal = 0;//经验值
		if(null != user.getEmpiricalTotal()){
			empiricalTotal =  user.getEmpiricalTotal();
		}
		String level_old = user.getLevel();//等级
		int get_level_integral = 0;
		//经验明细
		UserEmpiricalLog userEmpiricalLog = new UserEmpiricalLog();
		userEmpiricalLog.setCreateTime(new Date());
		userEmpiricalLog.setEmpirical(empirical);
		userEmpiricalLog.setTaskName(taskSystem);
		userEmpiricalLog.setRemark(taskSystem);
		userEmpiricalLog.setTaskCode(code);
		userEmpiricalLog.setYeEmpirical(empiricalTotal+empirical);
		userEmpiricalLogService.insert(userEmpiricalLog);
	
		//积分明细
		UserIntegralLog userIntegralLog = new UserIntegralLog();
		userIntegralLog.setCreateTime(new Date());
		userIntegralLog.setIntegral(integral);
		userIntegralLog.setTaskName(taskSystem);
		userIntegralLog.setRemark(taskSystem);
		userIntegralLog.setTaskCode(code);
		userIntegralLog.setYeIntegral(integralTotal+integral);
		userIntegralLogService.insert(userIntegralLog);
		//用户等级
		UserEmpirical userEmpirical_temp = userEmpiricalService.selectByEmpirical(empiricalTotal+empirical);
		if(null != userEmpirical_temp){
			user.setLevel(userEmpirical_temp.getLevel());
			user.setEmpiricalTotal(empiricalTotal+empirical);
			
			if(!userEmpirical_temp.getLevel().equals(level_old)){
				get_level_integral=userEmpirical_temp.getIntegralTotal();
				//升级积分明细
				UserIntegralLog userIntegralLog2 = new UserIntegralLog();
				userIntegralLog2.setCreateTime(new Date());
				userIntegralLog2.setIntegral(get_level_integral);
				userIntegralLog2.setTaskName(taskSystem);
				userIntegralLog2.setRemark(taskSystem);
				userIntegralLog2.setTaskCode(code);
				userIntegralLog2.setYeIntegral(integralTotal+integral+get_level_integral);
				userIntegralLogService.insert(userIntegralLog2);
				user.setIntegralTotal(integralTotal+integral+get_level_integral);
			}else{
				user.setIntegralTotal(integralTotal+integral);
			}
			wxUserService.updateByPrimaryKey(user);
		}
		
		return true;
	}
	
	*/
	
}
