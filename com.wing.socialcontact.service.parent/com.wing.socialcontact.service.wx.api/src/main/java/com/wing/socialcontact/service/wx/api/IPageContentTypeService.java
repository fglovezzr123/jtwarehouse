package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.PageContentType;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:28
 * @version 1.0
 */
public interface IPageContentTypeService{
	/**
	 * 条件查询
	 * @param param
	 * @param pageContentType
	 * @return
	 */
	public DataGrid selectAllPageContentType(PageParam param,PageContentType pageContentType);
	/**
	 * 添加
	 * @param pageContentType
	 * @return
	 */
	public String addPageContentType(PageContentType pageContentType);
	/**
	 * 更新
	 * @param pageContentType
	 * @return
	 */
	public String updatePageContentType(PageContentType pageContentType);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deletePageContentType(String[] ids);

	public PageContentType selectByPrimaryKey(String key);
	
	public PageContentType selectById(String id);
	
	public List<PageContentType> selectAllPageContentType();
	

}