/**  
 * @Project: tjy
 * @Title: RoleAction.java
 * @Package com.oa.manager.system.action
 * @date 2016-4-28 下午2:56:28
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.SyRole;
import com.wing.socialcontact.sys.bean.SyUsers;
import com.wing.socialcontact.sys.service.IRoleService;

/**
 * 
 * 类名：RoleAction
 * 功能：系统管理--角色管理
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-28 下午2:56:28
 *
 */
@Controller
@RequestMapping("/role")
public class RoleAction extends BaseAction{
	
	@Autowired
	private IRoleService roleService; 			
	
	
	/**
	 * 条件查询角色
	 * 
	 * @return
	 */
	@RequiresPermissions("role:read")
	@RequestMapping("load")
	public String load(){
		
		return "system/organize/role/load";
	
	}
	@RequiresPermissions("role:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,SyRole role){
		
		return ajaxJsonEscape(roleService.selectRoles(param, role));
		
	}
	
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequiresPermissions("role:add")
	@RequestMapping("addPage")
	public String addPage(){
		
		return "system/organize/role/add";
	
	}
	/**
	 * 添加角色 
	 * @param role
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("role:add")
	@RequestMapping("add")
	public ModelAndView add(SyRole role,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		
		return ajaxDone(roleService.addRole(role));
		
	}
	/**
	 * 跳转到角色修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("role:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		SyRole role=roleService.selectByPrimaryKey( id);
		if(role==null){
			return NODATA;
		}
		map.addAttribute("r",role);
		
		return "system/organize/role/update";
	}
	/**
	 * 修改角色
	 * @param role
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("role:update")
	@RequestMapping("update")
	public ModelAndView update(SyRole role,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(roleService.updateRole(role));
		
	}
	/**
	 * 删除角色
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("role:delete")
	@RequestMapping("del")
	public ModelAndView delete(String[] ids){
		
		return ajaxDone(roleService.deleteRoles(ids));
	
	}
	
	/**
	 * 跳转到 角色--修改权限页面 ，并 查询出所有的权限，和已有的权限
	 * @param param
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("role:readPower")
	@RequestMapping("power/updatePage")
	public String powerPage(){
		

		return "system/organize/role/power/update";
	
	}
	/**
	 * 跳转到 角色--修改权限页面 ，并 查询出所有的权限，和已有的权限
	 * @param param
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("role:readPower")
	@RequestMapping("power/updatePage/query")
	public ModelAndView queryPower(String id){
		
		return ajaxJsonEscape(roleService.selectPowers(id));
	
	}
	/**
	 * 修改角色权限
	 * @param roleId	角色id
	 * @param addMenuIds	需要添加的菜单id，多个用，隔开
	 * @param delMenuIds	需要删除的菜单id，多个用，隔开
	 * @param addActionIds	需要添加的操作id，多个用，隔开
	 * @param delActionIds	需要删除的操作id，多个用，隔开
	 * @return
	 */
	@RequiresPermissions("role:updatePower")
	@RequestMapping("power/update")
	public ModelAndView updatePowers(String roleId,String addMenuIds,String delMenuIds,String addActionIds,String delActionIds){
		
		return ajaxDone(roleService.updatePowers(roleId, addMenuIds, delMenuIds, addActionIds, delActionIds));
		
	}
	/**
	 * 查询角色已有用户 
	 * @return
	 */
	@RequiresPermissions("role:readUser")
	@RequestMapping("users/load")
	public String usersLoad(String roleId,ModelMap map){
		SyRole role=roleService.selectByPrimaryKey( roleId);
		if(role==null){
			map.addAttribute("name","角色不存在");
		}else{
			map.addAttribute("name",role.getRoleName());
		}
		return "system/organize/role/select_users";
	
	}
	/**
	 * 条件查询 用户，用于角色分配
	 * @param param
	 * @param role
	 * @param map
	 * @return
	 */
	@RequiresPermissions("role:readUser")
	@RequestMapping("users/query")
	public ModelAndView queryUsers(PageParam param,String roleId,SyUsers user){
		
		return ajaxJsonEscape(roleService.selectUsers(param, roleId, user));
	
	}
	/**
	 * 为角色批量分配用户
	 * @param roleId
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("role:addUser")
	@RequestMapping("roleAddUsers")
	public ModelAndView roleAddusers(String roleId,String[] ids){
		
	
		return ajaxDone(roleService.addUserRole(roleId, ids));
		
	}
	/**
	 * 查询角色已有用户 
	 * @return
	 */
	@RequiresPermissions("role:hasUser")
	@RequestMapping("roleUsers/load")
	public String roleUsersLoad(String roleId,ModelMap map){
		
		SyRole role=roleService.selectByPrimaryKey(roleId);
		if(role==null){
			map.addAttribute("name","角色不存在");
		}else{
			map.addAttribute("name",role.getRoleName());
		}
		
		return "system/organize/role/select_role_users";
	
	}
	/**
	 * 分配管理，条件查询 已具有此角色的用户，
	 * @param param
	 * @param role
	 * @param map
	 * @return
	 */
	@RequiresPermissions("role:hasUser")
	@RequestMapping("roleUsers/query")
	public ModelAndView queryRoleUsers(PageParam param,String roleId,SyUsers user){
	
		
		return ajaxJsonEscape(roleService.selectRoleUsers(param, roleId, user));
	}
	/**
	 * 批量删除角色已分配的用户
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("role:deleteUser")
	@RequestMapping("delRoleUsers")
	public ModelAndView delRoleUsers(String[] ids){
		
		return ajaxDone(roleService.delUserRole( ids));
		
	}
	/**
	 * 角色查询，查找带回
	 * @param param
	 * @param role
	 * @param type 1:单选，2：多选
	 * @param map
	 * @return
	 */
	@RequestMapping("lookUpPage")
	public String lookUpPage(Integer type){
		
		if(type!=null){
			if(type==2){
				//多选
			
				return "system/organize/role/lookup_more";
			}else {
				//单选
				
				return "system/organize/role/lookup";
			}
		}else{
			//默认单选
			return "system/organize/role/lookup";
		}
	}
	
	/**
	 * 查询角色名称	
	 * @param ids 角色id，多个用，隔开
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("getRoleNamesById")
	public ModelAndView getRoleNamesByIds(String ids){
		
		Map map=new HashMap();
		map.put(MsgConfig.STATUSCODE,MsgConfig.CODE_SUCCESS );

		if(StringUtils.isBlank(ids)){
			map.put("names","");
		}else{
			StringBuffer names=new StringBuffer();
			String[] sz_ids=ids.split(",");
			for(String id:sz_ids){
				SyRole r = roleService.selectByPrimaryKey(id);
				if(r!=null){
					names.append(r.getRoleName()+",");
				}
			
			}
			if("".equals(names.toString())){
				map.put("names",names.toString());
			}else{
				map.put("names",names.substring(0,names.lastIndexOf(",")));
			}
		}
		return ajaxJsonEscape(map);
	
	}
	
	
	
}
