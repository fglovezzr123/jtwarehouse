/**  
 * @Title: LoginUserNameTag.java
 * @date 2016-10-10 下午4:19:56
 * @Copyright: 2016 
 */
package com.wing.socialcontact.commons.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.sys.bean.SyUsers;
import com.wing.socialcontact.sys.service.IDeptService;
import com.wing.socialcontact.sys.service.IUserService;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 输出当前登陆用户 信息
 * @author	dijuli
 * @version	 1.0
 *
 */
public class LoginUserInfoTag extends SimpleTagSupport{
	/**
	 * 获取信息类型
	 * userId 
	 * userName 登陆账号
	 * trueName 姓名
	 * deptId 	所属部门id
	 * deptName	所属部门名称
	 */
	private String type;
	
	@Override  
	 public void doTag() throws JspException, IOException {
		 PageContext ctx = (PageContext)getJspContext(); 
		 JspWriter out=ctx.getOut();
		 Member me=ServletUtil.getMember();
		 
		 IDeptService deptService = ServletUtil.getApplicationContext().getBean(IDeptService.class);
		 
		 IUserService userService = ServletUtil.getApplicationContext().getBean(IUserService.class);
		 
		 SyUsers user = userService.selectByPrimaryKey(me.getId());
		 
		 if("userId".equals(type)){
			 out.print(me.getId());
		 }else  if("userName".equals(type)){
			 out.print(user.getUserName());
		 }else  if("trueName".equals(type)){
			 out.print(user.getTrueName());
		 }else  if("deptId".equals(type)){
			 out.print(me.getDeptId());
		 }else  if("deptName".equals(type)){
			 out.print(deptService.selectByPrimaryKey(user.getDeptId()).getDeptName());
		 }
		 
	 }

	public void setType(String type) {
		this.type = type;
	}
}
