package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.UserGreetings;

/**
 * 
 * @author gaojun
 * @date 2017-04-22 20:19:46
 * @version 1.0
 */
@Repository
public interface UserGreetingsDao extends Mapper<UserGreetings> {
	List selectByParam(Map parm);
	
	List<Map<String, Object>> selectAllUserGreetings(UserGreetings userGreetings);
	
	List<UserGreetings> selectByType(UserGreetings userGreetings);
	
	Integer getCountOneDay(String userId);
}
