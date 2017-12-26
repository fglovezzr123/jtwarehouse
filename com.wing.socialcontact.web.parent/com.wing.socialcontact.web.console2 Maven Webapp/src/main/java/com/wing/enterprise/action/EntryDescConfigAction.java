package com.wing.enterprise.action;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.EntryDescConfig;
import org.com.wing.enterprise.service.IEntryDescConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.QfyConstants;

/**
 * 图片+链接等介绍统一配置
 * @author sino
 *
 */
@Controller
@RequestMapping("/entryDescConfig")
public class EntryDescConfigAction extends BaseAction {
	
	@Autowired
	private IEntryDescConfigService entryDescConfigService;
	
	/**
	 * 首页企服联盟介绍配置新增修改
	 */
	@RequiresPermissions("entryDescConfig:update")
	@RequestMapping("updateQfIndexPage")
	public String entryDescConfigUpdatePage(ModelMap map){
	    EntryDescConfig entryDescConfig = new EntryDescConfig();
	    entryDescConfig.setType(QfyConstants.QFY_INDEX_DESC_TYPE);
	    List list = entryDescConfigService.selectDescConfig(entryDescConfig);
		map.addAttribute("dto", list.get(0));
		return "qfy/qfIndescDesc/update";
	}
	/**
	 * 
	 * //TODO 更新首页企服联盟介绍配置
	 * @param dto
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("entryDescConfig:update")
	@RequestMapping("update")
	public ModelAndView entryDescConfigUpdate(EntryDescConfig dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(entryDescConfigService.updateDescConfig(dto));
		
	}
	/**
     * 启动页配置
     */
    @RequiresPermissions("entryDescConfig:update")
    @RequestMapping("updateAppStartPage")
    public String updateAppStartPage(ModelMap map){
        EntryDescConfig entryDescConfig = new EntryDescConfig();
        entryDescConfig.setType(QfyConstants.QFY_APP_START_TYPE);
        List list = entryDescConfigService.selectDescConfig(entryDescConfig);
        map.addAttribute("dto", list.get(0));
        return "qfy/appStart";
    }
    /**
     * 常见问题配置
     */
    @RequiresPermissions("entryDescConfig:update")
    @RequestMapping("comProblemPage")
    public String comProblemPage(ModelMap map){
        EntryDescConfig entryDescConfig = new EntryDescConfig();
        entryDescConfig.setType(QfyConstants.QFY_COM_PROBLEM_TYPE);
        List list = entryDescConfigService.selectDescConfig(entryDescConfig);
        map.addAttribute("dto", list.get(0));
        return "qfy/comProblem";
    }
    /**
     * 常见问题配置
     */
    @RequiresPermissions("entryDescConfig:update")
    @RequestMapping("qfyAboutPage")
    public String qfyAboutPage(ModelMap map){
        EntryDescConfig entryDescConfig = new EntryDescConfig();
        entryDescConfig.setType(QfyConstants.QFY_ABOUT_TYPE);
        List list = entryDescConfigService.selectDescConfig(entryDescConfig);
        map.addAttribute("dto", list.get(0));
        return "qfy/qfyAbout";
    }
}
