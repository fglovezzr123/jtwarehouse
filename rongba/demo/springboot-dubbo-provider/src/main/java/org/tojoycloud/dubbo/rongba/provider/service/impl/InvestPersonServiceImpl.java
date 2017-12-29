package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.InvestPerson;
import org.tojoycloud.dubbo.rongba.api.service.InvestPersonService;
import org.tojoycloud.dubbo.rongba.provider.mapper.InvestPersonMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class InvestPersonServiceImpl implements InvestPersonService {

	@Autowired
	private InvestPersonMapper investPersonMapper;

	@Override
	public InvestPerson get(String id) {
		return investPersonMapper.get(id);
	}

	@Override
	public InvestPerson get(InvestPerson entity) {
		return investPersonMapper.get(entity);
	}

	@Override
	public List<InvestPerson> findList(InvestPerson entity) {
		return investPersonMapper.findList(entity);
	}

	@Override
	public void insert(InvestPerson entity) {
		 investPersonMapper.insert(entity);
	}

	@Override
	public void updateAccreditatedInvset(InvestPerson entity) {
		investPersonMapper.updateAccreditatedInvset(entity);
	}

	@Override
	public void delete(InvestPerson entity) {
		investPersonMapper.delete(entity);
	}


}
