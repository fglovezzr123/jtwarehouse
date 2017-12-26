package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wing.socialcontact.service.wx.bean.Keywords;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
@Repository
public interface KeywordsDao extends Mapper<Keywords> {
	/**
	 * 分页查询
	 * @param parm
	 * @return
	 */
	List<Keywords> selectByParam(Map<String,Object> params);
	/**
	 * top关键词查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	List<Keywords> selectTop(Map<String,Object> params);
}
