package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.Accessory;

/**
 * 
 * @author liangwj
 * @date 2017-03-27 11:24:18
 * @version 1.0
 */
@Repository
public interface AccessoryDao extends Mapper<Accessory> {
	List<Map<String, Object>> selectAllAccessoryMap();

	List selectByParam(Map parm);

	void deleteByTerm(Map parm);
}
