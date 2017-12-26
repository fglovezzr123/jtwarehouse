package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.MeetingGuest;
import com.wing.socialcontact.service.wx.bean.Project;

/**
 * 
 * @author liangwj
 * @date 2017-03-26 22:21:26
 * @version 1.0
 */
@Repository
public interface ProjectDao extends Mapper<Project> {
	/**
	 * 分页查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	List<Project> selectByParam(Map t);
	/**
	 * 根据会议ID查询此会议关联的项目
	 * @author liangwj
	 * @param paramObject
	 * @return
	 */
	List<Project> selectByMeetingId(String meetingId);
	/**
	 * 分页查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	List<Project> selectByProject(Project t);
	/**
	 * 条件分页查询项目联营
	 * @param parm
	 * @return
	 */
	List<Map> getTjyProjectByTerm(Map parm);
	
}
