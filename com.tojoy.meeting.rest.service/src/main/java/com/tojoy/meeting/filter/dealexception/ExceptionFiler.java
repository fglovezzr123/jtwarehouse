package com.tojoy.meeting.filter.dealexception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tojoy.meeting.common.ConstantDefinition;
import com.tojoy.meeting.common.report.ResponseCode;
import com.tojoy.meeting.report.ErrorResponseReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一 异常处理接口
 * 
 */
public class ExceptionFiler implements Filter
{

	private static Logger logger = LoggerFactory.getLogger(ConstantDefinition.LOGGER_MAIN);

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
			sendErrorResponse(response, "服务器端错误" + e.toString());
			logger.error("Exception :  ===   " + e.toString());
		}

	}

	private static void sendErrorResponse(HttpServletResponse response, String message)
	{
		try
		{
			ErrorResponseReport report = new ErrorResponseReport(ResponseCode.Error);
			report.setResponseTips(message);
			String resp = JSON.toJSONString(report, SerializerFeature.NotWriteDefaultValue, SerializerFeature.DisableCircularReferenceDetect);
			logger.info(response + "\r\n");
			response.setHeader("Content-Type", "application/x-json");
			response.getOutputStream().write(resp.getBytes("utf-8"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void init(FilterConfig arg0) throws ServletException
	{

	}

}
