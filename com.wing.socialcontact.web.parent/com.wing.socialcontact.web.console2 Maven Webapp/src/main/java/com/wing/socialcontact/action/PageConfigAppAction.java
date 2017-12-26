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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.commons.util.ApplicationPath;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IBannerService;
import com.wing.socialcontact.service.wx.api.IPageAggregateService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.Banner;
import com.wing.socialcontact.service.wx.bean.PageAggregate;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.sys.service.IUserService;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.ServletUtil;
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
@RequestMapping("/pageConfigApp")
public class PageConfigAppAction extends BaseAction {

	@Autowired
	private IUserService userService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IPageAggregateService pageAggregateService;

	@Autowired
	private IBannerService bannerService;

	@Autowired
	private IListValuesService listValuesService;

	@Autowired
	private RedisCache redisCache;

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
		String view_home_path = ApplicationPath.getParameter("view_home_path");
		map.addAttribute("pageUrl", view_home_path);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "pageApp/query";
	}

	/**
	 * 预览
	 * 
	 * @Title: view
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年6月29日 上午8:58:49
	 */
	@RequiresPermissions("page:read")
	@RequestMapping("view")
	public String view(String id, ModelMap map) {
		String view_home_path = ApplicationPath.getParameter("view_home_path");
		map.addAttribute("pageUrl", view_home_path + id);
		return "pageApp/view";
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
	public ModelAndView query(PageParam param, PageAggregate pageAggregate) {
		pageAggregate.setPageType(2);
		return ajaxJsonEscape(pageAggregateService.selectAllPageAggregate(param, pageAggregate));
	}

	/**
	 * 跳转到pageAggregate添加页面
	 * 
	 * @return
	 */
	@RequiresPermissions("page:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map) {
		List<ListValues> values = listValuesService.selectListByType(802);
		map.addAttribute("values", values);
		return "pageApp/add";
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
	public ModelAndView add(PageAggregate pageAggregate, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		pageAggregate.setCreateTime(new Date());
		pageAggregate.setCreateUserId(ServletUtil.getMember().getId());
		pageAggregate.setUpdateTime(new Date());
		pageAggregate.setUpdateUserId(ServletUtil.getMember().getId());
		return ajaxDone(pageAggregateService.addPageAggregate(pageAggregate));
	}

	/**
	 * 跳转到pageAggregate修改页面
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("page:update")
	@RequestMapping("updatePage")
	public String updatePage(String id, ModelMap map) {
		PageAggregate pageAggregate = pageAggregateService.selectByPrimaryKey(id);
		if (pageAggregate == null) {
			return NODATA;
		}
		String view_home_path = ApplicationPath.getParameter("view_home_path");
		pageAggregate.setPageUrl(view_home_path + pageAggregate.getId());
		map.addAttribute("b", pageAggregate);
		if (StringUtils.isNotEmpty(pageAggregate.getLbtId())) {
			Banner banner = bannerService.selectByPrimaryKey(pageAggregate.getLbtId());
			if (null != banner) {
				map.addAttribute("lbtName", banner.getTitle());
			}
		}
		List<ListValues> values = listValuesService.selectListByType(802);
		map.addAttribute("values", values);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "pageApp/update";
	}

	/**
	 * 修改pageAggregate
	 * 
	 * @param pageAggregate
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("page:update")
	@RequestMapping("update")
	public ModelAndView update(PageAggregate pageAggregate, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		pageAggregate.setUpdateTime(new Date());
		pageAggregate.setUpdateUserId(ServletUtil.getMember().getId());
		return ajaxDone(pageAggregateService.updatePageAggregate(pageAggregate));

	}

	/**
	 * 删除pageAggregate
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("page:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids) {
		for (String id : ids) {
			String catchkey = "DB:" + PageAggregate.class.getName() + "Page:" + id;
			redisCache.evict(catchkey);
			pageAggregateService.deletePageAggregate(id);
		}
		return ajaxDone(true);
	}

	/**
	 * 删除pageAggregate
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("page:read")
	@RequestMapping("clearCache")
	public ModelAndView clearCache(String[] ids) {
		for (String id : ids) {
			// 删除页面缓存
			String catchkey = "DB:" + PageAggregate.class.getName() + "Page:" + id;
			redisCache.evict(catchkey);
		}
		return ajaxDone(true);
	}

	/**
	 * 修改状态
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("page:add")
	@RequestMapping("updateStatus")
	@ResponseBody
	public Map<String, Object> updateStatus(String id) {
		PageAggregate pageAggregate = pageAggregateService.selectByPrimaryKey(id);
		pageAggregate.setStatus(pageAggregate.getStatus() == 1 ? 0 : 1);
		// 检测是否有相同名称的页面已被启用，如果有则不能启用
		if (pageAggregate.getStatus() == 1) {
			List<PageAggregate> pageList = pageAggregateService.selectPageByNameAndStatus(pageAggregate.getPageName(),
					1);
			if(null != pageList && !pageList.isEmpty()){
				return super.getAjaxResult("999", "本聚合页面已有相同名称的页面已启用，请先停用再启用", null);
			}
		}
		String result = pageAggregateService.updatePageAggregate(pageAggregate);
		if (result.equals(MsgConfig.MSG_KEY_SUCCESS)) {
			return super.getAjaxResult("0", "修改状态成功", null);
		} else {
			return super.getAjaxResult("999", "修改状态失败", null);
		}
	}

}
