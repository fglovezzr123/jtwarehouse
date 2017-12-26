package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.PageAggregate;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:28
 * @version 1.0
 */
@Repository
public interface PageAggregateDao extends Mapper<PageAggregate> {
	List query(Map<String, Object> param);
	List queryByName(Map<String, Object> param);
}
