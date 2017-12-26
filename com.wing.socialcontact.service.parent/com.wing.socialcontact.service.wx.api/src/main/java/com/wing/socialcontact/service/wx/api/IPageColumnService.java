package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.PageColumn;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:28
 * @version 1.0
 */
public interface IPageColumnService {
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllPageColumn(PageParam param, PageColumn pageColumn);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addPageColumn(PageColumn pageColumn);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updatePageColumn(PageColumn pageColumn);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deletePageColumn(String id);

	public PageColumn selectByPrimaryKey(String key);

	public PageColumn selectById(String id);

	public List<PageColumn> selectByPageId(String pageId);

}