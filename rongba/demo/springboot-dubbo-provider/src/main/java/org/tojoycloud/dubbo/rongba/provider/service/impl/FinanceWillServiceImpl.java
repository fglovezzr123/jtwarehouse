package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceWill;
import org.tojoycloud.dubbo.rongba.api.service.FinanceWillService;
import org.tojoycloud.dubbo.rongba.provider.mapper.FinanceWillMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class FinanceWillServiceImpl implements FinanceWillService {

	@Autowired
	private FinanceWillMapper financeWillMapper;

	@Override
	public FinanceWill get(String id) {
		return financeWillMapper.get(id);
	}

	@Override
	public FinanceWill get(FinanceWill entity) {
		return financeWillMapper.get(entity);
	}

	@Override
	public List<FinanceWill> findList(FinanceWill entity) {
		return financeWillMapper.findList(entity);
	}

	@Override
	public void insert(FinanceWill entity) {
		 financeWillMapper.insert(entity);
	}

	@Override
	public void update(FinanceWill entity) {
		financeWillMapper.update(entity);
	}

	@Override
	public void delete(FinanceWill entity) {
		financeWillMapper.delete(entity);
	}


}
