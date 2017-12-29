package com.wing.socialcontact.service.wx.dao;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.Faq;

/**
 * 
 * @author liangwj
 * @date 2017-03-26 22:21:25
 * @version 1.0
 */
@Repository
public interface FaqDao extends Mapper<Faq> {
	
}
