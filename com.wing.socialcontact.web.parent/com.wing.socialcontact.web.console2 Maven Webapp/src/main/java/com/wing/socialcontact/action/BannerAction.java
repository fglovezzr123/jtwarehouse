package com.wing.socialcontact.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IBannerService;
import com.wing.socialcontact.service.wx.api.IUserEmpiricalService;
import com.wing.socialcontact.service.wx.bean.Banner;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * banner管理
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/banner")
public class BannerAction extends BaseAction{
	
	@Autowired
	private IBannerService bannerService;
	@Autowired
	private IListValuesService listValuesService; 
	@Autowired
	private IUserEmpiricalService userEmpiricalService;
	/**
	 * 条件查询话题
	 * 
	 * @return
	 */
	@RequiresPermissions("banner:read")
	@RequestMapping("load")
	public String load(ModelMap map){
		map.addAttribute("type", 802);
		return "banner/load";
	
	}
	@RequiresPermissions("banner:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,Banner banner){
		return ajaxJsonEscape(bannerService.selectAllBanner(param, banner));
	}
	/**
	 * 跳转到banner添加页面
	 * @return
	 */
	@RequiresPermissions("banner:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		List<ListValues> values = listValuesService.selectListByType(802);
		map.addAttribute("values", values);
		List<UserEmpirical> userLevelList = userEmpiricalService.selectAllUserEmpirical();
		map.addAttribute("userLevelList", userLevelList);
		return "banner/add";
	
	}
	/**
	 * 添加banner
	 * @param banner
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("banner:add")
	@RequestMapping("add")
	public ModelAndView add(Banner banner,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		banner.setCreateTime(new Date());
		banner.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(bannerService.addBanner(banner));	
	}
	/**
	 * 跳转到banner修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("banner:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		Banner banner = bannerService.selectById(id);
		if(banner==null){
			return NODATA;
		}
		map.addAttribute("b",banner);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		List<ListValues> values = listValuesService.selectListByType(802);
		map.addAttribute("values", values);
		List<UserEmpirical> userLevelList = userEmpiricalService.selectAllUserEmpirical();
		map.addAttribute("userLevelList", userLevelList);
		return "banner/update";
	}
	/**
	 * 修改banner
	 * @param banner
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("banner:update")
	@RequestMapping("update")
	public ModelAndView update(Banner banner,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(bannerService.updateBanner(banner));
		
	}
	/**
	 * 删除banner
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("banner:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(bannerService.deleteBanner(ids));
	}
	
	/**
	 * 跳转到banner
	 * @return
	 */
	@RequiresPermissions("banner:add")
	@RequestMapping("lookUp")
	public String lookUp(ModelMap map){
		return "banner/lookUp";
	}
	
	
	
	/**
	 * 查询guide
	 * 
	 * @return
	 */
	@RequiresPermissions("banner:read")
	@RequestMapping("guide/load")
	public String guide(ModelMap map){
		map.addAttribute("type", 802);
		return "guide/load";
	
	}
	@RequiresPermissions("banner:read")
	@RequestMapping("guide/query")
	public ModelAndView guidequery(PageParam param,Banner banner){
		banner.setIsguide(1);
		return ajaxJsonEscape(bannerService.selectAllBanner(param, banner));
	}
	/**
	 * 跳转到banner添加页面
	 * @return
	 */
	@RequiresPermissions("banner:add")
	@RequestMapping("guide/addPage")
	public String guideaddPage(ModelMap map){
		return "guide/add";
	
	}
	
	/**
	 * 跳转到banner修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("banner:update")
	@RequestMapping("guide/updatePage")
	public String guideupdatePage(String id,ModelMap map){
		Banner banner = bannerService.selectById(id);
		if(banner==null){
			return NODATA;
		}
		map.addAttribute("b",banner);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "guide/update";
	}
	/**
	 * 跳转到banner修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("banner:update")
	@RequestMapping("guide/viewPage")
	public String viewPage(String id,ModelMap map){
		Banner banner = bannerService.selectById(id);
		if(banner==null){
			return NODATA;
		}
		map.addAttribute("b",banner);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "guide/view";
	}
	
}
