package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.BusinessDisscuss;

/**
 * 
 * @author zhangfan
 * @date 2017-04-21 10:59:49
 * @version 1.0
 */
@Repository
public interface BusinessDisscussDao extends Mapper<BusinessDisscuss> {
	
	List selectByParam(Map parm);
	
	List<Map<String, Object>> selectBDById(String id);
	
	List<Map<String, Object>> selectBDByParam(Map parm);
	
	List selectFrontByParam(Map parm);
}
