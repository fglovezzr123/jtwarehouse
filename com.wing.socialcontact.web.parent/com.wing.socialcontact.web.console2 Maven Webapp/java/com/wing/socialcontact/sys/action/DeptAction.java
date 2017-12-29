/**  
 * @Project: tjy
 * @Title: DeptAction.java
 * @Package com.oa.manager.system.action
 * @date 2016-4-2 下午4:11:32
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.action;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.cache.MyCache;
import com.wing.socialcontact.sys.bean.SyDept;
import com.wing.socialcontact.sys.model.PageParam;
import com.wing.socialcontact.sys.service.IDeptService;

/**
 * 
 * 类名：DeptAction
 * 功能：系统管理--组织机构--部门管理
 * 详细：部门的增删改查     
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-2 下午4:11:32
 *
 */
@Controller
@RequestMapping("/dept")
public class DeptAction extends BaseAction{
	
	@Autowired
	private IDeptService deptService; 
	
	/**
	 * 跳转到部门加载页面,查询出所有部门
	 * @return
	 */
	@RequiresPermissions("dept:read")
	@RequestMapping("load")
	public String load(ModelMap map){
		
		
		return "system/organize/dept/load";
	
	}
	/**
	 * 查询所有部门，返回json格式数据
	 * @return
	 */
	@RequestMapping("load/all")
	public ModelAndView alldept(){
		
		
		return ajaxJsonEscape(deptService.selectAllDeptsMap());
		
	}
	
	/**
	 * 跳转到部门添加页面
	 * @return
	 */
	@RequestMapping("addPage")
	public String addPage(){
		
		return "system/organize/dept/add";
	
	}
	/**
	 * 部门查询，查找带回
	 * @param param
	 * @param dept
	 * @param type 1:单选，2：多选，3：上级部门查询
	 * @param map
	 * @return
	 */
	@RequestMapping("lookUpPage")
	public String lookUpPage(Integer type){
	
		if(type!=null){
			if(type==2){
				return "system/organize/dept/lookup_more";
			}else{
				return "system/organize/dept/lookup";
			}
		}else{
			
			return "system/organize/dept/lookup";
		}
	}
	/**
	 * 部门查询，查找带回
	 * @param param
	 * @param dept
	 * @param type 1:单选，2：多选，3：上级部门查询
	 * @param map
	 * @return
	 */
	@RequestMapping("lookUp")
	public ModelAndView lookUp(PageParam param,SyDept dept){
		
		return ajaxJsonEscape(deptService.selectDepts(param, dept));
	}
	/**
	 * 添加部门
	 * @param dept
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("dept:add")
	@RequestMapping("add")
	public ModelAndView add(@Valid SyDept dept,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		
		return ajaxDone(deptService.addDept(dept));
		
	}
	/**
	 * 部门修改页面 
	 * @param id
	 * @param mav
	 * @return
	 */
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		SyDept dept=deptService.selectByPrimaryKey(id);
		if(dept==null){
			return NODATA;
		}
		map.addAttribute("dept",dept);
		
		return "system/organize/dept/update";
	}
	
	/**
	 * 修改部门
	 * @param dept
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("dept:update")
	@RequestMapping("update")
	public ModelAndView update(@Valid SyDept dept,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(deptService.updateDept(dept));
	}
	/**
	 * 删除部门
	 * @param id
	 * @return
	 */
	@RequiresPermissions("dept:delete")
	@RequestMapping("del")
	public ModelAndView delete(String id){
		
		return ajaxDone(deptService.deleteDept(id));
	
	}
	/**
	 * 查询部门名称	
	 * @param ids 部门id，多个用，隔开
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("getDeptNamesById")
	public ModelAndView getDeptNamesByIds(String ids){
		
		Map map=new HashMap();
	
		map.put("names",MyCache.getInstance().getDeptName(ids));
		
		return ajaxJsonEscape(map);
	
	}
	
}
