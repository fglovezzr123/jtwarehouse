package com.tojoy.service.console.dao;

import java.util.List;
import java.util.Map;

import com.tojoy.service.console.bean.SyLog;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SystemLogDao extends Mapper<SyLog> {

	List selectByParam(Map parm);
	
	
}