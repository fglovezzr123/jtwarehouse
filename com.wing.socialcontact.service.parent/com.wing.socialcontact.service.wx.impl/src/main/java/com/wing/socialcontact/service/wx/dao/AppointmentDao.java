package com.wing.socialcontact.service.wx.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecord;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecordDetails;
import tk.mybatis.mapper.common.Mapper;

public interface AppointmentDao extends Mapper<UserAppointmentRecord>
{
	int insertAPPointmentRecord(UserAppointmentRecord userAppointmentRecord);

	UserAppointmentRecord getUserAppointmentRecordByid(long id);

	int updateAppointmentRecord(UserAppointmentRecord userAppointmentRecord);

	List findWaitAppointmentRecord(Map paramMap);

	List findHaveAppointmentRecord(Map paramMap);

	List findByAppointmentRecord(Map paramMap);
	
	int insertDetails(UserAppointmentRecordDetails uard);
	
	List getUserAppointmentRecordByExpecttime(Date expecttime);
	
	List getDyjList(Map paramMap);
	
	List getYyjList(Map paramMap);
	
	List getByjList(Map paramMap);
	
	String getUserHobby(String userId);
}
