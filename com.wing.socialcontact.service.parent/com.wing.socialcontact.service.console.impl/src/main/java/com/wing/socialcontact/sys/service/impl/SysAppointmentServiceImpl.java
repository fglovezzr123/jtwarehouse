package com.wing.socialcontact.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.sys.bean.SysAppointment;
import com.wing.socialcontact.sys.dao.SysAppointmentDao;
import com.wing.socialcontact.sys.service.SysIAppointmentService;


@Service
public class SysAppointmentServiceImpl extends BaseServiceImpl<SysAppointment> implements SysIAppointmentService
{
	@Resource
	private SysAppointmentDao dao;
	
	
	@Override
	public List selectListByParam(PageParam param,String fromName, String fromTel, String toName, String toTel, String status)
	{
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("fromName", fromName);
		parm.put("fromTel", fromTel);
		parm.put("toName", toName);
		parm.put("toTel", toTel);
		parm.put("status", status);
		List lst = dao.selectByParam(parm);
		return dao.selectByParam(parm);
	}


	@Override
	public boolean deleteAppointments(String[] ids)
	{
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					SysAppointment appointment=dao.loadById(string);
					if(appointment!=null){
						SysAppointment appointmentUpdate = new SysAppointment();
						appointmentUpdate.setId(appointment.getId());
						appointmentUpdate.setStatus("3");
						dao.update(appointmentUpdate);
						count++;
					}
				}
			}
		}
		return count>0;
	}

	

	

}
