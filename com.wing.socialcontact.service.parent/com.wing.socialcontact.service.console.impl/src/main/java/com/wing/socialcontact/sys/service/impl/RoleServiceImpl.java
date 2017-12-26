/**  
 * @Project: tjy
 * @Title: RoleServiceImpl.java
 * @Package com.wing.socialcontact.sys.service.impl
 * @date 2016-4-28 下午2:57:53
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.SyRole;
import com.wing.socialcontact.sys.bean.SyRoleAction;
import com.wing.socialcontact.sys.bean.SyRoleMenu;
import com.wing.socialcontact.sys.bean.SyUserRole;
import com.wing.socialcontact.sys.bean.SyUsers;
import com.wing.socialcontact.sys.dao.MenuDao;
import com.wing.socialcontact.sys.dao.RoleDao;
import com.wing.socialcontact.sys.dao.SyActionDao;
import com.wing.socialcontact.sys.dao.SyRoleActionDao;
import com.wing.socialcontact.sys.dao.SyRoleMenuDao;
import com.wing.socialcontact.sys.dao.SyUserRoleDao;
import com.wing.socialcontact.sys.dao.UserDao;
import com.wing.socialcontact.sys.service.IRoleService;
import com.wing.socialcontact.sys.service.ISystemLogService;

/**
 * 
 * 类名：RoleServiceImpl
 * 功能：角色管理 业务层实现
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-28 下午2:57:53
 *
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<SyRole> implements IRoleService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:Role:\" + ";

	@Resource
	private RoleDao dao;
	@Resource
	private ISystemLogService systemLogServiceImpl;
	@Resource
	private MenuDao menuDao;
	@Resource
	private SyActionDao actionDao;
	@Resource
	private SyRoleActionDao syRoleActionDao;
	@Resource
	private SyRoleMenuDao roleMenuDao;
	@Resource
	private SyUserRoleDao userRoleDao;
	@Resource
	private UserDao userDao;
	
	
	public List selectAllRoles(){
		
		return dao.selectAll();
		
	}
	
	public DataGrid selectRoles(PageParam param,SyRole role){
		
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("roleName", role.getRoleName());
		parm.put("roleDesc", role.getRoleDesc());
		parm.put("orderStr", orderStr);
		List lst = dao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
		
	
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Long selectRolesCount(SyRole role){
		Map parm = new HashMap();
		parm.put("roleName", role.getRoleName());
		int count = dao.selectCountByParam(parm);
		return (long)count;
	}
	public String addRole(SyRole role){
		SyRole parm = new SyRole();
		parm.setRoleName(role.getRoleName());
		List lst = dao.select(parm);
		//Object obj=dao.findOne("from SyRole where roleName=? ",role.getRoleName());
		if(lst.size()==0){
			int res = dao.insert(role);
			if(res > 0){
				systemLogServiceImpl.saveLog("添加角色", "角色名称:"+role.getRoleName());
				
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.role.unique";//此角色名称已存在
		}
	}

	public String updateRole(SyRole role){
		SyRole parm = new SyRole();
		parm.setRoleName(role.getRoleName());
		SyRole obj = dao.selectOne(parm);
		if(obj==null || obj.getId().equals(role.getId())){
			if(super.updateByPrimaryKeyCache(role,role.getId())){
				systemLogServiceImpl.saveLog("修改角色", "角色名称:"+role.getRoleName());
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.role.unique";//此角色名称已存在
		}
	}
	public boolean deleteRoles(String[] ids){
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					SyRole r=selectByPrimaryKey(string);
					if(r!=null){
						systemLogServiceImpl.saveLog("删除角色", "删除名称:"+r.getRoleName());
						if(super.deleteByPrimaryKeyCache(string, SyRole.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map selectPowers(String id){

		List allMenus=menuDao.selectByMenuSort(new HashMap());
		List allActions=actionDao.selectByActionSort();
		List<String> oldMenus=dao.selectMenuIdByRoleId(id);
		List<String> oldActions=dao.selectActionIdByRoleId(id);
		Map map=new HashMap();
		map.put("menus", allMenus);
		map.put("actions", allActions);
		map.put("hasMenus", oldMenus);
		map.put("hasActions", oldActions);
		
		return map;
	
	}
	
	public boolean updatePowers(String roleId,String addMenuIds,String delMenuIds,String addActionIds,String delActionIds){
		//等待添加更新的对象集合
		//List<Object> c=new ArrayList<Object>();
		int count = 0;
		//删除菜单关联
		if(StringUtils.isNotBlank(delMenuIds)){
			String[] list_delMenuIds=delMenuIds.split(",");
			for(String id:list_delMenuIds){
				Map parm = new HashMap();
				parm.put("roleId",roleId);
				parm.put("menuId", id);
				dao.deleteRoleMenuByParam(parm);
				count++;
				//(" delete SyRoleMenu where roleId=? and menuId=? ", roleId,id);
			}
		}
		//添加菜单关联
		if(StringUtils.isNotBlank(addMenuIds)){
			String[] list_addMenuIds=addMenuIds.split(",");
			for(String id:list_addMenuIds){
			
				SyRoleMenu rm=new SyRoleMenu();
				rm.setRoleId(roleId);
				rm.setMenuId(id);
				count++;
				roleMenuDao.insert(rm);
			}
		}
		//删除操作关联
		if(StringUtils.isNotBlank(delActionIds)){
			String[] list_delActionIds=delActionIds.split(",");
			for(String id:list_delActionIds){
				Map parm = new HashMap();
				parm.put("roleId",roleId);
				parm.put("actionId", id);
				dao.deleteRoleActionByParam(parm);
				count++;
				//dao.delete(" delete SyRoleAction where roleId=? and actionId=? ", roleId,id);
			}
		}
		//添加操作关联
		if(StringUtils.isNotBlank(addActionIds)){
			String[] list_addActionIds=addActionIds.split(",");
			for(String id:list_addActionIds){
				SyRoleAction ra=new SyRoleAction();
				ra.setRoleId(roleId);
				ra.setActionId(id);

				count++;
				syRoleActionDao.insert(ra);
			}
		}
		return count>0;
	}
	
	@SuppressWarnings({ "unchecked" })
	public DataGrid selectUsers(PageParam param,String roleId,SyUsers user){
		DataGrid data=new DataGrid();
		//先查询出已具有此角色的用户id
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("userName", user.getUserName());
		parm.put("trueName", user.getTrueName());
		parm.put("deptId", user.getDeptId());
		parm.put("userSex", user.getUserSex());
		parm.put("userStatus", user.getUserStatus());
		parm.put("roleId", roleId);
		
		parm.put("orderStr", orderStr);
		List lst = userDao.selectUserByNotInRoleIdParm(parm);
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
		
	}
	public boolean addUserRole(String roleId,String[] ids){
		//等待添加的对象集合
		int count=0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					if(StringUtils.isNotBlank(string)){
						
						SyUserRole ur=new SyUserRole();
						ur.setRoleId(roleId);
						ur.setUserId(string);
						count = count + userRoleDao.insert(ur);
					}
				}
			}
		}
		return count>0;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataGrid selectRoleUsers(PageParam param,String roleId,SyUsers user){
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("userName", user.getUserName());
		parm.put("trueName", user.getTrueName());
		parm.put("deptId", user.getDeptId());
		parm.put("userSex", user.getUserSex());
		parm.put("userStatus", user.getUserStatus());
		
		parm.put("orderStr", orderStr);
		parm.put("roleId", roleId);
		List lst = userDao.selectUserByRoleIdParm(parm);
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		
		return data;
		
	}
	public boolean delUserRole(String[] ids){
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					userRoleDao.deleteByPrimaryKey(string);//.delete(" delete SyUserRole where id=? ", id);
				}
			}
		}
		return true;
	}

	@Override
	public SyRole selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, SyRole.class);
	}
	
	public List<String> selectRolesByUserId(String userId){
		return userDao.findRoleIdByUserId(userId);
		//return dao.find("select roleId from SyUserRole where userId=? ",userId);
	}

}
