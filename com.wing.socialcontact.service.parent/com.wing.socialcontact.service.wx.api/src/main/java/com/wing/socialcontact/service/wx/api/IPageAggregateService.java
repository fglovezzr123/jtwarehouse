package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.PageAggregate;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:28
 * @version 1.0
 */
public interface IPageAggregateService {

	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllPageAggregate(PageParam param, PageAggregate pageAggregate);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addPageAggregate(PageAggregate pageAggregate);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updatePageAggregate(PageAggregate pageAggregate);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deletePageAggregate(String id);

	public PageAggregate selectByPrimaryKey(String key);

	public PageAggregate selectById(String id);

	/**
	 * 根据id返回页面相关所有数据
	 * 
	 * @Title: selectPageById
	 * @Description: TODO
	 * @param id
	 * @return
	 * @return: PageAggregate
	 * @author: zengmin
	 * @date: 2017年7月11日 上午8:51:45
	 */
	public PageAggregate selectPageById(String id);

	/**
	 * 根据页面名称获取已启用的页面列表
	 * @Title: selectPageByNameAndStatus 
	 * @Description: TODO
	 * @param pageName
	 * @param status
	 * @return
	 * @return: List<PageAggregate>
	 * @author: zengmin   
	 * @date: 2017年8月18日 下午12:03:52
	 */
	public List<PageAggregate> selectPageByNameAndStatus(String pageName, int status);

}