package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.OpenHzbPayLogDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IOpenHzbPayLogService;
import com.wing.socialcontact.service.wx.bean.OpenHzbPayLog;

/**
 * 
 * @author liangwj
 * @date 2017-07-20 20:28:03
 * @version 1.0
 */
@Service
public class OpenHzbPayLogServiceImpl extends BaseServiceImpl<OpenHzbPayLog> implements IOpenHzbPayLogService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:OpenHzbPayLog:\" + ";

	@Resource
	private OpenHzbPayLogDao openHzbPayLogDao;

	@Override
	public DataGrid selectAllOpenHzbPayLog(PageParam param, OpenHzbPayLog openHzbPayLog) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		// parm.put("columnType", openHzbPayLog.getColumnType());
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		} else {
			parm.put("orderby", "OHPL.fk_time desc");
		}
		if (StringUtils.isNotEmpty(openHzbPayLog.getKeyword())) {
			parm.put("keyword", openHzbPayLog.getKeyword());
		}
		if (null != openHzbPayLog.getFkType()) {
			parm.put("fkType", openHzbPayLog.getFkType());
		}
		if (null != openHzbPayLog.getLogType()) {
			parm.put("logType", openHzbPayLog.getLogType());
		}
		if (null != openHzbPayLog.getShStatus()) {
			parm.put("shStatus", openHzbPayLog.getShStatus());
		}
		List lst = openHzbPayLogDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public DataGrid selectAllOpenHzbPayLogByOrder(PageParam param, OpenHzbPayLog openHzbPayLog) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		// parm.put("columnType", openHzbPayLog.getColumnType());
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		} else {
			parm.put("orderby", "OHPL.fk_time asc");
		}
		if (StringUtils.isNotEmpty(openHzbPayLog.getOrderId())) {
			parm.put("orderId", openHzbPayLog.getOrderId());
		}
		if (StringUtils.isNotEmpty(openHzbPayLog.getUserId())) {
			parm.put("userId", openHzbPayLog.getUserId());
		}
		if (StringUtils.isNotEmpty(openHzbPayLog.getKeyword())) {
			parm.put("keyword", openHzbPayLog.getKeyword());
		}
		if (null != openHzbPayLog.getFkType()) {
			parm.put("fkType", openHzbPayLog.getFkType());
		}
		if (null != openHzbPayLog.getLogType()) {
			parm.put("logType", openHzbPayLog.getLogType());
		}
		if (null != openHzbPayLog.getShStatus()) {
			parm.put("shStatus", openHzbPayLog.getShStatus());
		}
		List lst = openHzbPayLogDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addOpenHzbPayLog(OpenHzbPayLog openHzbPayLog) {
		if (StringUtils.isEmpty(openHzbPayLog.getId())) {
			String id = UUIDGenerator.getUUID();
			openHzbPayLog.setId(id);
		}
		int res = openHzbPayLogDao.insert(openHzbPayLog);
		if (res > 0) {
			return openHzbPayLog.getId();
		} else {
			return "";
		}
	}

	@Override
	public String updateOpenHzbPayLog(OpenHzbPayLog openHzbPayLog) {
		if (super.updateByPrimaryKeyCache(openHzbPayLog, openHzbPayLog.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteOpenHzbPayLog(String id) {
		return super.deleteByPrimaryKeyCache(id, OpenHzbPayLog.class);
	}

	@Override
	public OpenHzbPayLog selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, OpenHzbPayLog.class);
	}

	@Override
	public OpenHzbPayLog selectById(String id) {
		return openHzbPayLogDao.selectByPrimaryKey(id);
	}

	@Override
	public List<OpenHzbPayLog> selectByOrderId(String orderId) {
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("orderby", "fk_time asc");
		parm.put("orderId", orderId);
		parm.put("logType", (short) 1);
		return openHzbPayLogDao.list(parm);
	}

}