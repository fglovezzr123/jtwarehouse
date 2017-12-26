package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.MessageBulk;

/**
 * 
 * @author gaojun
 * @date 2017-06-28 11:18:36
 * @version 1.0
 */
@Repository
public interface MessageBulkDao extends Mapper<MessageBulk> {
	
	List selectByParam(Map parm);
}
