package com.tojoy.service.wx.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tojoy.service.wx.api.IMeetingSignupService;
import com.tojoy.service.wx.bean.MeetingSignup;
import com.tojoy.service.wx.bean.MeetingSignupRemind;
import com.tojoy.service.wx.dao.MeetingSignupDao;
import com.tojoy.service.wx.dao.MeetingSignupRemindDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tojoy.util.RedisCache;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
@Service
public class MeetingSignupServiceImpl implements IMeetingSignupService {
	/**
	 * 缓存的key值
	 */
	private static final String CACHE_PREFIX = "TJY:MeetingSignup:";
	@Resource
	private RedisCache redisCache;
	@Resource
	private MeetingSignupDao meetingSignupDao;
	@Resource
	private MeetingSignupRemindDao meetingSignupRemindDao;
	/**
	 * 查询
	 * @param userId
	 * @param meetingId
	 * @author liangwj
	 */
	@Override
	public MeetingSignup selectByMeetingIdAndUserId(String userId,String meetingId){
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(meetingId)){
			return null;
		}
		String key = CACHE_PREFIX+meetingId+":"+userId;
		
		MeetingSignup ms = redisCache.get(key,MeetingSignup.class);
		if(ms!=null){
			return ms;
		}
		
		ms =  meetingSignupDao.selectByMeetingIdAndUserId(userId, meetingId);
		if(ms!=null){
			redisCache.put(key, ms);
		}
		return ms;
	}
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 */
	@Override
	public int insertMeetingSignup(MeetingSignup t) {
		redisCache.evict(CACHE_PREFIX+t.getMeetingId()+":"+t.getUserId());
		return meetingSignupDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@Override
	public int updateMeetingSignup(MeetingSignup t) {
		redisCache.evict(CACHE_PREFIX+t.getMeetingId()+":"+t.getUserId());
		return meetingSignupDao.updateByPrimaryKey(t);
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@Override
	public int deleteMeetingSignup(MeetingSignup t) {
		redisCache.evict(CACHE_PREFIX+t.getMeetingId()+":"+t.getUserId());
		return meetingSignupDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@Override
	public List<MeetingSignup> queryMeetingSignup(MeetingSignup t) {
		return meetingSignupDao.select(t);
	}
	/**
	 * 单个查询
	 * @param id
	 * @return
	 * @author liangwj
	 */
	@Override
	public MeetingSignup getMeetingSignup(String id) {
		return meetingSignupDao.selectByPrimaryKey(id);
	}
	/**
	 * 分页查询
	 * @param param
	 * @param parm
	 * @return
	 * @author liangwj
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataGrid selectAllMeetingSignup(PageParam param, Map<String,Object> parm) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List list = meetingSignupDao.selectByParam(parm);
		PageInfo page = new PageInfo(list);
		return new DataGrid(page);
	}
	/**
	 * 待提醒会议报名信息新增
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@Override
	public int insertMeetingSignupRemind(MeetingSignupRemind t){
		return meetingSignupRemindDao.insert(t);
	}
	
	/**
	 *  会议预报名提醒报名信息更新
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@Override
	public int updateMeetingSignupRemind(MeetingSignupRemind t){
		String key = "TJY:MeetingSignupRemind:"+t.getFkId()+":"+t.getUserId();
		redisCache.evict(key);
		return meetingSignupRemindDao.updateByPrimaryKeySelective(t);
	}
	
	/**
	 * 待提醒会议报名信息查询
	 * @param param
	 * @return
	 * @author liangwj
	 */
	@Override
	public DataGrid selectAllMeetingSignupRemind(PageParam param) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<MeetingSignupRemind> list = meetingSignupRemindDao.selectMeetingRemind();
		PageInfo<MeetingSignupRemind> page = new PageInfo<MeetingSignupRemind>(list);
		return new DataGrid(page);
	}
	/**
	 * 根据会议ID和用户ID查询预报名信息
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	@Override
	public MeetingSignupRemind getMeetingSignupRemindByMeetingIdAndUserId(String meetingId, String userId) {
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(meetingId)){
			return null;
		}
		String key = "TJY:MeetingSignupRemind:"+meetingId+":"+userId;
		
		MeetingSignupRemind msr = redisCache.get(key,MeetingSignupRemind.class);
		if(msr!=null){
			return msr;
		}
		
		msr =  meetingSignupRemindDao.selectByMeetingIdAndUserId(meetingId,userId);
		if(msr!=null){
			redisCache.put(key, msr);
		}
		
		return msr;
	}
	/**
	 * 会议开始未提醒的数据查询(会议开始前一天的数据)
	 * @return
	 */
	@Override
	public List<MeetingSignup> selectUnRemind(){
		return this.meetingSignupDao.selectUnRemind();
	}
	/**
	 * 报名提醒用户
	 * @param param
	 * @param t
	 * @return
	 */
	@Override
	public DataGrid selectAllMeetingSignupRemind(PageParam param, MeetingSignupRemind t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<MeetingSignupRemind> list = meetingSignupRemindDao.selectByParam(t);
		PageInfo<MeetingSignupRemind> page = new PageInfo<MeetingSignupRemind>(list);
		return new DataGrid(page);
	}
	/**
	 * 报名用户支付状态修改
	 * @param id 
	 * @return
	 */
	@Override
	public int updateForChangestatus(String id) {
		if(StringUtils.isBlank(id)){
			return 0;
		}else{
			return meetingSignupDao.changeOrderStatusById(id);
		}
	}
}