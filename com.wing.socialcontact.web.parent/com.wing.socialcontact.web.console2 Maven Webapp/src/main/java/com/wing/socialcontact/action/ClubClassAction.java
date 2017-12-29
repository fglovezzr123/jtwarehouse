package com.wing.socialcontact.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IClubClassService;
import com.wing.socialcontact.service.wx.api.IClubService;
import com.wing.socialcontact.service.wx.bean.ClubClass;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.ServletUtil;

@Controller
@RequestMapping("/clubclass")
public class ClubClassAction extends BaseAction {

	@Autowired
	private IClubService clubService; 			
	@Autowired
	private IClubClassService clubClassService; 
	
	/**
	 * 条件查询分类
	 * 
	 * @return
	 */
	@RequiresPermissions("clubclass:read")
	@RequestMapping("clubclassload")
	public String clubclassload(){
		
		return "system/club/clubclassload";
	
	}
	@RequiresPermissions("clubclass:read")
	@RequestMapping("classquery")
	public ModelAndView classquery(){
		
		return ajaxJsonEscape(clubClassService.selectAllclassMap(null));
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequiresPermissions("clubclass:add")
	@RequestMapping("addPage")
	public String classaddPage(){
		
		return "system/club/clubclassadd";
	
	}
	/**
	 * 添加分类
	 * @param clubclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("clubclass:add")
	@RequestMapping("add")
	public ModelAndView classadd(ClubClass clubclass,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		clubclass.setCreateTime(new Date());
		clubclass.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(clubClassService.addclubclass(clubclass));	
	}
	/**
	 * 跳转到分类修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("clubclass:update")
	@RequestMapping("updatePage")
	public String classupdatePage(String id,ModelMap map){
		ClubClass clubclass = clubClassService.selectById(id);
		if(clubclass==null){
			return NODATA;
		}
		map.addAttribute("c",clubclass);
		
		return "system/club/clubclassupdate";
	}
	/**
	 * 修改分类
	 * @param clubclass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("clubclass:update")
	@RequestMapping("update")
	public ModelAndView classupdate(ClubClass clubclass,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		clubclass.setUpdateTime(new Date());
		clubclass.setUpdateUserId(ServletUtil.getMember().getId());
		return ajaxDone(clubClassService.updateclubclass(clubclass));
		
	}
	
	
	/**
	 * 删除分类
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("clubclass:delete")
	@RequestMapping("classdel")
	public ModelAndView nclassdel(String id){	
		return ajaxDone(clubClassService.deleteclubclasses(id));
	}
	
	/**
	 * 查询所有分类，返回json格式数据
	 * @return
	 */
	@RequestMapping("load/all")
	public ModelAndView allclass(){
		
		
		return ajaxJsonEscape(clubClassService.selectAllClassMap());
		
	}
	
	/**
	 * 分类查询页面
	 * @return
	 */
	@RequestMapping("lookUpPage")
	public String lookUpPage(){
			return "system/club/lookup";
	}
	@RequestMapping("lookUp")
	public ModelAndView lookUp(){
		List list = new ArrayList();
		return ajaxJsonEscape(clubClassService.selectAllClassMap());
	}
	
	
	
	
}
