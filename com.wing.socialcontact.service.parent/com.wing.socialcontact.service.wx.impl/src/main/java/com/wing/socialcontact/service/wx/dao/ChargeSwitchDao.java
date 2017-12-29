package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;
import com.wing.socialcontact.service.wx.bean.ChargeSwitch;
import tk.mybatis.mapper.common.Mapper;

public interface ChargeSwitchDao extends Mapper<ChargeSwitch> 
{
	List query(Map<String, Object> param);
	
	List selectChargeSwitchList();
	
	ChargeSwitch getChargeSwitchBySign(Map paramMap);
}
