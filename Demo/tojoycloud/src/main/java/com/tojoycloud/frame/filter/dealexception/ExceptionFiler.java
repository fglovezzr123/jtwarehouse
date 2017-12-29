package com.tojoycloud.frame.filter.dealexception;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统一 异常处理接口
 * 
 * @author root
 * 
 */
public class ExceptionFiler implements Filter
{

	private static Logger logger = LoggerFactory.getLogger("MainLog");

	public void destroy()
	{

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		try
		{
			String url = request.getRequestURI();
			logger.info(request.getMethod() + "   " + url);
			chain.doFilter(req, res);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			response.getOutputStream().write("服务器端错误".getBytes("utf-8"));
			logger.error("Exception :  ===   " + e.getMessage());
		}

	}

	public void init(FilterConfig arg0) throws ServletException
	{

	}

}
