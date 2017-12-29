package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.InvestProductInsure;
import org.tojoycloud.dubbo.rongba.api.service.InvestProductInsureService;
import org.tojoycloud.dubbo.rongba.provider.mapper.InvestProductInsureMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class InvestProductInsureServiceImpl implements InvestProductInsureService {

	@Autowired
	private InvestProductInsureMapper investProductInsureMapper;

	@Override
	public InvestProductInsure get(String id) {
		return investProductInsureMapper.get(id);
	}

	@Override
	public InvestProductInsure get(InvestProductInsure entity) {
		return investProductInsureMapper.get(entity);
	}

	@Override
	public List<InvestProductInsure> findList(InvestProductInsure entity) {
		return investProductInsureMapper.findList(entity);
	}

	@Override
	public void insert(InvestProductInsure entity) {
		 investProductInsureMapper.insert(entity);
	}

	@Override
	public void update(InvestProductInsure entity) {
		investProductInsureMapper.update(entity);
	}

	@Override
	public void delete(InvestProductInsure entity) {
		investProductInsureMapper.delete(entity);
	}


}
