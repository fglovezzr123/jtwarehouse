package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.TjyLiveSignup;
@Repository
public interface LiveSignupDao extends Mapper<TjyLiveSignup> {

	List<Map<String, Object>> getuserlist(Map parm);

	List usersignupedorno(Map parm);

	List selectmysignups(Map parm);

	List<TjyLiveSignup> getunremindsignups();

}
