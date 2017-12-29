package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.WalletLog;

/**
 * 
 * @author zengmin
 * @date 2017-04-11 08:52:35
 * @version 1.0
 */
public interface IWalletLogService {
	public DataGrid selectAllWalletLog(PageParam param, String nickname, String type, String remark, String mobile,String userId);

	public WalletLog selectByPrimaryKey(String key);

	public WalletLog selectById(String id);

	public String addWalletLog(WalletLog walletLog);

	public boolean updateWalletLog(WalletLog walletLog);

	public List<Map<String, Object>> selectByUserIdAndTypeAndStatus(String userId, String type, String status);

	/**
	 * 分页查询钱包记录
	 * 
	 * @param id
	 * @param type
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> selectWalletLogPageByUserIdAndType(String id, String type, int pageNum,
			int pageSize);

	/**
	 * 条件查询充值记录
	 * 
	 * @Title: selectCzWalletLogByParam
	 * @Description: TODO
	 * @param param
	 * @param mapParam
	 * @return
	 * @return: Object
	 * @author: zengmin
	 * @date: 2017年5月22日 下午3:15:17
	 */
	public Object selectCzWalletLogByParam(PageParam param, Map<String, Object> mapParam);
	
	public boolean deletewalletLog(String[] ids);
	
	public Integer selectRewardSum(String businessType,String fkId);

}