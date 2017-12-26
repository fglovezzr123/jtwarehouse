package com.wing.enterprise.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.EntryServiceClass;
import org.com.wing.enterprise.service.IEntryPriseService;
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
 * 分类管理
 * @author sino
 *
 */
@Controller
@RequestMapping("/entryServiceClass")
public class EntryServiceClassAction extends BaseAction {
	
	@Autowired
	private IEntryServiceClassService entryServiceClassService;
	@Autowired
    private IEntryPriseService entryPriseService;
	
	/**
	 * 分类列表
	 */
	@RequiresPermissions("entryServiceClass:read")
	@RequestMapping("load")
	public String classload(){
		return "qfy/serviceClass/classload";
	}
	
	/**
	 * 分类列表查询
	 * @param param
	 * @param entryServiceClass
	 * @return
	 */
	@RequiresPermissions("entryServiceClass:read")
	@RequestMapping("query")
	public ModelAndView classquery(PageParam param,EntryServiceClass entryServiceClass){
		return ajaxJsonEscape(entryServiceClassService.selectEntryServiceClass(param, entryServiceClass));
	}
	/**
	 * 分类列表树查询
	 * @param param
	 * @param entryServiceClass
	 * @return
	 */
	@RequiresPermissions("entryServiceClass:read")
	@RequestMapping("load/all")
	public ModelAndView loadAllClass(){
	    return ajaxJsonEscape(entryServiceClassService.selectByParentKey(null,null,null));
	}
	
	/**
	 * 分类新增
	 */
	@RequiresPermissions("entryServiceClass:add")
	@RequestMapping("addPage")
	public String classaddPage(ModelMap map,String parentId){
		return "qfy/serviceClass/classadd";
	}
	
	@RequiresPermissions("entryServiceClass:add")
	@RequestMapping("add")
	public ModelAndView classadd(EntryServiceClass dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		dto.setCreateTime(new Date());
		dto.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(entryServiceClassService.addEntryServiceClass(dto));
		
	}
	
	/**
	 * 分类修改
	 */
	@RequiresPermissions("entryServiceClass:update")
	@RequestMapping("updatePage")
	public String classupdatePage(String id,ModelMap map){
		EntryServiceClass dto = entryServiceClassService.selectByPrimaryKey(id);
		map.addAttribute("dto", dto);
//		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
//		String ossurl = ossConfig.getOss_getUrl();
//		map.addAttribute("ossurl", ossurl);
		return "qfy/serviceClass/classupdate";
	}
	
	@RequiresPermissions("entryServiceClass:update")
	@RequestMapping("update")
	public ModelAndView classupdate(EntryServiceClass dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(entryServiceClassService.updateEntryServiceClass(dto));
		
	}
	
	/**
	 * 分类删除
	 */
	@RequiresPermissions("entryServiceClass:delete")
	@RequestMapping("del")
	public ModelAndView del(String id){
	    
	    List<Map> ecs = entryServiceClassService.selectByParentKey(id, null, null);
	    if(ecs != null && ecs.size() > 0){
	        return ajaxDoneTextError("该分类存在下级,请先删除下级分类");
	    }
	    
	    PageParam param = new PageParam();
        param.setRows(100);
        param.setPage(1);
        
	    DataGrid dg = entryPriseService.selEntryPrise(param,null, null, id, null,null,null,null);
	    if(dg.getRows() != null && dg.getRows().size() > 0){
	        return ajaxDoneTextError("该分类已关联企服,请先删除企服中的分类");
	    }
	    
		return ajaxDone(entryServiceClassService.deleteEntryServiceClass(id));
	}
	
}
