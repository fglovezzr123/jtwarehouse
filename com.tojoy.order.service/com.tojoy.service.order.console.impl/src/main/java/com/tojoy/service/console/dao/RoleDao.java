package com.tojoy.service.console.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.tojoy.service.console.bean.SyRole;

@Repository
public interface RoleDao extends Mapper<SyRole> {

	/**
	 * 查询所有角色   返回List<Map>
	 * @return
	 */
	//List<Map> selectAllRoles();

	List selectByParam(Map parm);

	int selectCountByParam(Map parm);

	List<String> selectMenuIdByRoleId(String roleId);

	List<String> selectActionIdByRoleId(String roleId);

	void deleteRoleMenuByParam(Map parm);

	void deleteRoleActionByParam(Map parm);

	List<Map> selectAllRoles();
	
}