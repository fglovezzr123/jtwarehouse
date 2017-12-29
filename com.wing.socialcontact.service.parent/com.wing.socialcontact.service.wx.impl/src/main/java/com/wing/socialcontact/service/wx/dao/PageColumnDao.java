package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.PageColumn;

/**
 * 
 * @author zengmin
 * @date 2017-06-28 16:39:28
 * @version 1.0
 */
@Repository
public interface PageColumnDao extends Mapper<PageColumn> {
	List query(Map<String, Object> param);
}
