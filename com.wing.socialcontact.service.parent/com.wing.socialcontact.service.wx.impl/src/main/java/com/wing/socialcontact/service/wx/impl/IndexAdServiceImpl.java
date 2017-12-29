package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.IndexAdDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IIndexAdService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.IndexAd;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;

/**
 * 
 * @author zengmin
 * @date 2017-07-07 15:45:48
 * @version 1.0
 */
@Service
public class IndexAdServiceImpl extends BaseServiceImpl<IndexAd> implements IIndexAdService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:IndexAd:\" + ";

	@Resource
	private IndexAdDao indexAdDao;

	@Resource
	private IWxUserService wxUserService;

	@Resource
	private ITjyUserService tjyUserService;

	@Override
	public DataGrid selectAllIndexAd(PageParam param, IndexAd indexAd) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		// parm.put("columnType", indexAd.getColumnType());
		if(null != indexAd.getStatus()){
			parm.put("status", indexAd.getStatus());
		}
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		} else {
			parm.put("orderby", "order_num asc");
		}
		List lst = indexAdDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addIndexAd(IndexAd indexAd) {
		int res = indexAdDao.insert(indexAd);
		if (res > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateIndexAd(IndexAd indexAd) {
		if (super.updateByPrimaryKeyCache(indexAd, indexAd.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteIndexAd(String id) {
		return super.deleteByPrimaryKeyCache(id, IndexAd.class);
	}

	@Override
	public IndexAd selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, IndexAd.class);
	}

	@Override
	public IndexAd selectById(String id) {
		return indexAdDao.selectByPrimaryKey(id);
	}

	@Override
	public IndexAd selectIndexAdByUserId(String userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", 1);
		param.put("orderby", "order_num asc");
		List<IndexAd> lst = indexAdDao.query(param);
		if (null == lst || lst.isEmpty()) {
			return null;
		}
		WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		if (null == tjyUser) {
			return null;
		}
		for (IndexAd indexAd : lst) {
			// 全部
			if (indexAd.getUserRange() == 1) {
				return indexAd;
			}
			// 用户等级
			if (StringUtils.isNotEmpty(wxUser.getLevel())) {
				if (indexAd.getUserLevelName().indexOf(wxUser.getLevel()) != -1) {
					// 认证用户可见
					if (indexAd.getReconUserFlag() == 1) {
						if (null != tjyUser.getReconStatus() && tjyUser.getReconStatus() == 2) {
							return indexAd;
						}
					} else {
						// 非认证用户可见
						if (null == tjyUser.getReconStatus() || tjyUser.getReconStatus() != 2) {
							return indexAd;
						}
					}
				}
			}
			// 注册用户
			// if (indexAd.getRegUserFlag() == 1) {
			// return indexAd;
			// }
		}
		return null;
	}
}