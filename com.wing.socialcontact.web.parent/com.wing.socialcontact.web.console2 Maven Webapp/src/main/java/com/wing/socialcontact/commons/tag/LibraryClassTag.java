package com.wing.socialcontact.commons.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.wing.socialcontact.service.wx.api.ILibraryClassService;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;
import com.wing.socialcontact.util.ServletUtil;

public class LibraryClassTag extends SimpleTagSupport{

	/**
	 * id
	 */
	private String id;
	
	
	@Override  
	 public void doTag() throws JspException, IOException {
		 PageContext ctx = (PageContext)getJspContext(); 
		 JspWriter out=ctx.getOut();
		 
		 ILibraryClassService ClassService = ServletUtil.getApplicationContext().getBean(ILibraryClassService.class);
		 
		 
		 TjyLibraryClass lclass = ClassService.selectByPrimaryKey(id);
			if(lclass!=null){
					out.print(lclass.getContent());
			}
			
	 }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
