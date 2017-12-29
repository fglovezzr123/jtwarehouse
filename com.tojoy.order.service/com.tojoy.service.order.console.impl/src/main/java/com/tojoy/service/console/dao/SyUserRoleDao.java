package com.tojoy.service.console.dao;

import java.util.List;

import com.tojoy.service.console.bean.SyUserRole;
import org.springframework.stereotype.Repository;

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
