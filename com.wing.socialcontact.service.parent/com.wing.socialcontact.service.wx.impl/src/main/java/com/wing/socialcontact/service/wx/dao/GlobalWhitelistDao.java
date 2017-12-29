package com.wing.socialcontact.service.wx.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wing.socialcontact.service.wx.bean.GlobalWhitelist;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
@Repository
public interface GlobalWhitelistDao extends Mapper<GlobalWhitelist> {
	List<GlobalWhitelist> selectByParam(GlobalWhitelist t);
}
