package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.Vote;

/**
 * 
 * @author zhangfan
 * @date 2017-04-13 10:46:03
 * @version 1.0
 */
@Repository
public interface VoteDao extends Mapper<Vote> {
	
	List selectByParam(Map parm);
	
}
