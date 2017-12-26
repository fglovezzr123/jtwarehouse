package com.tojoy.service.console.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.tojoy.service.console.bean.SyDept;

@Repository
public interface DeptDao extends Mapper<SyDept> {

	List<Map<String, Object>> selectAllDeptsMap();

	List<Map<String, Object>> selectByDeptName(Map parm);
	
}