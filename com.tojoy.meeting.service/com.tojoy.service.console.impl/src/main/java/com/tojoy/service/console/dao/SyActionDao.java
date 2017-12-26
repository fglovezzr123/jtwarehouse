package com.tojoy.service.console.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tojoy.service.console.bean.SyAction;

import tk.mybatis.mapper.common.Mapper;
@Repository
public interface SyActionDao extends Mapper<SyAction> {

	/**
	 * 根据角色id查操作url
	 * @param id
	 * @return
	 */
	//find("select distinct a.actionUrl from SyAction a,SyRoleAction ra where a.id=ra.actionId and ra.roleId=? ",id);
	List<String> findurlbyroleid(String roleId);

	List selectByActionSort();

	List selectByParam(Map parm);

}
