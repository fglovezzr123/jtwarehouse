package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.Banner;
import com.wing.socialcontact.service.wx.bean.Honor;
import com.wing.socialcontact.service.wx.bean.UserIntegralLog;

/**
 * 
 * @author gaojun
 * @date 2017-04-14 22:34:35
 * @version 1.0
 */
@Repository
public interface HonorDao extends Mapper<Honor> {
	List selectByParam(Map parm);
	
	List<Map<String, Object>> selectAllHonor(Honor honor);
	
	List<Honor> selectByType(Map parm);
	
	Honor selectByCode(@Param("honorCode") String honorCode);
	
}
