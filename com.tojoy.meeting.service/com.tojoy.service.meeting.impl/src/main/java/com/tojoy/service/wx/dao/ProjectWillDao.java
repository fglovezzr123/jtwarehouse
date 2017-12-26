package com.tojoy.service.wx.dao;

import java.util.List;
import java.util.Map;

import com.tojoy.service.wx.bean.ProjectWill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
/**
 * 
 * @author liangwj
 * @date 2017-03-26 22:21:26
 * @version 1.0
 */
@Repository
public interface ProjectWillDao extends Mapper<ProjectWill> {
	/**
	 * 分页查询
	 * @param parm
	 * @return
	 */
	List<ProjectWill> selectByParam(Map parm);
	/**
	 * 项目报名处理
	 * @param id
	 * @param status 
	 * @return
	 * @author liangwj
	 */
	int updateProjectWillStatus(@Param("id") String id, @Param("status") Integer status);
	/**
	 * 项目报名查询
	 * @param id
	 * @return
	 * @author liangwj
	 */
	ProjectWill getById(String id);
	
}
