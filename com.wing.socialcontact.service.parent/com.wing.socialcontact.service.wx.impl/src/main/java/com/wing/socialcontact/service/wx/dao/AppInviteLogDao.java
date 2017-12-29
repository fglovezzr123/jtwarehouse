package com.wing.socialcontact.service.wx.dao;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.AppInviteLog;
@Repository
public interface AppInviteLogDao extends Mapper<AppInviteLog> {

}
