package com.wing.enterprise.action;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.QfyConfig;
import org.com.wing.enterprise.service.IQfyConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.sys.action.BaseAction;
/**
 * 企服云-通讯录开关
 * 
 * 
 * @ClassName: QfyConfigAction
 * @Description: TODO
 * @author: sino
 * @date:2017年6月13日
 */
@Controller
@RequestMapping("qfyConfig")
public class QfyConfigAction extends BaseAction {

	@Autowired
	private IQfyConfigService qfyConfigService;
	

 	 //修改页面
 	 @RequiresPermissions("qfyconfig:update")
     @RequestMapping("updatePage")
     public String updatePage(String id,ModelMap map){
 		QfyConfig qc = qfyConfigService.selConfig("1");
 		if(qc==null){
 			return NODATA;
 		}
 		map.addAttribute("qc", qc);
     	return "qfy/qfyConfig";
     }

 	 //修改保存
 	@RequiresPermissions("qfyconfig:update")
    @RequestMapping("update")
    public ModelAndView update(QfyConfig dto){
    	return ajaxDone(qfyConfigService.updateDescConfig(dto));
    }
}
