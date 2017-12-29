package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.ILibraryLiveService;
import com.wing.socialcontact.service.wx.bean.Meeting;
import com.wing.socialcontact.service.wx.bean.MeetingGuest;
import com.wing.socialcontact.service.wx.bean.MeetingProject;
import com.wing.socialcontact.service.wx.bean.Project;
import com.wing.socialcontact.service.wx.bean.TjyLibrary;
import com.wing.socialcontact.service.wx.bean.TjyLibraryLive;
import com.wing.socialcontact.service.wx.dao.LibraryLiveDao;
import com.wing.socialcontact.service.wx.dao.MeetingGuestDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class LibraryLiveServiceImpl extends BaseServiceImpl<TjyLibraryLive> implements
		ILibraryLiveService {

	@Resource
	private LibraryLiveDao libraryLiveDao;

	@Resource
	private MeetingGuestDao meetingGuestDao;
	
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 */
	public DataGrid selectAllLibraryLive(PageParam param, TjyLibraryLive t,Date stime ,Date etime) {
		
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("title", t.getTitle());
		parm.put("type", t.getType());
		parm.put("stime", stime);
		parm.put("etime", etime);
		List<Map<String, Object>> list = libraryLiveDao.selectByParam(parm);

		PageInfo page = new PageInfo(list);

		data.setRows(list);
		data.setTotal(page.getTotal());
		
		return data;
	}

	/**
	 * 根据id获取详情
	 */
	@Override
	public TjyLibraryLive getLibraryLive(String id) {
		TjyLibraryLive l =  libraryLiveDao.selectByPrimaryKey(id);
		//查询嘉宾关联项目信息
		if(l!=null){
			List<MeetingGuest>  guests = meetingGuestDao.selectByMeetingId(l.getId());
			l.setLiveGuests(guests);
		}
		
		return l;
	}

	/**
	 * 新增
	 */
	@Override
	public int insertTjyLibraryLive(TjyLibraryLive t) {
		t.setId(t.getId()==null||t.getId().trim().length()==0?null:t.getId());
		int n = libraryLiveDao.insert(t);
		super.blurdelete(t);
		//转换嘉宾
		t.parse();
		
		List<MeetingGuest> list2 = t.getLiveGuests();
		for(MeetingGuest g : list2){
			meetingGuestDao.insert(g);
		}
		return n;
		
	}

	/**
	 * 修改
	 */
	@Override
	public int updateTjyLibraryLive(TjyLibraryLive t) {
		//int n = libraryLiveDao.updateByPrimaryKey(t);
		 int n=0;
		 if(super.updateByPrimaryKeyCache(t, t.getId())){
			n=1; 
		 };
		//转换嘉宾
		t.parse();
		meetingGuestDao.deleteByMeetingId(t.getId());
		
		List<MeetingGuest> list2 = t.getLiveGuests();
		for(MeetingGuest g : list2){
			meetingGuestDao.insert(g);
		}
		return n;
	}

	@Override
	public List<Map> libraryLiveList(Integer page, Integer size, Integer type,
			String key) {
		Map parm = new HashMap();
		parm.put("title", key);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		parm.put("type", type);
		List lst= selectListByPrimaryKeyCache(page+size+type+key,TjyLibraryLive.class);
		if(lst==null){
			lst=libraryLiveDao.libraryLiveList(parm);
			insertCache(lst,page+size+type+key,TjyLibraryLive.class);
		}
		return lst;
	}

	@Override
	public List<TjyLibraryLive> selectWksLives() {
		
		return libraryLiveDao.selectWksLives();
	}

	@Override
	public List<TjyLibraryLive> selectJxzLives() {
		return libraryLiveDao.selectJxzLives();
	}

	@Override
	public void updatestatusbyid(int i, String id) {
		Map parm = new HashMap();
		parm.put("status", i);
		parm.put("id",id);
		libraryLiveDao.updatestatusbyid(parm);
	}
}
