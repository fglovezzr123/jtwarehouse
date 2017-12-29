package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.SysBlacklistDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ISysBlacklistService;
import com.wing.socialcontact.service.wx.bean.SysBlacklist;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.SysBlacklist;

/**
 * 
 * @author zengmin
 * @date 2017-08-08 08:56:15
 * @version 1.0
 */
@Service
public class SysBlacklistServiceImpl extends BaseServiceImpl<SysBlacklist> implements ISysBlacklistService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:SysBlacklist:\" + ";

	@Resource
	private SysBlacklistDao sysBlacklistDao;

	@Override
	public DataGrid selectAllSysBlacklist(PageParam param, SysBlacklist sysBlacklist) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		} else {
			parm.put("orderby", "SB.manager_time desc");
		}
		TjyUser tjyUser = sysBlacklist.getTjyUser();
		if (null != tjyUser) {
			if (StringUtils.isNotEmpty(tjyUser.getMobile())) {
				parm.put("mobile", tjyUser.getMobile());
			}
			if (StringUtils.isNotEmpty(tjyUser.getNickname())) {
				parm.put("userName", tjyUser.getNickname());
			}
		}
		if (StringUtils.isNotEmpty(sysBlacklist.getManagerUserName())) {
			parm.put("managerUserName", sysBlacklist.getManagerUserName());
		}
		List lst = sysBlacklistDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addSysBlacklist(SysBlacklist sysBlacklist) {
		int res = sysBlacklistDao.insert(sysBlacklist);
		if (res > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateSysBlacklist(SysBlacklist sysBlacklist) {
		if (super.updateByPrimaryKeyCache(sysBlacklist, sysBlacklist.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public SysBlacklist selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, SysBlacklist.class);
	}

	@Override
	public SysBlacklist selectById(String id) {
		// TODO Auto-generated method stub
		return sysBlacklistDao.selectByPrimaryKey(id);
	}

	@Override
	public String deleteSysBlacklist(String[] ids) {
		int i = 0;
		for (String id : ids) {
			SysBlacklist sysBlacklist = selectByPrimaryKey(id);
			if (sysBlacklist != null) {
				if (super.deleteByPrimaryKeyCache(sysBlacklist.getId(), SysBlacklist.class)) {
					i++;
				}
			}
		}
		if (i > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean selectSysBlacklistByUserId(String userId) {
		List<SysBlacklist> list = sysBlacklistDao.selectSysBlacklistByUserId(userId);
		if (null == list || list.isEmpty())
			return false;
		return true;
	}
}