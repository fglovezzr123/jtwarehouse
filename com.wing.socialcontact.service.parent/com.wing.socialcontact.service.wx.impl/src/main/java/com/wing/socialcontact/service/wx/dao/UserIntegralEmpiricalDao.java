package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.UserIntegralEmpirical;
import com.wing.socialcontact.service.wx.bean.WxUser;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:54
 * @version 1.0
 */
@Repository
public interface UserIntegralEmpiricalDao extends Mapper<UserIntegralEmpirical> {
	List selectByParam(Map parm);
	
	UserIntegralEmpirical selectByIeType(String IeType);
}
