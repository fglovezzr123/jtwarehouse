package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Banner;

/**
 * 
 * @author zhangfan
 * @date 2017-03-31 17:43:02
 * @version 1.0
 */
@Repository
public interface BannerDao extends Mapper<Banner> {
	
	List selectByParam(Map parm);
	
	List<Banner> selectByType(Map parm);
	
}
