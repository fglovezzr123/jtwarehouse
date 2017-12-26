/**  
 * @Project: tjy
 * @Title: MainAction.java
 * @Package com.oa.manager.system.action
 * @date 2016-4-1 下午3:18:28
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.cache.MyCache;
import com.wing.socialcontact.config.BaseConfig;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.model.Member;
import com.wing.socialcontact.sys.model.OnLineUser;
import com.wing.socialcontact.sys.model.PageParam;
import com.wing.socialcontact.sys.service.IDeptService;
import com.wing.socialcontact.sys.service.IMainService;
import com.wing.socialcontact.sys.service.IMenuService;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 
 * 类名：MainAction
 * 功能：系统主界面
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-1 下午3:18:28
 *
 */
@Controller
@RequestMapping("/main")
public class MainAction extends BaseAction{
	
	@Autowired
	private IMainService service; 
	@Autowired
	private IMenuService menuService;//菜单 
	@Autowired
	private IDeptService deptService; //部门
	/**
	 * 跳转到系统主界面  刷新主界面将会执行此方法
	 * @return
	 */
	@RequestMapping("")
	public String to(ModelMap map){
	
		if(SecurityUtils.getSubject().isPermitted("schedule:read")){
			//有日程安排提醒权限
			map.addAttribute("scheduleWarn",true);
		}
		//消息提提 定时刷新间隔时间
		map.addAttribute("msgWarnTime",BaseConfig.webconfig.getMsgwarnTime());
			
		//查询出顶部一级菜单
		map.addAttribute("menus",service.selectMenusTop());
	    //查询出用户的自定义快捷菜单
		
		List<Map<String,Object>> menus=menuService.selectMySyMenuMy();
		for(Map<String,Object>  m:menus){
			m.put("url",((String)m.get("url")).replaceAll("rel=","rel=my_"));//统一追加前缀 防止和正常菜单rel冲突
		}
	
		map.addAttribute("mymenus",menus);
		
		//用户基本信息
		Member me=ServletUtil.getMember();
		map.addAttribute("userId",me.getId());
		map.addAttribute("trueName", MyCache.getInstance().getTrueName(me.getId()));
		map.addAttribute("deptId",me.getDeptId());
		map.addAttribute("deptName", MyCache.getInstance().getDeptName(me.getDeptId()));
		
		return "main/main";			
		
		
	}
	/**
	 * 跳转到主工作区 首页
	 * @return
	 */
	@RequestMapping("home")
	public String home(ModelMap map){
		Member me=ServletUtil.getMember();
		Subject currentUser =SecurityUtils.getSubject();

		PageParam param=new PageParam();
		param.setRows(5);
		
		return "main/home";
	}
	
	/**
	 * 查询在线人员列表，部门列表，在线人员数量
	 * @return
	 */
	@RequestMapping("online")
	public ModelAndView online(){
		Map<String,OnLineUser> onLineUsers=ServletUtil.getOnLineUsers();
		Set<Entry<String,OnLineUser>> entrys=onLineUsers.entrySet();
		List<Map<String,Object>> users=new ArrayList<Map<String,Object>>();
		for(Entry<String,OnLineUser> e:entrys){
			OnLineUser u=e.getValue();
			Map<String,Object> user=new HashMap<String,Object>();
			user.put("id", u.getId());
			user.put("trueName", u.getTrueName());
			user.put("deptId", u.getDeptId());
			user.put("sex", u.getSex());
			users.add(user);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(MsgConfig.STATUSCODE, MsgConfig.CODE_SUCCESS);
		map.put("users", users);
		map.put("depts", deptService.selectAllDeptsMap());
		map.put("onlineNum",ServletUtil.getOnLineUsers().size() );
		
		return ajaxJsonEscape(map);
		
	}
	
	/**
	 * 查询未读信息数量，页面Ajax定时请求
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("warnmsg")
	public ModelAndView  queryNotReadNum(){
		Member me=ServletUtil.getMember();
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put(MsgConfig.STATUSCODE, MsgConfig.CODE_SUCCESS);
		
		Subject currentUser =SecurityUtils.getSubject();
		
		return ajaxJsonEscape(map);
	}
	
	
	
	
	
}
