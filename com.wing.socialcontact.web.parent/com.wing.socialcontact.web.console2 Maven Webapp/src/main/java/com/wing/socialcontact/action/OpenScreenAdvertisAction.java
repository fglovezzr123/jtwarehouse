package com.wing.socialcontact.action;

import java.util.Date;
import java.util.Map;
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
import com.wing.socialcontact.service.wx.api.IOpenScreenAdvertisService;
import com.wing.socialcontact.service.wx.bean.OpenScreenAdvertis;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 开屏广告配置
 * 
 * @ClassName: OpenScreenAdvertisAction
 * @Description: app端配置开屏广告
 * @author 刘涛
 */

@Controller
@RequestMapping("/advertis")
public class OpenScreenAdvertisAction extends BaseAction
{
	
	@Autowired
	private IOpenScreenAdvertisService openScreenAdvertisService;
	/**
	 * 列表
	 * 
	 * @Title: load
	 * @Description: 加载页面
	 * @param map
	 * @return: String
	 * @author 刘涛
	 */
	@RequiresPermissions("advertis:read")
	@RequestMapping("load")
	public String load(ModelMap map) {
		return "advertis/query";
	}
	
	
	/**
	 * ajax分页查询
	 * 
	 * @param param
	 * @param activity
	 * @return
	 */
	@RequiresPermissions("advertis:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param, OpenScreenAdvertis apenScreenAdvertis) {
		return ajaxJsonEscape(openScreenAdvertisService.selectAllAdvertis(param, apenScreenAdvertis));
	}
	
	/**
	 * 跳转到openScreenAdvertis添加页面
	 * 
	 * @return
	 */
	@RequiresPermissions("advertis:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map) {
		//List<UserEmpirical> userLevelList = userEmpiricalService.selectAllUserEmpirical();
		//map.addAttribute("userLevelList", userLevelList);
		return "advertis/add";
	}
	
	/**
	 * 添加openScreenAdvertis
	 * 
	 * @param openScreenAdvertis
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("advertis:add")
	@RequestMapping("add")
	public ModelAndView add(OpenScreenAdvertis openScreenAdvertis, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		openScreenAdvertis.setCreateTime(new Date());
		openScreenAdvertis.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(openScreenAdvertisService.addAdvertis(openScreenAdvertis));
	}
	
	/**
	 * 跳转到openScreenAdvertis修改页面
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("advertis:update")
	@RequestMapping("updatePage")
	public String updatePage(String id, ModelMap map) {
		OpenScreenAdvertis openScreenAdvertis = openScreenAdvertisService.selectByPrimaryKey(id);
		if (openScreenAdvertis == null) {
			return NODATA;
		}
		map.addAttribute("b", openScreenAdvertis);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		//List<UserEmpirical> userLevelList = userEmpiricalService.selectAllUserEmpirical();
		//map.addAttribute("userLevelList", userLevelList);
		return "advertis/update";
	}
	
	
	/**
	 * 修改openScreenAdvertis
	 * 
	 * @param openScreenAdvertis
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("advertis:update")
	@RequestMapping("update")
	public ModelAndView update(OpenScreenAdvertis openScreenAdvertis, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		return ajaxDone(openScreenAdvertisService.updateAdvertis(openScreenAdvertis));

	}
	
	/**
	 * 删除openScreenAdvertis
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("advertis:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids) {
		return ajaxDone(openScreenAdvertisService.deleteAdvertis(ids[0]));
	}
	
	
	/**
	 * 修改状态
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("advertis:add")
	@RequestMapping("updateStatus")
	@ResponseBody
	public Map<String, Object> updateStatus(String id) {
		OpenScreenAdvertis openScreenAdvertis = openScreenAdvertisService.selectByPrimaryKey(id);
		openScreenAdvertis.setStatus(openScreenAdvertis.getStatus() == 1 ? 0 : 1);
		String result = openScreenAdvertisService.updateAdvertis(openScreenAdvertis);
		if (result.equals(MsgConfig.MSG_KEY_SUCCESS)) {
			return super.getAjaxResult("0", "修改状态成功", null);
		} else {
			return super.getAjaxResult("999", "修改状态失败", null);
		}
	}
}
