package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.TjyNewsClass;

/**
 * 
 * @author zhangfan
 *
 */
public interface INewsClassService{
	/**
	 * 查询出所有分类
	 * @return
	 */
	public List<TjyNewsClass> selectAllNewsClass();
	/**
	 * 条件查询
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectNewsClasses(PageParam param,TjyNewsClass newsClass,String startTime,String endTime);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addNewsClass(TjyNewsClass newsClass);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateNewsClass(TjyNewsClass newsClass);
	/**
	 * 删除
	 * @return
	 */
	public String deleteNewsClass(String id);

	public TjyNewsClass selectByPrimaryKey(String key);
	
	public TjyNewsClass selectById(String id);
	
	public List<Map<String, Object>> selectAllclassMap(String classRoot);
	
	public List<Map<String, Object>> selectNewsclassMap();
	/**
	 * 查询前端新闻分类
	 * @param classRoot
	 * @return
	 */
	public List<Map<String, Object>> selectFrontClass(String classRoot);
}
