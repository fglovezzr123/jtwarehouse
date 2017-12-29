package com.wing.socialcontact.commons.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.wing.socialcontact.service.wx.api.IClubClassService;
import com.wing.socialcontact.service.wx.bean.ClubClass;
import com.wing.socialcontact.util.ServletUtil;

public class ClubClassTag extends SimpleTagSupport {

	/**
	 * id
	 */
	private String id;
	
	
	@Override  
	 public void doTag() throws JspException, IOException {
		 PageContext ctx = (PageContext)getJspContext(); 
		 JspWriter out=ctx.getOut();
		 
		 IClubClassService ClassService = ServletUtil.getApplicationContext().getBean(IClubClassService.class);
		 
		 
		 ClubClass lclass = ClassService.selectByPrimaryKey(id);
			if(lclass!=null){
				out.print(lclass.getName());
			}
			
	 }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
