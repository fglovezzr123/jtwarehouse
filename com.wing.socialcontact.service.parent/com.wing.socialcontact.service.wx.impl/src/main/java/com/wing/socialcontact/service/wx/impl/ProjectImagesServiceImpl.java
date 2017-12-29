package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

import com.wing.socialcontact.service.wx.dao.ProjectImagesDao;
import com.wing.socialcontact.service.wx.api.IProjectImagesService;
import com.wing.socialcontact.service.wx.bean.ProjectImages;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
@Service
public class ProjectImagesServiceImpl implements IProjectImagesService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private ProjectImagesDao projectImagesDao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 */
	public int insertProjectImages(ProjectImages t) {
		return projectImagesDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateProjectImages(ProjectImages t) {
		return projectImagesDao.updateByPrimaryKey(t);
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int deleteProjectImages(ProjectImages t) {
		return projectImagesDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public List<ProjectImages> queryProjectImages(ProjectImages t) {
		return projectImagesDao.select(t);
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public ProjectImages getProjectImages(String id) {
		return projectImagesDao.selectByPrimaryKey(id);
	}
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllProjectImages(PageParam param, ProjectImages t) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		List list = projectImagesDao.selectByParam(parm);
		PageInfo page = new PageInfo(list);
		data.setRows(list);
		data.setTotal(page.getTotal());
		return data;
	}
}