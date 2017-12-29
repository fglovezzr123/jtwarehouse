package com.wing.socialcontact.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;


@Repository
public interface TjrbInvestProductInsureDao extends tk.mybatis.mapper.common.Mapper<TjrbInvestProductInsure> {

	List selectByParam(Map parm);
	
}
