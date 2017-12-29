package com.wing.socialcontact.service.wx.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.RewardSetDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IRewardSetService;
import com.wing.socialcontact.service.wx.bean.RewardSet;

/**
 * 
 * @author zhangfan
 * @date 2017-06-15 12:03:43
 * @version 1.0
 */
@Service
public class RewardSetServiceImpl extends BaseServiceImpl<RewardSet> implements IRewardSetService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:RewardSet:\" + ";;
	
	@Resource
	private RewardSetDao rewardSetDao;

	@Override
	public String addRewardSet(RewardSet rs) {
		int res = rewardSetDao.insert(rs);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateRewardSet(RewardSet rs) {
		if(super.updateByPrimaryKeyCache(rs,rs.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public RewardSet selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, RewardSet.class);
	}

	@Override
	public RewardSet selectById(String id) {
		return rewardSetDao.selectByPrimaryKey(id);
	}

	@Override
	public List selectRewardSet() {
		List list = null;
		list = rewardSetDao.select(null);
		return list;
	}

}