package com.wing.socialcontact.service.wx.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.Config;
import com.wing.socialcontact.service.wx.bean.Honor;

/**
 * 
 * @author gaojun
 * @date 2017-09-18 15:40:53
 * @version 1.0
 */
@Repository
public interface ConfigDao extends Mapper<Config> {
	Config selectByType(@Param("type") String selectByType);
}
