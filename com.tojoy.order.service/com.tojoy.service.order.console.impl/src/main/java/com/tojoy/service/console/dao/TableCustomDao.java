package com.tojoy.service.console.dao;

import java.util.List;
import java.util.Map;

import com.tojoy.service.console.bean.SyTableCustom;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface TableCustomDao extends Mapper<SyTableCustom> {

	List selectByParam(Map parm);
	
	
}