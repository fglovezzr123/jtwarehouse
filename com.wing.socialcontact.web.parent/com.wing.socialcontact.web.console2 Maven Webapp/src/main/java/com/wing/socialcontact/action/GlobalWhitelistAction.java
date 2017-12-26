package com.wing.socialcontact.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IGlobalWhitelistService;
import com.wing.socialcontact.service.wx.bean.GlobalWhitelist;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.sys.action.BaseAction;

/**
 * 白名单管理
 * 
 * @author zhangzheng
 * 
 */
@Controller
@RequestMapping("/globalwhitelist")
public class GlobalWhitelistAction extends BaseAction {

	@Autowired
	private IGlobalWhitelistService globalWhitelistService;
	/**
	 * 系统白名单列表
	 */
	@RequiresPermissions("globalwhitelist:read")
	@RequestMapping("load")
	public String classload() {
		return "system/globalwhitelist/load";
	}

	/**
	 * 系统白名单表查询
	 * 
	 * @param param
	 * @param t
	 * @return
	 */
	@RequiresPermissions("globalwhitelist:read")
	@RequestMapping("query")
	public ModelAndView classquery(PageParam param, GlobalWhitelist t) {
		return ajaxJsonEscape(globalWhitelistService.selectAllGlobalWhitelist(param,t));
	}
	/**
	 * 系统白名单表查询
	 * 
	 * @param param
	 * @param t
	 * @return
	 */
	@RequiresPermissions("globalwhitelist:read")
	@RequestMapping("/user/query")
	public ModelAndView classuserquery(PageParam param, TjyUser t) {
		return ajaxJsonEscape(globalWhitelistService.selectAllTjyUser(param, t));
	}
	/**   
	 *  系统白名单新增
	 */
	@RequiresPermissions("globalwhitelist:add")
	@RequestMapping("addPage")
	public String classaddPage(ModelMap map) {
		return "system/globalwhitelist/add";
	}
	
	@RequiresPermissions("globalwhitelist:add")
	@RequestMapping("add")
	public ModelAndView classadd(String types,String ids) {
		if(StringUtils.isEmpty(types)){
			return ajaxDoneError(MsgConfig.MSG_KEY_FAIL);
		}
		if(StringUtils.isEmpty(ids)){
			return ajaxDone(MsgConfig.MSG_KEY_SUCCESS);
		}
		try {
			return ajaxDone(globalWhitelistService.insertGlobalWhitelists(types,ids));
		} catch (Exception e) {
			return ajaxDoneError(MsgConfig.MSG_KEY_FAIL);
		}

	}
	/**
	 *  系统白名单删除
	 */
	@RequiresPermissions("globalwhitelist:delete")
	@RequestMapping("del")
	public ModelAndView del(String ids) {
		if(StringUtils.isEmpty(ids)){
			return ajaxDone(MsgConfig.MSG_KEY_SUCCESS);
		}
		try {
			return ajaxDone(globalWhitelistService.deleteGlobalWhitelists(ids)+"");
		} catch (Exception e) {
			return ajaxDoneError(MsgConfig.MSG_KEY_FAIL);
		}
	}
}
