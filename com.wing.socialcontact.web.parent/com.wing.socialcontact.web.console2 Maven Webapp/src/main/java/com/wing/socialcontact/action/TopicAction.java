package com.wing.socialcontact.action;

import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IReportService;
import com.wing.socialcontact.service.wx.api.ITopicService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Report;
import com.wing.socialcontact.service.wx.bean.Topic;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 话题pk管理
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/topic")
public class TopicAction extends BaseAction{
	
	@Autowired
	private ITopicService topicService; 
	
	@Autowired
	private IReportService reportService; 
	
	@Autowired
	private IWxUserService wxUserService;
	
	/**
	 * 条件查询话题
	 * 
	 * @return
	 */
	@RequiresPermissions("topic:read")
	@RequestMapping("load")
	public String load(){
		
		return "system/topic/load";
	
	}
	@RequiresPermissions("topic:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,Topic topic,String startTime,String endTime,String createUserId,String userId){
		
		return ajaxJsonEscape(topicService.selectAllTopic(param, topic,startTime,endTime, createUserId,userId,0));
	}
	
	
	/**
	 * 跳转到话题添加页面
	 * @return
	 */
	@RequiresPermissions("topic:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		/*String userName = "管理员";
		map.addAttribute("userName",userName);*/
		return "system/topic/add";
	
	}
	/**
	 * 添加话题
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("topic:add")
	@RequestMapping("add")
	public ModelAndView add(Topic topic,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		String mobile = topic.getCreateUserId();
		WxUser user = wxUserService.selectByMobile(mobile);
		if(null == user){
			return ajaxDoneTextError("该手机用户未在本系统中注册！");
		}
		topic.setCreateTime(new Date());
		topic.setCreateUserId(String.valueOf(user.getId()));
		topic.setCreateUserName(user.getUsername());
		topic.setRedCount(0);
		topic.setBlueCount(0);
		topic.setIsRecommend(2);
		topic.setIsShow(2);
		topic.setAllowComment(1);
		topic.setStatus(1);
		topic.setIsAd(0);
		String str = topicService.addTopic(topic);
		if(str.equals("msg.topic.unique")){
			return ajaxDoneTextError("该话题已存在！");
		}
		return ajaxDone(str);	
	}
	/**
	 * 跳转到话题修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("topic:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		Topic topic = topicService.selectById(id);
		if(topic==null){
			return NODATA;
		}
		map.addAttribute("t",topic);
		
		return "system/topic/update";
	}
	/**
	 * 修改话题
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("topic:update")
	@RequestMapping("update")
	public ModelAndView update(Topic topic,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		String str = topicService.updateTopic(topic);
		if(str.equals("msg.topic.unique")){
			return ajaxDoneTextError("该话题已存在！");
		}
		return ajaxDone(str);	
	}
	/**
	 * 删除话题
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("topic:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(topicService.deleteTopics(ids));
	}
	/**
	 * 条件查询话题举报
	 * 
	 * @return
	 */
	@RequiresPermissions("tcreport:read")
	@RequestMapping("reportload")
	public String reportload(){
		
		return "system/topic/reportload";
	
	}
	@RequiresPermissions("tcreport:read")
	@RequestMapping("reportquery")
	public ModelAndView reportquery(PageParam param,String title,String type,String startTime,String endTime,String isShow){
		
		return ajaxJsonEscape(reportService.selectAllTopicReport(param, title, "1",startTime,endTime,isShow));
	}
	/**
	 * 跳转到话题举报修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("tcreport:update")
	@RequestMapping("reportupdatePage")
	public String reportupdatePage(String id,ModelMap map){
		Map<String, Object> report = reportService.selectReportById(id);
		if(report==null){
			return NODATA;
		}
		map.addAttribute("r",report);
		
		return "system/topic/reportupdate";
	}
	/**
	 * 修改话题举报
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("tcreport:update")
	@RequestMapping("reportupdate")
	public ModelAndView reportupdate(Report report,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(reportService.updateReport(report));
		
	}
	/**
	 * 删除话题举报
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("tcreport:delete")
	@RequestMapping("reportdel")
	public ModelAndView reportdel(String[] ids){	
		return ajaxDone(reportService.deleteReport(ids));
	}
	
	/**
	 * 条件查询话题广告
	 * 
	 * @return
	 */
	@RequiresPermissions("topicad:read")
	@RequestMapping("loadAd")
	public String loadAd(){
		
		return "system/topic/loadad";
	
	}
	@RequiresPermissions("topicad:read")
	@RequestMapping("queryAd")
	public ModelAndView queryAd(PageParam param,Topic topic,String startTime,String endTime){
		
		return ajaxJsonEscape(topicService.selectAllTopic(param, topic,startTime,endTime, null,null,1));
	}
	
	
	/**
	 * 跳转到话题广告添加页面
	 * @return
	 */
	@RequiresPermissions("topicad:add")
	@RequestMapping("addPageAd")
	public String addPageAd(ModelMap map){
		return "system/topic/addad";
	
	}
	/**
	 * 添加话题广告
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("topicad:add")
	@RequestMapping("addAd")
	public ModelAndView addAd(Topic topic,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		String mobile = topic.getCreateUserId();
		WxUser user = wxUserService.selectByMobile(mobile);
		if(null == user){
			return ajaxDoneTextError("该手机用户未在本系统中注册！");
		}
		topic.setCreateTime(new Date());
		topic.setCreateUserId(String.valueOf(user.getId()));
		topic.setCreateUserName(user.getUsername());
		topic.setRedCount(0);
		topic.setBlueCount(0);
		topic.setIsRecommend(2);
		topic.setIsShow(2);
		topic.setAllowComment(1);
		topic.setStatus(1);
		topic.setIsAd(1);
		topic.setSort(0);
		String str = topicService.addTopic(topic);
		if(str.equals("msg.topic.unique")){
			return ajaxDoneTextError("该话题已存在！");
		}
		return ajaxDone(str);	
	}
	/**
	 * 跳转到话题广告修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("topicad:update")
	@RequestMapping("updatePageAd")
	public String updatePageAd(String id,ModelMap map){
		Topic topic = topicService.selectById(id);
		if(topic==null){
			return NODATA;
		}
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		map.addAttribute("t",topic);
		
		return "system/topic/updatead";
	}
	/**
	 * 修改话题广告
	 * @param newsclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("topicad:update")
	@RequestMapping("updateAd")
	public ModelAndView updateAd(Topic topic,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		String str = topicService.updateTopic(topic);
		if(str.equals("msg.topic.unique")){
			return ajaxDoneTextError("该话题已存在！");
		}
		return ajaxDone(str);
		
	}
	/**
	 * 删除话题广告
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("topicad:delete")
	@RequestMapping("delAd")
	public ModelAndView delAd(String[] ids){	
		return ajaxDone(topicService.deleteTopics(ids));
	}
	/**
	 * 跳转到话题查看页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("topic:update")
	@RequestMapping("view")
	public String view(String id,ModelMap map){
		Topic topic = topicService.selectById(id);
		if(topic==null){
			return NODATA;
		}
		map.addAttribute("t",topic);
		
		return "system/topic/view";
	}
}
