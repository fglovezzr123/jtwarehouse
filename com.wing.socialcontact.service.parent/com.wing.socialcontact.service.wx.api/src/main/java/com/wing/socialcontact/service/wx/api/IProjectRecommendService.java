package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.ProjectRecommend;

/**
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
public interface IProjectRecommendService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int insertProjectRecommend(ProjectRecommend t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int updateProjectRecommend(ProjectRecommend t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int deleteProjectRecommend(ProjectRecommend t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public List<ProjectRecommend> queryProjectRecommend(ProjectRecommend t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public ProjectRecommend getProjectRecommend(String id);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllProjectRecommend(PageParam pageParam, Map<String, Object> map);
	/**
	 * 参与项目会员总数
	 * @param projectRecommend
	 * @return
	 * @author liangwj
	 */
	public int selectDistinctUserCount(ProjectRecommend projectRecommend);
	/**
	 * 更新
	 * @param map
	 */
	public int updateProjectRecommendByMap(Map<String, Object> map);
}