package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.Activity;

/**
 * <p>Title:活动管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年4月18日 下午3:07:24
 */
@Repository
public interface ActivityDao extends Mapper<Activity> {

	List<Map<String, Object>> getclassMap(Map parm);
	
    
    List selectByParam(Map param);


	List selectbytag(Map param);


	List<Map> getActivityByTerm(Map parm);


	Map getactivityDetailByid(Map parm);


	List getmyactivitiesbyid(Map parm);


	List<Activity> getjxzActivity();


	List<Activity> getbmjsActivities();


	List<Activity> getbmzActivities();


	void updateStatusById(Map parm);



}
