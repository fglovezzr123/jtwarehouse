package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.InvestProduct;
import org.tojoycloud.dubbo.rongba.api.service.InvestProductService;
import org.tojoycloud.dubbo.rongba.provider.mapper.InvestProductMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class InvestProductServiceImpl implements InvestProductService {

	@Autowired
	private InvestProductMapper investProductMapper;

	@Override
	public InvestProduct get(String id) {
		return investProductMapper.get(id);
	}

	@Override
	public InvestProduct get(InvestProduct entity) {
		return investProductMapper.get(entity);
	}

	@Override
	public List<InvestProduct> findList(InvestProduct entity) {
		return investProductMapper.findList(entity);
	}

	@Override
	public void insert(InvestProduct entity) {
		 investProductMapper.insert(entity);
	}

	@Override
	public void update(InvestProduct entity) {
		investProductMapper.update(entity);
	}

	@Override
	public void delete(InvestProduct entity) {
		investProductMapper.delete(entity);
	}


}
