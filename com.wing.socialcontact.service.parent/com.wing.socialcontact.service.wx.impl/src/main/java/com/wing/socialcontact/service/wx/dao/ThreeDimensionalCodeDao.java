package com.wing.socialcontact.service.wx.dao;

import com.wing.socialcontact.service.wx.bean.ThreeDimensionalCode;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecord;
import tk.mybatis.mapper.common.Mapper;

public interface ThreeDimensionalCodeDao extends Mapper<ThreeDimensionalCode>
{
	int insertTDCode(ThreeDimensionalCode threeDimensionalCode);
	
	int updateTDCode(ThreeDimensionalCode threeDimensionalCode);
}
