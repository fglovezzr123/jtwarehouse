package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.RewardDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IRewardService;
import com.wing.socialcontact.service.wx.bean.Reward;

/**
 * 
 * @author zhangfan
 * @date 2017-06-12 20:37:47
 * @version 1.0
 */
@Service
public class RewardServiceImpl extends BaseServiceImpl<Reward> implements IRewardService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:Reward:\" + ";
	
	@Resource
	private RewardDao rewardDao;

	@Override
	public DataGrid selectAllReward(PageParam param, Reward reward, String startTime, String endTime,
			String createUserId,String startTimeyx,String endTimeyx,String userId) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("question", reward.getQuestion());
		parm.put("voType", reward.getVoType());
		parm.put("status", reward.getStatus());
		parm.put("startTime", startTime);
		parm.put("createUserId", createUserId);
		parm.put("userId", userId);
		parm.put("endTime", endTime);
		parm.put("startTimeyx", startTimeyx);
		parm.put("endTimeyx", endTimeyx);
		parm.put("orderStr", orderStr);
		List lst = rewardDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	@Override
	public DataGrid selectAllReward2(PageParam param, Reward reward, String startTime, String endTime,
			String createUserId,String startTimeyx,String endTimeyx,String userId) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("question", reward.getQuestion());
		parm.put("voType", reward.getVoType());
		parm.put("status", reward.getStatus());
		parm.put("startTime", startTime);
		parm.put("createUserId", createUserId);
		parm.put("userId", userId);
		parm.put("endTime", endTime);
		parm.put("startTimeyx", startTimeyx);
		parm.put("endTimeyx", endTimeyx);
		parm.put("orderStr", orderStr);
		List lst = rewardDao.selectByParam2(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addReward(Reward reward) {
		int res = rewardDao.insert(reward);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateReward(Reward reward) {
		if(super.updateByPrimaryKeyCache(reward,reward.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteRewards(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					Reward r = selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, Reward.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public Reward selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Reward.class);
	}

	@Override
	public Reward selectById(String id) {
		return rewardDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateStatus(String[] ids) {
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					Reward r = selectById(string);
					if(r!=null){
						r.setStatus(3);	
						updateReward(r);
						count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public Map<String, Object> selectRewardById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = null;
		list = rewardDao.selectRewardById(id);
		if(list!=null&&list.size()>0){
			map = (Map<String, Object>) list.get(0);
		}
		return map;
	}

	@Override
	public List selectPastReward() {
		return rewardDao.selectPastReward();
	}

	@Override
	public List selectFrontReward(Integer page, Integer size, String question, String voType, String createUserId,
			Integer type,Integer rewardFinish,String curruserId) {
		Map parm = new HashMap();
		parm.put("question", question);
		parm.put("voType", voType);
		if(page!=null&&size!=null){
			parm.put("start", (page-1)*size);
			parm.put("size", size);
		}
		parm.put("createUserId", createUserId);
		parm.put("type", type);
		parm.put("rewardFinish", rewardFinish);
		parm.put("curruserId", curruserId);
		List lst = rewardDao.selectFrontByParam(parm);
		return lst;
	}

	@Override
	public List selectMyAttention(String userId, Integer page, Integer size) {
		Map parm = new HashMap();
		parm.put("userId", userId);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		List lst = rewardDao.selectMyAttention(parm);
		return lst;
	}

	@Override
	public List selectZjTopList(Integer top) {
		Map parm = new HashMap();
		parm.put("top", top);
		List lst = rewardDao.selectZjTopList(parm);
		return lst;
	}

	@Override
	public List selectXsTopList(Integer top) {
		Map parm = new HashMap();
		parm.put("top", top);
		List lst = rewardDao.selectXsTopList(parm);
		return lst;
	}

	@Override
	public List selectYdTopList(Integer top) {
		Map parm = new HashMap();
		parm.put("top", top);
		List lst = rewardDao.selectYdTopList(parm);
		return lst;
	}

	@Override
	public String selectZjPm(String curruserId) {
		String pm = "";
		Map<String, Object> map = new HashMap<String, Object>();
		Map parm = new HashMap();
		parm.put("userId", curruserId);
		List lst = rewardDao.selectZjPm(parm);
		if(lst!=null&&lst.size()>0){
			map = (Map<String, Object>) lst.get(0);
			pm = map.get("rownum").toString();
		}
		return pm;
	}

	@Override
	public String selectXsPm(String curruserId) {
		String pm = "";
		Map<String, Object> map = new HashMap<String, Object>();
		Map parm = new HashMap();
		parm.put("userId", curruserId);
		List lst = rewardDao.selectXsPm(parm);
		if(lst!=null&&lst.size()>0){
			map = (Map<String, Object>) lst.get(0);
			pm = map.get("rownum").toString();
		}
		return pm;
	}

	@Override
	public String selectYdPm(String curruserId) {
		String pm = "";
		Map map = new HashMap();
		Map parm = new HashMap();
		parm.put("userId", curruserId);
		List lst = rewardDao.selectYdPm(parm);
		if(lst!=null&&lst.size()>0){
			map = (Map) lst.get(0);
			pm = map.get("rownum").toString();
		}
		return pm;
	}

	@Override
	public List selectMyReward(String userId, Integer page, Integer size) {
		Map parm = new HashMap();
		parm.put("userId", userId);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		List lst = rewardDao.selectMyReward(parm);
		return lst;
	}


}