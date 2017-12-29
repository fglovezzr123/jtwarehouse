package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.OpenHzbOrderDao;
import com.wing.socialcontact.service.wx.dao.OpenHzbPayLogDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IOpenHzbOrderService;
import com.wing.socialcontact.service.wx.api.IOpenHzbPayLogService;
import com.wing.socialcontact.service.wx.bean.OpenHzbOrder;

/**
 * 
 * @author liangwj
 * @date 2017-07-20 20:28:03
 * @version 1.0
 */
@Service
public class OpenHzbOrderServiceImpl extends BaseServiceImpl<OpenHzbOrder> implements IOpenHzbOrderService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:OpenHzbOrder:\" + ";

	@Resource
	private OpenHzbOrderDao openHzbOrderDao;

	@Resource
	private IOpenHzbPayLogService openHzbPayLogService;

	@Override
	public DataGrid selectAllOpenHzbOrder(PageParam param, OpenHzbOrder openHzbOrder,Date stime,Date etime,Integer dfklow,Integer dfkhigh) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		// parm.put("columnType", openHzbOrder.getColumnType());
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		} else {
			parm.put("orderby", "OHO.create_time desc");
		}
		if (null != openHzbOrder.getStatus()) {
			parm.put("status", openHzbOrder.getStatus());
		}
		if (null != openHzbOrder.getLevel()) {
			parm.put("level", openHzbOrder.getLevel());
		}
		if (StringUtils.isNotEmpty(openHzbOrder.getKeyword())) {
			parm.put("keyword", openHzbOrder.getKeyword());
		}
		if (null != openHzbOrder.getCreateTime()) {
			parm.put("createTime", openHzbOrder.getCreateTime());
		}
		parm.put("stime",stime );
		parm.put("etime",etime );
		parm.put("dfklow", dfklow);
		parm.put("dfkhigh",dfkhigh );
		List lst = openHzbOrderDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addOpenHzbOrder(OpenHzbOrder openHzbOrder) {
		String id = UUIDGenerator.getUUID();
		openHzbOrder.setId(id);
		int res = openHzbOrderDao.insert(openHzbOrder);
		if (res > 0) {
			return id;
		} else {
			return "";
		}
	}

	@Override
	public String updateOpenHzbOrder(OpenHzbOrder openHzbOrder) {
		if (super.updateByPrimaryKeyCache(openHzbOrder, openHzbOrder.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteOpenHzbOrder(String id) {
		return super.deleteByPrimaryKeyCache(id, OpenHzbOrder.class);
	}

	@Override
	public OpenHzbOrder selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, OpenHzbOrder.class);
	}

	@Override
	public OpenHzbOrder selectById(String id) {
		return openHzbOrderDao.selectByPrimaryKey(id);
	}

	@Override
	public OpenHzbOrder selectByUserId(String userId) {
		List<OpenHzbOrder> openHzbOrderList = openHzbOrderDao.queryByUserId(userId);
		OpenHzbOrder openHzbOrder = null;
		if (null != openHzbOrderList && !openHzbOrderList.isEmpty()) {
			openHzbOrder = openHzbOrderList.get(0);
			openHzbOrder.setPayLogList(openHzbPayLogService.selectByOrderId(openHzbOrder.getId()));
		}
		return openHzbOrder;
	}

}