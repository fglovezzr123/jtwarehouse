package com.wing.socialcontact.action;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IRefundInstructionService;
import com.wing.socialcontact.service.wx.bean.RefundInstruction;
import com.wing.socialcontact.sys.action.BaseAction;
@Controller
@RequestMapping("refundinstruction/")
public class RefundInstructionAction extends BaseAction {

	
	@Autowired
	private IRefundInstructionService refundInstructionService;
	
	
	/**
	 * 列表
	 */
	@RequiresPermissions("refundinstruction:read")
	@RequestMapping("load")
	public String load(){
		return "system/refundinstruction/load";
	}
	
	/**
	 * 查询
	 * @param param
	 * @param refundinstruction
	 * @return
	 */
	@RequiresPermissions("refundinstruction:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param){
		return ajaxJsonEscape(refundInstructionService.selectrefundinstruction(param));
	}
	
	/**
	 * 新增
	 */
	@RequiresPermissions("refundinstruction:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		
		return "system/refundinstruction/add";
	}
	
	@RequiresPermissions("refundinstruction:add")
	@RequestMapping("add")
	public ModelAndView add(RefundInstruction dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(refundInstructionService.addrefundinstruction(dto));
		
	}
	
	/**
	 * 修改
	 */
	@RequiresPermissions("refundinstruction:update")
	@RequestMapping("updatePage")
	public String classupdatePage(Integer id,ModelMap map){
		RefundInstruction dto = refundInstructionService.getrefundinstructionByid(id);
		map.addAttribute("dto", dto);
		return "system/refundinstruction/update";
	}
	
	@RequiresPermissions("refundinstruction:update")
	@RequestMapping("update")
	public ModelAndView classupdate( RefundInstruction dto,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(refundInstructionService.updaterefundinstruction(dto));
		
	}
	
	/**
	 * 敏感词编辑
	 */
	@RequestMapping("updateMgc")
	public String updateMgc(ModelMap map){
		RefundInstruction dto = refundInstructionService.getrefundinstructionByid(3);
		map.addAttribute("dto", dto);
		return "system/refundinstruction/updateMgc";
	}
	/**
	 * 关注通知编辑
	 */
	@RequestMapping("updateGztz")
	public String updateGztz(ModelMap map){
		RefundInstruction dto = refundInstructionService.getrefundinstructionByid(4);
		map.addAttribute("dto", dto);
		return "system/refundinstruction/updateGztz";
	}
	
}
