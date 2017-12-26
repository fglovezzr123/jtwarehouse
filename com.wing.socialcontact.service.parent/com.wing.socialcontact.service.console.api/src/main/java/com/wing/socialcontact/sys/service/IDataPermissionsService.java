/**  
 * @Title: IDataPermissions.java
 * @date 2016-10-18 下午4:06:08
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service;

import org.springframework.cache.annotation.Cacheable;

import com.wing.socialcontact.sys.bean.SyDataPermissions;

/**
 * 
 * @author	dijuli
 * @version	 1.0
 *
 */
public interface IDataPermissionsService{
	

	/**
	 * 根据类型编码查询，如果不存在则创建一个并返回,存在则直接返回
	 * @param per
	 * @return
	 */
	public SyDataPermissions updateSelectOne(SyDataPermissions per);
	
	/**
	 * 修改
	 * @param per
	 * @return
	 */
	public String updateSyDataPermissions(SyDataPermissions per);
	
	/**
	 * 根据类型获取查询规则 如果没有查到此类型查询规则 则返回Null
	 * @param type
	 * @return
	 */
	public String selectRules(String type);
	

	public SyDataPermissions selectByPrimaryKey(String id);
	
}
