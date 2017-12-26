package com.wing.socialcontact.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wing.socialcontact.sys.bean.SyUserRole;

import tk.mybatis.mapper.common.Mapper;
@Repository
public interface SyUserRoleDao extends Mapper<SyUserRole> {

	/**
	 * 根据用户id查询用户角色id
	 * @param userId
	 * @return
	 */
	List<String> selectroleidbyuserid(String userId);

}
