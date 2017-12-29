package com.wing.enterprise.action;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.ShareConfig;
import org.com.wing.enterprise.service.IShareConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.QfyConstants;
/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月10日 下午2:35:18
 */
@Controller
@RequestMapping("qfy/shareconfig")
public class ShareConfigAction extends BaseAction {

	@Autowired
	private IShareConfigService shareConfigservice;
	
	
	/**
     * 分享管理
     */
    @RequiresPermissions("shareconfig:update")
    @RequestMapping("load")
    public String loadPage(ModelMap map){
    	ShareConfig dto = shareConfigservice.getById(QfyConstants.QFY_SHARE_CONFIG);
    	map.addAttribute("dto", dto);
        return "qfy/shareconfig/view";
    }
    /**
     * 分享管理
     */
    @RequiresPermissions("shareconfig:update")
    @RequestMapping("updatePage")
    public String updatePage(ModelMap map){
    	ShareConfig dto = shareConfigservice.getById(QfyConstants.QFY_SHARE_CONFIG);
    	map.addAttribute("dto", dto);
    	return "qfy/shareconfig/update";
    }
    
    @RequiresPermissions("shareconfig:update")
    @RequestMapping("update")
    public ModelAndView entryadd(ShareConfig dto,Errors errors){
        if(errors.hasErrors()) {  
            ModelAndView mav=getValidationMessage(errors);
            if(mav!=null)return mav;
        }
        return ajaxDone(shareConfigservice.updateShareConfig(dto));
        
    }
}
