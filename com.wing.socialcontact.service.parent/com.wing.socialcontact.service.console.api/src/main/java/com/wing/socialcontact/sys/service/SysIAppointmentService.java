package com.wing.socialcontact.sys.service;

import java.util.List;
import com.wing.socialcontact.common.model.PageParam;

public interface SysIAppointmentService
{
	public List selectListByParam(PageParam param,String fromName,String fromTel, String toName,String toTel,String status);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteAppointments(String[] ids);
}
