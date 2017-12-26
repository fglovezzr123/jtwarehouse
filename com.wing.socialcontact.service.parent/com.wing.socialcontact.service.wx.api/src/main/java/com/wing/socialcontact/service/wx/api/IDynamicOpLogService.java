package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.DynamicOpLog;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-19 14:19:54
 * @version 1.0
 */
public interface IDynamicOpLogService{

	/**
	 * 查询出所有
	 * @return
	 */
	public List selectAllDynamicOpLog(DynamicOpLog dynamicOpLog);
	/**
	 * 条件查询
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectDynamicOpLog(PageParam param,DynamicOpLog dynamicOpLog);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addDynamicOpLog(DynamicOpLog dynamicOpLog);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateDynamicOpLog(DynamicOpLog dynamicOpLog);


	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public DynamicOpLog selectById(String id);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteOpLogById(String id);
	

}