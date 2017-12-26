/**  
 * @Project: tjy
 * @Title: IDeptService.java
 * @Package com.oa.manager.system.service
 * @date 2016-4-3 下午5:11:18
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.bean.SyDept;

/**
 * 
 * 类名：IDeptService
 * 功能：部门管理 业务操作
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-3 下午5:11:18
 *
 */
public interface IDeptService {
	

	/**
	 * 根据id获取部门
	 * @param id
	 * @return  部门
	 */
	public SyDept selectByPrimaryKey(String id);
	/**
	 * 添加部门
	 * @param d
	 * @return  添加结果对应的国际资源的key
	 */
	public String addDept(SyDept d);
	
	/**
	 * 查询出所有部门
	 */
	public List<SyDept> selectAllDepts();
	/**
	 * 查询出所有部门，id deptName superId
	 * @return
	 */
	public List<Map<String,Object>> selectAllDeptsMap();
	
	/**
	 * 修改部门
	 * @param d
	 * @return
	 */
	public String updateDept(SyDept d);
	
	/**
	 * 删除部门
	 * @param id
	 * @return
	 */
	public String deleteDept(String id);

	/**
	 * 部门条件分页查询
	 * @param param
	 * @param dept
	 * @return
	 */
	public DataGrid selectDepts(PageParam param,SyDept dept);
	
}
