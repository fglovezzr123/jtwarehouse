package com.wing.socialcontact.interceptors;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.wing.socialcontact.sensitive.SensitiveWordResult;
import com.wing.socialcontact.sensitive.SimpleKWSeekerProcessor;

/**
 * 敏感词拦截器
 */
public class SensitiveFrontInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = LoggerFactory.getLogger(SensitiveFrontInterceptor.class);
	private SimpleKWSeekerProcessor simpleKWSeekerProcessor;
	
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("access ").append(url);
			Map<String,String> headers = toString(request, sb);
			
			//x-requested-with=XMLHttpRequest
			String requestType = headers.get("x-requested-with");
			//application/json
			String contentType = headers.get("content-type");
			
			log.debug("requestType:"+requestType);
			log.debug("contentType:"+contentType);
			
			boolean hasSensitive = false;
			String sensitive = "";
			for (Object tag : request.getParameterMap().keySet()) {
				String value = request.getParameter(tag.toString());
				sensitive = hasSensitiveWords(value);
				if(sensitive.length()>0){
					hasSensitive = true;
					break;
				}
			}
			if(hasSensitive){
				//判断请求类型
				if(requestType!=null&&requestType.equalsIgnoreCase("XMLHttpRequest")){
					Map<String,Object> res = new HashMap<String,Object>();
					res.put("code", "999");
					res.put("msg", "含敏感词"+sensitive);
					res.put("dataobj", "");
					//ajax请求
					PrintWriter out = null;  
					response.setCharacterEncoding("UTF-8");  
				    response.setContentType("application/json; charset=utf-8");
				    
				    out = response.getWriter();  
			        out.append(JSON.toJSONString(res));
			        out.flush();
				}else{
					request.setAttribute("message", "含敏感词"+sensitive);
					request.setAttribute("tips", "请重新输入后重试");
					request.getRequestDispatcher("/WEB-INF/jsp/exception.jsp").forward(request, response);
				}
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private String hasSensitiveWords(String words){
		String result="";
		try {
			if(simpleKWSeekerProcessor==null){
				simpleKWSeekerProcessor = SimpleKWSeekerProcessor.newInstance();
			}
			List<SensitiveWordResult> list = simpleKWSeekerProcessor.getKWSeeker().findWords(words);
			for(SensitiveWordResult swr : list){
				if(result.length()==0){
					result = "【"+swr.getWord()+"】";
				}
			}
			return result;
		} catch (Exception e) {
			return result;
		}
	}
	/**
	 * 把request转化为字符串
	 * 
	 * @param req
	 * @param sb
	 */
	private Map<String,String> toString(HttpServletRequest req, StringBuilder sb) {
		Map<String,String> headers = Maps.newHashMap();
		
		sb.append(req.getMethod()).append(" ").append(req.getRequestURI()).append("\n");
		Enumeration<String> enu = req.getHeaderNames();
		while (enu.hasMoreElements()) {
			String tag = enu.nextElement();
			sb.append(tag).append("=").append(req.getHeader(tag)).append("\n");
			headers.put(tag.toLowerCase(), req.getHeader(tag));
		}
		sb.append('\n');
		for (Object tag : req.getParameterMap().keySet()) {
			sb.append(tag).append(" : ")
					.append(req.getParameter(tag.toString())).append("\n");
		}
		return headers;
	}
}