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
import com.wing.socialcontact.service.wx.api.IOpenScreenAdvertisService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.IndexAd;
import com.wing.socialcontact.service.wx.bean.OpenScreenAdvertis;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.service.wx.dao.OpenScreenAdvertisDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class OpenScreenAdvertisServiceImpl extends BaseServiceImpl<OpenScreenAdvertis> implements IOpenScreenAdvertisService
{
	@Resource
	private OpenScreenAdvertisDao openScreenAdvertisDao;
	@Resource
	private IWxUserService wxUserService;
	@Resource
	private ITjyUserService tjyUserService;
	
	@Override
	public DataGrid selectAllAdvertis(PageParam param, OpenScreenAdvertis advertis)
	{
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		if(null != advertis.getStatus()){
			parm.put("status", advertis.getStatus());
		}
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		} else {
			parm.put("orderby", "order_num asc");
		}
		List lst = openScreenAdvertisDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addAdvertis(OpenScreenAdvertis advertis)
	{
		int res = openScreenAdvertisDao.insert(advertis);
		if (res > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateAdvertis(OpenScreenAdvertis advertis)
	{
		if (super.updateByPrimaryKeyCache(advertis, advertis.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteAdvertis(String id)
	{
		return super.deleteByPrimaryKeyCache(id, OpenScreenAdvertis	.class);
	}

	@Override
	public OpenScreenAdvertis selectByPrimaryKey(String key)
	{
		return super.selectByPrimaryKeyCache(key, OpenScreenAdvertis.class);
	}

	@Override
	public OpenScreenAdvertis selectById(String id)
	{
		return openScreenAdvertisDao.selectByPrimaryKey(id);
	}

	@Override
	public OpenScreenAdvertis selectAdvertisByUserId(String userId)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", 1);
		param.put("orderby", "order_num asc");
		List<OpenScreenAdvertis> lst = openScreenAdvertisDao.query(param);
		if (null == lst || lst.isEmpty()) {
			return null;
		}
		WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		if (null == tjyUser) {
			return null;
		}
		
		return null;
	}

	@Override
	public List selectAdvertisList()
	{
		return openScreenAdvertisDao.selectAdvertisList();
	}

}
