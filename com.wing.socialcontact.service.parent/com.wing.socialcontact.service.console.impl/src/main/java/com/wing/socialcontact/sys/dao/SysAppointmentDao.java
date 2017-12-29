package com.wing.socialcontact.sys.dao;

import java.util.List;
import java.util.Map;
import com.wing.socialcontact.sys.bean.SysAppointment;
import tk.mybatis.mapper.common.Mapper;

public interface SysAppointmentDao extends Mapper<SysAppointment> 
{
	List selectByParam(Map parm);
	public SysAppointment loadById(String id);
	public int update(SysAppointment sysAppointment);
}
