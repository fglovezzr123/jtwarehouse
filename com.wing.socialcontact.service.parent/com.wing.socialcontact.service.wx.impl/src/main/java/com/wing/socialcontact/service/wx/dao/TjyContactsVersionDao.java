package com.wing.socialcontact.service.wx.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.TjyContactsVersion;
@Repository
public interface TjyContactsVersionDao extends Mapper<TjyContactsVersion> {

	Map selectByUserIdAndFriendId(Map param);

	Map selectAddFriendInfo(Map addFriendParam);

}
