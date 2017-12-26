package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.BusinessClass;

/**
 * 
 * @author zhangfan
 * @date 2017-04-18 21:18:06
 * @version 1.0
 */
@Repository
public interface BusinessClassDao extends Mapper<BusinessClass> {
	
	List selectByParam(Map parm);
	
	List selectAllClassMap(Map parm);
	
}
