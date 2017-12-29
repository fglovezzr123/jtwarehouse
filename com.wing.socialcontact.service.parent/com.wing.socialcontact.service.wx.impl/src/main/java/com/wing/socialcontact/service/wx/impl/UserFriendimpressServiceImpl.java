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
import com.wing.socialcontact.service.wx.dao.UserFriendimpressDao;
import com.wing.socialcontact.service.wx.dao.UserFriendimpressDao;
import com.wing.socialcontact.service.wx.api.IUserFriendimpressService;
import com.wing.socialcontact.service.wx.bean.UserFriendimpress;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author gaojun
 * @date 2017-04-14 22:34:36
 * @version 1.0
 */
@Service
public class UserFriendimpressServiceImpl extends BaseServiceImpl<UserFriendimpress>
		implements IUserFriendimpressService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:userFriendimpress:\" + ";

	@Resource
	private UserFriendimpressDao userFriendimpressDao;
	
	public List selectAllUserFriendimpress(UserFriendimpress userFriendimpress){
		return userFriendimpressDao.selectAllUserFriendimpressMap(userFriendimpress);
	}
	
	@Override
	public DataGrid selectAllUserFriendimpress(PageParam param, UserFriendimpress userFriendimpress) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		// parm.put("columnType", userFriendimpress.getColumnType());
		parm.put("orderStr", orderStr);
		List lst = userFriendimpressDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addUserFriendimpress(UserFriendimpress userFriendimpress) {
		int res = userFriendimpressDao.insert(userFriendimpress);
		if (res > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateUserFriendimpress(UserFriendimpress userFriendimpress) {
		if (super.updateByPrimaryKeyCache(userFriendimpress, userFriendimpress.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteUserFriendimpress(String[] ids) {
		// 等待删除的对象集合
		int count = 0;
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				String[] myids = id.split(",");
				for (String string : myids) {
					UserFriendimpress r = selectByPrimaryKey(string);
					if (r != null) {
						if (super.deleteByPrimaryKeyCache(string, UserFriendimpress.class))
							count++;
					}
				}
			}
		}
		return count > 0;
	}

	@Override
	public UserFriendimpress selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, UserFriendimpress.class);
	}

	@Override
	public UserFriendimpress selectById(String id) {
		return userFriendimpressDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Map<String, Object>> selectByUserIdAndType(String userId, int type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("type", type);
		//param.put("deleted", 0);
		return userFriendimpressDao.selectByParam(param);
	}
	
	@Override
	public List<Map<String, Object>> selectByUserIdAndType(String createUserId,String userId, int type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("createUserId", createUserId);
		param.put("userId", userId);
		param.put("type", type);
		//param.put("deleted", 0);
		return userFriendimpressDao.selectByParam(param);
	}
	
	@Override
	public List<Map<String, Object>> selectcountByUserId(String userId, int type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("type", type);
		//param.put("deleted", 0);
		return userFriendimpressDao.selectcountByUserId(param);
	}

}