package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.UserFav;

/**
 * 
 * @author gaojun
 * @date 2017-04-11 15:34:07
 * @version 1.0
 */
@Repository
public interface UserFavDao extends Mapper<UserFav> {
	List<Map<String, Object>> selectAllUserFavMap(UserFav userFav);
	
	List<Map<String, Object>> selectAllUserFavMap2(UserFav userFav);

	List selectByParam(Map parm);
}
