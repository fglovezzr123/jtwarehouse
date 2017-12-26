package com.wing.enterprise.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 拦截器(设置本地线程变量)
 */
public class LogonFrontInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = LoggerFactory.getLogger(LogonFrontInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			CtxHolder.setMember(request);
			return true;
		} catch (Exception e) {
			log.error("",e);
			return true;
		}
	}
}