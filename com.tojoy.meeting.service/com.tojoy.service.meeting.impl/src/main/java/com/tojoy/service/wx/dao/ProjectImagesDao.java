package com.tojoy.service.wx.dao;

import com.tojoy.service.wx.bean.ProjectImages;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
@Repository
public interface ProjectImagesDao extends Mapper<ProjectImages> {
	/**
	 * 分页查询
	 * @param parm
	 * @return
	 */
	List<ProjectImages> selectByParam(Map parm);
}
