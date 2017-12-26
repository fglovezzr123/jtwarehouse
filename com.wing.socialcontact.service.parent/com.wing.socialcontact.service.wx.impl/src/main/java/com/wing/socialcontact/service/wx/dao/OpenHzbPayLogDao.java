package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.OpenHzbPayLog;

/**
 * 
 * @author liangwj
 * @date 2017-07-20 20:28:03
 * @version 1.0
 */
@Repository
public interface OpenHzbPayLogDao extends Mapper<OpenHzbPayLog> {
	List query(Map<String, Object> param);
	List list(Map<String, Object> param);
}
