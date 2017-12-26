/**  
 * @Project: tjy
 * @Title: MenuServiceImpl.java
 * @Package com.wing.socialcontact.sys.service.impl
 * @date 2016-4-24 上午8:41:50
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.Menu;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.BaseConfig;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.exception.MyRuntimeException;
import com.wing.socialcontact.sys.bean.SyAction;
import com.wing.socialcontact.sys.bean.SyMenu;
import com.wing.socialcontact.sys.bean.SyMenuMy;
import com.wing.socialcontact.sys.dao.MenuDao;
import com.wing.socialcontact.sys.dao.MenuMyDao;
import com.wing.socialcontact.sys.dao.SyActionDao;
import com.wing.socialcontact.sys.service.IMenuService;
import com.wing.socialcontact.sys.service.ISystemLogService;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 
 * 类名：MenuServiceImpl
 * 功能：菜单管理 业务层实现
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-24 上午8:41:50
 *
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<SyMenu> implements IMenuService{

	@Resource
	private MenuDao dao;
	@Resource
	private MenuMyDao menuMyDao;
	@Resource
	private SyActionDao actionDao;
	@Resource
	private ISystemLogService systemLogServiceImpl;
	
	//获取用户可以访问的菜单
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String,Object>> selectMenusByUserId(String userId){
		
		List<Map<String,Object>> menus=dao.findmenubyuserid(ServletUtil.getMember().getId());
		
		return menus;

	}
	
	/*//获取用户可以访问的菜单
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String,Object>> selectMyMenusfor(){
		
		List<String> roleIds=ServletUtil.getRoleIdsByUserId(userId);
		StringBuffer hql=new StringBuffer(" select distinct new Map(" +
				"m.id as id,m.menuName as name,m.menuSuperId as pid,m.menuUrl as url," +
				"m.menuExternal as external,m.menuFresh as fresh,m.menuIcon as icon,m.menuOpen as open," +
				"m.menuRel as rel,m.menuTarget as target )from ");
		Map map=new HashMap();
		if(ServletUtil.isDeveloper()){
			hql.append(" SyMenu m ");
		}else{
			hql.append(" SyRoleMenu rm, SyMenu m where rm.menuId=m.id and rm.roleId in(:roleIds)  and  m.menuStatus=1 ");
			map.put("roleIds", roleIds);
		}
		return dao.find(hql+" order by m.menuSort asc ",map);

	}
	*/
		
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryMenus(){
		Map parm = new HashMap();
		if(ServletUtil.isDeveloper()){
			//return dao.find("select new Map(m.id as id,m.menuName as menuName,m.menuSuperId as menuSuperId,m.menuIcon as menuIcon,m.menuOpen as menuOpen)from SyMenu m order by menuSort asc");
		}else{
			parm.put("menuStatus", 1);
			//return dao.find("select new Map(m.id as id,m.menuName as menuName,m.menuSuperId as menuSuperId,m.menuIcon as menuIcon,m.menuOpen as menuOpen) from SyMenu m where menuStatus=1 order by menuSort asc");
		}
		return dao.selectByMenuSort(parm);
	}
	public String[] selectMenusIcons(){
		
		File node=new File(BaseConfig.webPath+"/resource/images/menu/");
		String[] names=node.list();
		for(int i=0,k=names.length; i<k;i++){
			names[i]="resource/images/menu/"+names[i];
		}
		return names;
	}
	
	public String addMenu(SyMenu m){
		//Object obj=dao.findOne("from SyMenu where menuName=? and menuSuperId=?",m.getMenuName(),m.getMenuSuperId());
		SyMenu parm = new SyMenu();
		parm.setMenuName(m.getMenuName());
		parm.setMenuSuperId(m.getMenuSuperId());
		List lst = dao.select(parm);
		if(lst.size()==0){
			if(dao.insert(m)>0){
				systemLogServiceImpl.saveLog("添加菜单", "菜单名:"+m.getMenuName());
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.menuname.unique";//菜单名已被占用
		}
		
	}
	
	public String updateMenu(SyMenu m){
		SyMenu parm = new SyMenu();
		parm.setMenuName(m.getMenuName());
		parm.setMenuSuperId(m.getMenuSuperId());
		SyMenu obj = dao.selectOne(parm);
		//Object obj=dao.findOne("from SyMenu where menuName=? and menuSuperId=? and id!=?",m.getMenuName(),m.getMenuSuperId(),m.getId());
		if(obj==null || obj.getId().equals(m.getId())){
			if(ServletUtil.isDeveloper()){
				//开发人员可修改全部字段
				if(super.updateByPrimaryKeyCache(m,m.getId())){
					systemLogServiceImpl.saveLog("修改菜单", "菜单名:"+m.getMenuName());
					return MsgConfig.MSG_KEY_SUCCESS;
				}else{
					return MsgConfig.MSG_KEY_FAIL;
				}
			}else{
				//非开发人员只可修改部分字段
				
				SyMenu old=dao.selectByPrimaryKey( m.getId());
				old.setMenuIcon(m.getMenuIcon());
				old.setMenuName(m.getMenuName());
				old.setMenuOpen(m.getMenuOpen());
				old.setMenuSort(m.getMenuSort());
				old.setMenuSuperId(m.getMenuSuperId());
				
				return MsgConfig.MSG_KEY_SUCCESS;
			}
			
			
		}else{
			return "msg.menuname.unique";//菜单名已被占用
		}
	}
	public String deleteMenu(String id){
		
		//Object o=dao.findOne("from SyMenu where menuSuperId=? ",id);
		SyMenu parm = new SyMenu();
		parm.setMenuSuperId(id);
		List lst = dao.select(parm);
		if(lst.size()>0){
			return "msg.menu.haschild";//菜单下属还有子菜单，无法删除
		}else{
			SyMenu menu=dao.selectByPrimaryKey(id);
			if(menu!=null){
				if(super.deleteByPrimaryKeyCache(menu.getId(), SyMenu.class)){
					systemLogServiceImpl.saveLog("删除菜单", "菜单名称："+menu.getMenuName());
					return MsgConfig.MSG_KEY_SUCCESS;
				}else{
					return MsgConfig.MSG_KEY_FAIL;
				}
			}else{
				return MsgConfig.MSG_KEY_NODATA;
			}
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map findMenuById(String id){
		SyMenu menu = dao.selectByPrimaryKey(id);
		SyMenu pm = dao.selectByPrimaryKey(menu.getMenuSuperId());
		Map r = new HashMap();
		r.put("m", menu);
		r.put("superName", pm.getMenuName());
		return r;//(Map)dao.findOne("select new Map(m as m,(select menuName from SyMenu where id=m.menuSuperId)as superName)from SyMenu m where m.id=?",id);
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataGrid selectActions(PageParam param,SyAction action){
		DataGrid data=new DataGrid();
//		StringBuffer sb=new StringBuffer("from SyAction a where 1=1 ");
//		List list=new ArrayList();
//		if(StringUtils.isNotBlank(action.getActionName())){
//			sb.append(" and a.actionName like ? ");
//			list.add("%"+action.getActionName()+"%");
//		}
//		if(StringUtils.isNotBlank(action.getActionUrl())){
//			sb.append(" and a.actionUrl like ? ");
//			list.add("%"+action.getActionUrl()+"%");	
//		}
//		if(StringUtils.isNotBlank(action.getMenuId())){
//			sb.append(" and a.menuId = ? ");
//			list.add(action.getMenuId());	
//		}
//		data.setTotal((Long)dao.findUniqueOne("select count(*)"+sb,list));
//		//排序规则
//		if(StringUtils.isNotBlank(param.getSort())){
//			
//			param.appendOrderBy(sb);//排序
//			
//		}else{
//			sb.append(" order by a.actionSort");
//		}
//		data.setRows(dao.findPage(sb.toString(),param.getPage(),param.getRows(),list));
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("actionName", action.getActionName());
		parm.put("actionUrl", action.getActionUrl());
		parm.put("menuId", action.getMenuId());
		parm.put("orderStr", orderStr);
		List lst = actionDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	
	public boolean deleteActions(String[] ids){
		for(String id:ids){
			actionDao.deleteByPrimaryKey(id);
		}
		return true;
	}
	//获取用户可以访问的所有菜单  
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SyMenu> selectMyMenus(){
//		List<String> roleIds=ServletUtil.getRoleIdsByUserId(ServletUtil.getMember().getId());
//		StringBuffer hql=new StringBuffer(" select distinct  m from ");
//		Map map=new HashMap();
//		if(ServletUtil.isDeveloper()){
//			hql.append(" SyMenu m ");
//		}else{
//			hql.append(" SyRoleMenu rm, SyMenu m where  rm.menuId=m.id and rm.roleId in(:roleIds)  and  m.menuStatus=1 ");
//			map.put("roleIds", roleIds);
//			
//		}
//		return dao.find(hql+" order by m.menuSort asc ",map);
		return dao.findSyMenubyuserid(ServletUtil.getMember().getId());

	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataGrid selectMyMenus(PageParam param,SyMenu menu){
		DataGrid data=new DataGrid();
//		StringBuffer sb=new StringBuffer("from SyMenu m ,SyMenuMy my where m.id=my.menuId and my.userId=? ");
//		List list=new ArrayList();
//		list.add(ServletUtil.getMember().getId());
//		if(StringUtils.isNotBlank(menu.getMenuName())){
//			sb.append(" and m.menuName like ? ");
//			list.add("%"+menu.getMenuName()+"%");
//		}
//		
//		data.setTotal((Long)dao.findUniqueOne("select count(*)"+sb,list));
//		
//		//排序规则
//		if(StringUtils.isNotBlank(param.getSort())){
//			
//			param.appendOrderBy(sb);//排序
//			
//		}else{
//			sb.append(" order by my.sort asc ");
//		}
//		
//		data.setRows(dao.find("select new Map(my.id as id,m.menuName as name,my.sort as sort) "+sb,list));
		

		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("menuName", menu.getMenuName());
		parm.put("userId", ServletUtil.getMember().getId());
		parm.put("orderStr", orderStr);
		List lst = actionDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		
		return data;
	}
	
	public String addSyMenuMy(SyMenuMy m){
		SyMenuMy parm = new SyMenuMy();
		parm.setUserId(ServletUtil.getMember().getId());
		parm.setMenuId(m.getMenuId());
		List lst = menuMyDao.select(parm);
		//Object obj=dao.findOne("from SyMenuMy  where userId=? and menuId=? ",ServletUtil.getMember().getId(),m.getMenuId());
		if(lst.size()==0){
			m.setUserId(ServletUtil.getMember().getId());
			menuMyDao.insert(m);
			if(StringUtils.isNotBlank(m.getId())){
				
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			throw new MyRuntimeException("菜单重复！此菜单已添加到快捷菜单了！请重新选择其他菜单!");
		}
	}
	public String updateSyMenuMy(SyMenuMy m){
		SyMenuMy parm = new SyMenuMy();
		parm.setUserId(ServletUtil.getMember().getId());
		parm.setMenuId(m.getMenuId());
		SyMenuMy obj = menuMyDao.selectOne(parm);
		//Object obj=dao.findOne("from SyMenuMy where userId=? and menuId=? and id!=? ",ServletUtil.getMember().getId(),m.getMenuId(),m.getId());
		if(obj==null || obj.getId().equals(m.getId())){
			//SyMenuMy old=dao.get(SyMenuMy.class, m.getId());
			obj.setMenuId(m.getMenuId());
			obj.setSort(m.getSort());
			if(menuMyDao.updateByPrimaryKey(obj)>0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			throw new MyRuntimeException("菜单重复！此菜单已添加到快捷菜单了！请重新选择其他菜单!");
		}
	}
	public boolean deleteSyMenuMy(String id){
		//等待删除的对象集合
		return menuMyDao.deleteByPrimaryKey(id)>0;//.delete("delete SyMenuMy where id=?",id);
	}
	
	//查询出用户可以访问的自定义快捷菜单 
	public List selectMySyMenuMy(){
		Map parm = new HashMap();
		parm.put("menuStatus",1);
		parm.put("userId",ServletUtil.getMember().getId());
		return menuMyDao.selectMyMenus(parm);
		//return dao.find(" select new Map(m.menuName as name,m.menuExternal as external,m.menuIcon as icon,m.menuRel as rel,m.menuTarget as target,m.menuUrl as url) from  SyMenu m ,SyMenuMy my where  my.menuId=m.id and my.userId=?  and  m.menuStatus=1  order by my.sort asc ",ServletUtil.getMember().getId());
	}
	
	
	
	public void getChildrenMenusBySuperId(List<SyMenu> list, Menu menu,String superId){
		
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
				getChildrenMenusBySuperId(list, m, m.getId());
				
				childrenMenus.add(m);
			}
		}
		menu.setChildrenMenus(childrenMenus);
		
	}

	@Override
	public SyMenuMy selectSyMenuMyByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return menuMyDao.selectByPrimaryKey(id);
	}

	@Override
	public SyMenu selectByPrimaryKey(String menuId) {
		// TODO Auto-generated method stub
		return super.selectByPrimaryKeyCache(menuId, SyMenu.class);
	}

	@Override
	public boolean updateAction(SyAction action) {
		// TODO Auto-generated method stub
		return actionDao.updateByPrimaryKey(action)>0;
	}

	@Override
	public SyAction selectActionByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return actionDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean addAction(SyAction action) {
		// TODO Auto-generated method stub
		return actionDao.insert(action)>0;
	}
	
	
}
