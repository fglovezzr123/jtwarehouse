/**  
 * @Project: tjy
 * @Title: DeptServiceImpl.java
 * @Package com.wing.socialcontact.sys.service.impl
 * @date 2016-4-3 下午5:11:01
 * @Copyright: 2016 
 */
package com.tojoy.service.console.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tojoy.service.console.bean.SyUsers;
import com.tojoy.service.console.dao.UserDao;
import com.tojoy.service.console.service.ISystemLogService;
import com.tojoy.service.console.dao.DeptDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;
import com.tojoy.config.MsgConfig;
import com.tojoy.service.console.bean.SyDept;
import com.tojoy.service.console.service.IDeptService;

/**
 * 
 * 类名：DeptServiceImpl
 * 功能：部门管理 业务操作
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-3 下午5:11:01
 *
 */
@Service
@Transactional
public class DeptServiceImpl extends BaseServiceImpl<SyDept> implements IDeptService{

	/**
	 * 缓存的key值
	 */
	
	@Resource
	private DeptDao dao;
	@Resource
	private UserDao userDao;
	@Resource
	private ISystemLogService systemLogServiceImpl;
	
	public String addDept(SyDept d){
		SyDept parm = new SyDept();
		parm.setDeptName(d.getDeptName());
		parm.setSuperId(d.getSuperId());
		List lst = dao.select(parm);
		
		if(lst.size()==0){
			int i = dao.insert(d);
			if(i>0){
				systemLogServiceImpl.saveLog("添加部门", "部门名称:"+d.getDeptName());
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.deptname.unique";//部门名称已被占用
		}
		
	}
	
	/**
	 * 根据id获取部门
	 */
	public SyDept selectByPrimaryKey(String id){
		return super.selectByPrimaryKeyCache(id, SyDept.class);
	}
	
	/**
	 * 查询出所有部门
	 */
	@SuppressWarnings("unchecked")
	public List<SyDept> selectAllDepts(){
		return dao.select(new SyDept());
	}
	/**
	 * 查询出所有部门
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> selectAllDeptsMap(){
		return dao.selectAllDeptsMap();
		//return dao.find("select new Map(id as id,deptName as deptName,superId as superId)from SyDept  order by deptSort asc");
	}
	
	/**
	 * 递归查询子节点 
	 * @param superMap
	 * @param depts
	 */
	/*@SuppressWarnings("unchecked")
	private void queryChildDepts(List<SyDept> depts){
		
		for(SyDept d:depts){
			List<SyDept> zdepts=(List<SyDept>)dao.find("from SyDept where superId=? order by deptSort asc",d.getId());
			if(!zdepts.isEmpty()){
				d.setDepts(zdepts);
				queryChildDepts(zdepts);	
			}
		}
	}*/

	private SyDept update(SyDept d){
		if(super.updateByPrimaryKeyCache(d,d.getId())){
			return d;
		}else{
			return null;
		}
	}
	public String updateDept(SyDept d){
		SyDept parm = new SyDept();
		parm.setDeptName(d.getDeptName());
		parm.setSuperId(d.getSuperId());
		//parm.setId(d.getId());
		SyDept obj = dao.selectOne(parm);
		
		
		if(obj!=null&&!d.getId().equals(obj.getId())){
			return "msg.deptname.unique";//菜单名已被占用
		}else{
			if(update(d)!=null){
				systemLogServiceImpl.saveLog("修改部门", "部门名称："+d.getDeptName());
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}
//		if(obj==null){
//			if(update(d)!=null){
//				systemLogServiceImpl.saveLog("修改部门", "部门名称："+d.getDeptName());
//				return MsgConfig.MSG_KEY_SUCCESS;
//			}else{
//				return MsgConfig.MSG_KEY_FAIL;
//			}
//		}else{
//			return "msg.deptname.unique";//菜单名已被占用
//		}
	}

	public String deleteDept(String id){
		//SyDept o=dao.findOne("from SyDept where superId=? ",id);
		SyDept parm = new SyDept();
		parm.setSuperId(id);
		List<SyDept> o=dao.select(parm);
		if(o!=null&&o.size()>0){
			return "msg.dept.haschild";//部门下属还有子部门，无法删除
		}else{
			//Object userObj=dao.findOne("from SyUsers where deptId=?",id);
			SyUsers u = new SyUsers();
			u.setDeptId(id);
			List lst = userDao.select(u);
			if(lst.size()>0){
				return "msg.dept.hasuser";//有用户属于此部门，无法删除
			}
			SyDept dept=dao.selectByPrimaryKey(id);
			if(dept!=null){
				if(super.deleteByPrimaryKeyCache(id, SyDept.class)){
					systemLogServiceImpl.saveLog("删除部门", "部门名称："+dept.getDeptName());
					
					return MsgConfig.MSG_KEY_SUCCESS;
				}else{
					return MsgConfig.MSG_KEY_FAIL;
				}
			}else{
				return MsgConfig.MSG_KEY_NODATA;
			}
		}
		
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataGrid selectDepts(PageParam param,SyDept dept){
		DataGrid data=new DataGrid();
//		StringBuffer sb=new StringBuffer("from SyDept d where 1=1 ");
//		List list=new ArrayList();
//		if(StringUtils.isNotBlank(dept.getDeptName())){
//			sb.append(" and d.deptName like ? ");
//			list.add("%"+dept.getDeptName()+"%");
//		}
//		data.setTotal((Long)dao.findUniqueOne("select count(*)"+sb,list));
//		param.appendOrderBy(sb);//排序
//		List<Map<String,Object>> rows=dao.findPage(sb.toString(),param.getPage(),param.getRows(),list);
//		data.setRows(rows);
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();parm.put("deptName", dept.getDeptName());
		List lst = dao.selectByDeptName(parm);
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	
	}
	
	
}
