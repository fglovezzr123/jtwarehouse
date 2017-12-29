package com.wing.socialcontact.action;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IDaKaService;
import com.wing.socialcontact.service.wx.api.IUserEmpiricalService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;

/**
 * 大咖管理
 * @author xuxinyuan
 *
 */
@Controller
@RequestMapping("/daka")
public class DakaAction extends BaseAction{
	
	@Autowired
	private IDaKaService daKaService;
	@Autowired
	private IListValuesService listValuesService; 
	@Autowired
	private IUserEmpiricalService userEmpiricalService; 
	/**
	 * 条件查询志同道合
	 * 
	 * @return
	 */
//	@RequiresPermissions("daka:read")
	@RequestMapping("ztdhload")
	public String ztdhload(ModelMap map){
		List<ListValues> values = listValuesService.selectListByType(12);
		map.addAttribute("jobs", values);
		List<ListValues> industrys = listValuesService.selectListByType(8001);
		map.addAttribute("industrys", industrys);
		List<UserEmpirical> UserEmpirical = userEmpiricalService.selectAllUserEmpirical();
		map.addAttribute("userEmpirical", UserEmpirical);
		return "daka/ztdhload";
	
	}
//	@RequiresPermissions("daka:read")
	@RequestMapping("ztdhquery")
	public ModelAndView ztdhquery(PageParam param,String isztdh, String nickname,String job,String industry,String level,String comname,String place){
		return ajaxJsonEscape(daKaService.queryZtdhUserListByparam(param, Integer.parseInt(isztdh), nickname, job, industry,level,comname,place));
	}
	
	/**
	 * 跳转添加页面
	 * @return
	 */
//	@RequiresPermissions("daka:add")
	@RequestMapping("ztdhaddPage")
	public String ztdhaddPage(ModelMap map){
		List<ListValues> values = listValuesService.selectListByType(12);
		map.addAttribute("jobs", values);
		List<ListValues> industrys = listValuesService.selectListByType(8001);
		map.addAttribute("industrys", industrys);
		return "daka/ztdhadd";
	
	}
	
//	@RequiresPermissions("daka:add")
	@RequestMapping("addztdh")
	public ModelAndView addztdh(String[] ids){	
		return ajaxDone(daKaService.addZtdh(ids));
		
	}
	
//	@RequiresPermissions("daka:delete")
	@RequestMapping("ztdhdel")
	public ModelAndView ztdhdel(String[] ids){	
		return ajaxDone(daKaService.deleteZtdhs(ids));
	}
	/**
	 * 条件查询话题
	 * 
	 * @return
	 */
	@RequiresPermissions("daka:read")
	@RequestMapping("load")
	public String load(ModelMap map){
		List<ListValues> values = listValuesService.selectListByType(12);
		map.addAttribute("jobs", values);
		List<ListValues> industrys = listValuesService.selectListByType(8001);
		map.addAttribute("industrys", industrys);
		return "daka/load";
		
	}
	@RequiresPermissions("daka:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,String isDk, String nickname,String job,String industry){
		return ajaxJsonEscape(daKaService.queryUserListByparam(param, Integer.parseInt(isDk), nickname, job, industry));
	}
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@RequiresPermissions("daka:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		List<ListValues> values = listValuesService.selectListByType(12);
		map.addAttribute("jobs", values);
		List<ListValues> industrys = listValuesService.selectListByType(8001);
		map.addAttribute("industrys", industrys);
		return "daka/add";
		
	}
	
	@RequiresPermissions("daka:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(daKaService.deleteDakas(ids));
	}
	
	@RequiresPermissions("daka:add")
	@RequestMapping("addDaka")
	public ModelAndView addDaka(String[] ids){	
		return ajaxDone(daKaService.addDaks(ids));
		
	}
	/**
	 * @return
	 */
	@RequestMapping("sortPage")
	public String sortPage(ModelMap map,String id){
		TjyUser user = daKaService.loadById(id);
		map.addAttribute("user", user);
		return "daka/updatesort";
	
	}
	/**
	 * @return
	 */
	@RequestMapping("ztdhsortPage")
	public String ztdhsortPage(ModelMap map,String id){
		TjyUser user = daKaService.loadById(id);
		map.addAttribute("user", user);
		return "daka/updateztdhsort";
		
	}
	
	@RequestMapping("updatesort")
	public ModelAndView updatesort(String id,int sort){	
		return ajaxDone(daKaService.updatesort(id,sort));
		
	}
	@RequestMapping("updateztdhsort")
	public ModelAndView updateztdhsort(String id,int ztdhsort){	
		return ajaxDone(daKaService.updateztdhsort(id,ztdhsort));
		
	}
	
}
