package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.Honor;
import com.wing.socialcontact.service.wx.bean.UserFav;
import com.wing.socialcontact.service.wx.bean.UserFriendimpress;

/**
 * 
 * @author gaojun
 * @date 2017-04-14 22:34:36
 * @version 1.0
 */
@Repository
public interface UserFriendimpressDao extends Mapper<UserFriendimpress> {
	List selectByParam(Map parm);
	
	List selectcountByUserId(Map parm);
	
	List<Map<String, Object>> selectAllUserFriendimpressMap(UserFriendimpress userFriendimpress);
	
	List<UserFriendimpress> selectByType(Map parm);
}
