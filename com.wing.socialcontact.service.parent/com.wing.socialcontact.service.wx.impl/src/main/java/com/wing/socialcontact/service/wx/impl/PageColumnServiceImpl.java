package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.PageColumnDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IPageColumnService;
import com.wing.socialcontact.service.wx.api.IPageElementService;
import com.wing.socialcontact.service.wx.bean.PageColumn;
import com.wing.socialcontact.service.wx.bean.PageElement;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:28
 * @version 1.0
 */
@Service
public class PageColumnServiceImpl extends BaseServiceImpl<PageColumn> implements IPageColumnService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:PageColumn:\" + ";

	@Resource
	private PageColumnDao pageColumnDao;

	@Resource
	private IPageElementService pageElementService;

	@Override
	public DataGrid selectAllPageColumn(PageParam param, PageColumn pageColumn) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		// parm.put("columnType", pageColumn.getColumnType());
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		} else {
			parm.put("orderby", "order_num asc");
		}
		parm.put("pageId", pageColumn.getPageId());
		List lst = pageColumnDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addPageColumn(PageColumn pageColumn) {
		String id = UUIDGenerator.getUUID();
		pageColumn.setId(id);
		int res = pageColumnDao.insert(pageColumn);
		if (res > 0) {
			List<PageElement> elementList = pageColumn.getElementList();
			if (null != elementList && !elementList.isEmpty()) {
				for (PageElement pageElement : elementList) {
					if (StringUtils.isEmpty(pageElement.getId())) {
						pageElement.setId(null);
					}
					pageElement.setColumnId(id);
					pageElementService.addPageElement(pageElement);
				}
			}
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updatePageColumn(PageColumn pageColumn) {
		PageColumn pageColumnOld = selectByPrimaryKey(pageColumn.getId());
		boolean clearElementFlag = false;
		if(pageColumn.getColumnType() == 2){
			clearElementFlag = true;
		}
		if (super.updateByPrimaryKeyCache(pageColumn, pageColumn.getId())) {
			if(clearElementFlag){
				pageElementService.deletePageElementByColumnId(pageColumn.getId());
			}else{
				List<PageElement> elementList = pageColumn.getElementList();
				if (null != elementList && !elementList.isEmpty()) {
					for (PageElement pageElement : elementList) {
						if (StringUtils.isNotEmpty(pageElement.getId())) {
							pageElementService.updatePageElement(pageElement);
						} else {
							pageElement.setId(null);
							pageElement.setColumnId(pageColumn.getId());
							pageElementService.addPageElement(pageElement);
						}
					}
				}
			}
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deletePageColumn(String id) {
		boolean bo = super.deleteByPrimaryKeyCache(id, PageColumn.class);
		if (bo) {
			pageElementService.deletePageElementByColumnId(id);
		}
		return bo;
	}

	@Override
	public PageColumn selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, PageColumn.class);
	}

	@Override
	public PageColumn selectById(String id) {
		return pageColumnDao.selectByPrimaryKey(id);
	}

	@Override
	public List<PageColumn> selectByPageId(String pageId) {
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("orderby", "order_num asc");
		parm.put("pageId", pageId);
		List<PageColumn> resultList = pageColumnDao.query(parm);
		if (null != resultList && !resultList.isEmpty()) {
			for (PageColumn pageColumn : resultList) {
				pageColumn.setElementList(pageElementService.selectAllPageElementByColumnId(pageColumn.getId()));
			}
		}
		return resultList;
	}
}