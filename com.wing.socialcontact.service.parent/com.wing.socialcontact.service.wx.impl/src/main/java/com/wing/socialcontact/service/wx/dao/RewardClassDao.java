package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.RewardClass;

/**
 * 
 * @author zhangfan
 * @date 2017-06-12 13:25:02
 * @version 1.0
 */
@Repository
public interface RewardClassDao extends Mapper<RewardClass> {
	
	List selectByParam(Map parm);
	
	List selectAllClassMap(Map parm);
}
