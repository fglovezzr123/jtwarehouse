package com.tojoy.service.wx.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.tojoy.service.wx.api.IMeetingWhitelistService;
import com.tojoy.service.wx.bean.MeetingWhitelist;
import com.tojoy.service.wx.dao.MeetingWhitelistDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;
import com.tojoy.config.MsgConfig;
import com.tojoy.util.RedisCache;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
@Service
public class MeetingWhitelistServiceImpl implements IMeetingWhitelistService {
	private static final String CACHE_PREFIX = "TJY:MeetingWhitelist:";
	@Resource
	private RedisCache redisCache;
	@Resource
	private MeetingWhitelistDao meetingWhitelistDao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 */
	@Override
	public int insertMeetingWhitelist(MeetingWhitelist t) {
		return meetingWhitelistDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@Override
	public int updateMeetingWhitelist(MeetingWhitelist t) {
		return meetingWhitelistDao.updateByPrimaryKey(removeCacheMeetingWhitelist(t));
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@Override
	public int updateSelectiveMeetingWhitelist(MeetingWhitelist t) {
		return meetingWhitelistDao.updateByPrimaryKeySelective(removeCacheMeetingWhitelist(t));
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@Override
	public int deleteMeetingWhitelist(MeetingWhitelist t) {
		return meetingWhitelistDao.delete(removeCacheMeetingWhitelist(t));
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@Override
	public List<MeetingWhitelist> queryMeetingWhitelist(MeetingWhitelist t) {
		return addCacheMeetingWhitelists(meetingWhitelistDao.select(t));
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@Override
	public MeetingWhitelist getMeetingWhitelist(String id) {
		MeetingWhitelist mw = getCacheMeetingWhitelistById(id);
		mw = mw==null?meetingWhitelistDao.selectByPrimaryKey(id):mw;
		addCacheMeetingWhitelist(mw);
		return mw;
	}
	/**
	 * 单个查询
	 * @param userId
	 * @return
	 * @author liangwj
	 */
	public MeetingWhitelist getMeetingWhitelistByUk(String userId,String meetingId) {
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(meetingId)){
			return null;
		}else{
			MeetingWhitelist p = new MeetingWhitelist(userId, meetingId);
			MeetingWhitelist mw = getCacheMeetingWhitelistByUk(p);
			mw = mw==null?meetingWhitelistDao.selectOne(new MeetingWhitelist(userId, meetingId)):mw;
			addCacheMeetingWhitelist(mw);
			return mw;
		}
	}
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@Override
	public DataGrid selectAllMeetingWhitelist(PageParam param, MeetingWhitelist t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<MeetingWhitelist> list = meetingWhitelistDao.selectByParam(t);
		PageInfo<MeetingWhitelist> page = new PageInfo<MeetingWhitelist>(list);
		addCacheMeetingWhitelists(list);
		return new DataGrid(page);
	}
	/**
	 * 批量增加缓存
	 * @param list
	 * @return
	 */
	private List<MeetingWhitelist> addCacheMeetingWhitelists(List<MeetingWhitelist> list){
		if(list!=null&&list.size()>0){
			for(MeetingWhitelist t:list){
				addCacheMeetingWhitelist(t);
			}
		}
		return list;
	}
	/**
	 * 增加单个缓存
	 * @param t
	 * @return
	 */
	private MeetingWhitelist addCacheMeetingWhitelist(MeetingWhitelist t){
		if(t!=null&&StringUtils.isNotBlank(t.getUserId())
				&&StringUtils.isNotBlank(t.getMeetingId())&&StringUtils.isNotBlank(t.getId())){
			redisCache.put(CACHE_PREFIX+t.getMeetingId()+":"+t.getUserId(),t);
			redisCache.put(CACHE_PREFIX+t.getId(),t);
			
		}
		return t;
	}
	/**
	 * 删除单个缓存
	 * @param t
	 * @return
	 */
	private MeetingWhitelist removeCacheMeetingWhitelist(MeetingWhitelist t){
		if(t!=null&&StringUtils.isNotBlank(t.getUserId())
				&&StringUtils.isNotBlank(t.getMeetingId())){
			redisCache.evict(CACHE_PREFIX+t.getMeetingId()+":"+t.getUserId());
			redisCache.evict(CACHE_PREFIX+t.getId());
		}
		return t;
	}
	/**
	 * 获取单个缓存
	 * @param t
	 * @return
	 */
	private MeetingWhitelist getCacheMeetingWhitelistByUk(MeetingWhitelist t){
		if(t!=null&&StringUtils.isNotBlank(t.getUserId())
				&&StringUtils.isNotBlank(t.getMeetingId())){
			return redisCache.get(CACHE_PREFIX+t.getMeetingId()+":"+t.getUserId(),MeetingWhitelist.class);
		}else{
			return null;
		}
	}
	/**
	 * 获取单个缓存
	 * @param t
	 * @return
	 */
	private MeetingWhitelist getCacheMeetingWhitelistById(String id){
		if(StringUtils.isNotBlank(id)){
			return redisCache.get(CACHE_PREFIX+id,MeetingWhitelist.class);
		}else{
			return null;
		}
	}
	/**
	 * 添加会议白名单
	 * @param meetingId
	 * @param ids
	 * @return
	 */
	@Override
	public String insertWhitelists(String meetingId,String ids) {
		String[] idArr = ids.split(";");
		for(String id : idArr){
			if(StringUtils.isNotBlank(id)){
				MeetingWhitelist g = new MeetingWhitelist();
				g.setUserId(id);
				g.setMeetingId(meetingId);
				
				if(meetingWhitelistDao.selectCount(g)==0){
					g.setCreateTime(new Date());
					meetingWhitelistDao.insert(g);
				}
			}
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}
	/**
	 * 批量删除会议白名单
	 * @param ids
	 * @return
	 */
	@Override
	public String deleteMeetingWhitelists(String ids) {
		if(StringUtils.isBlank(ids)){
			return MsgConfig.MSG_KEY_SUCCESS;
		}
		String[] idArr = ids.split(";");
		MeetingWhitelist t = new MeetingWhitelist();
		for(String id : idArr){
			if(StringUtils.isNotBlank(id)){
				t.setId(id);
				deleteMeetingWhitelist(t);
			}
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}
}