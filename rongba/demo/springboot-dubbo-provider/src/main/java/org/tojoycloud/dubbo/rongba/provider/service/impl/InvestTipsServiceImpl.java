package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.InvestTips;
import org.tojoycloud.dubbo.rongba.api.service.InvestTipsService;
import org.tojoycloud.dubbo.rongba.provider.mapper.InvestTipsMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class InvestTipsServiceImpl implements InvestTipsService {

	@Autowired
	private InvestTipsMapper investTipsMapper;

	@Override
	public InvestTips get(String id) {
		return investTipsMapper.get(id);
	}

	@Override
	public InvestTips get(InvestTips entity) {
		return investTipsMapper.get(entity);
	}

	@Override
	public List<InvestTips> findList(InvestTips entity) {
		return investTipsMapper.findList(entity);
	}

	@Override
	public void insert(InvestTips entity) {
		 investTipsMapper.insert(entity);
	}

	@Override
	public void update(InvestTips entity) {
		investTipsMapper.update(entity);
	}

	@Override
	public void delete(InvestTips entity) {
		investTipsMapper.delete(entity);
	}


}
