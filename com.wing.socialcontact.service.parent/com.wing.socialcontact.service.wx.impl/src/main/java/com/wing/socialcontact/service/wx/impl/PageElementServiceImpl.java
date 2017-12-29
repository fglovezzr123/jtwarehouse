package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.wing.socialcontact.service.wx.bean.PageContentType;
import com.wing.socialcontact.service.wx.dao.PageContentTypeDao;
import com.wing.socialcontact.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.PageElementDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IPageElementService;
import com.wing.socialcontact.service.wx.bean.PageElement;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:29
 * @version 1.0
 */
@Service
public class PageElementServiceImpl extends BaseServiceImpl<PageElement> implements IPageElementService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:PageElement:\" + ";

	@Resource
	private PageElementDao pageElementDao;
	@Resource
	private PageContentTypeDao pageContentTypeDao;

	@Override
	public DataGrid selectAllPageElement(PageParam param, PageElement pageElement) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		// parm.put("columnType", pageElement.getColumnType());
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		}else{
			parm.put("orderby", "order_num asc");
		}
		parm.put("columnId", pageElement.getColumnId());
		List lst = pageElementDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addPageElement(PageElement pageElement) {
		int res = pageElementDao.insert(pageElement);
		if (res > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updatePageElement(PageElement pageElement) {
		if (super.updateByPrimaryKeyCache(pageElement, pageElement.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deletePageElement(String id) {
		return super.deleteByPrimaryKeyCache(id, PageElement.class);
	}

	@Override
	public PageElement selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, PageElement.class);
	}

	@Override
	public PageElement selectById(String id) {
		return pageElementDao.selectByPrimaryKey(id);
	}

	@Override
	public List<PageElement> selectAllPageElementByColumnId(String columnId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("columnId", columnId);
		param.put("orderby", "order_num asc");
		List<PageElement> pageElements = pageElementDao.query(param);
		for (PageElement pageElement : pageElements) {
			if (!StringUtil.isEmpty(pageElement.getContentTypeId())) {
				PageContentType pageContentType = pageContentTypeDao.selectByPrimaryKey(pageElement.getContentTypeId());
				pageElement.setElementKey(pageContentType.getContentKey());
			}
		}
		return pageElements;
	}
	
	@Override
	public boolean deletePageElementByColumnId(String columnId) {
		return pageElementDao.deleteByColumnId(columnId);
	}

}