package com.wing.socialcontact.sys.action;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IMessageBulkService;
import com.wing.socialcontact.service.wx.bean.MessageBulk;

/**
 *消息模板
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/messagebulk")
public class MessageBulkAction extends BaseAction{
	
	@Autowired
	private IMessageBulkService messageBulkService; 			
	
	
	/**
	 * 条件查询资讯分类
	 * 
	 * @return
	 */
	@RequiresPermissions("messageBulk:read")
	@RequestMapping("list")
	public String messageBulkload(){
		
		return "system/messageBulk/messageBulk_list";
	
	}
	@RequiresPermissions("messageBulk:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,MessageBulk messageBulk,String startTimef,String endTimef){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//获取Calendar实例
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		if(!StringUtils.isEmpty(startTimef)&&!StringUtils.isEmpty(endTimef))
	    	//把字符串转成日期类型
		try {
			startTime.setTime(df.parse(startTimef));
			endTime.setTime(df.parse(endTimef));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	    if (startTime.compareTo(endTime) > 0) {
	    	return  ajaxDoneTextError("查询开始时间不能大于结束时间！");
		}

		return ajaxJsonEscape(messageBulkService.selectMessageBulks(param, messageBulk, startTimef, endTimef));
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequiresPermissions("messageBulk:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		MessageBulk messageBulk  = new MessageBulk();
		
		map.put("dto", messageBulk);
		return "system/messageBulk/messageBulk_edit";
	
	}
	/**
	 * 添加
	 * @param messageBulk
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("messageBulk:add")
	@RequestMapping("add")
	public ModelAndView add(MessageBulk messageBulk,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		messageBulk.setCreateTime(new Date());
		messageBulk.setUpdateTime(new Date());
		return ajaxDone(messageBulkService.addMessageBulk(messageBulk));	
	}
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("messageBulk:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		MessageBulk messageBulk = messageBulkService.selectById(id);
		
		if(messageBulk==null){
			return NODATA;
		}
		map.addAttribute("dto",messageBulk);
		
		return "system/messageBulk/messageBulk_edit";
	}
	/**
	 * 修改
	 * @param messageBulk
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("messageBulk:update")
	@RequestMapping("update")
	public ModelAndView update(MessageBulk messageBulk,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		messageBulk.setUpdateTime(new Date());
		return ajaxDone(messageBulkService.updateMessageBulk(messageBulk));
		
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("messageBulk:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		
		return ajaxDone(messageBulkService.deleteMessageBulk(ids));
	
	}
	
	

	
	
}
