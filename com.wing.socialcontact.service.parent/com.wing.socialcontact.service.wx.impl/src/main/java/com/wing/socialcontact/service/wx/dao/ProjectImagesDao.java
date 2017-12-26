package com.wing.socialcontact.service.wx.dao;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.ProjectImages;

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
