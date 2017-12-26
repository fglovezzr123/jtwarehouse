package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.BusinessClass;

/**
 * 
 * @author zhangfan
 * @date 2017-04-18 21:18:06
 * @version 1.0
 */
public interface IBusinessClassService{

	/**
	 * 查询出分类
	 * @return
	 */
	public List selectAllClass(Integer queryRows,Integer isRecommend);
	/**
	 * 条件查询
	 * @return
	 */
	public DataGrid selectClasses(PageParam param,BusinessClass bclass);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addClass(BusinessClass bclass);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateClass(BusinessClass bclass);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteClasses(String[] ids);

	public BusinessClass selectByPrimaryKey(String key);
	
	public BusinessClass selectById(String id);
	
	public String deleteClass(String id);

}