/**  
` * @Project: tjy
 * @Title: MainServiceImpl.java
 * @Package com.wing.socialcontact.sys.service.impl
 * @date 2016-5-3 下午4:08:30
 * @Copyright: 2016 
 */
package com.tojoy.service.console.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tojoy.service.console.bean.SyUsers;
import com.tojoy.service.console.dao.MenuDao;
import com.tojoy.service.console.service.IMainService;
import org.springframework.stereotype.Service;

import com.tojoy.util.RedisCache;
import com.tojoy.util.ServletUtil;

/**
 * 
 * 类名：MainServiceImpl
 * 功能：
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-5-3 下午4:08:30
 *
 */
@Service
public class MainServiceImpl implements IMainService {

	@Resource
	private MenuDao menuDao;
	@Resource
	private RedisCache redisCache;
	
	
	//刷新用户信息
	@SuppressWarnings("unchecked")
	public void init(SyUsers u){
		
//		Member me=ServletUtil.getMember();
//		
//		me.setDeptId(u.getDeptId());
//		
//		//获取全局 用户列表
//		Map<String,OnLineUser> usersMap=ServletUtil.getOnLineUsers();
//		
//		OnLineUser onmy=usersMap.get(me.getId());
//		onmy.setTrueName(u.getTrueName());
//		onmy.setDeptId(u.getDeptId());
//		
//		if(u.getUserSex()==1){
//			//onmy.setSex("男");
//		}else{
//		//	onmy.setSex("女");
//		}
		//获取用户角色ID集合
		//List<String> roleIds=dao.find("select roleId from SyUserRole where userId=? ",u.getId());
		//me.setRoleIds(roleIds);
		//updatePowerUrls(me);
		
	}
	
	
	/*private List<SyMenu> updatePowerUrls(Member me){
		//查询全部可用菜单
		List<SyMenu> allMenus=menuService.selectMenus();
		List<SyMenu> myMenus=null;
		if(!ServletUtil.isDeveloper()){
			myMenus=new ArrayList<SyMenu>();
			
			//所有角色拥有的菜单ID集合
			Set<String> menuIds=new HashSet<String>();
			
			for(String id:me.getRoleIds()){
				menuIds.addAll(dao.find("select menuId from SyRoleMenu where roleId=? ",id));
			}
			for(SyMenu m:allMenus){
				
				if(menuIds.contains(m.getId())){
					myMenus.add(m);
				}
			}
			//用户可以访问的url集合
			Set<String> urls=new HashSet<String>();
			//获取操作url
			for(String id:me.getRoleIds()){
				List<SyAction> acts=dao.find("select a from SyAction a,SyRoleAction ra where a.id=ra.actionId and ra.roleId=? ",id);
				for(SyAction a:acts){
					String url=a.getActionUrl();
					if(StringUtils.isNotBlank(url)){
						urls.addAll(Arrays.asList(url.split(",")));
					}
				}
			}
			//将用户有权限访问的菜单的url放入 urls
			for(SyMenu m:myMenus){
				String url=m.getMenuUrl();
				if(StringUtils.isNotBlank(url)){
					urls.add(url.split("\\?")[0]);
				}
			}
			me.setUrls(urls);
		
		}else{
			myMenus=allMenus;
		}
		return myMenus;
	}
	*/

	//获取用户可以访问的一级菜单  顶部菜单
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List selectMenusTop(String userId){
		
//		List<String> roleIds=ServletUtil.getRoleIdsByUserId(ServletUtil.getMember().getId());
//		StringBuffer hql=new StringBuffer(" select distinct m from ");
//		Map map=new HashMap();
//		if(ServletUtil.isDeveloper()){
//			hql.append(" SyMenu m where m.menuSuperId=:sid ");
//			map.put("sid", "0");
//			
//		}else{
//			hql.append(" SyRoleMenu rm, SyMenu m where m.menuSuperId=:sid and rm.menuId=m.id and rm.roleId in(:roleIds)  and  m.menuStatus=1 ");
//			map.put("roleIds", roleIds);
//			map.put("sid", "0");
//			
//		}
//		List<SyMenu> menus=dao.find(hql+" order by m.menuSort asc ",map);
		
		if(ServletUtil.isDeveloper()){
			List<Map<String,Object>> menus=menuDao.findmenubyuserid(userId);
			return menus;
		}else{
			List<Map<String,Object>> menus=menuDao.findmenuformainnotdev(userId);
			return menus;
		}
		

	}


	@Override
	public void clearRedis() {
		redisCache.clear();
	}
	
}
