package com.tojoy.service.console.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.tojoy.service.console.bean.SyDataPermissions;

@Repository
public interface DataPermissionsDao extends Mapper<SyDataPermissions> {
	
	List<Map<String, Object>> selectAlls();
}