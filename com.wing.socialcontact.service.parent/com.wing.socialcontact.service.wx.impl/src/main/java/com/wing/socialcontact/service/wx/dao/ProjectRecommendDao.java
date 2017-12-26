package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.ProjectRecommend;

/**
 * 
 * @author liangwj
 * @date 2017-03-26 22:21:26
 * @version 1.0
 */
@Repository
public interface ProjectRecommendDao extends Mapper<ProjectRecommend> {
	/**
	 * 分页查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	List<ProjectRecommend> selectByParam(Map<String, Object> map);
	/**
	 * 自荐项目的用户数
	 * @param projectRecommend
	 * @return
	 */
	int selectDistinctUserCount(ProjectRecommend projectRecommend);
	/**
	 * 自荐项目查询
	 * @param id
	 * @return
	 */
	ProjectRecommend getById(String id);
	/**
	 * 自荐项目更新
	 * @param map
	 */
	int updateProjectRecommendByMap(Map<String, Object> map);
	
}
