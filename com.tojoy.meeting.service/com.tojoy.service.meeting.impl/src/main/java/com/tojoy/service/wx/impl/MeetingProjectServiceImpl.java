package com.tojoy.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tojoy.service.wx.api.IMeetingProjectService;
import com.tojoy.service.wx.bean.MeetingProject;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tojoy.common.model.DataGrid;
import com.tojoy.common.model.PageParam;

import com.tojoy.service.wx.dao.MeetingProjectDao;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:12:35
 * @version 1.0
 */
@Service
public class MeetingProjectServiceImpl implements IMeetingProjectService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private MeetingProjectDao meetingProjectDao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public int insertMeetingProject(MeetingProject t) {
		return meetingProjectDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public int updateMeetingProject(MeetingProject t) {
		return meetingProjectDao.updateByPrimaryKey(t);
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public int deleteMeetingProject(MeetingProject t) {
		return meetingProjectDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public List<MeetingProject> queryMeetingProject(MeetingProject t) {
		return meetingProjectDao.select(t);
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Override
	public MeetingProject getMeetingProject(String id) {
		return meetingProjectDao.selectByPrimaryKey(id);
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
	public DataGrid selectAllMeetingProject(PageParam param, MeetingProject t) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		List list = meetingProjectDao.selectByParam(parm);
		PageInfo page = new PageInfo(list);
		data.setRows(list);
		data.setTotal(page.getTotal());
		return data;
	}
}