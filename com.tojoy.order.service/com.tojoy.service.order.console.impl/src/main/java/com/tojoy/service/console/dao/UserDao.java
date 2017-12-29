package com.tojoy.service.console.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.tojoy.service.console.bean.SyUsers;

@Repository
public interface UserDao extends Mapper<SyUsers> {


	/**
	 * 根据用户id查询权限
	 * @param userId
	 * @return
	 */
	List findrolebyuserID(String userId);

	/**
	 * 根据用户id查询权限id
	 * @param userId
	 * @return
	 */
	List<String> findRoleIdByUserId(String userId);

	/**
	 * 查询所有用户
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> getuserinfoMap(Map parm);

	List<Map<String,Object>> selectUsersLookUp(Map parm);

	List selectUserByNotInRoleIdParm(Map parm);

	List selectUserByRoleIdParm(Map parm);

	void updatedeptleader(String id);

	void deleteuserrole(String id);
	
	
}