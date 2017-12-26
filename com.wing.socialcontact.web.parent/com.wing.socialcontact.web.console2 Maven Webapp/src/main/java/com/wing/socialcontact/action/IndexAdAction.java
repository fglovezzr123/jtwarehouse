package com.wing.socialcontact.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IIndexAdService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserEmpiricalService;
import com.wing.socialcontact.service.wx.bean.IndexAd;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.service.IUserService;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 首页弹出框配置
 * 
 * @ClassName: IndexAdAction
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年7月7日 下午3:56:05
 */
@Controller
@RequestMapping("/indexAd")
public class IndexAdAction extends BaseAction {

	@Autowired
	private IUserService userService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IIndexAdService indexAdService;

	@Autowired
	private IUserEmpiricalService userEmpiricalService;

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
	@RequiresPermissions("indexAd:read")
	@RequestMapping("load")
	public String load(ModelMap map) {
		return "indexAd/query";
	}

	/**
	 * ajax分页查询
	 * 
	 * @param param
	 * @param activity
	 * @return
	 */
	@RequiresPermissions("indexAd:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param, IndexAd indexAd) {
		return ajaxJsonEscape(indexAdService.selectAllIndexAd(param, indexAd));
	}

	/**
	 * 跳转到indexAd添加页面
	 * 
	 * @return
	 */
	@RequiresPermissions("indexAd:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map) {
		List<UserEmpirical> userLevelList = userEmpiricalService.selectAllUserEmpirical();
		map.addAttribute("userLevelList", userLevelList);
		return "indexAd/add";
	}

	/**
	 * 添加indexAd
	 * 
	 * @param indexAd
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("indexAd:add")
	@RequestMapping("add")
	public ModelAndView add(IndexAd indexAd, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		indexAd.setCreateTime(new Date());
		indexAd.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(indexAdService.addIndexAd(indexAd));
	}

	/**
	 * 跳转到indexAd修改页面
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("indexAd:update")
	@RequestMapping("updatePage")
	public String updatePage(String id, ModelMap map) {
		IndexAd indexAd = indexAdService.selectByPrimaryKey(id);
		if (indexAd == null) {
			return NODATA;
		}
		map.addAttribute("b", indexAd);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		List<UserEmpirical> userLevelList = userEmpiricalService.selectAllUserEmpirical();
		map.addAttribute("userLevelList", userLevelList);
		return "indexAd/update";
	}

	/**
	 * 修改indexAd
	 * 
	 * @param indexAd
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("indexAd:update")
	@RequestMapping("update")
	public ModelAndView update(IndexAd indexAd, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		return ajaxDone(indexAdService.updateIndexAd(indexAd));

	}

	/**
	 * 删除indexAd
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("indexAd:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids) {
		return ajaxDone(indexAdService.deleteIndexAd(ids[0]));
	}

	/**
	 * 修改状态
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("indexAd:add")
	@RequestMapping("updateStatus")
	@ResponseBody
	public Map<String, Object> updateStatus(String id) {
		IndexAd indexAd = indexAdService.selectByPrimaryKey(id);
		indexAd.setStatus(indexAd.getStatus() == 1 ? 0 : 1);
		String result = indexAdService.updateIndexAd(indexAd);
		if (result.equals(MsgConfig.MSG_KEY_SUCCESS)) {
			return super.getAjaxResult("0", "修改状态成功", null);
		} else {
			return super.getAjaxResult("999", "修改状态失败", null);
		}
	}

}
