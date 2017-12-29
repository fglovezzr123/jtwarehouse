package com.wing.socialcontact.service.wx.dao;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.Sysconfig;

/**
 * 
 * @author zengmin
 * @date 2017-04-17 15:04:05
 * @version 1.0
 */
@Repository
public interface SysconfigDao extends Mapper<Sysconfig> {
	
	int remoteEnable(); 
}
