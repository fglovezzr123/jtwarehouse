package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.dao.PageContentTypeDao;
import com.wing.socialcontact.service.wx.api.IPageContentTypeService;
import com.wing.socialcontact.service.wx.bean.Honor;
import com.wing.socialcontact.service.wx.bean.PageContentType;
import com.wing.socialcontact.service.wx.bean.TjyNewsClass;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:29
 * @version 1.0
 */
@Service
public class PageContentTypeServiceImpl extends BaseServiceImpl<PageContentType> implements IPageContentTypeService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private PageContentTypeDao pageContentTypeDao;
	


	@Override
	public DataGrid selectAllPageContentType(PageParam param, PageContentType pageContentType) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		//parm.put("orderStr", orderStr);
		List lst = pageContentTypeDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	
	

	@Override
	public String addPageContentType(PageContentType pageContentType) {
		int res = pageContentTypeDao.insert(pageContentType);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updatePageContentType(PageContentType pageContentType) {
		if(super.updateByPrimaryKeyCache(pageContentType,pageContentType.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deletePageContentType(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					PageContentType r=selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, PageContentType.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public PageContentType selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, PageContentType.class);
	}

	@Override
	public PageContentType selectById(String id) {
		return pageContentTypeDao.selectByPrimaryKey(id);
	}
	
	@Override
	public List<PageContentType> selectAllPageContentType() {
		Map parm = new HashMap();
		List lst = pageContentTypeDao.selectByParam(parm);
		return lst;
	}

}