package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wing.socialcontact.service.wx.bean.TjyNewsClass;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface NewsClassDao extends Mapper<TjyNewsClass> {

	List<TjyNewsClass> selectAllNewsClassMap();

	List selectByParam(Map parm);
	
	List<Map<String, Object>> selectAllclassMap(Map parm);
	
	List<Map<String, Object>> selectNewsclassMap();
	
	List<Map<String, Object>> selectFrontClass(Map parm);
}
