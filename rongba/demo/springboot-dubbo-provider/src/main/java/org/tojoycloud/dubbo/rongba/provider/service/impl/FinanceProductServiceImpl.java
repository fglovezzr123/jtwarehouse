package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceProduct;
import org.tojoycloud.dubbo.rongba.api.service.FinanceProductService;
import org.tojoycloud.dubbo.rongba.provider.mapper.FinanceProductMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class FinanceProductServiceImpl implements FinanceProductService {

	@Autowired
	private FinanceProductMapper financeProductMapper;

	@Override
	public FinanceProduct get(String id) {
		return financeProductMapper.get(id);
	}

	@Override
	public FinanceProduct get(FinanceProduct entity) {
		return financeProductMapper.get(entity);
	}

	@Override
	public List<FinanceProduct> findList(FinanceProduct entity) {
		return financeProductMapper.findList(entity);
	}

	@Override
	public void insert(FinanceProduct entity) {
		 financeProductMapper.insert(entity);
	}

	@Override
	public void update(FinanceProduct entity) {
		financeProductMapper.update(entity);
	}

	@Override
	public void delete(FinanceProduct entity) {
		financeProductMapper.delete(entity);
	}


}
