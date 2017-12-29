package com.tojoycloud.filter.dealexception;

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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tojoycloud.common.ConstantDefinition;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.report.ErrorResponseReport;

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
