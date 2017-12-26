package com.wing.socialcontact.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.sys.bean.SyDataPermissions;

@Repository
public interface DataPermissionsDao extends Mapper<SyDataPermissions> {
	
	List<Map<String, Object>> selectAlls();
}