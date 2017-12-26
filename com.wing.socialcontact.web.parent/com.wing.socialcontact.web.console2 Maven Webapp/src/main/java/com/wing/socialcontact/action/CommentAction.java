package com.wing.socialcontact.action;

import java.util.Date;



import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.ICommentService;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 评论管理
 * @author GAOJUN
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentAction extends BaseAction{
	
	@Autowired
	private ICommentService commentService; 

	/**
	 * 条件查询评论
	 * 
	 * @return
	 */
	@RequiresPermissions("comment:read")
	@RequestMapping("list")
	public String list(ModelMap map){
		return "comment/comment_list";
	
	}
	@RequiresPermissions("comment:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,Comment comment,String startTimef, String endTimef,String trueNamef,String title,String mobile,String userId){
		//OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		//String ossurl = ossConfig.getOss_getUrl();
		//map.addAttribute("ossurl", ossurl);
		return ajaxJsonEscape(commentService.selectComments(param, comment, startTimef, endTimef, trueNamef,title,mobile,userId));
	}
	/**
	 * 跳转到评论添加页面
	 * @return
	 */
	@RequiresPermissions("comment:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		return "comment/add";
	
	}
	/**
	 * 添加评论
	 * @param Comment
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("comment:add")
	@RequestMapping("add")
	public ModelAndView add(Comment comment,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		comment.setCreateTime(new Date());
		comment.setCreateUserId(ServletUtil.getMember().getId());
		comment.setCreateUserName(ServletUtil.getMember().getUserName());
		return ajaxDone(commentService.addComment(comment));	
	}
	/**
	 * 跳转到评论修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("comment:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		Comment comment = commentService.selectById(id);
		if(comment==null){
			return NODATA;
		}
		map.addAttribute("b",comment);
		return "comment/update";
	}
	/**
	 * 修改评论
	 * @param Comment
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("comment:update")
	@RequestMapping("update")
	public ModelAndView update(Comment comment,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(commentService.updateComment(comment));
		
	}
	/**
	 * 删除评论
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("comment:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(commentService.deleteComments(ids));
	}
	
}
