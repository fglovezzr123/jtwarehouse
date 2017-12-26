package com.wing.socialcontact.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wing.socialcontact.service.wx.api.ISysBlacklistService;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

public class BlacklistFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		if (ServletUtil.isLogin(httprequest)) {
			ISysBlacklistService sysBlacklistService = (ISysBlacklistService) SpringContextUtil
					.getBean("sysBlacklistService");
			boolean isBlack = sysBlacklistService
					.selectSysBlacklistByUserId(ServletUtil.getMember(httprequest).getId());
			if (isBlack) {
				httprequest.getSession().removeAttribute("me");
				httpresponse.sendRedirect(httprequest.getContextPath() + "/m/sys/blackPage.do");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
