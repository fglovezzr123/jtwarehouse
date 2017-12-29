package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.InvestWill;
import org.tojoycloud.dubbo.rongba.api.service.InvestWillService;
import org.tojoycloud.dubbo.rongba.provider.mapper.InvestWillMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class InvestWillServiceImpl implements InvestWillService {

	@Autowired
	private InvestWillMapper investWillMapper;

	@Override
	public InvestWill get(String id) {
		return investWillMapper.get(id);
	}

	@Override
	public InvestWill get(InvestWill entity) {
		return investWillMapper.get(entity);
	}

	@Override
	public List<InvestWill> findList(InvestWill entity) {
		return investWillMapper.findList(entity);
	}

	@Override
	public void insert(InvestWill entity) {
		 investWillMapper.insert(entity);
	}

	@Override
	public void update(InvestWill entity) {
		investWillMapper.update(entity);
	}

	@Override
	public void delete(InvestWill entity) {
		investWillMapper.delete(entity);
	}


}
