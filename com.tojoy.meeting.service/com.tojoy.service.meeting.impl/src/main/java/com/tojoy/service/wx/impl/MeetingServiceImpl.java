package com.tojoy.service.wx.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.tojoy.service.wx.api.IMeetingService;
import com.tojoy.service.wx.api.IMeetingSignupService;
import com.tojoy.service.wx.api.IMeetingWhitelistService;
import com.tojoy.service.wx.bean.*;
import com.tojoy.service.wx.dao.*;
import com.tojoy.util.UUIDGenerator;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;

import com.tojoy.util.RedisCache;
import com.tojoy.vhall.api.BaseAPI;
import com.tojoy.vhall.api.WebinarAPI;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:12:35
 * @version 1.0
 */
@Service
public class MeetingServiceImpl implements IMeetingService {

	@Resource
	private MeetingDao meetingDao;
	
	@Resource
	private MeetingProjectDao meetingProjectDao;
	
	@Resource
	private MeetingGuestDao meetingGuestDao;
	
	@Resource
	private ProjectDao projectDao;

	@Resource
	private RedisCache redisCache;

	@Resource
	private DistrictDao districtDao;

	@Resource
	private MeetingRelationDao meetingRelationDao;

	@Resource
	private MeetingSuccessiveDao meetingSuccessiveDao;

