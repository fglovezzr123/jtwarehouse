package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.BpPerson;
import org.tojoycloud.dubbo.rongba.api.service.BpPersonService;
import org.tojoycloud.dubbo.rongba.provider.mapper.BpPersonMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class BpPersonServiceImpl implements BpPersonService {

	@Autowired
	private BpPersonMapper bpPersonMapper;

	@Override
	public BpPerson get(String id) {
		return bpPersonMapper.get(id);
	}

	@Override
	public BpPerson get(BpPerson entity) {
		return bpPersonMapper.get(entity);
	}

	@Override
	public List<BpPerson> findList(BpPerson entity) {
		return bpPersonMapper.findList(entity);
	}

	@Override
	public void insert(BpPerson entity) {
		 bpPersonMapper.insert(entity);
	}

	@Override
	public void update(BpPerson entity) {
		bpPersonMapper.update(entity);
	}

	@Override
	public void delete(BpPerson entity) {
		bpPersonMapper.delete(entity);
	}


}
