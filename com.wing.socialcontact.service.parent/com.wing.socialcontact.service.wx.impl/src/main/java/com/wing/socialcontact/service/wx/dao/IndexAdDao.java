package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.IndexAd;

/**
 * 
 * @author zengmin
 * @date 2017-07-07 15:45:48
 * @version 1.0
 */
@Repository
public interface IndexAdDao extends Mapper<IndexAd> {
	List query(Map<String, Object> param);
}
