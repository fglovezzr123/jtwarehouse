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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.im.api.IImFriendrequestService;
import com.wing.socialcontact.service.im.api.IImGroupfavService;
import com.wing.socialcontact.service.im.api.IImGroupinfoService;
import com.wing.socialcontact.service.im.api.IImGrouprequestService;
import com.wing.socialcontact.service.im.api.IImGroupusersService;
import com.wing.socialcontact.service.im.bean.ImGroupinfo;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.ServletUtil;

import tk.mybatis.mapper.util.StringUtil;

/**
 * 人脉圈
 * @author	xuxinyuan
 * @version	 1.0
 *
 */
@Controller
@RequestMapping("/im/m")
public class ImRelationshipsAction extends BaseAction{

	@Autowired
	private IImGroupinfoService imGroupinfoService; 
	
	@Autowired
	private IListValuesService listValuesService; 
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IImFriendrequestService imFriendrequestService;
	
	/**
	 * 获取我的同好圈子
	 * @return
	 */
	@RequestMapping("selIdenticalHobbyWorld")
	public @ResponseBody Map selIdenticalHobbyWorld(HttpServletRequest request,String hobby,String groupName,String pageNum, String pageSize) throws IOException{
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try {
				List performs = imGroupinfoService.selIdenticalHobbyWorld(userId, hobby,groupName,Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				return super.getSuccessAjaxResult("同好圈子获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 同行精英
	 * @return
	 */
	@RequestMapping("selPeerElite")
	public @ResponseBody Map selPeerElite(HttpServletRequest request, String industry,String nickname,String pageNum, String pageSize) throws IOException{
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try {
				TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
				industry = tjyUser.getIndustry();
				//List performs = imGroupinfoService.selPeerElite(userId, industry, nickname,Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				List performs = imGroupinfoService.selPeerElite2(userId, industry, nickname);
				return super.getSuccessAjaxResult("同行精英获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 同城商友
	 * @return
	 */
	@RequestMapping("selCityElite")
	public @ResponseBody Map selCityElite(HttpServletRequest request,String pageNum, String pageSize) throws IOException{
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try {
				//List performs = imGroupinfoService.selCityElite(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				List performs = imGroupinfoService.selCityElite2(userId);
				return super.getSuccessAjaxResult("同城商友获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 智同道合
	 * @return
	 */
	@RequestMapping("selSameIdeasElite")
	public @ResponseBody Map selSameIdeasElite(HttpServletRequest request,String pageNum, String pageSize) throws IOException{
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}		
		
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
			
			try {
				if (!"1".equals(tjyUser.getIsRealname() + "")) {
					List performs = imFriendrequestService.findNoFriendUserListByUserId(userId, Integer.parseInt(pageNum),
							Integer.parseInt(pageSize), null, null, null);
					return super.getSuccessAjaxResult("智同道合获取成功！", performs);
				}else{
					List performs = imGroupinfoService.selSameIdeasElite(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
					return super.getSuccessAjaxResult("智同道合获取成功！", performs);
				}
				
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 大咖列表
	 * @return
	 */
	@RequestMapping("selDKList")
	public @ResponseBody Map selDKList(HttpServletRequest request,String pageNum, String pageSize) throws IOException{
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try {
				List performs = imGroupinfoService.selDKList(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				return super.getSuccessAjaxResult("大咖列表获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 二度人脉
	 * @return
	 */
	@RequestMapping("selTwoDegreeConnections")
	public @ResponseBody Map selTwoDegreeConnections(HttpServletRequest request,String pageNum, String pageSize) throws IOException{
		String userId =  super.checkLogin(request);
		if(userId == null || "".equals(userId))	{
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}	
		
		if(StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			try {
				List performs = imGroupinfoService.selTwoDegreeConnections(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				return super.getSuccessAjaxResult("二度人脉获取成功！", performs);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 获取行业列表
	 * @return
	 */
	@RequestMapping("selindustry")
	public @ResponseBody Map selindustry(HttpServletRequest request) throws IOException{
		try {
			List performs = listValuesService.selectListByType(8001);
			return super.getSuccessAjaxResult("行业列表获取成功！", performs);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}
	
	/**
	 * 获取爱好列表
	 * @return
	 */
	@RequestMapping("selhobby")
	public @ResponseBody Map selhobby(HttpServletRequest request) throws IOException{
		try {
			List performs = listValuesService.selectListByType(8002);
			return super.getSuccessAjaxResult("爱好列表获取成功！", performs);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}
	
}
