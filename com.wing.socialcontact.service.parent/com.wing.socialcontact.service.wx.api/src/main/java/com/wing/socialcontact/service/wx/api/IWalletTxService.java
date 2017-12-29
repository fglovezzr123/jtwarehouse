package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.WalletTx;

/**
 * 
 * @author zengmin
 * @date 2017-05-04 16:15:40
 * @version 1.0
 */
public interface IWalletTxService {
	
	public DataGrid selectAllWalletTx(PageParam param,String nickname,String state,String mobile);
	
	public WalletTx selectByPrimaryKey(String key);

	public WalletTx selectById(String id);

	public boolean addWalletTx(WalletTx walletTx);

	public boolean updateWalletTx(WalletTx walletTx);

	public List<Map<String, Object>> selectByUserId(String userId);

	public List<Map<String, Object>> selectByParam(Map<String, Object> param);
	
	public boolean updateStatus(String[] ids, String status);
}