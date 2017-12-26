package com.tojoy.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tojoy.service.wx.api.IMeetingGuestService;
import com.tojoy.service.wx.bean.MeetingGuest;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;

import com.tojoy.service.wx.dao.MeetingGuestDao;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:12:35
 * @version 1.0
 */
@Service
public class MeetingGuestServiceImpl implements IMeetingGuestService {

	@Resource
	private MeetingGuestDao meetingGuestDao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public int insertMeetingGuest(MeetingGuest t) {
		return meetingGuestDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public int updateMeetingGuest(MeetingGuest t) {
		return meetingGuestDao.updateByPrimaryKey(t);
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public int deleteMeetingGuest(MeetingGuest t) {
		return meetingGuestDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public List<MeetingGuest> queryMeetingGuest(MeetingGuest t) {
		return meetingGuestDao.select(t);
	}
	/**
	 * 单个查询
	 * @param id
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public MeetingGuest getMeetingGuest(String id) {
		return meetingGuestDao.selectByPrimaryKey(id);
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
	public DataGrid selectAllMeetingGuest(PageParam param, MeetingGuest t) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		List list = meetingGuestDao.selectByParam(parm);
		PageInfo page = new PageInfo(list);
		data.setRows(list);
		data.setTotal(page.getTotal());
		return data;
	}
}