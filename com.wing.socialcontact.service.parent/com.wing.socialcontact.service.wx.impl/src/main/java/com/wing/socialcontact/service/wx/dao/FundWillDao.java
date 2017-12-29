package com.wing.socialcontact.service.wx.dao;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.FundWill;

/**
 * 
 * @author liangwj
 * @date 2017-03-26 22:21:26
 * @version 1.0
 */
@Repository
public interface FundWillDao extends Mapper<FundWill> {
	
}
