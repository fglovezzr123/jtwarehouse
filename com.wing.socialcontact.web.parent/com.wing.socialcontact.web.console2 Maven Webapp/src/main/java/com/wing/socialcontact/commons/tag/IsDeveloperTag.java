/**  
 * @Title isDeveloperTag.java
 * @date 2016-12-9 下午5:23:40
 * @Copyright: 2016 
 */
package com.wing.socialcontact.commons.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.wing.socialcontact.util.ServletUtil;

/**
 * 判断是否是开发人员 
 * 如果是开发人员 则输出标签内包括的内容
 * @author dijuli
 * @version 1.0
 */
public class IsDeveloperTag extends TagSupport{

	/**
	 * @Fields serialVersionUID : 
	 */
	
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException { //对标签内容的处理
		
		if(ServletUtil.isDeveloper()){
			return EVAL_BODY_INCLUDE;//标签内包含的内容被正常执行
		}
		return SKIP_BODY ; //不显示该标签之间的内容
	}
}
