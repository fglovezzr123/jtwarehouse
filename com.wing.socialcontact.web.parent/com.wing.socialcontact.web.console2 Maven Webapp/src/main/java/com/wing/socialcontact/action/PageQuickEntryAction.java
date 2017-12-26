package com.wing.socialcontact.action;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IPageQuickEntryService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.PageQuickEntry;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.service.IUserService;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 聚合页面配置
 * 
 * @ClassName: PageConfigAction
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年6月29日 上午8:53:27
 */
@Controller
@RequestMapping("/pageConfig/pageQuickEntry")
public class PageQuickEntryAction extends BaseAction {

	@Autowired
	private IUserService userService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IPageQuickEntryService pageQuickEntryService;

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
	@RequiresPermissions("page:read")
	@RequestMapping("load")
	public String load(ModelMap map) {
		return "page/pageQuickEntry/query";
	}

	/**
	 * ajax分页查询
	 * 
	 * @param param
	 * @param activity
	 * @return
	 */
	@RequiresPermissions("page:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param, String pid, PageQuickEntry pageQuickEntry) {
		return ajaxJsonEscape(pageQuickEntryService.selectAllPageQuickEntry(param, pageQuickEntry));
	}

	/**
	 * 跳转到pageQuickEntry添加页面
	 * 
	 * @return
	 */
	@RequiresPermissions("page:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map, String pid) {
		map.addAttribute("pageId", pid);
		return "page/pageQuickEntry/add";
	}

	/**
	 * 添加page
	 * 
	 * @param page
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("page:add")
	@RequestMapping("add")
	public ModelAndView add(PageQuickEntry pageQuickEntry, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		return ajaxDone(pageQuickEntryService.addPageQuickEntry(pageQuickEntry));
	}

	/**
	 * 跳转到pageQuickEntry修改页面
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("page:update")
	@RequestMapping("updatePage")
	public String updatePage(String id, ModelMap map) {
		PageQuickEntry pageQuickEntry = pageQuickEntryService.selectByPrimaryKey(id);
		if (pageQuickEntry == null) {
			return NODATA;
		}
		map.addAttribute("b", pageQuickEntry);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "page/pageQuickEntry/update";
	}

	/**
	 * 修改pageQuickEntry
	 * 
	 * @param pageQuickEntry
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("page:update")
	@RequestMapping("update")
	public ModelAndView update(PageQuickEntry pageQuickEntry, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		return ajaxDone(pageQuickEntryService.updatePageQuickEntry(pageQuickEntry));

	}

	/**
	 * 删除pageQuickEntry
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("page:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids) {
		for (String id : ids) {
			pageQuickEntryService.deletePageQuickEntry(id);
		}
		return ajaxDone(true);
	}

}
