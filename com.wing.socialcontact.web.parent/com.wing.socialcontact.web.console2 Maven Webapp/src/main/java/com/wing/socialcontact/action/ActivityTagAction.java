package com.wing.socialcontact.action;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IActivityTagService;
import com.wing.socialcontact.service.wx.bean.ActivityTag;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;
/**
 * 
 * <p>Title:活动标签管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年4月18日 下午3:58:37
 */
@Controller
@RequestMapping("/activitytag")
public class ActivityTagAction extends BaseAction {

	@Autowired
	private IActivityTagService activityTagService;
	
	@Autowired
	private IListValuesService listValuesService;
	
	
	/**
	 * 活动标签列表
	 */
	@RequiresPermissions("activitytag:read")
	@RequestMapping("load")
	public String classload(){
		return "system/activitytag/load";
	}
	
	/**
	 * 活动标签列表查询
	 * @param param
	 * @param activitytag
	 * @return
	 */
	@RequiresPermissions("activitytag:read")
	@RequestMapping("query")
	public ModelAndView classquery(PageParam param,ActivityTag activitytag){
		return ajaxJsonEscape(activityTagService.selectactivitytag(param, activitytag));
	}
	
	/**
	 * 活动标签新增
	 */
	@RequiresPermissions("activitytag:add")
	@RequestMapping("addPage")
	public String classaddPage(ModelMap map){
		
		List classes = listValuesService.selectListByType(9002);
		map.addAttribute("classes", classes);
		return "system/activitytag/add";
	}
	
	@RequiresPermissions("activitytag:add")
	@RequestMapping("add")
	public ModelAndView classadd(ActivityTag dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		dto.setCreateTime(new Date());
		dto.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(activityTagService.addActivityTag(dto));
		
	}
	
	/**
	 * 活动标签修改
	 */
	@RequiresPermissions("activitytag:update")
	@RequestMapping("updatePage")
	public String classupdatePage(String id,ModelMap map){
		ActivityTag dto = activityTagService.getActivityTagByid(id);
		map.addAttribute("dto", dto);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		List classes = listValuesService.selectListByType(9002);
		map.addAttribute("classes", classes);
		return "system/activitytag/update";
	}
	
	@RequiresPermissions("activitytag:update")
	@RequestMapping("update")
	public ModelAndView classupdate( ActivityTag dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(activityTagService.updateActivityTag(dto));
		
	}
	
	 //根据分类id查材料
	 @RequestMapping("tags")
	 public ModelAndView material(String cid){
		 
		return ajaxJsonEscape(activityTagService.activityTag(cid));
	 }
	 
	 /**
	 * 活动标签删除
	 */
	@RequiresPermissions("activitytag:delete")
	@RequestMapping("del")
	public ModelAndView del(String id){
		
		return ajaxDone(activityTagService.deleteactivitytag(id));
	}
}
