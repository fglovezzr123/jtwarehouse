package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.SysBlacklist;

/**
 * 
 * @author zengmin
 * @date 2017-08-08 08:56:15
 * @version 1.0
 */
@Repository
public interface SysBlacklistDao extends Mapper<SysBlacklist> {

	List<SysBlacklist> selectSysBlacklistByUserId(String userId);

	List<Map<String,Object>> query(Map<String, Object> param);

	
}
