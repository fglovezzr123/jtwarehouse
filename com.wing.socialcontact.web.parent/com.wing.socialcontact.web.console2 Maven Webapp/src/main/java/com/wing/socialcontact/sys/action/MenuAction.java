/**  
 * @Project: tjy
 * @Title: MenuAction.java
 * @Package com.oa.manager.system.action
 * @date 2016-4-24 上午8:41:21
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.common.model.Menu;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.BaseConfig;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.SyAction;
import com.wing.socialcontact.sys.bean.SyDept;
import com.wing.socialcontact.sys.bean.SyMenu;
import com.wing.socialcontact.sys.bean.SyMenuMy;
import com.wing.socialcontact.sys.bean.SyUsers;
import com.wing.socialcontact.sys.service.IDeptService;
import com.wing.socialcontact.sys.service.IMenuService;
import com.wing.socialcontact.sys.service.IUserService;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 
 * 类名：MenuAction
 * 功能：系统管理--菜单管理
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-24 上午8:41:21
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuAction extends BaseAction{

	@Autowired
	private IMenuService service; 
	@Autowired
	private IUserService userService; 
	@Autowired
	private IDeptService deptService; //部门
	
	/**
	 * 跳转到菜单管理页面 查询出所有菜单
	 * @param map
	 * @return
	 */
	@RequiresPermissions("menu:read")
	@RequestMapping("load")
	public String load(){
		
		
		return "system/menu/load";
	}
	/**
	 * 跳转到菜单管理页面 查询出所有菜单
	 * @param map
	 * @return
	 */
	@RequestMapping("query")
	public ModelAndView query(){
		
		return ajaxJsonEscape(service.queryMenus());
	}
	
	/**
	 * 跳转到菜单添加页面
	 * @return
	 */
	@RequestMapping("addPage")
	public String addPage(){
		if(!ServletUtil.isDeveloper()){
			return NOPOWER;
		}
		return "system/menu/add";
	
	}
	/**
	 * 添加菜单
	 * @param menu
	 * @param errors
	 * @return
	 */
	@RequestMapping("add")
	public ModelAndView add(SyMenu menu,Errors errors){
		if(!ServletUtil.isDeveloper()){
			return ajaxDoneError(MsgConfig.MSG_KEY_NOPOWER);
		}
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(service.addMenu(menu));
		
	}
	/**
	 * 查询出所有可用菜单图标
	 * @return
	 */
	@RequestMapping("icons")
	public String showMenuIcons(ModelMap map){
		
		
		File node=new File(BaseConfig.webPath+"/resource/images/menu/");
		String[] names=node.list();
		for(int i=0,k=names.length; i<k;i++){
			names[i]="resource/images/menu/"+names[i];
		}
		map.addAttribute("icons",names);
		
		return "system/menu/icon_lookup";
	}
	/**
	 * 菜单查找带回
	 * @return
	 */
	@RequestMapping("lookUpPage")
	public String lookUp(){
		
		
		return "system/menu/lookup";
	}
	/**
	 * 查找带回 查询我能访问的所有菜单
	 * @param map
	 * @return
	 */
	@RequestMapping("queryMyAll")
	public ModelAndView queryMyAll(){
		
		return ajaxJsonEscape(service.selectMyMenus());
	}
	
	
	
	/**
	 * 菜单修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("menu:read")
	@SuppressWarnings("unchecked")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		map.addAllAttributes(service.findMenuById(id));
		if(map.get("m")==null){
			return NODATA;
		}
		return "system/menu/update";
	}
	
	/**
	 * 修改菜单
	 * @param menu
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("menu:update")
	@RequestMapping("update")
	public ModelAndView update(SyMenu menu,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		
		return ajaxDone(service.updateMenu(menu));
		
	}
	/**
	 * 删除菜单
	 * @param id
	 * @return
	 */
	@RequestMapping("del")
	public ModelAndView delete(String id){
		if(!ServletUtil.isDeveloper()){
			return ajaxDoneError(MsgConfig.MSG_KEY_NOPOWER);
		}
		return ajaxDone(service.deleteMenu(id));
	
	}
	/**
	 * action条件查询
	 * @param param
	 * @param action
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("action/query")
	public ModelAndView selectActions(PageParam param,SyAction action) {
		if(!ServletUtil.isDeveloper()){
			return ajaxDoneError(MsgConfig.MSG_KEY_NOPOWER);
		}
		return ajaxJsonEscape(service.selectActions(param, action));
	}
	/**
	 * 跳转到action添加页面
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("action/addPage")
	public String addActionPage() {
		if(!ServletUtil.isDeveloper()){
			return NOPOWER;
		}
		return "system/menu/action/add";
	
	}
	/**
	 * 添加action
	 * @param action
	 * @param errors
	 * @return
	 */
	@RequestMapping("action/add")
	public ModelAndView addAction(SyAction action,Errors errors){
		if(!ServletUtil.isDeveloper()){
			return ajaxDoneError(MsgConfig.MSG_KEY_NOPOWER);
		}
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(service.addAction(action));
		
	}

	/**
	 * 跳转到action编辑页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("action/updatePage")
	public String editAction(String id,ModelMap map){
		if(!ServletUtil.isDeveloper()){
			return NOPOWER;
		}
		SyAction a=service.selectActionByPrimaryKey(id);
		if(a==null){
			return NODATA;
		}
		map.addAttribute("act",a);
		
		return "system/menu/action/update";
	}
	/**
	 * 更新action
	 * @param action
	 * @param errors
	 * @return
	 */
	@RequestMapping("action/update")
	public ModelAndView updateAction(SyAction action,Errors errors){
		if(!ServletUtil.isDeveloper()){
			return ajaxDoneError(MsgConfig.MSG_KEY_NOPOWER);
		}
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(service.updateAction(action));
		
	}
	/**
	 * 删除action
	 * @param id
	 * @return
	 */
	@RequestMapping("action/del")
	public ModelAndView deleteActions(String[] ids){
		if(!ServletUtil.isDeveloper()){
			return ajaxDoneError(MsgConfig.MSG_KEY_NOPOWER);
		}
		return ajaxDone(service.deleteActions(ids));
	
	}
	/**
	 * 获取用户可以使用的菜单和一些用户基本信息
	 * @return
	 */
	@RequestMapping("mymenus")
	public ModelAndView selectMyMenus(){
		Member me=ServletUtil.getMember();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(MsgConfig.STATUSCODE, MsgConfig.CODE_SUCCESS);
		map.put("menus", service.selectMenusByUserId(me.getId()));
		map.put("userId", me.getId());
		SyUsers u = userService.selectByPrimaryKey(me.getId());
		map.put("userName", u.getTrueName());
		map.put("deptId",me.getDeptId());
		SyDept d = deptService.selectByPrimaryKey(me.getDeptId());
		map.put("deptName",d.getDeptName());
		

		
		
		if(ServletUtil.getSessionAttribute("fromLogin")!=null){
			//登录完成之后第一次查询菜单
			ServletUtil.getSession().removeAttribute("fromLogin");
		}else{
			//非登录完整之后的刷新菜单 需要清空用户授权信息
			ServletUtil.removeAuthorizationCache(me.getId());
		}
		return ajaxJsonEscape(map);
	}
	
	/**
	 * 获取用户可以使用的菜单和一些用户基本信息
	 * @return
	 */
	@RequestMapping("mymenus/by")
	public String selectMyMenusById(String id,ModelMap map){
		
		SyMenu m=service.selectByPrimaryKey(id);
		if(m!=null){
			map.addAttribute("name",m.getMenuName());
		}
		Menu menu=new Menu();
		
		getChildrenMenusBySuperIdForMy(service.selectMyMenus(), menu, id);
		
		List<Menu> list=menu.getChildrenMenus();
		StringBuffer sb=new StringBuffer();
		for(Menu l:list){
			sb.append("<li><a ");
			if(l.getUrl()!=null&&!"".equals(l.getUrl())){
				sb.append("href=\"");
				sb.append(l.getUrl());
				sb.append("\" target=\"").append(l.getTarget()).append("\" rel=\"").append(l.getRel()).append("\" external=\"");
				//sb.append(l.getExternal()).append("\" fresh=\"false\" title=\"").append(l.getName()).append("\" ");
				sb.append(l.getExternal()).append("\" fresh=\"").append(l.getFresh()).append("\" title=\"").append(l.getName()).append("\" ");
			}else{
				sb.append(" style=\"color: black;\" ");
			}
			sb.append(" ><img src=\"").append(l.getIcon()).append("\" />&nbsp;&nbsp;");
			sb.append(l.getName());
			sb.append("</a>");
			List<Menu> cs=l.getChildrenMenus();
			if(cs!=null&&!cs.isEmpty()){
				sb.append("<ul class=\"ul-submenu\">");
				appendMenus(cs, sb);
				sb.append("</ul>");
			}
			sb.append("</li>");
		}
		map.addAttribute("menus",sb.toString());
		
		return "main/middle";
	}
		
	private void getChildrenMenusBySuperIdForMy(List<SyMenu> list, Menu menu,String superId){
			
		List<Menu> childrenMenus=new ArrayList<Menu>();
		
		for(SyMenu s:list){
			if(s.getMenuSuperId().equals(superId)){
				Menu m=new Menu();
				m.setExternal(s.getMenuExternal());
				m.setFresh(s.getMenuFresh());
				m.setIcon(s.getMenuIcon());
				m.setId(s.getId());
				m.setName(s.getMenuName());
				m.setOpen(s.getMenuOpen());
				m.setRel(s.getMenuRel());
				m.setSuperId(s.getMenuSuperId());
				m.setTarget(s.getMenuTarget());
				m.setUrl(s.getMenuUrl());
				
				//递归获取下级菜单
				getChildrenMenusBySuperIdForMy(list, m, m.getId());
				
				childrenMenus.add(m);
			}
		}
		menu.setChildrenMenus(childrenMenus);
		
	}

	//递归拼接子菜单
	private void appendMenus(List<Menu> list,StringBuffer sb){
		for(Menu l:list){
			sb.append("<li><a ");
			
			if(l.getUrl()!=null&&!"".equals(l.getUrl())){
				sb.append("href=\"");
				sb.append(l.getUrl());
				sb.append("\" target=\"").append(l.getTarget()).append("\" rel=\"").append(l.getRel()).append("\" external=\"");
				//sb.append(l.getExternal()).append("\" fresh=\"false\" title=\"").append(l.getName()).append("\" ");
				sb.append(l.getExternal()).append("\" fresh=\"").append(l.getFresh()).append("\" title=\"").append(l.getName()).append("\" ");
			}else{
				sb.append(" style=\"color: black;\" ");
			}
			sb.append(" ><img src=\"").append(l.getIcon()).append("\" />&nbsp;&nbsp;");
			sb.append(l.getName());
			sb.append("</a>");
			List<Menu> cs=l.getChildrenMenus();
			if(cs!=null&&!cs.isEmpty()){
				sb.append("<ul class=\"ul-submenu\">");
				appendMenus(cs, sb);
				sb.append("</ul>");
			}
			sb.append("</li>");
		}
	}
	/**
	 * 跳转到快捷菜单的管理页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("my/load")
	public String loadMy(){
		
		return "system/menu/my/load";
	}
	
	@RequestMapping("my/query")
	public ModelAndView queryMy(PageParam param,SyMenu menu){
		
		return ajaxJsonEscape(service.selectMyMenus(param,menu));
	}
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping("my/addPage")
	public String addPageMy(){
		
		return "system/menu/my/add";
	
	}
	
	@RequestMapping("my/add")
	public ModelAndView addMy(SyMenuMy m,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		
		return ajaxDone(service.addSyMenuMy(m));
		
	}
	
	@RequestMapping("my/updatePage")
	public String updatePageMy(String id,ModelMap map){
		SyMenuMy my=service.selectSyMenuMyByPrimaryKey(id);
		if(my==null){
			return NODATA;
		}
		SyMenu menu=service.selectByPrimaryKey(my.getMenuId());
		if(menu==null){
			return NODATA;
		}
		map.addAttribute("my",my);
		map.addAttribute("name",menu.getMenuName());
		
		return "system/menu/my/update";
	}
	
	@RequestMapping("my/update")
	public ModelAndView update(SyMenuMy m,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		return ajaxDone(service.updateSyMenuMy(m));
		
	}
	
	@RequestMapping("my/del")
	public ModelAndView deleteMy(String id){
		
		return ajaxDone(service.deleteSyMenuMy(id));
	
	}
	
	
}
