package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.InvestmentClass;

public interface InvestmentClassDao extends Mapper<InvestmentClass> {

	List<Map<String, Object>> getclassMap(Map parm);

	List<Map> getClassList();


}
