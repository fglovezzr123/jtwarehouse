package com.wing.socialcontact.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;
import com.wing.socialcontact.sys.dao.TjrbInvestProductInsureDao;
import com.wing.socialcontact.sys.service.TjrbInvestProductInsureService;



@Service
public class TjrbInvestProductInsureServiceImpl implements TjrbInvestProductInsureService{
	
	
	@Autowired
	private TjrbInvestProductInsureDao tjrbInvestProductInsureDao;
	
	/**
	 * 
	 */
	@Override
	public List<TjrbInvestProductInsure> selectTjrbInvestProductInsure() {
		// TODO Auto-generated method stub
		return tjrbInvestProductInsureDao.selectTjrbInvestProductInsure();
	}
	
	/**
	 * 
	 */
	@Override
	public boolean deleteTjrbInvestProductInsureById(int id) {
		// TODO Auto-generated method stub
		return tjrbInvestProductInsureDao.deleteTjrbInvestProductInsureById(id);
	}

	/**
	 * 
	 */
	@Override
	public TjrbInvestProductInsure selectTjrbInvestProductInsureById(int id) {
		// TODO Auto-generated method stub
		return tjrbInvestProductInsureDao.selectTjrbInvestProductInsureById(id);
	}

	/**
	 * 
	 */
	@Override
	public boolean updateTjrbInvestProductInsure(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return tjrbInvestProductInsureDao.updateTjrbInvestProductInsure(map);
	}

	/**
	 * 
	 */
	@Override
	public Integer saveTjrbInvestProductInsure(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return tjrbInvestProductInsureDao.saveTjrbInvestProductInsure(map);
	}

	/**
	 * 
	 */
	@Override
	public List<TjrbInvestProductInsure> selectTjrbInvestProductInsureLike(Map<String, String> map) {

		return tjrbInvestProductInsureDao.selectTjrbInvestProductInsureLike(map);
	}


}
