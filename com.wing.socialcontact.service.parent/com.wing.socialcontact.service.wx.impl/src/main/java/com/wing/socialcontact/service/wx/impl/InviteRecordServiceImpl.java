package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.InviteRecordDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IInviteRecordService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.InviteRecord;

/**
 * 
 * @author zengmin
 * @date 2017-07-13 09:51:06
 * @version 1.0
 */
@Service
public class InviteRecordServiceImpl extends BaseServiceImpl<InviteRecord> implements IInviteRecordService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:InviteRecord:\" + ";

	@Resource
	private InviteRecordDao inviteRecordDao;

	@Resource
	private IWxUserService wxUserService;

	@Resource
	private ITjyUserService tjyUserService;

	@Override
	public DataGrid selectAllInviteRecord(PageParam param, InviteRecord inviteRecord) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		// parm.put("columnType", inviteRecord.getColumnType());
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		} else {
			parm.put("orderby", "create_time asc");
		}
		List lst = inviteRecordDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addInviteRecord(InviteRecord inviteRecord) {
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("userId", inviteRecord.getUserId());
		parm.put("byqOpenId", inviteRecord.getByqOpenId());
		List lst = inviteRecordDao.query(parm);
		if (null != lst && !lst.isEmpty()) {
			return MsgConfig.MSG_KEY_FAIL;
		}
		int res = inviteRecordDao.insert(inviteRecord);
		if (res > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateInviteRecord(InviteRecord inviteRecord) {
		if (super.updateByPrimaryKeyCache(inviteRecord, inviteRecord.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteInviteRecord(String id) {
		return super.deleteByPrimaryKeyCache(id, InviteRecord.class);
	}

	@Override
	public InviteRecord selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, InviteRecord.class);
	}

	@Override
	public InviteRecord selectById(String id) {
		return inviteRecordDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Map<String, Object>> selectInviteRecordPageByUserId(String userId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);// 分页
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return inviteRecordDao.queryByUserId(param);
	}
	
	@Override
	public List<Map<String, Object>> selectInviteRecordPageByOpenId(String openId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("openId", openId);
		return inviteRecordDao.queryByOpenId(param);
	}

}