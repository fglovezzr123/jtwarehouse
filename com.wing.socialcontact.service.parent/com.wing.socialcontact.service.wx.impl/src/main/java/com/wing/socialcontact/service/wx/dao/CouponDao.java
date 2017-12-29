package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.Coupon;

/**
 * 
 * @author zhangfan
 * @date 2017-07-20 10:24:15
 * @version 1.0
 */
@Repository
public interface CouponDao extends Mapper<Coupon> {
	
	List selectByParam(Map parm);
	
	List<Map<String, Object>> selectAllStore(Map parm);
	
}
