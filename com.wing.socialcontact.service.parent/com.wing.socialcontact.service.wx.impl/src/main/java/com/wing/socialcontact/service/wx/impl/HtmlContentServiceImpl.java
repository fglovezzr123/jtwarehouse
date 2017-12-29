package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

import com.wing.socialcontact.service.wx.dao.HtmlContentDao;
import com.wing.socialcontact.service.wx.api.IHtmlContentService;
import com.wing.socialcontact.service.wx.bean.HtmlContent;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:12:35
 * @version 1.0
 */
@Service
public class HtmlContentServiceImpl implements IHtmlContentService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private HtmlContentDao htmlContentDao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int insertHtmlContent(HtmlContent t) {
		return htmlContentDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int updateHtmlContent(HtmlContent t) {
		return htmlContentDao.updateByPrimaryKey(t);
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public int deleteHtmlContent(HtmlContent t) {
		return htmlContentDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public List<HtmlContent> queryHtmlContent(HtmlContent t) {
		return htmlContentDao.select(t);
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public HtmlContent getHtmlContent(String id) {
		return htmlContentDao.selectByPrimaryKey(id);
	}
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public DataGrid selectAllHtmlContent(PageParam param, HtmlContent t) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		List list = htmlContentDao.selectByParam(parm);
		PageInfo page = new PageInfo(list);
		data.setRows(list);
		data.setTotal(page.getTotal());
		return data;
	}
}