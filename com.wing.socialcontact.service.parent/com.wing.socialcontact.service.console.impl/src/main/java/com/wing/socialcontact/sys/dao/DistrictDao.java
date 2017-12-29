package com.wing.socialcontact.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.sys.bean.SyDistrict;

@Repository
public interface DistrictDao extends Mapper<SyDistrict> {

	List selectByParam(Map parm);

	int selectDistrictCount(SyDistrict dis);
	
}