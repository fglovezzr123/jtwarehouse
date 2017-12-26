package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.NewsPayLog;

/**
 * 
 * @author zhangfan
 * @date 2017-06-28 09:31:35
 * @version 1.0
 */
@Repository
public interface NewsPayLogDao extends Mapper<NewsPayLog> {
	
	List selectByParam(Map parm);
	
}
