package com.tojoy.service.wx.dao;

import java.util.List;
import java.util.Map;

import com.tojoy.service.wx.bean.SyDistrict;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;


@Repository
public interface DistrictDao extends Mapper<SyDistrict> {

	List selectByParam(Map parm);

	int selectDistrictCount(SyDistrict dis);
	
}