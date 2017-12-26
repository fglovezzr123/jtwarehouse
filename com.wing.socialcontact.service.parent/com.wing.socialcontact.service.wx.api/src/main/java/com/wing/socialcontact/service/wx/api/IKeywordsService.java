package com.wing.socialcontact.service.wx.api;

import java.util.List;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

import com.wing.socialcontact.service.wx.bean.Keywords;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
public interface IKeywordsService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 */
	public int insertKeywords(Keywords t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateKeywords(Keywords t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateSelectiveKeywords(Keywords t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int deleteKeywords(Keywords t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public List<Keywords> queryKeywords(Keywords t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public Keywords getKeywords(String id);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllKeywords(PageParam param, Keywords t);
	/**
	 * top关键词查询
	 * @param types
	 * @param top
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllTop(int types,int top);
}