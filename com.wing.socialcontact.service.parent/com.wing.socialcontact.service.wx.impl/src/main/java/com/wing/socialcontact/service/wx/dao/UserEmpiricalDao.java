package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.service.wx.bean.UserEmpiricalLog;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:51
 * @version 1.0
 */
@Repository
public interface UserEmpiricalDao extends Mapper<UserEmpirical> {
	List selectByParam(Map parm);
	
	/**
	 *根据分获取等级
	 * @param Empirical
	 * @return
	 */
	UserEmpirical selectByEmpirical(@Param("Empirical") Integer Empirical);
	/**
	 *根据level获取等级
	 * @param Empirical
	 * @return
	 */
	UserEmpirical selectBylevel(@Param("level") String level);
}
