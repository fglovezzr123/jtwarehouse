/**  
 * @Project: tjy
 * @Title: UserServiceImpl.java
 * @Package com.wing.socialcontact.sys.service.impl
 * @date 2016-3-29 下午2:20:27
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.SyDept;
import com.wing.socialcontact.sys.bean.SyRole;
import com.wing.socialcontact.sys.bean.SyUserRole;
import com.wing.socialcontact.sys.bean.SyUsers;
import com.wing.socialcontact.sys.dao.DeptDao;
import com.wing.socialcontact.sys.dao.MenuDao;
import com.wing.socialcontact.sys.dao.RoleDao;
import com.wing.socialcontact.sys.dao.SyActionDao;
import com.wing.socialcontact.sys.dao.SyRoleActionDao;
import com.wing.socialcontact.sys.dao.SyRoleMenuDao;
import com.wing.socialcontact.sys.dao.SyUserRoleDao;
import com.wing.socialcontact.sys.dao.UserDao;
import com.wing.socialcontact.sys.service.IDeptService;
import com.wing.socialcontact.sys.service.ISystemLogService;
import com.wing.socialcontact.sys.service.IUserService;
import com.wing.socialcontact.util.MD5Util;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 
 * 类名：UserServiceImpl
 * 功能：用户管理 业务层实现
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-3-29 下午2:20:27
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<SyUsers> implements IUserService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:User:\" + ";
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private SyActionDao syActionDao;
	
	@Resource
	private IDeptService deptService;	
	@Resource
	private DeptDao deptDao;
	
	@Resource
	private SyRoleMenuDao syRoleMenuDao;
	
	@Resource
	private MenuDao menuDao;
	
	@Resource
	private SyRoleActionDao syRoleActionDao;
	
	@Resource
	private SyUserRoleDao syUserRoleDao;
	
	@Resource
	private RoleDao roledao;
	@Resource
	private ISystemLogService systemLogServiceImpl;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataGrid selectUsers(PageParam param,SyUsers user){
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("userName", user.getUserName());
		parm.put("trueName", user.getTrueName());
		if(user.getDeptId()==null||"".equals(user.getDeptId())||"0".equals(user.getDeptId())){
			parm.put("deptId", null);
		}else{
			parm.put("deptId", user.getDeptId());
		}
		parm.put("userSex", user.getUserSex());
		parm.put("userStatus", user.getUserStatus());
		List<Map<String,Object>> lst=userDao.getuserinfoMap(parm);
		for(Map<String,Object> map:lst){
			SyDept dept = deptService.selectByPrimaryKey((String)map.get("deptId"));
			map.put("deptName",dept.getDeptName());
		 
		}	
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
		
	}
	
	
	public String addUser(SyUsers user){
		SyUsers param = new SyUsers();
		param.setTrueName(user.getTrueName());
		SyUsers obj=userDao.selectOne(param); 
		if(obj==null){
			
			Member me=ServletUtil.getMember();
			user.setRegisterUid(me.getId());
			
			user.setUserPassword(MD5Util.MD5(user.getUserPassword()));
			user.setErrorCount((short)0);
			user.setLastLoginIp("x.x.x.x");//设置用户最后登录ip，可以根据此ip判断用户是否为第一次登录系统
			user.setRegisterTime(new Timestamp(new Date().getTime()));
			userDao.insert(user);
				
				systemLogServiceImpl.saveLog("添加用户", "账号:"+user.getUserName());
				
				return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return "msg.username.unique";//用户名已被占用
		}
	}
	
	public String addUsers(List<Map> excel){
		
		boolean flag =true;
		List<SyUsers> lu  = new ArrayList<SyUsers>();
		for (Map<Integer ,String> map : excel) {
			SyUsers user = new SyUsers();
			user.setUserName(map.get(0));
			user.setTrueName(map.get(1));
			SyDept param = new SyDept();
			param.setDeptName(map.get(2));;
			SyDept dept=deptDao.selectOne(param);
			if(dept!=null && !"".equals(dept)){
				user.setDeptId(dept.getId());
			}
			if("女".equals(map.get(3))){
				user.setUserSex((short)0);
			}else{
				user.setUserSex((short)1);
			}
			user.setMobilePhoneNumber(map.get(4));
			if("是".equals(map.get(5))){
				user.setUserStatus((short)1);
			}else{
				//禁止登陆
				user.setUserStatus((short)0);
			}
			
			
			user.setUserPassword("H1AF2G39C90F59F00H5DHA574BA4EE3H");//默认密码123456
			Member me=ServletUtil.getMember();
			user.setRegisterUid(me.getId());
			user.setErrorCount((short)0);
			user.setLastLoginIp("x.x.x.x");//设置用户最后登录ip，可以根据此ip判断用户是否为第一次登录系统
			user.setRegisterTime(new Timestamp(new Date().getTime()));
			lu.add(user);
			if(userDao.insert(user)>0){
			}else{
				flag =false;
				break;
			};
		}
		
		if(flag){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
					
		}
		
		
			
	}
	

	public String updateUser(SyUsers u){
		SyUsers old=userDao.selectByPrimaryKey(u.getId());
		if(old==null){
			return MsgConfig.MSG_KEY_NODATA;
		}
		SyUsers parm = new SyUsers();
		parm.setUserName(u.getUserName());
		SyUsers obj=userDao.selectOne(parm);
		if(obj!=null){
			if(u.getId().equals(obj.getId())){
			}else{
				return "msg.username.unique";//用户名已被占用
			}
		}
//		if(StringUtils.isNotBlank(u.getUserPassword())){
//			old.setUserPassword(MD5Util.MD5(u.getUserPassword()));
//		}
		old.setUserName(u.getUserName());
		old.setTrueName(u.getTrueName());
		old.setUserSex(u.getUserSex());
		old.setDeptId(u.getDeptId());
		old.setUserDesc(u.getUserDesc());
		old.setUserStatus(u.getUserStatus());
		old.setMobilePhoneNumber(u.getMobilePhoneNumber());
		

		old.setLastLoginIp(u.getLastLoginIp());//登录ip
		old.setLastLoginTime(u.getLastLoginTime());//登录时间
		old.setErrorCount((short)0);//将密码错误次数重置为0
		old.setErrorTime(u.getErrorTime());
		systemLogServiceImpl.saveLog("修改用户", "账号:"+old.getUserName());
		updateByPrimaryKey(old);
		
		return MsgConfig.MSG_KEY_SUCCESS;
		
	}
	
	
	public boolean deleteUser(String[] ids){
		
		boolean flag = true;
		String[] all =null; 
		for( String id : ids ){
			all = id.split(",");
		}
		if(all==null){
			return false;
		}
		//等待删除的对象集合
		for(String id:all){
			SyUsers user=userDao.selectByPrimaryKey(id);
			if(user!=null){
				//开发人员账号，超级管理员账号不可删除
				if(!user.getUserName().equals("admin")){
					
					systemLogServiceImpl.saveLog("删除用户", "账号"+user.getUserName()+" 姓名"+user.getTrueName());
					 if(super.deleteByPrimaryKeyCache(id, SyUsers.class)){
						 //删除角色
						 userDao.deleteuserrole(id);
						 super.deleteall(SyRole.class);
						 //部门领导置为空
						 userDao.updatedeptleader(id);
						 super.deleteall(SyDept.class);
					 }else{
						flag=false;
					 };
				}
				
			}
		}
		

		return flag;
	}
	private UserDao deleteuserrole(String id) {
		// TODO Auto-generated method stub
		return null;
	}


	@SuppressWarnings("rawtypes")
	public List selectUserRoles(String userId){
		/**
		 *查角色
		 */
		return userDao.findrolebyuserID(userId);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map selectUserRolesAndIds(String userId){
		
		List<Map> allRoles=roledao.selectAllRoles();
		List<String> oldRoles=userDao.findRoleIdByUserId(userId);
		Map map=new HashMap();
		map.put("roles", allRoles);
		map.put("hasRoles", oldRoles);
		
		return map;
		
	}
	public boolean updateUserRoles(String userId,String[] addRoleIds,String[] delRoleIds){
		
		boolean flag = true;
		//等待修改的对象集合
		List<Object> c=new ArrayList<Object>();
		//添加用户角色关联
		
		for(String id:addRoleIds){
			SyUserRole ur=new SyUserRole();
			ur.setRoleId(id);
			ur.setUserId(userId);
			SyUserRole re =syUserRoleDao.selectOne(ur);
			if (re!=null){
				
			}else{
			    if(syUserRoleDao.insert(ur)>0){
			    }else{
			    	flag=false;
			    }
			}
		}
		//删除用户角色关联
		for(String id:delRoleIds){
			SyUserRole ur=new SyUserRole();
			ur.setRoleId(id);
			ur.setUserId(userId);
			if(syUserRoleDao.delete(ur)>0){}else{flag=false;};
		}
		return flag;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,Object> selectUserPowers(String userId){
		
		List<String> ids=userDao.findRoleIdByUserId(userId);
		Map<String,Object> map=new HashMap<String,Object>();
		if(!ids.isEmpty()){
			Map<String,Object> queryValues=new HashMap<String,Object>();
			queryValues.put("roleIds", ids);
			List menus=menuDao.findmenubyuserid(userId);//.find("",queryValues);
			List actions=syRoleActionDao.findactionbyuserid(userId);//find("select distinct new Map(a.id as id,a.menuId as menuId, a.actionName as actionName) from SyRoleAction ra,SyAction a where ra.actionId=a.id and ra.roleId in(:roleIds) order by a.actionSort asc",queryValues);
			
			map.put("menus", menus);
			map.put("actions", actions);
			
		}else{
			map.put("noRole", true);
		}
		return map;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<SyUsers> selectAllUsers(){
		
		return  userDao.selectAll();
		
	}

	private boolean updateByPrimaryKey(SyUsers user){
		return super.updateByPrimaryKeyCache(user,user.getId());
	}
	
	public boolean updatePassword(String id,String userPassword){
		SyUsers user = selectByPrimaryKey(id);
		
		user.setUserPassword(MD5Util.MD5(userPassword));
	
		if(userDao.updateByPrimaryKey(user)>0){
			return true;
		};
		return false;
	}
	
	public boolean updateMyPassword(String oldPassword,String userPassword){
		SyUsers user=selectByPrimaryKey(ServletUtil.getMember().getId());
		if(MD5Util.MD5Validate(oldPassword, user.getUserPassword())){
			user.setUserPassword(MD5Util.MD5(userPassword));
			updateByPrimaryKey(user);
			systemLogServiceImpl.saveLog("修改密码", "");
			return true;
		}else{
			return false;
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataGrid selectUsersLookUp(PageParam param,SyUsers user){
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("trueName", user.getTrueName());
		parm.put("deptId", user.getDeptId());
		parm.put("userSex", user.getUserSex());
		parm.put("userStatus", user.getUserStatus());
		List<Map<String,Object>> lst=userDao.selectUsersLookUp(parm);
		for(Map<String,Object> map:lst){
			SyDept dept = deptService.selectByPrimaryKey((String)map.get("deptId"));
			map.put("deptName",dept.getDeptName());
		 
		}	
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
		
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataGrid selectUsersLookUpNumber(PageParam param,SyUsers user){
		DataGrid data=new DataGrid();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("trueName", user.getTrueName());
		parm.put("deptId", user.getDeptId());
		parm.put("userSex", user.getUserSex());
		parm.put("userStatus", user.getUserStatus());
		List<Map<String,Object>> lst=userDao.selectUsersLookUp(parm);
		for(Map<String,Object> map:lst){
			SyDept dept = deptService.selectByPrimaryKey((String)map.get("deptId"));
			map.put("deptName",dept.getDeptName());
		 
		}	
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
		
	}
	public SyUsers findUserByLoginName(String name){
		
		SyUsers parm = new SyUsers();
		
		parm.setUserName(name);

		return userDao.selectOne(parm);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Collection<String>> selectRolesPowers(String userId){
		Map<String,Collection<String>> map=new HashMap<String,Collection<String>>();
		List<String> roleIds=userDao.findRoleIdByUserId(userId);//find("select roleId from SyUserRole where userId=? ",userId);
		//用户可以访问的url集合
		Set<String> urls=new HashSet<String>();
		for(String id:roleIds){
			List<String> menuUrls=syRoleMenuDao.selecturlbyroleid(id);//.find("select distinct m.menuUrl from SyRoleMenu rm,SyMenu m where rm.menuId=m.id and rm.roleId=? ",id);
			for(String url:menuUrls){
				if(StringUtils.isNotBlank(url)){
					urls.add(url.split("\\?")[0]);
				}
			}
		}
		//获取操作url
		for(String id:roleIds){
			List<String> actUrl=syActionDao.findurlbyroleid(id);// find("select distinct a.actionUrl from SyAction a,SyRoleAction ra where a.id=ra.actionId and ra.roleId=? ",id);
			for(String url:actUrl){
				if(StringUtils.isNotBlank(url)){
					urls.addAll(Arrays.asList(url.split(",")));
				}
			}
		}
		map.put("roleIds", roleIds);
		map.put("powers", urls);
		return map;
	}


	public SyUsers findUserById(String id) {
		return super.selectByPrimaryKeyCache(id, SyUsers.class);
	}


	@Override
	public SyUsers selectByPrimaryKey(String userId) {
		System.out.println("后："+RpcContext.getContext().getAttachment("userId"));
		return super.selectByPrimaryKeyCache(userId, SyUsers.class);
	}

}
