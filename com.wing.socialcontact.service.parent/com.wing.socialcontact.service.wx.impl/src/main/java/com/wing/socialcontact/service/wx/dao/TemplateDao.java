package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.Template;

/**
 * 
 * @author liangwj
 * @date 2017-03-27 11:22:42
 * @version 1.0
 */
@Repository
public interface TemplateDao extends Mapper<Template> {
	List<Map<String, Object>> selectAllTemplateMap();

	List selectByParam(Map parm);
}
