package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IWalletTxService;
import com.wing.socialcontact.service.wx.bean.WalletTx;
import com.wing.socialcontact.service.wx.dao.WalletTxDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author zengmin
 * @date 2017-05-04 16:15:41
 * @version 1.0
 */
@Service
public class WalletTxServiceImpl extends BaseServiceImpl<WalletTx> implements IWalletTxService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:WalletTx:\"";

	@Resource
	private WalletTxDao walletTxDao;
	
	
	
	@Override
	public DataGrid selectAllWalletTx(PageParam param,String nickname,String state,String mobile) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("nickname", nickname);
		parm.put("state", state);
		parm.put("mobile", mobile);
		List lst = walletTxDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	
	@Override
	public boolean addWalletTx(WalletTx walletTx) {
		int res = walletTxDao.insert(walletTx);
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public WalletTx selectById(String id) {
		return walletTxDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Map<String, Object>> selectByParam(Map<String, Object> param) {
		return walletTxDao.selectByParam(param);
	}

	@Override
	public WalletTx selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, WalletTx.class);
	}

	@Override
	public List<Map<String, Object>> selectByUserId(String userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userid", userId);
		return walletTxDao.query(param);
	}

	@Override
	public boolean updateWalletTx(WalletTx walletTx) {
		return super.updateByPrimaryKeyCache(walletTx, walletTx.getId());
	}
	
	@Override
	public boolean updateStatus(String[] ids, String status) {
		for (String id : ids) {
			WalletTx o = walletTxDao.selectByPrimaryKey(id);
			if (status != null) {
				o.setState(status);
				walletTxDao.updateByPrimaryKey(o);
			}
		}
		return true;
	}

}