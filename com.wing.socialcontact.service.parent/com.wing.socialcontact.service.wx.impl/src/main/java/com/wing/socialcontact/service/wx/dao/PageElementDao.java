package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.PageElement;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:29
 * @version 1.0
 */
@Repository
public interface PageElementDao extends Mapper<PageElement> {
	List query(Map<String, Object> param);

	boolean deleteByColumnId(String columnId);
}
