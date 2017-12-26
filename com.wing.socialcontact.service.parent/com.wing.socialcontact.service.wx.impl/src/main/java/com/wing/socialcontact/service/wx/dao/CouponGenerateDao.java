package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.CouponGenerate;

/**
 * 
 * @author zhangfan
 * @date 2017-07-20 11:00:18
 * @version 1.0
 */
@Repository
public interface CouponGenerateDao extends Mapper<CouponGenerate> {
	
	List selectByParam(Map parm);
	
}