	@Resource
	private IMeetingWhitelistService meetingWhitelistService;
	
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public int insertMeeting(Meeting t) {
		t.setId(t.getId()==null||t.getId().trim().length()==0?null:t.getId());
		int n = meetingDao.insert(t);
		//转换嘉宾和关联项目
		t.parse();
		
		List<Project> list1 = t.getMeetingProjects();
		for(Project g : list1){
			MeetingProject e = new MeetingProject();
			e.setMeetingId(t.getId());
			e.setProjectId(g.getId());
			e.setCreateTime(new Date());
			meetingProjectDao.insert(e);
		}
		
		List<MeetingGuest> list2 = t.getMeetingGuests();
		Integer i=0;
		for(MeetingGuest g : list2){
			g.setSort(i++);
			meetingGuestDao.insert(g);
		}

		List<MeetingRelation> list3 = t.getMeetingRelationList();
		if(list3!=null){
			for(MeetingRelation r : list3){
				r.setMeetingId(t.getId());
				meetingRelationDao.insert(r);
			}
		}

		List<MeetingSuccessive> list4 = t.getMeetingSuccessiveList();
		if(list3!=null){
			for(MeetingSuccessive r : list4){
				r.setId(UUIDGenerator.getUUID());
				r.setFkId(t.getId());
				r.setCreateTime(new Date());
				meetingSuccessiveDao.insert(r);
			}
		}

		return n;
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
/*	@CacheEvict(key="'TJY:Meeting:'+#id",allEntries=true)*/
	public int updateMeeting(Meeting t) {
		int n = meetingDao.updateByPrimaryKey(t);
		//转换嘉宾和关联项目
		t.parse();
		
		meetingProjectDao.deleteByMeetingId(t.getId());
		meetingGuestDao.deleteByMeetingId(t.getId());
		meetingRelationDao.deleteByMeetingId(t.getId());
		meetingSuccessiveDao.deleteByMeetingId(t.getId());
		
		List<Project> list1 = t.getMeetingProjects();
		for(Project g : list1){
			MeetingProject e = new MeetingProject();
			e.setMeetingId(t.getId());
			e.setProjectId(g.getId());
			e.setCreateTime(new Date());
			meetingProjectDao.insert(e);
		}
		
		List<MeetingGuest> list2 = t.getMeetingGuests();
		Integer i=1;
		for(MeetingGuest g : list2){
			g.setSort(i++);
			meetingGuestDao.insert(g);
		}

		List<MeetingRelation> list3 = t.getMeetingRelationList();
		if(list3!=null){
			for(MeetingRelation r : list3){
				meetingRelationDao.insert(r);
			}
		}

		List<MeetingSuccessive> list4 = t.getMeetingSuccessiveList();
		if(list4!=null){
			for(MeetingSuccessive r : list4){
				r.setId(UUIDGenerator.getUUID());
				r.setFkId(t.getId());
				r.setCreateTime(new Date());
				meetingSuccessiveDao.insert(r);
			}
		}

		return n;
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	@CacheEvict(key="'TJY:Meeting:'+#id",allEntries=true)
	public int deleteMeeting(Meeting t) {
		return meetingDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	@Cacheable(key="'TJY:Meeting:'+#id")
	public List<Meeting> queryMeeting(Meeting t) {
		return meetingDao.select(t);
	}
	/**
	 * 单个查询
	 * @param id
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	/*@Cacheable(key="'TJY:Meeting:'+#id")*/
	@Override
	public Meeting getMeeting(String id) {
		Meeting m =  meetingDao.selectByPrimaryKey(id);

		//往届会议
		List<MeetingSuccessive> meetingSuccessives = meetingSuccessiveDao.queryMeetingSuccessiveByMeetingId(id);
		m.setMeetingSuccessiveList(meetingSuccessives);

		//查询选集视频
		List<MeetingRelation> meetingRelations = meetingRelationDao.queryMeetingRelationByMeetingId(id);
		m.setMeetingRelationList(meetingRelations);

		//查询嘉宾关联项目信息
		List<MeetingGuest>  guests = meetingGuestDao.selectByMeetingId(m.getId());
		m.setMeetingGuests(guests);
		
		List<Project> projects =  projectDao.selectByMeetingId(m.getId());
		m.setMeetingProjects(projects);
		//关联省市区名称
		m.setPname(place(m.getProvince()));
		m.setCname(place(m.getCity()));
		m.setConame(place(m.getCounty()));
		return m;
	}
	
	public String place (String districkid){
		String name = "";
		SyDistrict d = districtDao.selectByPrimaryKey(districkid);
		if(null!=d){
			name = d.getDisName();
		}
		return name ;
	}
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public DataGrid selectAllMeeting(PageParam param, Meeting t) {
		
		PageHelper.startPage(param.getPage(), param.getRows());
		List<Meeting> list = meetingDao.selectByParam(t);
		PageInfo<Meeting> page = new PageInfo<Meeting>(list);
		DataGrid data=new DataGrid(page);
		return data;
	}
	/**
	 * vhall k生成
	 * @param openK 是否开启k认证 1开启0不开启
	 * @param tjyUserId
	 * @param webinar_id
	 * @return
	 */
	@Override
	public String createVhallKey(String openK,String tjyUserId,String webinar_id) {
		String k = BaseAPI.createVedioSignWithoutRegisterUser(tjyUserId, tjyUserId, webinar_id).get("sign").toString();
		String key = "TJY:VHALL:K:"+k;
		redisCache.put(key, k, 3600L);//1个小时内有效
		
		WebinarAPI.wholeAuthUrlWebinar("1".equals(openK)?1:0,BaseAPI.AUTH_URL,BaseAPI.FAILURE_URL,1);
		return k;
	}
	/**
	 * vhall k验证
	 * @param k
	 * @return
	 */
	@Override
	public String validVhallKey(String k) {
		if(k==null){
			return "fail";
		}else{
			String key = "TJY:VHALL:K:"+k;
			
			ValueWrapper obj = redisCache.get(key);
			String value = obj!=null?(String)obj.get():null;
			
			if(value!=null&&value.trim().length()>0){
				redisCache.evict(key);
				return "pass";
			}else{
				return "fail";
			}
		}
	}
	@Override
	public DataGrid selectMyCollectMeeting(PageParam param, Meeting t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<Meeting> list = meetingDao.selectMyCollectMeeting(t);
		PageInfo<Meeting> page = new PageInfo<Meeting>(list);
		DataGrid data=new DataGrid(page);
		return data;
	}
	/**
	 * 会议白名单查询
	 * @param param
	 * @param t
	 * @return
	 */
	@Override
	public DataGrid selectAllMeetingWhitelist(PageParam param, MeetingWhitelist t) {
		return meetingWhitelistService.selectAllMeetingWhitelist(param, t);
	}
	/**
	 * 添加会议白名单
	 * @param meetingId
	 * @param ids
	 * @return
	 */
	@Override
	public String insertWhitelists(String meetingId,String ids) {
		return meetingWhitelistService.insertWhitelists(meetingId, ids);
	}
	/**
	 * 批量删除白名单
	 * @param ids
	 * @return
	 */
	@Override
	public String deleteGlobalWhitelists(String ids) {
		return meetingWhitelistService.deleteMeetingWhitelists(ids);
	}
	/**
	 * 是否是白名单用户
	 * @param userId
	 * @param meetingId
	 * @return
	 */
	@Override
	public boolean isWhitelist(String userId, String meetingId) {
		int s = meetingDao.isWhitelist(userId, meetingId);
		return s>0?true:false;
	}
}