package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.UserFriendimpress;
import com.wing.socialcontact.service.wx.bean.UserHonor;
import com.wing.socialcontact.service.wx.bean.UserIntegralLog;

/**
 * 
 * @author gaojun
 * @date 2017-04-14 22:34:36
 * @version 1.0
 */
@Repository
public interface UserHonorDao extends Mapper<UserHonor> {
	List selectByParam(Map parm);
	
	List<UserHonor> selectByType(Map parm);
	
	UserHonor selectByUserIdAndHonorId(@Param("userId") String userId, @Param("honorId") String honorId);
	
	List<Map<String, Object>>  selectByUserId(@Param("userId") String userId);

	List<Map> getAllHonors();

}
