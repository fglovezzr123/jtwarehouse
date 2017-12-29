package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.HzbManagerLogDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IHzbManagerLogService;
import com.wing.socialcontact.service.wx.bean.HzbManagerLog;

/**
 * 
 * @author liangwj
 * @date 2017-07-22 18:21:15
 * @version 1.0
 */
@Service
public class HzbManagerLogServiceImpl extends BaseServiceImpl<HzbManagerLog> implements IHzbManagerLogService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:HzbManagerLog:\" + ";

	@Resource
	private HzbManagerLogDao hzbManagerLogDao;

	@Override
	public DataGrid selectAllHzbManagerLog(PageParam param, HzbManagerLog hzbManagerLog) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		// parm.put("columnType", hzbManagerLog.getColumnType());
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		} else {
			parm.put("orderby", "HML.manager_time desc");
		}
		parm.put("userId", hzbManagerLog.getUserId());
		List lst = hzbManagerLogDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addHzbManagerLog(HzbManagerLog hzbManagerLog) {
		int res = hzbManagerLogDao.insert(hzbManagerLog);
		if (res > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateHzbManagerLog(HzbManagerLog hzbManagerLog) {
		if (super.updateByPrimaryKeyCache(hzbManagerLog, hzbManagerLog.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteHzbManagerLog(String id) {
		return super.deleteByPrimaryKeyCache(id, HzbManagerLog.class);
	}

	@Override
	public HzbManagerLog selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, HzbManagerLog.class);
	}

	@Override
	public HzbManagerLog selectById(String id) {
		return hzbManagerLogDao.selectByPrimaryKey(id);
	}

	@Override
	public List<HzbManagerLog> selectByUserId(String userId) {
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("orderby", "manager_time asc");
		parm.put("userId", userId);
		return hzbManagerLogDao.list(parm);
	}

	@Override
	public List<Map<String, Object>> selectHzbLogPageByUserId(String userId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);// 分页
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("orderby", "HML.manager_time desc");
		return hzbManagerLogDao.queryByQt(param);
	}

	@Override
	public double selectHzbLcjeByUserIdAndTime(String userId, Date startDate, Date endDate) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startDate", DateUtil.date2String(startDate,"yyyy-MM-dd HH:mm:ss"));
		param.put("endDate", DateUtil.date2String(endDate,"yyyy-MM-dd HH:mm:ss"));
		return hzbManagerLogDao.queryHzbLcjeByUserIdAndTime(param);
	}

	@Override
	public double selectHzbLxjeByUserIdAndTime(String userId, Date startDate, Date endDate) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startDate", DateUtil.date2String(startDate,"yyyy-MM-dd HH:mm:ss"));
		param.put("endDate", DateUtil.date2String(endDate,"yyyy-MM-dd HH:mm:ss"));
		return hzbManagerLogDao.queryHzbLxjeByUserIdAndTime(param);
	}
}