package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.UserIntegral;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:53
 * @version 1.0
 */
@Repository
public interface UserIntegralDao extends Mapper<UserIntegral> {
	List selectByParam(Map parm);
	UserIntegral selectByCode(String code);
}
