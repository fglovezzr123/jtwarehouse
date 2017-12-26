package com.wing.socialcontact.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IPageColumnService;
import com.wing.socialcontact.service.wx.api.IPageContentTypeService;
import com.wing.socialcontact.service.wx.api.IPageElementService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.PageColumn;
import com.wing.socialcontact.service.wx.bean.PageContentType;
import com.wing.socialcontact.service.wx.bean.PageElement;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;
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
@RequestMapping("/pageConfigApp/pageColumn")
public class PageColumnAppAction extends BaseAction {

	@Autowired
	private IUserService userService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IPageColumnService pageColumnService;

	@Autowired
	private IPageContentTypeService pageContentTypeService;

	@Autowired
	private IPageElementService pageElementService;
	
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
	@RequiresPermissions("page:read")
	@RequestMapping("load")
	public String load(ModelMap map) {
		return "pageApp/pageColumn/query";
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
	public ModelAndView query(PageParam param, String pid, PageColumn pageColumn) {
		return ajaxJsonEscape(pageColumnService.selectAllPageColumn(param, pageColumn));
	}

	/**
	 * 跳转到pageColumn添加页面
	 * 
	 * @return
	 */
	@RequiresPermissions("page:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map, String pid) {
		System.out.println(pid);
		map.addAttribute("pageId", pid);
		List<PageContentType> pageContentTypeList = pageContentTypeService.selectAllPageContentType();
		map.addAttribute("pageContentTypeList", pageContentTypeList);
		return "pageApp/pageColumn/add";
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
	public ModelAndView add(PageColumn pageColumn, String elementStr, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		try {
			if (StringUtils.hasLength(elementStr)) {
				JSONArray ja = JSON.parseArray(elementStr);
				List<PageElement> elementList = new ArrayList<PageElement>();
				for (int i = 0; i < ja.size(); i++) {
					JSONObject jo = ja.getJSONObject(i);
					elementList.add(JSON.toJavaObject(jo, PageElement.class));
				}
				pageColumn.setElementList(elementList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxDone(pageColumnService.addPageColumn(pageColumn));
	}

	/**
	 * 跳转到pageColumn修改页面
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("page:update")
	@RequestMapping("updatePage")
	public String updatePage(String id, ModelMap map) {
		PageColumn pageColumn = pageColumnService.selectByPrimaryKey(id);
		if (pageColumn == null) {
			return NODATA;
		}
		if (null != pageColumn) {
			List<PageElement> elementList = pageElementService.selectAllPageElementByColumnId(pageColumn.getId());
			pageColumn.setElementList(elementList);
		}
		List<PageContentType> pageContentTypeList = pageContentTypeService.selectAllPageContentType();
		map.addAttribute("pageContentTypeList", pageContentTypeList);
		map.addAttribute("b", pageColumn);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "pageApp/pageColumn/update";
	}

	/**
	 * 修改pageColumn
	 * 
	 * @param pageColumn
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("page:update")
	@RequestMapping("update")
	public ModelAndView update(PageColumn pageColumn, String elementStr, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		try {
			if (StringUtils.hasLength(elementStr)) {
				JSONArray ja = JSON.parseArray(elementStr);
				List<PageElement> elementList = new ArrayList<PageElement>();
				for (int i = 0; i < ja.size(); i++) {
					JSONObject jo = ja.getJSONObject(i);
					elementList.add(JSON.toJavaObject(jo, PageElement.class));
				}
				pageColumn.setElementList(elementList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxDone(pageColumnService.updatePageColumn(pageColumn));

	}

	/**
	 * 删除pageColumn
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("page:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids) {
		for (String id : ids) {
			pageColumnService.deletePageColumn(id);
		}
		return ajaxDone(true);
	}

	/**
	 * 删除pageElement
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("page:delete")
	@RequestMapping("delElement")
	@ResponseBody
	public Map<String, Object> delElement(String id) {
		boolean bo = pageElementService.deletePageElement(id);
		if (bo) {
			return super.getAjaxResult("0", "删除成功", null);
		} else {
			return super.getAjaxResult("999", "删除失败", null);
		}
	}
}
