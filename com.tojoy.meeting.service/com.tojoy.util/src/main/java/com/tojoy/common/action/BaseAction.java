package com.tojoy.common.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tojoy.common.model.Member;
import com.tojoy.util.Constants;


/**
 * 
 * 类名：BaseAction
 * 功能：action 父类
 * 详细：所有action类的父类 一些常用方法
 * 版本：1.0
 *
 */
public abstract class BaseAction { 
	
	/**
	 * 自定义状态码 和自定义提示信息，
	 * @return
	 */
	protected Map<String,Object> getAjaxResult(String code,String msg,Object dataobj) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("code", code);
		res.put("msg", msg);
		res.put("dataobj", dataobj);
		return res;
	}
	protected Map<String,Object> getSuccessAjaxResult(String msg,Object dataobj) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("code", Constants.AJAX_CODE_SUCCESS);
		res.put("msg", msg);
		res.put("dataobj", dataobj);
		return res;
	}
	protected Map<String,Object> getSuccessAjaxResult(String msg) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("code", Constants.AJAX_CODE_SUCCESS);
		res.put("msg", msg);
		res.put("dataobj", "");
		return res;
	}
	protected Map<String,Object> getSuccessAjaxResult() {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("code", Constants.AJAX_CODE_SUCCESS);
		res.put("msg", Constants.AJAX_MSG_SUCCESS);
		res.put("dataobj", "");
		return res;
	}
	
	/**
	 * 验证是否登录
	 * @param request
	 * @return
	 */
	protected String checkLogin(HttpServletRequest request){
		Member member = (Member)request.getSession().getAttribute("me");
		String userId =  member != null ?member.getId():null;
		if(userId == null || "".equals(userId))	{
			return null;
		}else{
			return userId;
		}
	}
	
	
}
