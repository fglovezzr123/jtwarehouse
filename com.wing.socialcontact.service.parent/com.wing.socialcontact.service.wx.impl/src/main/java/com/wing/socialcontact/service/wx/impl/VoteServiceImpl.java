package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.VoteDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IVoteService;
import com.wing.socialcontact.service.wx.bean.Vote;

/**
 * 
 * @author zhangfan
 * @date 2017-04-13 10:46:03
 * @version 1.0
 */
@Service
public class VoteServiceImpl extends BaseServiceImpl<Vote> implements IVoteService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:Vote:\" + ";
	
	@Resource
	private VoteDao voteDao;

	@Override
	public String addVote(Vote vote) {
		Vote parm = new Vote();
		parm.setFkId(vote.getFkId());
		parm.setUserId(vote.getUserId());
		List lst = voteDao.select(parm);
		if(lst.size()==0){
			int res = voteDao.insert(vote);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.vote.unique";//已投过票
		}
		
	}

	@Override
	public String updateVote(Vote vote) {
		if(super.updateByPrimaryKeyCache(vote,vote.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public Vote selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Vote.class);
	}

	@Override
	public Vote selectById(String id) {
		// TODO Auto-generated method stub
		return voteDao.selectByPrimaryKey(id);
	}

	@Override
	public String deleteVote(String id) {
		Vote vote = selectByPrimaryKey(id);
		if(vote!=null){
			if(super.deleteByPrimaryKeyCache(vote.getId(), Vote.class)){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return MsgConfig.MSG_KEY_NODATA;
		}
	}

	@Override
	public String selectMyVoteType(String userId, String fkId) {
		Map parm = new HashMap();
		parm.put("userId", userId);
		parm.put("fkId", fkId);
		List list = null;
		list = voteDao.selectByParam(parm);
		String voteType = "";
		if(list!=null&&list.size()>0){
			Map map = (Map) list.get(0);
			voteType = map.get("vote_type")==null?"":map.get("vote_type").toString();
		}
		return voteType;
	}

}