package com.wing.socialcontact.commons.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 根据上传图片的path返回oss图片访问路径
 * 
 * @author sino
 * 
 */
public class OssTag extends SimpleTagSupport {

	/**
	 * 图片路径 不能以/或者\开头
	 */
	private String path;
	
	
	@Override
	public void doTag() throws JspException, IOException {
		PageContext ctx = (PageContext) getJspContext();
		JspWriter out = ctx.getOut();
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		out.print(ossConfig.getOss_getUrl()+path);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
