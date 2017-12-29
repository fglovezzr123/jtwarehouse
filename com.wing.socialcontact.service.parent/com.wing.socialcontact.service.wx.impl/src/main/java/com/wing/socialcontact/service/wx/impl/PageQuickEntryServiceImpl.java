package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.PageQuickEntryDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IPageQuickEntryService;
import com.wing.socialcontact.service.wx.bean.PageQuickEntry;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:29
 * @version 1.0
 */
@Service
public class PageQuickEntryServiceImpl extends BaseServiceImpl<PageQuickEntry> implements IPageQuickEntryService {

	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:PageQuickEntry:\" + ";

	@Resource
	private PageQuickEntryDao pageQuickEntryDao;

	@Override
	public DataGrid selectAllPageQuickEntry(PageParam param, PageQuickEntry pageQuickEntry) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		// parm.put("columnType", pageQuickEntry.getColumnType());
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		} else {
			parm.put("orderby", "order_num asc");
		}
		parm.put("pageId", pageQuickEntry.getPageId());
		List lst = pageQuickEntryDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addPageQuickEntry(PageQuickEntry pageQuickEntry) {
		int res = pageQuickEntryDao.insert(pageQuickEntry);
		if (res > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updatePageQuickEntry(PageQuickEntry pageQuickEntry) {
		if (super.updateByPrimaryKeyCache(pageQuickEntry, pageQuickEntry.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deletePageQuickEntry(String id) {
		return super.deleteByPrimaryKeyCache(id, PageQuickEntry.class);
	}

	@Override
	public PageQuickEntry selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, PageQuickEntry.class);
	}

	@Override
	public PageQuickEntry selectById(String id) {
		return pageQuickEntryDao.selectByPrimaryKey(id);
	}

	@Override
	public List<PageQuickEntry> selectByPageId(String pageId) {
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("orderby", "order_num asc");
		parm.put("pageId", pageId);
		return pageQuickEntryDao.query(parm);
	}

}