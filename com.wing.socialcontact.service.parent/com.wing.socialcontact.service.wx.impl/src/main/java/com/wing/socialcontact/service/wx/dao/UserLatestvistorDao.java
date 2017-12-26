package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.UserFav;
import com.wing.socialcontact.service.wx.bean.UserLatestvistor;

/**
 * 
 * @author gaojun
 * @date 2017-05-02 11:14:41
 * @version 1.0
 */
@Repository
public interface UserLatestvistorDao extends Mapper<UserLatestvistor> {
	List<Map<String, Object>> selectLatestVistors(UserLatestvistor userLatestvistor);

	List selectByParam(Map parm);
}
