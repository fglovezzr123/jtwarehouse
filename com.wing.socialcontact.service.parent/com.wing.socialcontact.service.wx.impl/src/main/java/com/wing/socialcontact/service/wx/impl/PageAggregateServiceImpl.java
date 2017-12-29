package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.PageAggregateDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.RedisCache;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IBannerService;
import com.wing.socialcontact.service.wx.api.IPageAggregateService;
import com.wing.socialcontact.service.wx.api.IPageColumnService;
import com.wing.socialcontact.service.wx.api.IPageElementService;
import com.wing.socialcontact.service.wx.api.IPageQuickEntryService;
import com.wing.socialcontact.service.wx.bean.Banner;
import com.wing.socialcontact.service.wx.bean.PageAggregate;
import com.wing.socialcontact.service.wx.bean.PageColumn;
import com.wing.socialcontact.service.wx.bean.PageQuickEntry;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:28
 * @version 1.0
 */
@Service
public class PageAggregateServiceImpl extends BaseServiceImpl<PageAggregate> implements IPageAggregateService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:PageAggregate:\" + ";

	@Resource
	private PageAggregateDao pageAggregateDao;

	@Resource
	private RedisCache redisCache;

	@Resource
	private IPageColumnService pageColumnService;

	@Resource
	private IPageQuickEntryService pageQuickEntryService;

	@Resource
	private IPageElementService pageElementService;

	@Resource
	private IBannerService bannerService;

	@Override
	public DataGrid selectAllPageAggregate(PageParam param, PageAggregate pageAggregate) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		// parm.put("columnType", pageAggregate.getColumnType());
		if (StringUtils.isNotEmpty(pageAggregate.getPageName())) {
			parm.put("pageName", pageAggregate.getPageName());
		}
		if (null != pageAggregate.getStatus()) {
			parm.put("status", pageAggregate.getStatus());
		}
		if (null != pageAggregate.getPageType()) {
			parm.put("pageType", pageAggregate.getPageType());
		}
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		}
		List lst = pageAggregateDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addPageAggregate(PageAggregate pageAggregate) {
		int res = pageAggregateDao.insert(pageAggregate);
		if (res > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updatePageAggregate(PageAggregate pageAggregate) {
		if (super.updateByPrimaryKeyCache(pageAggregate, pageAggregate.getId())) {
			//删除页面缓存
			String catchkey = "DB:" + PageAggregate.class.getName() + "Page:" + pageAggregate.getId();
			redisCache.evict(catchkey);
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deletePageAggregate(String id) {
		return super.deleteByPrimaryKeyCache(id, PageAggregate.class);
	}

	@Override
	public PageAggregate selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, PageAggregate.class);
	}

	@Override
	public PageAggregate selectById(String id) {
		return pageAggregateDao.selectByPrimaryKey(id);
	}

	@Override
	public PageAggregate selectPageById(String id) {
		String catchkey = "DB:" + PageAggregate.class.getName() + "Page:" + id.toString();
		ValueWrapper obj = redisCache.get(catchkey);
		if (null != obj) {
			return (PageAggregate) obj.get();
		}
		PageAggregate pageAggregate = selectByPrimaryKey(id);
		if (null == pageAggregate) {
			return null;
		}
		// 获取banner
		if (pageAggregate.getLbtFlag() == 1) {
			if (StringUtils.isNotEmpty(pageAggregate.getLbtId())) {
				List<Banner> bannerList = bannerService.selectByColumnType(pageAggregate.getLbtId());
				pageAggregate.setBannerList(bannerList);
			}
		}
		// 获取快捷方式
		List<PageQuickEntry> pageQuickEntryList = pageQuickEntryService.selectByPageId(id);
		pageAggregate.setPageQuickEntryList(pageQuickEntryList);
		// 获取栏目及元素
		List<PageColumn> pageColumnList = pageColumnService.selectByPageId(id);
		pageAggregate.setPageColumnList(pageColumnList);
		String pageColumnJsonStr=JSON.toJSONString(pageColumnList);
		pageAggregate.setPageColumnJsonStr(pageColumnJsonStr);
		redisCache.put(catchkey, pageAggregate);
		return pageAggregate;
	}
	
	@Override
	public List<PageAggregate> selectPageByNameAndStatus(String pageName, int status) {
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("pageName", pageName);
		parm.put("status", status);
		return pageAggregateDao.queryByName(parm);
	}

}