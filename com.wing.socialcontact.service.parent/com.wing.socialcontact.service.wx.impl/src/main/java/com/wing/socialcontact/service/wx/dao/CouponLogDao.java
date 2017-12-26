package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.CouponLog;

/**
 * 
 * @author zhangfan
 * @date 2017-07-20 11:03:44
 * @version 1.0
 */
@Repository
public interface CouponLogDao extends Mapper<CouponLog> {
	
	List selectByParam(Map parm);
	
	List selectFrontByParam(Map parm);
}
