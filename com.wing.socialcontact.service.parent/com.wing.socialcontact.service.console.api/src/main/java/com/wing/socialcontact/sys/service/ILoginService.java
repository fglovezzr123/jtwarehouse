/**  
 * @Project: tjy
 * @Title: ILoginService.java
 * @Package com.oa.manager.system.service
 * @date 2016-4-1 下午3:23:07
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.sys.bean.SyLoginLog;

/**
 * 
 * 类名：ILoginService
 * 功能：
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-1 下午3:23:07
 *
 */

public interface ILoginService {
	
	/**
	 * 用户登录验证
	 * @param vercode	验证码
	 * @param name		用户名
	 * @param password	登录密码
	 * @param request
	 * @param response
	 * @return
	 */
	public Map updateLogin(String vercode,String name,String password,String loginIp,String sessionId);
	/**
	 * 界面解锁
	 * @param password
	 * @return
	 */
	public boolean unlock(HttpSession session,String password);
	
	public void save(SyLoginLog log);
}
