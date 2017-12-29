package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.InvestType;
import org.tojoycloud.dubbo.rongba.api.service.InvestTypeService;
import org.tojoycloud.dubbo.rongba.provider.mapper.InvestTypeMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class InvestTypeServiceImpl implements InvestTypeService {

	@Autowired
	private InvestTypeMapper investTypeMapper;

	@Override
	public InvestType get(String id) {
		return investTypeMapper.get(id);
	}

	@Override
	public InvestType get(InvestType entity) {
		return investTypeMapper.get(entity);
	}

	@Override
	public List<InvestType> findList(InvestType entity) {
		return investTypeMapper.findList(entity);
	}

	@Override
	public void insert(InvestType entity) {
		 investTypeMapper.insert(entity);
	}

	@Override
	public void update(InvestType entity) {
		investTypeMapper.update(entity);
	}

	@Override
	public void delete(InvestType entity) {
		investTypeMapper.delete(entity);
	}


}
