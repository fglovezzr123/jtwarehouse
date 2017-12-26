package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;
import com.wing.socialcontact.service.wx.bean.OpenScreenAdvertis;
import tk.mybatis.mapper.common.Mapper;

public interface OpenScreenAdvertisDao extends Mapper<OpenScreenAdvertis> 
{
	List query(Map<String, Object> param);
	
	List selectAdvertisList();
}
