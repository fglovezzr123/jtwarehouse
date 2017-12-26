package com.wing.socialcontact.service.wx.dao;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.HtmlContent;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:05:28
 * @version 1.0
 */
@Repository
public interface HtmlContentDao extends Mapper<HtmlContent> {
	/**
	 * 分页查询
	 * @param parm
	 * @return
	 */
	List selectByParam(Map parm);
}
