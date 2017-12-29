package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.dao.WalletLogDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.service.wx.api.IUserHonorService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.bean.Topic;
import com.wing.socialcontact.service.wx.bean.WalletLog;

/**
 * 
 * @author zengmin
 * @date 2017-04-11 08:52:35
 * @version 1.0
 */
@Service
public class WalletLogServiceImpl extends BaseServiceImpl<WalletLog> implements IWalletLogService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:WalletLog:\" + ";

	@Resource
	private WalletLogDao walletLogDao;
	@Autowired
	private IUserHonorService userHonorService;

	@Override
	public DataGrid selectAllWalletLog(PageParam param, String nickname, String type, String remark, String mobile,String userId) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("nickname", nickname);
		parm.put("type", type);
		parm.put("remark", remark);
		parm.put("mobile", mobile);
		parm.put("userId", userId);
		List lst = walletLogDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addWalletLog(WalletLog walletLog) {
		String id = UUIDGenerator.getUUID();
		walletLog.setId(id);
		int res = walletLogDao.insert(walletLog);
		String type = walletLog.getType();
		String userId = walletLog.getUserId();
		int jbAmount = 0;
		if(!StringUtils.isEmpty(type)&&!StringUtils.isEmpty(userId)){
			jbAmount = walletLogDao.selectJbSum(userId);
			//挥金如土	累计打赏超过100wJ币
			if(jbAmount>=1000000){
				userHonorService.addUserAndHonor(userId, "honor_007");
			}
		}
		if (res > 0) {
			return id;
		} else {
			return "";
		}
	}

	@Override
	public WalletLog selectById(String id) {
		return walletLogDao.selectByPrimaryKey(id);
	}

	@Override
	public WalletLog selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, WalletLog.class);
	}

	@Override
	public boolean updateWalletLog(WalletLog walletLog) {
		return super.updateByPrimaryKeyCache(walletLog, walletLog.getId());
	}

	@Override
	public List<Map<String, Object>> selectByUserIdAndTypeAndStatus(String userId, String type, String status) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		param.put("payStatus", status);
		param.put("userId", userId);
		param.put("deleted", 0);
		param.put("orderby", "create_time desc");
		return walletLogDao.query(param);
	}

	@Override
	public List<Map<String, Object>> selectWalletLogPageByUserIdAndType(String id, String type, int pageNum,
			int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		param.put("userId", id);
		param.put("deleted", 0);
		param.put("fyFlag", 1);
		param.put("start", (pageNum - 1) * pageSize);
		param.put("size", pageSize);
		param.put("orderby", "create_time desc");
		return walletLogDao.query(param);
	}
	
	@Override
	public Object selectCzWalletLogByParam(PageParam param, Map<String, Object> mapParam) {
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		mapParam.put("businessType", 1);
		List lst = walletLogDao.selectByParam(mapParam);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	
	@Override
	public boolean deletewalletLog(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					WalletLog r = selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, WalletLog.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public Integer selectRewardSum(String businessType, String fkId) {
		Map parm = new HashMap();
		parm.put("businessType", businessType);
		parm.put("fkId", fkId);
		Integer count = walletLogDao.selectRewardSum(parm);
		return count;
	}

}