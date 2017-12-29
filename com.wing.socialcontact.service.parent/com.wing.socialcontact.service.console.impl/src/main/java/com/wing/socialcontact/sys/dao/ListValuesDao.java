package com.wing.socialcontact.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.sys.bean.ListValues;

@Repository
public interface ListValuesDao extends Mapper<ListValues> {

	List selectByParam(Map parm);
	
	
}