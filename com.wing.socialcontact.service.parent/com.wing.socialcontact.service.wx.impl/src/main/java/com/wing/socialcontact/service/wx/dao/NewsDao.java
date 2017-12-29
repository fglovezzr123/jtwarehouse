package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wing.socialcontact.service.wx.bean.TjyNews;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author zhangfan
 *
 */
@Repository
public interface NewsDao extends Mapper<TjyNews> {

	List selectByParam(Map parm);
	
	List selectHotByParam(Map parm);

	List<Map<String, Object>> selectNewsById(String id);
	
	List selectByParamFront(Map parm);
	
	List selectHtmlByParam(Map parm);
	
	List<Map<String, Object>> selectNewsByIdHt(String id);
}
