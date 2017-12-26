/**  
 * @Project: jxoa
 * @Title: ServletUtil.java
 * @Package com.oa.commons.util
 * @date 2013-5-13 下午3:27:40
 * @Copyright: 2013 
 */
package com.wing.socialcontact.util;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.wing.socialcontact.common.model.IpInfo;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.common.model.OnLineUser;
import com.wing.socialcontact.sys.service.IRoleService;

/**
 * 
 * 类名：ServletUtil 功能：Servlet工具�? 详细：获取ServletContext session，request
 * ApplicationContext 作�?：LiuJincheng 版本�?.0 日期�?013-5-13 下午3:27:40
 *
 */
public class ServletUtil implements ServletContextAware {
	private static ThreadLocal<IpInfo> local = new ThreadLocal<IpInfo>();

	private static ServletContext servletContext;

	public static IpInfo getIpInfo() {
		return local.get() == null ? new IpInfo() : local.get();
	}

	public static void setIpInfo(IpInfo ipInfo) {
		local.set(ipInfo);
	}

	/**
	 * 获取ServletContext
	 * 
	 * @return
	 */
	public static ServletContext getServletContext() {

		return servletContext;
	}

	/**
	 * 获取ServletContext保存的数�?
	 * 
	 * @param name
	 * @return
	 */
	public static void setServletContextAttribute(String name, Object object) {

		servletContext.setAttribute(name, object);

	}

	/**
	 * 获取ServletContext保存的数�?
	 * 
	 * @param name
	 * @return
	 */
	public static Object getServletContextAttribute(String name) {

		return servletContext.getAttribute(name);

	}

	/**
	 * 获取HttpServletRequest
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {

		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

	}

	/**
	 * 获取HttpServletRequest 保存的数�?
	 * 
	 * @param name
	 * @return
	 */
	public static Object getRequestAttribute(String name) {

		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getAttribute(name);

	}

	/**
	 * 获取 Session
	 * 
	 * @return
	 */
	public static Session getSession() {

		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 获取 Session保存的数�?
	 * 
	 * @param name
	 * @return
	 */
	public static Object getSessionAttribute(String name) {

		return getSession().getAttribute(name);

	}

	/**
	 * 获取当前用户对象--微信端不能使用
	 * @Title: getMember 
	 * @Description: TODO
	 * @return
	 * @return: Member
	 * @author: zengmin   
	 * @date: 2017年4月12日 下午4:10:15
	 */
	public static Member getMember() {
		try {
			Member m = (Member) getSession().getAttribute("minfo");
			m = m == null ? new Member() : m;
			m.setIpInfo(getIpInfo());
			return m;
		} catch (Exception e) {
			try {
				Member m = JSON.parseObject(RpcContext.getContext().getAttachment("minfo"), Member.class);
				return m == null ? new Member() : m;
			} catch (Exception e2) {
				e2.printStackTrace();
				return new Member();
			}
		}
	}
	
	public static String getMemberId() {
		Member m = getMember();
		return m==null?"":m.getId();
	}

	/**
	 * 获取在线用户Map
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, OnLineUser> getOnLineUsers() {

		return (Map<String, OnLineUser>) getServletContextAttribute("onLineUsers");
	}

	/**
	 * 判断当前用户是不是开发�?
	 * 
	 * @return
	 */
	public static boolean isDeveloper() {
		// return (Boolean)getSession().getAttribute("dev");
		return true;
	}

	/**
	 * 判断当前用户是不是系统超级管理员
	 * 
	 * @return
	 */
	public static boolean isSuperAdmin() {
		return true;
		// return (Boolean)getSession().getAttribute("sa");
	}

	/**
	 * 获取ApplicationContext
	 * 
	 * @param c
	 * @return
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {

		return WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	}

	/**
	 * 清空缓存�?用户权限认证信息
	 * 
	 * @param userId
	 *            用户id
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void removeAuthorizationCache(String userId) {
		EhCacheManager ehCacheManager = (EhCacheManager) getApplicationContext().getBean("shiroEhcacheManager");

		Cache cache = ehCacheManager.getCache("shiroAuthRealm.authorizationCache");

		// System.out.println("==="+cache.keys()+ehCacheManager.getCacheManager().getCache("shiroAuthRealm.authorizationCache").getSize());

		System.out.println("清空认证缓存" + cache.remove(new SimplePrincipalCollection(userId, "shiroAuthRealm")));
	}

	/**
	 * 获取当前用户角色ids
	 * 
	 * @return
	 */
	public static List<String> getCurrentUserRoleIds() {
		return getRoleIdsByUserId(getMember().getId());
	}

	/**
	 * 根据用户id获取当前用户角色ids
	 * 
	 * @param userId
	 * @return
	 */
	public static List<String> getRoleIdsByUserId(String userId) {

		IRoleService roleService = getApplicationContext().getBean(IRoleService.class);
		return roleService.selectRolesByUserId(userId);
	}

	/**
	 * 判断�?��返回的信息格式是不是json
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isJSONResponse(HttpServletRequest request) {
		// 根据Accept 类型判断返回的信息格�?
		String accept = request.getHeader("Accept");
		if (accept == null) {
			return true;// 如果Accept 为空 则返�?json格式
		} else {
			if (accept.contains("application/json")) {
				return true;
			} else if ("1".equals(request.getParameter("ajax"))) {
				// iframe方式上传表单，默认隐藏提交一个ajax参数
				return true;
			}
		}
		return false;
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		servletContext = arg0;
	}

	/**
	 * 获取当前登录用户
	 * @Title: getMember 
	 * @Description: TODO
	 * @param req
	 * @return
	 * @return: Member
	 * @author: zengmin   
	 * @date: 2017年4月12日 下午4:10:24
	 */
	public static Member getMember(HttpServletRequest req) {
		Member m = (Member) req.getSession().getAttribute("me");
//		m = m == null ? new Member() : m;
		return m;
	}
	
	/**
	 * 判断当前是否已登录
	 * @Title: isLogin 
	 * @Description: TODO
	 * @param req
	 * @return
	 * @return: boolean
	 * @author: zengmin   
	 * @date: 2017年5月10日 上午11:15:35
	 */
	public static boolean isLogin(HttpServletRequest req) {
		Member m = (Member) req.getSession().getAttribute("me");
		if(null == m){
			return false;
		}
		if(StringUtils.isEmpty(m.getId())){
			return false;
		}
		return true;
	}

}
