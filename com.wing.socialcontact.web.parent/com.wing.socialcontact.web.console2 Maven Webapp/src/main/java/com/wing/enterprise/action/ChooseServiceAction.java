package com.wing.enterprise.action;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.EntryPrise;
import org.com.wing.enterprise.service.IEntryPriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.action.BaseAction;
/**
 * 
 * <p>Title:精选企服管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月9日 下午7:56:29
 */
@Controller
@RequestMapping("qfy/chooseservice")
public class ChooseServiceAction extends BaseAction {

	@Autowired
	private IEntryPriseService entryPriseService;
	
	/**
     * 企服列表
     */
    @RequiresPermissions("chooseservice:read")
    @RequestMapping("load")
    public String entryload(){
        return "qfy/chooseservice/load";
    }
    
    /**
     * 企服列表查询
     * @param param
     * @param EntryPrise
     * @return
     */
    @RequiresPermissions("chooseservice:read")
    @RequestMapping("query")
    public ModelAndView entryquery(PageParam param,EntryPrise entryPrise,Integer type){
    	entryPrise.setIsGood(type);
        return ajaxJsonEscape(entryPriseService.selEntryPrises(param, entryPrise,null));
    }
    
    /**
     * 企服修改
     */
    @RequiresPermissions("entryPrise:update")
    @RequestMapping("updatePage")
    public String entryupdatePage(){
        return "qfy/chooseservice/update";
    }
    
    /**
     * 企服更新
     */
    @RequiresPermissions("chooseservice:update")
    @RequestMapping("update")
    public ModelAndView update(String id,Integer type){
    	EntryPrise dto = entryPriseService.getEntryPriseByid(id);
    	dto.setIsGood(type);
        return ajaxDone(entryPriseService.updateEntryPriseByDto(dto));
    }
}
