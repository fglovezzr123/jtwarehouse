package com.wing.socialcontact.service.wx.api;

import java.util.List;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

import com.wing.socialcontact.service.wx.bean.HtmlContent;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:12:34
 * @version 1.0
 */
public interface IHtmlContentService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-04-04 00:12:34
	 */
	public int insertHtmlContent(HtmlContent t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:34
	 */
	public int updateHtmlContent(HtmlContent t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int deleteHtmlContent(HtmlContent t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public List<HtmlContent> queryHtmlContent(HtmlContent t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public HtmlContent getHtmlContent(String id);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public DataGrid selectAllHtmlContent(PageParam param, HtmlContent t);
}