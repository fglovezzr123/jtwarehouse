package com.wing.socialcontact.service.wx.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wing.socialcontact.service.wx.api.IThreeDimensionalCodeService;
import com.wing.socialcontact.service.wx.bean.ThreeDimensionalCode;
import com.wing.socialcontact.service.wx.dao.ThreeDimensionalCodeDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;



@Service
public class ThreeDimensionalCodeServiceImpl extends BaseServiceImpl<ThreeDimensionalCode> implements IThreeDimensionalCodeService
{
	
	
	@Resource
	private ThreeDimensionalCodeDao threeDimensionalCodeDao;

	@Override
	public ThreeDimensionalCode insertTDCode(ThreeDimensionalCode threeDimensionalCode)
	{
		threeDimensionalCodeDao.insertTDCode(threeDimensionalCode);
		return threeDimensionalCode;
	}

	@Override
	public int updateTDCode(ThreeDimensionalCode tdCode)
	{
		return threeDimensionalCodeDao.updateTDCode(tdCode);
	}

}
