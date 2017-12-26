package com.wing.socialcontact.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.ISysBlacklistService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.SysBlacklist;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 系统黑名单
 * 
 * @ClassName: SysBlacklistAction
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年8月8日 上午10:10:30
 */
@Controller
@RequestMapping("/blacklist")
public class SysBlacklistAction extends BaseAction {

	@Autowired
	private ISysBlacklistService sysBlacklistService;

	@Autowired
	private ITjyUserService tjyUserService;
	
	@Autowired
	private IListValuesService listValuesService;

	/**
	 * 列表
	 * 
	 * @Title: load
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年6月29日 上午8:58:49
	 */
	@RequiresPermissions("blacklist:read")
	@RequestMapping("load")
	public String load(ModelMap map) {
		return "blacklist/query";
	}

	/**
	 * ajax分页查询
	 * 
	 * @param param
	 * @param activity
	 * @return
	 */
	@RequiresPermissions("blacklist:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param, SysBlacklist blacklist) {
		return ajaxJsonEscape(sysBlacklistService.selectAllSysBlacklist(param, blacklist));
	}

	/**
	 * 跳转到blacklist添加页面
	 * 
	 * @return
	 */
	@RequiresPermissions("blacklist:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map) {
		return "blacklist/add";
	}

	/**
	 * 添加blacklist
	 * 
	 * @param blacklist
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("blacklist:add")
	@RequestMapping("add")
	public ModelAndView add(SysBlacklist blacklist, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		blacklist.setManagerTime(new Date());
		blacklist.setManagerUser(ServletUtil.getMember().getId());
		return ajaxDone(sysBlacklistService.addSysBlacklist(blacklist));
	}

	/**
	 * 跳转到blacklist修改页面
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("blacklist:update")
	@RequestMapping("updatePage")
	public String updatePage(String id, ModelMap map) {
		SysBlacklist blacklist = sysBlacklistService.selectByPrimaryKey(id);
		if (blacklist == null) {
			return NODATA;
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(blacklist.getUserId());
		blacklist.setTjyUser(tjyUser);
		map.addAttribute("b", blacklist);
		return "blacklist/update";
	}

	/**
	 * 修改blacklist
	 * 
	 * @param blacklist
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("blacklist:update")
	@RequestMapping("update")
	public ModelAndView update(SysBlacklist blacklist, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		blacklist.setLastTime(new Date());
		blacklist.setLastUser(ServletUtil.getMember().getId());
		return ajaxDone(sysBlacklistService.updateSysBlacklist(blacklist));

	}

	/**
	 * 删除blacklist
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("blacklist:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids) {
		return ajaxDone(sysBlacklistService.deleteSysBlacklist(ids));
	}

	
	/**
	 * 黑名单页面配置
	 * 
	 * @Title: pageconfig
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年6月29日 上午8:58:49
	 */
	@RequiresPermissions("blacklist:pageconfig")
	@RequestMapping("pageconfig")
	public String pageconfig(ModelMap map) {
		List<Map<String, Object>> listValues = listValuesService.selectListByType(999);
		if (null != listValues && !listValues.isEmpty()) {
			map.put("yqConfig", listValues.get(0));
		}
		return "config/blacklist_manager";
	}

	/**
	 * 黑名单页面配置保存
	 * 
	 * @param manager
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("blacklist:pageconfig")
	@RequestMapping("manager")
	public ModelAndView manager(ListValues listValues, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		String result = "";
		if (StringUtils.isEmpty(listValues.getId())) {
			listValues.setId(null);
			listValues.setListType(999);
			listValues.setSortno(1);
			listValues.setDeleted(0);
			listValues.setListValue("黑名单页面配置");
			result = listValuesService.addListValues(listValues);
		} else {
			result = listValuesService.updateListValues(listValues);
		}
		return ajaxDone(result);
	}
}
