package com.wing.socialcontact.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.service.wx.api.IConfigService;
import com.wing.socialcontact.service.wx.bean.Config;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * config管理
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/tjyconfig")
public class TjyConfigAction extends BaseAction{
	
	@Autowired
	private IConfigService configService;
	/**
	 * 条件查询话题
	 * 
	 * @return
	 */
	//@RequiresPermissions("tjytjyconfig:read")
	@RequestMapping("load")
	public String load(ModelMap map){
		return "tjyconfig/tjyconfig_load";
	
	}
	
	/**
	 * 跳转到config添加页面
	 * @return
	 */
	//@RequiresPermissions("tjyconfig:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		Config config = configService.selectByType("1");
		map.addAttribute("config", config);
		return "tjyconfig/tjyconfig_load";
	
	}
	/**
	 * 添加config
	 * @param config
	 * @param errors
	 * @return
	 */
	//@RequiresPermissions("tjyconfig:add")
	@RequestMapping("add")
	public ModelAndView add(Config config,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		
		return ajaxDone(configService.addConfig(config));	
	}
	/**
	 * 跳转到config修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	//@RequiresPermissions("tjyconfig:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		Config config = configService.selectById(id);
		if(config==null){
			return NODATA;
		}
	
		return "config/config_update";
	}
	/**
	 * 修改config
	 * @param config
	 * @param errors
	 * @return
	 */
	//@RequiresPermissions("tjyconfig:update")
	@RequestMapping("update")
	public ModelAndView update(Config config,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
				
		Config config_temp = configService.selectById(config.getId());
		String config1 = "0";
		if(!StringUtils.isEmpty(config_temp.getConfig1())){
			config1 = config_temp.getConfig1();
		}
		if(!config1.equals(config.getConfig1())){
			//免打扰后要清redis
			redisCache.removeall("selPeerElite");
			redisCache.removeall("selcityElite");
		}
		
		return ajaxDone(configService.updateConfig(config));
		
	}
	/**
	 * 删除config
	 * @param ids
	 * @return
	 */
	//@RequiresPermissions("tjyconfig:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(configService.deleteConfig(ids));
	}
}
