package com.wing.socialcontact.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.sys.bean.SyMenuMy;

@Repository
public interface MenuMyDao extends Mapper<SyMenuMy> {

	List selectMyMenus(Map parm);
	
}