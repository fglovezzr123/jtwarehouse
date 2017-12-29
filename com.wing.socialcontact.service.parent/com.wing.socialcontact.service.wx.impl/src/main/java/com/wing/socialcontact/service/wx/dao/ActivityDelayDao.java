package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.ActivityDelay;
@Repository
public interface ActivityDelayDao extends Mapper<ActivityDelay> {

	List selectList(Map parm);

}
