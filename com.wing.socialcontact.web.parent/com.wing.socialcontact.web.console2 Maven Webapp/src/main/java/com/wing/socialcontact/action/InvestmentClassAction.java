package com.wing.socialcontact.action;

import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IInvestmentClassService;
import com.wing.socialcontact.service.wx.api.IInvestmentService;
import com.wing.socialcontact.service.wx.bean.InvestmentClass;
import com.wing.socialcontact.util.ServletUtil;
@Controller
@RequestMapping("/investmentClass")
public class InvestmentClassAction extends BaseAction {

	@Autowired
	private IInvestmentService investmentService;
	@Autowired
	private IInvestmentClassService investmentClassService;
	
	@RequiresPermissions("investmentClass:read")
	@RequestMapping("load")
	public String load(){
		return "system/investmentClass/load";
	}
	
	@RequiresPermissions("investmentClass:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,InvestmentClass investmentClassc){
		return ajaxJsonEscape(investmentClassService.selectinvestmentClass(param, investmentClassc));
	}
	
	/**
	 * 投资分类新增
	 */
	@RequiresPermissions("investmentClass:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		return "system/investmentClass/add";
	}
	
	@RequiresPermissions("investmentClass:add")
	@RequestMapping("add")
	public ModelAndView add( InvestmentClass dto,Errors errors){
		
		dto.setCreateTime(new Date());
		dto.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(investmentClassService.addinvestmentClass(dto));
	
	}
	
	/**
	 * 投资分类修改
	 */
	@RequiresPermissions("investmentClass:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		InvestmentClass dto = investmentClassService.getinvestmentClassByid(id);
		map.addAttribute("dto", dto);
		return "system/investmentClass/update";
	}
	
	@RequiresPermissions("investmentClass:update")
	@RequestMapping("update")
	public ModelAndView update( InvestmentClass dto,Errors errors){
		dto.setUpdateTime(new Date());
		dto.setUpdateUserId(ServletUtil.getMember().getId());
		return ajaxDone(investmentClassService.updateinvestmentClass(dto));
		
	}
	
	/**
	 * 投资分类详情
	 */
	@RequiresPermissions("investmentClass:read")
	@RequestMapping("viewPage")
	public String viewPage(String id,ModelMap map){
		InvestmentClass dto = investmentClassService.getinvestmentClassByid(id);
		map.addAttribute("dto", dto);
		return "system/investmentClass/view";
	}
	
	/**
	 * 投资分类删除
	 */
	@RequiresPermissions("investmentClass:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		
		return ajaxDone(investmentClassService.deleteinvestmentClass(ids));
	}
}
