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
 * 
 * <p>Title:热门服务管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月9日 下午4:09:41
 */
@Controller
@RequestMapping("qfy/hotservice")
public class HotServiceAction extends BaseAction {

	@Autowired
	private IEntryServiceTagService entryServiceTagService;
	
	@Autowired
	private IUserService userService;
	@Autowired
    private OssConfig ossConfig;
	
	/**
	 * 热门服务列表
	 */
	@RequiresPermissions("hotservice:read")
	@RequestMapping("load")
	public String classload(){
		return "qfy/hotservice/tagload";
	}
	
	/**
	 * 热门服务列表查询
	 * @param param
	 * @param hotservice
	 * @return
	 */
	@RequiresPermissions("hotservice:read")
	@RequestMapping("query")
	public ModelAndView tagQuery(PageParam param,EntryServiceTag entryServiceTag){
	    
	    DataGrid dg = entryServiceTagService.selectHotByParam(param, entryServiceTag);
        List lst = dg.getRows();
        for (Object object : lst) {
            Map tagMap = (Map) object;
            tagMap.put("img_path", ossConfig.getOss_getUrl()+tagMap.get("img_path"));
        }
        
		return ajaxJsonEscape(dg);
	}
	/**
	 * 热门服务添加、修改企业
	 */
	@RequiresPermissions("hotservice:update")
	@RequestMapping("updatePage")
	public String tagUpdatePage(String id,ModelMap map){
		EntryServiceTag dto = entryServiceTagService.selectByPrimaryKey(id);
		map.addAttribute("dto", dto);
		return "qfy/hotservice/tagupdate";
	}
	
	@RequiresPermissions("hotservice:update")
	@RequestMapping("update")
	public ModelAndView tagUpdate(EntryServiceTag dto,Errors errors){
		EntryServiceTag entryServiceTag = entryServiceTagService.selectByPrimaryKey(dto.getId());
		entryServiceTag.setHotSort(dto.getHotSort());
		entryServiceTag.setHotEntryId(dto.getHotEntryId());
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(entryServiceTagService.updateEntryServiceTag(entryServiceTag));
		
	}
	
	/**
	 * 标签删除
	 */
	@RequiresPermissions("hotservice:delete")
	@RequestMapping("del")
	public ModelAndView del(String id){
		EntryServiceTag entryServiceTag = entryServiceTagService.selectByPrimaryKey(id);
		entryServiceTag.setHotSort(0);
		entryServiceTag.setHotEntryId(null);
		return ajaxDone(entryServiceTagService.updateEntryServiceTag(entryServiceTag));
	}
	
}
