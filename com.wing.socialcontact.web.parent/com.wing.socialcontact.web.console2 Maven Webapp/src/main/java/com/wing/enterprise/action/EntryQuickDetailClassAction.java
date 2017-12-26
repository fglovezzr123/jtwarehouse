package com.wing.enterprise.action;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.QuickDetailClass;
import org.com.wing.enterprise.service.IEntryQuickDetailClassService;
import org.com.wing.enterprise.service.IEntryQuickDoorService;
import org.com.wing.enterprise.service.IEntryServiceClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 聚合页分类管理
 * @author sino
 *
 */
@Controller
@RequestMapping("/quickDetailClass")
public class EntryQuickDetailClassAction extends BaseAction {
	
	@Autowired
	private IEntryQuickDetailClassService entryQuickDetailClassService;
	@Autowired
	private IEntryQuickDoorService entryQuickDoorService;
	@Autowired
    private IEntryServiceClassService entryServiceClassService;
	
	/**
	 * 聚合页分类列表
	 */
	@RequiresPermissions("entryQuickDetailClass:read")
	@RequestMapping("load")
	public String classload(ModelMap map){
	    List quicks = entryQuickDetailClassService.selectAllQucikDoor();
        map.addAttribute("quicks", quicks);
        List classes = entryQuickDetailClassService.selectAllClasses();
        map.addAttribute("classes", classes);
		return "qfy/detailClass/load";
	}
	
	/**
	 * 聚合页分类列表查询
	 * @param param
	 * @param entryQuickDetailClass
	 * @return
	 */
	@RequiresPermissions("entryQuickDetailClass:read")
	@RequestMapping("query")
	public ModelAndView quickDetailClassQuery(PageParam param,QuickDetailClass quickDetailClass){
		return ajaxJsonEscape(entryQuickDetailClassService.selectQuickDetailClass(param, quickDetailClass));
	}
	/**
	 * 聚合页分类新增
	 */
	@RequiresPermissions("entryQuickDetailClass:add")
	@RequestMapping("addPage")
	public String quickDetailClassAddPage(ModelMap map){
	    List quicks = entryQuickDoorService.selectAllQuickDoor();
        map.addAttribute("quicks", quicks);
        List classes = entryServiceClassService.selectSecond();
        map.addAttribute("classes", classes);
		return "qfy/detailClass/add";
	}
	
	@RequiresPermissions("entryQuickDetailClass:add")
	@RequestMapping("add")
	public ModelAndView quickDetailClassAdd(QuickDetailClass dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		List list = entryQuickDetailClassService.isExist(dto);
		if(list != null && list.size() > 0){
		    return ajaxDoneTextError("存在相同的适用位置及分类，请修改！");
		}
		dto.setCreateTime(new Date());
		dto.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(entryQuickDetailClassService.addQuickDetailClass(dto));
		
	}
	
	/**
	 * 聚合页分类修改
	 */
	@RequiresPermissions("entryQuickDetailClass:update")
	@RequestMapping("updatePage")
	public String quickDetailClassUpdatePage(String id,ModelMap map){
		QuickDetailClass dto = entryQuickDetailClassService.selectByPrimaryKey(id);
		map.addAttribute("dto", dto);
		List quicks = entryQuickDoorService.selectAllQuickDoor();
        map.addAttribute("quicks", quicks);
        List classes = entryServiceClassService.selectSecond();
        map.addAttribute("classes", classes);
		return "qfy/detailClass/update";
	}
	
	@RequiresPermissions("entryQuickDetailClass:update")
	@RequestMapping("update")
	public ModelAndView quickDetailClassUpdate(QuickDetailClass dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		List list = entryQuickDetailClassService.isExist(dto);
        if(list != null && list.size() > 0){
            return ajaxDoneTextError("存在相同的适用位置及分类，请修改！");
            //return ajaxDoneError("存在相同的适用位置及分类，请修改！");
        }
		return ajaxDone(entryQuickDetailClassService.updateQuickDetailClass(dto));
		
	}
	
	/**
	 * 聚合页分类删除
	 */
	@RequiresPermissions("entryQuickDetailClass:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		return ajaxDone(entryQuickDetailClassService.deleteQuickDetailClass(ids));
	}
	
}
