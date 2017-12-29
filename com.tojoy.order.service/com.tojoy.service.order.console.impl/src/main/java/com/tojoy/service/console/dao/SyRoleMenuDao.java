package com.tojoy.service.console.dao;

import java.util.List;

import com.tojoy.service.console.bean.SyRoleMenu;
import org.springframework.stereotype.Repository;

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
