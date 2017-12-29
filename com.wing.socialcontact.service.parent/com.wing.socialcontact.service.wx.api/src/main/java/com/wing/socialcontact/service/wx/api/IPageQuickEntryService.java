package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.PageQuickEntry;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:29
 * @version 1.0
 */
public interface IPageQuickEntryService{

	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllPageQuickEntry(PageParam param, PageQuickEntry pageQuickEntry);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addPageQuickEntry(PageQuickEntry pageQuickEntry);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updatePageQuickEntry(PageQuickEntry pageQuickEntry);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deletePageQuickEntry(String id);

	public PageQuickEntry selectByPrimaryKey(String key);

	public PageQuickEntry selectById(String id);

	public List<PageQuickEntry> selectByPageId(String pageId);

}