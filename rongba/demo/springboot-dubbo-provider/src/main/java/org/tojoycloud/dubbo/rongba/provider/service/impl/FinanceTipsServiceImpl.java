package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceTips;
import org.tojoycloud.dubbo.rongba.api.service.FinanceTipsService;
import org.tojoycloud.dubbo.rongba.provider.mapper.FinanceTipsMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class FinanceTipsServiceImpl implements FinanceTipsService {

	@Autowired
	private FinanceTipsMapper financeTipsMapper;

	@Override
	public FinanceTips get(String id) {
		return financeTipsMapper.get(id);
	}

	@Override
	public FinanceTips get(FinanceTips entity) {
		return financeTipsMapper.get(entity);
	}

	@Override
	public List<FinanceTips> findList(FinanceTips entity) {
		return financeTipsMapper.findList(entity);
	}

	@Override
	public void insert(FinanceTips entity) {
		 financeTipsMapper.insert(entity);
	}

	@Override
	public void update(FinanceTips entity) {
		financeTipsMapper.update(entity);
	}

	@Override
	public void delete(FinanceTips entity) {
		financeTipsMapper.delete(entity);
	}


}
