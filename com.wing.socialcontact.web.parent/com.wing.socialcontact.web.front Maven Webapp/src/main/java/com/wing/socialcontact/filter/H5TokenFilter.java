package com.wing.socialcontact.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

import com.wing.socialcontact.common.model.IpInfo;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.H5TokenUtil;
import com.wing.socialcontact.util.IpUtil;
import com.wing.socialcontact.util.SpringContextUtil;

public class H5TokenFilter implements Filter {

	private IWxUserService wxUserService;

	private ITjyUserService tjyUserService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		wxUserService = (IWxUserService)SpringContextUtil.getBean("wxUserService");
		tjyUserService = (ITjyUserService)SpringContextUtil.getBean("tjyUserService");
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		//if(httprequest.getSession().getAttribute(Constants.SESSION_WXUSER_ID)==null){
			String token = httprequest.getParameter("h5token");
			if(token!=null){
				String userid = H5TokenUtil.getUserByH5Token(token);
				if(!"".equals(userid)){
					//登陆
					String loginIp = IpUtil.getIpAddr(httprequest);
					IpInfo ipInfo = IpUtil.getIpInfo(loginIp);
					TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userid);
					Member me = new Member();// 需要放入当前session 的用户信息
					me.setId(tjyUser.getId());
					me.setIpInfo(ipInfo);
					me.setLoginTime(DateUtil.currentTimestamp());
					if (null != tjyUser) {
						me.setIsRealname(tjyUser.getIsRealname() + "");
						me.setKfTelephone(tjyUser.getKfTelephone());
					}
	
					httprequest.getSession().setAttribute("me", me);
					httprequest.getSession().setAttribute(Constants.SESSION_WXUSER_ID, me.getId() + "");
					httprequest.getSession().setAttribute(Constants.SESSION_WXUSER_NICKNAME, tjyUser.getNickname());
					httprequest.getSession().setAttribute(Constants.SESSION_WXUSER_HDPIC, tjyUser.getHeadPortrait());
					
				}else{
					System.out.println(httprequest.getMethod());
					if("POST".equals(httprequest.getMethod())){
						PrintWriter writer = httpresponse.getWriter();
						httpresponse.setContentType("application/json");
						httpresponse.setHeader("Cache-Control", "no-cache");
						httpresponse.setCharacterEncoding("UTF-8");
						Map<String,Object> res = new HashMap<String,Object>();
				        res.put("code", "302");
				        res.put("msg", "需重新登录");
				        res.put("dataobj", "");
				        String rets = Json.toJson(res, JsonFormat.compact());
		    			writer.print(rets);
		                return ;
					}else{
						//跳转登陆
						httpresponse.sendRedirect("/tj/token/invalid");
						return;
					}
				}
				
			}
		//}
		XssRequestWrappery xrequest = new XssRequestWrappery(httprequest);
		chain.doFilter(xrequest, response);
	}
	@Override
	public void destroy() {
	}

}
