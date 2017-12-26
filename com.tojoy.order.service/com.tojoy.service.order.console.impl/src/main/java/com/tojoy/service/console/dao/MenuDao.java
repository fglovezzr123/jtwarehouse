package com.tojoy.service.console.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.tojoy.service.console.bean.SyMenu;

@Repository
public interface MenuDao extends Mapper<SyMenu> {
	List selectByMenuSort(Map parm);

	List<Map<String,Object>> findmenubyuserid(String userId);
	
	List<Map<String,Object>> findmenuformainnotdev(String userId);

	List selectByParam(Map parm);

	List<SyMenu> findSyMenubyuserid(String id);
}