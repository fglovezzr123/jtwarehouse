package com.wing.socialcontact.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wing.socialcontact.sys.bean.SyRoleMenu;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SyRoleMenuDao extends Mapper<SyRoleMenu> {

	/**
	 * 根据roleid 查询菜单的 menuurl
	 * @param id
	 * @return
	 */
	
	//.find("select distinct m.menuUrl from SyRoleMenu rm,SyMenu m where rm.menuId=m.id and rm.roleId=? ",id);
	List<String> selecturlbyroleid(String id);

}
