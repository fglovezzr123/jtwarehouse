/**  
 * @Project: tjy
 * @Title: DeptNameTag.java
 * @Package com.oa.commons.tag
 * @date 2016-6-6 下午9:57:06
 * @Copyright: 2016 
 */
package com.wing.socialcontact.commons.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.wing.socialcontact.sys.bean.SyDept;
import com.wing.socialcontact.sys.service.IDeptService;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 
 * 类名：DeptNameTag
 * 功能：根据部门id，输出部门名称
 * 详细：从缓存中获取
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-6-6 下午9:57:06
 *
 */
public class DeptNameTag extends SimpleTagSupport{
	/**
	 * id
	 */
	private String id;
	
	
	@Override  
	 public void doTag() throws JspException, IOException {
		 PageContext ctx = (PageContext)getJspContext(); 
		 JspWriter out=ctx.getOut();
		 
		 IDeptService deptService = ServletUtil.getApplicationContext().getBean(IDeptService.class);
		 
		 if("0".equals(id)||"".equals(id)){
			 out.print("");
		 }else{
			 SyDept dept = deptService.selectByPrimaryKey(id);
			 out.print(dept.getDeptName());
		 }
		 
			
	 }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
