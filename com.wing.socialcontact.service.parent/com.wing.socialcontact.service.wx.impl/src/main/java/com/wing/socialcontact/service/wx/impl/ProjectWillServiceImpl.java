package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.ProjectWillDao;
import com.wing.socialcontact.util.BeanMapUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IProjectWillService;
import com.wing.socialcontact.service.wx.bean.ProjectWill;

/**
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Service
public class ProjectWillServiceImpl implements IProjectWillService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private ProjectWillDao projectWillDao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int insertProjectWill(ProjectWill t) {
		return projectWillDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int updateProjectWill(ProjectWill t) {
		return projectWillDao.updateByPrimaryKey(t);
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int deleteProjectWill(ProjectWill t) {
		return projectWillDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public List<ProjectWill> queryProjectWill(ProjectWill t) {
		return projectWillDao.select(t);
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public ProjectWill getProjectWill(String id) {
		return projectWillDao.getById(id);
	}
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataGrid selectAllProjectWill(PageParam param, Map<String,Object> parm) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List list = projectWillDao.selectByParam(parm);
		PageInfo page = new PageInfo(list);
		return new DataGrid(page);
	}
	/**
	 * 项目报名处理
	 * @param id
	 * @param status
	 * @return
	 * @author liangwj
	 */
	public int updateProjectWillStatus(String id,Integer status) {
		return projectWillDao.updateProjectWillStatus(id,status);
	}
}