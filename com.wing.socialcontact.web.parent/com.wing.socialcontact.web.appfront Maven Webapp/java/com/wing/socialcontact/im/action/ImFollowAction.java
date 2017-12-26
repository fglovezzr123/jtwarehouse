/**  
 * @Title: DataPermissionsAction.java
 * @date 2016-10-18 下午4:05:33
 * @Copyright: 2016 
 */
package com.wing.socialcontact.im.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.im.api.IImFollowService;
import com.wing.socialcontact.service.im.api.IImFriendService;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 关注接口
 * @author	xuxinyuan
 * @version	 1.0
 *
 */
@Controller
@RequestMapping("/im/m")
public class ImFollowAction extends BaseAction{

	@Autowired
	private IImFollowService imFollowService; 
	
	/**
	 * 我的关注列表
	 * @return
	 */
	@RequestMapping("selMyFollowList")
	public @ResponseBody Map selMyFollowList(HttpServletRequest request,String pageNum, String pageSize) throws IOException{
		
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				List performs = imFollowService.findMyFollowUsers(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				return super.getSuccessAjaxResult("获取成功！", performs);
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
		
	}
	
	/**
	 * 我的粉丝列表
	 * @return
	 */
	@RequestMapping("selMyFansList")
	public @ResponseBody Map selMyFansList(HttpServletRequest request,String pageNum, String pageSize) throws IOException{
		
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				List performs = imFollowService.findMyFansUsers(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				return super.getSuccessAjaxResult("获取成功！", performs);
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
		
	}
	
	/**
	 * 我的关注数量
	 * @return
	 */
	@RequestMapping("selMyFollowCount")
	public @ResponseBody Map selMyFollowCount(HttpServletRequest request,String userId) throws IOException{
		if(StringUtils.isEmpty(userId)){
			userId =  super.checkLogin(request);
		}
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		try{
			int count = imFollowService.findMyFollowUsersCount(userId);
			return super.getSuccessAjaxResult("获取成功！", count);
		}catch(Exception e){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
		
	}
	
	/**
	 * 我的粉丝数量
	 * @return
	 */
	@RequestMapping("selMyFansCount")
	public @ResponseBody Map selMyFansCount(HttpServletRequest request,String userId) throws IOException{
		
		if(StringUtils.isEmpty(userId)){
			userId =  super.checkLogin(request);
		}
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		try{
			int count = imFollowService.findMyFansUsersCount(userId);
			return super.getSuccessAjaxResult("获取成功！", count);
		}catch(Exception e){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
		
	}
	
	/**
	 * 添加关注
	 * @return
	 */
	@RequestMapping("addMyFollowUser")
	public @ResponseBody Map addMyFollowUser(HttpServletRequest request,String followUser) throws IOException{
		
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if(StringUtil.isEmpty(followUser)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				String resultStr = imFollowService.insertFollowUsers(userId, followUser);
				return super.getSuccessAjaxResult("关注成功！", resultStr);
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
		
	}
	
	/**
	 * 取消关注
	 * @return
	 */
	@RequestMapping("delMyFollowUser")
	public @ResponseBody Map delMyFollowUser(HttpServletRequest request,String followUser) throws IOException{
		
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if(StringUtil.isEmpty(followUser)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				String resultStr = imFollowService.deleteFollowUsers(userId, followUser);
				return super.getSuccessAjaxResult("取消成功！", resultStr);
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
		
	}
	
	/**
	 * 是否关注
	 * @return
	 */
	@RequestMapping("isFollowUser")
	public @ResponseBody Map isFollowUser(HttpServletRequest request,String followUser) throws IOException{
		
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if(StringUtil.isEmpty(followUser)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try{
				boolean resultStr = imFollowService.isFollowUser(userId, followUser);
				return super.getSuccessAjaxResult("获取成功！", resultStr);
			}catch(Exception e){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
		
	}
	
}
