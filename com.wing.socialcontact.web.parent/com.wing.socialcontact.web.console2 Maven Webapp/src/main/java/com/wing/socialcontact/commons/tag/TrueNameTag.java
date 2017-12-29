/**  
 * @Project: tjy
 * @Title: TrueNameTag.java
 * @Package com.oa.commons.tag
 * @date 2016-6-6 下午9:56:45
 * @Copyright: 2016 
 */
package com.wing.socialcontact.commons.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.wing.socialcontact.sys.bean.SyDept;
import com.wing.socialcontact.sys.bean.SyUsers;
import com.wing.socialcontact.sys.service.IUserService;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 
 * 类名：TrueNameTag
 * 功能：根据用户id，输出用户姓名
 * 详细：从缓存中获取
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-6-6 下午9:56:45
 *
 */
public class TrueNameTag extends SimpleTagSupport{
	/**
	 * id
	 */
	private String id;
	
	@Override  
	 public void doTag() throws JspException, IOException {
		 PageContext ctx = (PageContext)getJspContext(); 
		 JspWriter out=ctx.getOut();
		
		 IUserService userService = ServletUtil.getApplicationContext().getBean(IUserService.class);
		 
		 if("0".equals(id)||"".equals(id)){
			 out.print("");
		 }else{
			 SyUsers user = userService.selectByPrimaryKey(id);
			 out.print(user.getTrueName());
		 }
		 
			 
	 }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
