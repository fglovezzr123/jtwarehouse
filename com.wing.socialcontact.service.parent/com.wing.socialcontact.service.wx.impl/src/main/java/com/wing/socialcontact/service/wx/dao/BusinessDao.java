package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.Business;

/**
 * 
 * @author zhangfan
 * @date 2017-04-18 12:01:49
 * @version 1.0
 */
@Repository
public interface BusinessDao extends Mapper<Business> {
	
	List selectByParam(Map parm);
	List selectByParam2(Map parm);
	
	List selectFrontByParam(Map parm);
	
	List<Map<String, Object>> selectBusinessById(String id);
	
	List<Map<String, Object>> selectMyAttention(Map parm);
	
	List selectPastBusiness();
}
