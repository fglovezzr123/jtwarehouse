package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.ProjectImagesDao;
import com.wing.socialcontact.service.wx.dao.ProjectRecommendDao;
import com.wing.socialcontact.util.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IProjectRecommendService;
import com.wing.socialcontact.service.wx.bean.Project;
import com.wing.socialcontact.service.wx.bean.ProjectImages;
import com.wing.socialcontact.service.wx.bean.ProjectRecommend;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.dao.ListValuesDao;

/**
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Service
public class ProjectRecommendServiceImpl implements IProjectRecommendService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	@Resource
	private ProjectRecommendDao projectRecommendDao;
	@Resource
	private ProjectImagesDao projectImagesDao;
	@Resource
	private ListValuesDao dao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int insertProjectRecommend(ProjectRecommend t) {
		t.setId(UUIDGenerator.getUUID());
		t.setDeleted(0);
		t.setStatus(2);
		t.setShowEnable(1);
		t.setCreateTime(new Date());
		int n = projectRecommendDao.insert(t);
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
	public int updateProjectRecommend(ProjectRecommend t) {
		int n = projectRecommendDao.updateByPrimaryKey(t);
		//图片
		ProjectImages arg0 = new ProjectImages();
		arg0.setProjectId(t.getId());
		projectImagesDao.delete(arg0 );
		
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
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int deleteProjectRecommend(ProjectRecommend t) {
		return projectRecommendDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public List<ProjectRecommend> queryProjectRecommend(ProjectRecommend t) {
		return projectRecommendDao.select(t);
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public ProjectRecommend getProjectRecommend(String id) {
		ProjectRecommend p = projectRecommendDao.getById(id);
		/*//类型信息
		String[] protypes=p.getPrjType().split(",");
		for(int i = 0;i<protypes.length;i++){
			ListValues lv =  dao.selectByPrimaryKey(protypes[i]);
			if(i==0){
				p.setPrjTypeName(lv.getListValue());
			}else{
				p.setPrjTypeName(p.getPrjTypeName()+","+lv.getListValue());
			}
		}*/
		//图片信息
		Map<String,Object> parm = Maps.newHashMap();
		parm.put("projectid", StringUtils.isBlank(id)?"-1":id);
		List<ProjectImages> list = projectImagesDao.selectByParam(parm);
		p.setProjectImages(list);
		return p;
	}
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllProjectRecommend(PageParam pageParam, Map<String, Object> map) {
		PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
		List<ProjectRecommend> list = projectRecommendDao.selectByParam(map);
		PageInfo<ProjectRecommend> page = new PageInfo<ProjectRecommend>(list);
		List<ProjectRecommend> list2 = page.getList();
		//图片信息
		Map<String,Object> parm = Maps.newHashMap();
		for(ProjectRecommend p : list2){
			parm.put("projectid", p.getId());
			List<ProjectImages> list3 = projectImagesDao.selectByParam(parm);
			p.setProjectImages(list3);
		}
		return new DataGrid(page);
	}
	/**
	 * 参与项目会员总数
	 * @param projectRecommend
	 * @return
	 * @author liangwj
	 */
	public int selectDistinctUserCount(ProjectRecommend projectRecommend) {
		return projectRecommendDao.selectDistinctUserCount(projectRecommend);
	}
	/**
	 * 更新
	 * @param map
	 */
	public int updateProjectRecommendByMap(Map<String, Object> map){
		return projectRecommendDao.updateProjectRecommendByMap(map);
	}
}