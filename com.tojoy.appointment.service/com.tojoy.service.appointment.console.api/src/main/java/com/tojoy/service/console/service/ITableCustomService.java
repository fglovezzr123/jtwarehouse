/**  
 * @Project: tjy
 * @Title: ITableCustomService.java
 * @Package com.oa.manager.system.service
 * @date 2016-6-17 上午11:57:36
 * @Copyright: 2016 
 */
package com.tojoy.service.console.service;

import java.util.List;

import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;
import com.tojoy.service.console.bean.SyTableCustom;

/**
 * 
 * 类名：ITableCustomService
 * 功能：
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-6-17 上午11:57:36
 *
 */
public interface ITableCustomService{
	/**
	 * 分页 条件查询
	 * @param param
	 * @param t
	 * @return
	 */
	public DataGrid selectTableCustoms(PageParam param,SyTableCustom t);
	/**
	 * 根据类型查询出模块的自定义内容
	 * @param type
	 * @return
	 */
	public List<SyTableCustom> selectTableCustom(short type);
	
	
	/**
	 * 添加
	 * @param tc
	 * @return
	 */
	public String addTableCustom(SyTableCustom tc);
	/**
	 * 开发模式--修改
	 * @param tc
	 * @return
	 */
	public String updateDevTableCustom(SyTableCustom tc);
	/**
	 * 用户自定义设置修改，只能修改部分属性
	 * @param tc
	 * @return
	 */
	public String updateTableCustom(SyTableCustom tc);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteTableCustom(String[] ids);
	public SyTableCustom selectByPrimaryKey(String id);
	
}
