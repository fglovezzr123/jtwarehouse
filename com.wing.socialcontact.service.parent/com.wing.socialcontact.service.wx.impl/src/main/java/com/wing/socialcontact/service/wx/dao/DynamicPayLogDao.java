package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.DynamicOpLog;
import com.wing.socialcontact.service.wx.bean.DynamicPayLog;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:05
 * @version 1.0
 */
@Repository
public interface DynamicPayLogDao extends Mapper<DynamicPayLog> {
	List<Map<String, Object>> selectAllDynamicPayLogMap(DynamicPayLog dynamicPayLog);
	List selectByParam(Map parm);
}
