/**  
 * @Project: tjy
 * @Title: IMenuService.java
 * @Package com.oa.manager.system.service
 * @date 2016-4-24 上午8:41:38
 * @Copyright: 2016 
 */
package com.tojoy.service.console.service;

import java.util.List;
import java.util.Map;

import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.Menu;
import com.tojoy.common.model.PageParam;
import com.tojoy.service.console.bean.SyAction;
import com.tojoy.service.console.bean.SyMenu;
import com.tojoy.service.console.bean.SyMenuMy;

/**
 * 
 * 类名：IMenuService
 * 功能：菜单管理 业务层
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-24 上午8:41:38
 *
 */
public interface IMenuService{
	
	/**
	 * 查询出所有可用菜单
	 */
//	public List<SyMenu> selectMenus();
	/**
	 * 查询菜单，用于菜单管理
	 * @return
	 */
	public List<Map<String,Object>> queryMenus();
	/**
	 * 根据用户id 查询出用户可以使用的左侧菜单
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> selectMenusByUserId(String userId);
	
	/**
	 * 查询出所有可用菜单图标的所有路径，在resource下
	 * @return
	 */
	public String[] selectMenusIcons();
	/**
	 * 添加菜单
	 * @param m
	 * @return
	 */
	public String addMenu(SyMenu m);
	/**
	 * 更新菜单
	 * @param m
	 * @return
	 */
	public String updateMenu(SyMenu m);
	/**
	 * 菜单删除
	 * @param id
	 * @return
	 */
	public String deleteMenu(String id);

	/**
	 * 查询单个菜单，级联查询上级菜单名称
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map findMenuById(String id);
	
	/**
	 * action 条件分页查询
	 * @param param
	 * @param action
	 * @return
	 */
	public DataGrid selectActions(PageParam param,SyAction action);
	
	/**
	 * 批量删除action
	 * @param ids
	 * @return
	 */
	public boolean deleteActions(String[] ids);
	
	public List selectMyMenus();
	/**
	 * 条件分页查询 我的快捷菜单
	 * @param menu
	 * @return
	 */
	public DataGrid selectMyMenus(PageParam param,SyMenu menu);
	/**
	 * 添加快捷菜单
	 * @param m
	 * @return
	 */
	public String addSyMenuMy(SyMenuMy m);
	/**
	 * 修改快捷菜单
	 * @param m
	 * @return
	 */
	public String updateSyMenuMy(SyMenuMy m);
	/**
	 * 删除快捷菜单
	 * @param id
	 * @return
	 */
	public boolean deleteSyMenuMy(String id);
	/**
	 * 查询出用户可以访问的自定义快捷菜单 
	 * @return
	 */
	public List selectMySyMenuMy();
	
	/**
	 * 获取菜单的子菜单 
	 * @param list
	 * @param menu
	 * @param superId
	 */
	public void getChildrenMenusBySuperId(List<SyMenu> list, Menu menu,String superId);
	public SyMenuMy selectSyMenuMyByPrimaryKey(String id);
	public SyMenu selectByPrimaryKey(String menuId);
	public boolean updateAction(SyAction action);
	public SyAction selectActionByPrimaryKey(String id);
	public boolean addAction(SyAction action);
	
}
