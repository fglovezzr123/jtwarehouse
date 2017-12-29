package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.AttentionDao;
import com.wing.socialcontact.service.wx.dao.MeetingProjectDao;
import com.wing.socialcontact.service.wx.dao.ProjectDao;
import com.wing.socialcontact.service.wx.dao.ProjectImagesDao;
import com.wing.socialcontact.service.wx.dao.ProjectWillDao;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IProjectService;
import com.wing.socialcontact.service.wx.bean.Attention;
import com.wing.socialcontact.service.wx.bean.Meeting;
import com.wing.socialcontact.service.wx.bean.MeetingProject;
import com.wing.socialcontact.service.wx.bean.Project;
import com.wing.socialcontact.service.wx.bean.ProjectImages;
import com.wing.socialcontact.service.wx.bean.ProjectWill;

/**
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Service
public class ProjectServiceImpl implements IProjectService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private ProjectDao projectDao;
	@Resource
	private AttentionDao attentionDao;
	@Resource
	private ProjectImagesDao projectImagesDao;
	@Resource
	private ProjectWillDao projectWillDao;
	@Resource
	private MeetingProjectDao meetingProjectDao;
	/**
	 * 项目联营首页数据
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public Project indexList(Project t){
		//1.轮播数据
		
		//2.项目信息（项目招募中）
		 List<Project> list = projectDao.select(t);
		 return null;
	}
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int insertProject(Project t) {
		int n = projectDao.insert(t);
		//图片
		List<ProjectImages> list1 = t.parse();
		for(ProjectImages g : list1){
			ProjectImages e = new ProjectImages();
			e.setProjectId(t.getId());
			e.setCreateTime(new Date());
			e.setImageUrl(g.getImageUrl());
			e.setDeleted(0);
			projectImagesDao.insert(e);
		}
		
		return n;
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int updateProject(Project t) {
		int n = projectDao.updateByPrimaryKey(t);
		//图片
		t.parse();
		ProjectImages arg0 = new ProjectImages();
		arg0.setProjectId(t.getId());
		projectImagesDao.delete(arg0 );
		
		List<ProjectImages> list1 = t.getProjectImages();
		for(ProjectImages g : list1){
			ProjectImages e = new ProjectImages();
			e.setProjectId(t.getId());
			e.setCreateTime(new Date());
			e.setImageUrl(g.getImageUrl());
			e.setDeleted(0);
			projectImagesDao.insert(e);
		}
		
		return n;
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int deleteProject(Project t) {
		//删除项目合作
		ProjectWill will = new ProjectWill();
		will.setPrjId(t.getId());
		projectWillDao.delete(will);
		//删除项目会议关联
		MeetingProject mp = new MeetingProject();
		mp.setProjectId(t.getId());
		meetingProjectDao.delete(mp);
		Attention attention = new Attention();
		attention.setFkId(t.getId());
		attentionDao.delete(attention);
		return projectDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public List<Project> queryProject(Project t) {
		return projectDao.select(t);
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public Project getProject(String id,String userId) {
		userId = StringUtils.isBlank(userId)?"-1":userId;
		//基本信息
		Project p = projectDao.selectByPrimaryKey(id);
		//图片信息
		Map<String,Object> parm = Maps.newHashMap();
		parm.put("projectid", StringUtils.isBlank(id)?"-1":id);
		List<ProjectImages> list = projectImagesDao.selectByParam(parm);
		p.setProjectImages(list);
		
		//关注人数
		p.setExtProp("attentionCount", attentionDao.selectCount(new Attention(null,id)));
		//意向人数
		p.setExtProp("willCount", projectWillDao.selectCount(new ProjectWill(null,id)));
		//当前用户是否已关注
		p.setExtProp("attentiond", attentionDao.selectCount(new Attention(userId,id))>0?"1":"-1");
		//当前用户发送意向次数
		ProjectWill pw = new ProjectWill(null,id);
		pw.setUserId(userId);
		p.setExtProp("userWilledCount", projectWillDao.selectCount(pw));

		return p;
	}
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	@Deprecated
	public DataGrid selectAllProject(PageParam param, Map t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<Project> list = projectDao.selectByParam(t);
		PageInfo<Project> page = new PageInfo<Project>(list);
		return new DataGrid(page);
	}
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-04-04 00:12:35
	 */
	public DataGrid selectAllProject2(PageParam param, Project t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<Project> list = projectDao.selectByProject(t);
		PageInfo<Project> page = new PageInfo<Project>(list);
		return new DataGrid(page);
	}
	@Override
	public List<Map> getTjyProjectByTerm(Integer page, Integer size, String key, String userId) {
		Map parm = new HashMap();
		parm.put("titles", key);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		parm.put("userId", userId);
		parm.put("showEnable", 1);
		parm.put("isApl", 1);
		List lst=projectDao.getTjyProjectByTerm(parm);
		return lst;
	}
}