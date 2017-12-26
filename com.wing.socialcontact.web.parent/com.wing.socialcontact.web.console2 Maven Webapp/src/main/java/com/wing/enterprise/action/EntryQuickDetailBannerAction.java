package com.wing.enterprise.action;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.QuickDetailBanner;
import org.com.wing.enterprise.service.IEntryQuickDetailBannerService;
import org.com.wing.enterprise.service.IEntryQuickDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 聚合页幻灯片管理
 * @author sino
 *
 */
@Controller
@RequestMapping("/quickDetailBanner")
public class EntryQuickDetailBannerAction extends BaseAction {
	
	@Autowired
	private IEntryQuickDetailBannerService entryQuickDetailBannerService;
	@Autowired
	private IEntryQuickDoorService entryQuickDoorService;
	
	
	/**
	 * 聚合页幻灯片列表
	 */
	@RequiresPermissions("entryQuickDetailBanner:read")
	@RequestMapping("load")
	public String bannerload(ModelMap map){
	    List quicks = entryQuickDetailBannerService.selectAllQucikDoor();
	    map.addAttribute("quicks", quicks);
		return "qfy/detailBanner/load";
	}
	
	/**
	 * 聚合页幻灯片列表查询
	 * @param param
	 * @param entryQuickDetailBanner
	 * @return
	 */
	@RequiresPermissions("entryQuickDetailBanner:read")
	@RequestMapping("query")
	public ModelAndView quickDetailBannerQuery(PageParam param,QuickDetailBanner quickDetailBanner){
		return ajaxJsonEscape(entryQuickDetailBannerService.selectQuickDetailBanner(param, quickDetailBanner));
	}
	/**
	 * 聚合页幻灯片新增
	 */
	@RequiresPermissions("entryQuickDetailBanner:add")
	@RequestMapping("addPage")
	public String quickDetailBannerAddPage(ModelMap map){
	    List quicks = entryQuickDoorService.selectAllQuickDoor();
        map.addAttribute("quicks", quicks);
		return "qfy/detailBanner/add";
	}
	
	@RequiresPermissions("entryQuickDetailBanner:add")
	@RequestMapping("add")
	public ModelAndView quickDetailBannerAdd(QuickDetailBanner dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		dto.setCreateTime(new Date());
		dto.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(entryQuickDetailBannerService.addQuickDetailBanner(dto));
		
	}
	
	/**
	 * 聚合页幻灯片修改
	 */
	@RequiresPermissions("entryQuickDetailBanner:update")
	@RequestMapping("updatePage")
	public String quickDetailBannerUpdatePage(String id,ModelMap map){
	    List quicks = entryQuickDoorService.selectAllQuickDoor();
        map.addAttribute("quicks", quicks);
		QuickDetailBanner dto = entryQuickDetailBannerService.selectByPrimaryKey(id);
		map.addAttribute("dto", dto);
		return "qfy/detailBanner/update";
	}
	
	@RequiresPermissions("entryQuickDetailBanner:update")
	@RequestMapping("update")
	public ModelAndView quickDetailBannerUpdate(QuickDetailBanner dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(entryQuickDetailBannerService.updateQuickDetailBanner(dto));
		
	}
	
	/**
	 * 聚合页幻灯片删除
	 */
	@RequiresPermissions("entryQuickDetailBanner:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		return ajaxDone(entryQuickDetailBannerService.deleteQuickDetailBanner(ids));
	}
	
}
