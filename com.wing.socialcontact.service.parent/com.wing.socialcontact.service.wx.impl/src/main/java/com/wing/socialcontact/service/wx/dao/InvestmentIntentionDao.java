package com.wing.socialcontact.service.wx.dao;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.InvestmentIntention;


public interface InvestmentIntentionDao extends Mapper<InvestmentIntention> {

	List<Map<String, Object>> getintentionMap(Map parm);

	List<Map> getinvestmentList(Map parm);

}
