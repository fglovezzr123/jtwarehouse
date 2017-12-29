package com.wing.enterprise.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.EntryServiceTag;
import org.com.wing.enterprise.service.IEntryServiceTagService;
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
import com.wing.socialcontact.sys.service.IUserService;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 标签管理
 * @author sino
 *
 */
@Controller
@RequestMapping("/entryServiceTag")
public class EntryServiceTagAction extends BaseAction {
	
	@Autowired
	private IEntryServiceTagService entryServiceTagService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private OssConfig ossConfig;
	
	/**
	 * 标签列表
	 */
	@RequiresPermissions("entryServiceTag:read")
	@RequestMapping("load")
	public String classload(){
		return "qfy/serviceTag/tagload";
	}
	
	/**
	 * 标签列表查询
	 * @param param
	 * @param entryServiceTag
	 * @return
	 */
	@RequiresPermissions("entryServiceTag:read")
	@RequestMapping("query")
	public ModelAndView tagQuery(PageParam param,EntryServiceTag entryServiceTag){
	    
	    DataGrid dg = entryServiceTagService.selectEntryServiceTag(param, entryServiceTag);
	    List lst = dg.getRows();
	    for (Object object : lst) {
            Map tagMap = (Map) object;
            tagMap.put("img_path", ossConfig.getOss_getUrl()+tagMap.get("img_path"));
        }
		return ajaxJsonEscape(dg);
	}
	/**
	 * 标签新增
	 */
	@RequiresPermissions("entryServiceTag:add")
	@RequestMapping("addPage")
	public String tagAddPage(ModelMap map){
		return "qfy/serviceTag/tagadd";
	}
	
	@RequiresPermissions("entryServiceTag:add")
	@RequestMapping("add")
	public ModelAndView tagAdd(EntryServiceTag dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		dto.setCreateTime(new Date());
		dto.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(entryServiceTagService.addEntryServiceTag(dto));
		
	}
	
	/**
	 * 标签修改
	 */
	@RequiresPermissions("entryServiceTag:update")
	@RequestMapping("updatePage")
	public String tagUpdatePage(String id,ModelMap map){
		EntryServiceTag dto = entryServiceTagService.selectByPrimaryKey(id);
		map.addAttribute("dto", dto);
//		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
//		String ossurl = ossConfig.getOss_getUrl();
//		map.addAttribute("ossurl", ossurl);
		return "qfy/serviceTag/tagupdate";
	}
	
	@RequiresPermissions("entryServiceTag:update")
	@RequestMapping("update")
	public ModelAndView tagUpdate(EntryServiceTag dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(entryServiceTagService.updateEntryServiceTag(dto));
		
	}
	
	/**
	 * 标签删除
	 */
	@RequiresPermissions("entryServiceTag:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		return ajaxDone(entryServiceTagService.deleteEntryServiceTag(ids));
	}
	
}
