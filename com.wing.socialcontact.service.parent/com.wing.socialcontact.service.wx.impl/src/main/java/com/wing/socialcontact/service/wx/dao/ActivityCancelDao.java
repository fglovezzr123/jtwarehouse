package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.ActivityCancel;
@Repository 
public interface ActivityCancelDao extends Mapper<ActivityCancel> {

	List selectList(Map parm);

}
