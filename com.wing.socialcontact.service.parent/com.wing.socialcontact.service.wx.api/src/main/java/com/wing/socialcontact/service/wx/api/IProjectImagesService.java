package com.wing.socialcontact.service.wx.api;

import java.util.List;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

import com.wing.socialcontact.service.wx.bean.ProjectImages;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
public interface IProjectImagesService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 */
	public int insertProjectImages(ProjectImages t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateProjectImages(ProjectImages t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int deleteProjectImages(ProjectImages t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public List<ProjectImages> queryProjectImages(ProjectImages t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public ProjectImages getProjectImages(String id);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllProjectImages(PageParam param, ProjectImages t);
}