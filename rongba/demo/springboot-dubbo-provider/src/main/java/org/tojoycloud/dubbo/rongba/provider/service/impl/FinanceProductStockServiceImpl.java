package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceProductStock;
import org.tojoycloud.dubbo.rongba.api.service.FinanceProductStockService;
import org.tojoycloud.dubbo.rongba.provider.mapper.FinanceProductStockMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class FinanceProductStockServiceImpl implements FinanceProductStockService {

	@Autowired
	private FinanceProductStockMapper financeProductStockMapper;

	@Override
	public FinanceProductStock get(String id) {
		return financeProductStockMapper.get(id);
	}

	@Override
	public FinanceProductStock get(FinanceProductStock entity) {
		return financeProductStockMapper.get(entity);
	}

	@Override
	public List<FinanceProductStock> findList(FinanceProductStock entity) {
		return financeProductStockMapper.findList(entity);
	}

	@Override
	public void insert(FinanceProductStock entity) {
		 financeProductStockMapper.insert(entity);
	}

	@Override
	public void update(FinanceProductStock entity) {
		financeProductStockMapper.update(entity);
	}

	@Override
	public void delete(FinanceProductStock entity) {
		financeProductStockMapper.delete(entity);
	}


}
