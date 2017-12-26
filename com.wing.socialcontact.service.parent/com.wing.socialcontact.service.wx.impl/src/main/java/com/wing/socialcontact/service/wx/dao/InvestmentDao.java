package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.Investment;

public interface InvestmentDao extends Mapper<Investment> {

	List<Map<String, Object>> getMap(Map parm);

	List getInvestmentByClassId(String classid);

	List<Map> getinvestmentList(Map parm);

}
