package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.PageElement;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:29
 * @version 1.0
 */
public interface IPageElementService {
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllPageElement(PageParam param, PageElement pageElement);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addPageElement(PageElement pageElement);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updatePageElement(PageElement pageElement);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deletePageElement(String id);

	public PageElement selectByPrimaryKey(String key);

	public PageElement selectById(String id);

	public List<PageElement> selectAllPageElementByColumnId(String columnId);

	public boolean deletePageElementByColumnId(String columnId);

}