package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.service.wx.bean.ThreeDimensionalCode;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecord;

public interface IThreeDimensionalCodeService
{
	/**
	 * 生成三维码
	 * 
	 * @param
	 * @return
	 */
	public ThreeDimensionalCode insertTDCode(ThreeDimensionalCode tdCode);
	/**
	 * 修改三维码
	 * 
	 * @param
	 * @return
	 */
	public int updateTDCode(ThreeDimensionalCode tdCode);
}
