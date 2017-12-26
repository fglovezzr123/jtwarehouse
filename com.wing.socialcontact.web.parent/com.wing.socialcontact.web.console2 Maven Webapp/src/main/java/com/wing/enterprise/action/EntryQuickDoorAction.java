package com.wing.enterprise.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.QuickDoor;
import org.com.wing.enterprise.service.IEntryQuickDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.ConfigUtil;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 快捷入口管理
 * @author sino
 *
 */
@Controller
@RequestMapping("/quickDoor")
public class EntryQuickDoorAction extends BaseAction {
	
	@Autowired
	private IEntryQuickDoorService entryQuickDoorService;
	@Autowired
    private OssConfig ossConfig;
	
	/**
	 * 快捷入口列表
	 */
	@RequiresPermissions("entryQuickDoor:read")
	@RequestMapping("load")
	public String classload(){
		return "qfy/quickDoor/load";
	}
	
	/**
	 * 快捷入口列表查询
	 * @param param
	 * @param entryQuickDoor
	 * @return
	 */
	@RequiresPermissions("entryQuickDoor:read")
	@RequestMapping("query")
	public ModelAndView quickDoorQuery(PageParam param,QuickDoor quickDoor){
	    DataGrid dg = entryQuickDoorService.selectQuickDoor(param, quickDoor);
        List lst = dg.getRows();
        for (Object object : lst) {
            Map tagMap = (Map) object;
            tagMap.put("quickImgPath", ossConfig.getOss_getUrl()+tagMap.get("quickImgPath"));
            tagMap.put("quicklink", ConfigUtil.DOMAIN+"/qfyfront/m/qfy/quickIndexPage.do?id="+tagMap.get("id"));
        }
		return ajaxJsonEscape(dg);
	}
	/**
	 * 快捷入口新增
	 */
	@RequiresPermissions("entryQuickDoor:add")
	@RequestMapping("addPage")
	public String quickDoorAddPage(ModelMap map){
		return "qfy/quickDoor/add";
	}
	
	@RequiresPermissions("entryQuickDoor:add")
	@RequestMapping("add")
	public ModelAndView quickDoorAdd(QuickDoor dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		dto.setCreateTime(new Date());
		dto.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(entryQuickDoorService.addQuickDoor(dto));
		
	}
	
	/**
	 * 快捷入口修改
	 */
	@RequiresPermissions("entryQuickDoor:update")
	@RequestMapping("updatePage")
	public String quickDoorUpdatePage(String id,ModelMap map){
		QuickDoor dto = entryQuickDoorService.selectByPrimaryKey(id);
		map.addAttribute("dto", dto);
//		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
//		String ossurl = ossConfig.getOss_getUrl();
//		map.addAttribute("ossurl", ossurl);
		return "qfy/quickDoor/update";
	}
	
	@RequiresPermissions("entryQuickDoor:update")
	@RequestMapping("update")
	public ModelAndView quickDoorUpdate(QuickDoor dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(entryQuickDoorService.updateQuickDoor(dto));
		
	}
	
	/**
	 * 快捷入口删除
	 */
	@RequiresPermissions("entryQuickDoor:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		return ajaxDone(entryQuickDoorService.deleteQuickDoor(ids));
	}
	
}
