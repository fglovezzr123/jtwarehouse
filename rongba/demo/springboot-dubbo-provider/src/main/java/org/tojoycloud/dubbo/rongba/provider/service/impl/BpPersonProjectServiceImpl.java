package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.BpPersonProject;
import org.tojoycloud.dubbo.rongba.api.service.BpPersonProjectService;
import org.tojoycloud.dubbo.rongba.provider.mapper.BpPersonProjectMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class BpPersonProjectServiceImpl implements BpPersonProjectService {

	@Autowired
	private BpPersonProjectMapper bpPersonProjectMapper;

	@Override
	public BpPersonProject get(String id) {
		return bpPersonProjectMapper.get(id);
	}

	@Override
	public BpPersonProject get(BpPersonProject entity) {
		return bpPersonProjectMapper.get(entity);
	}

	@Override
	public List<BpPersonProject> findList(BpPersonProject entity) {
		return bpPersonProjectMapper.findList(entity);
	}

	@Override
	public void insert(BpPersonProject entity) {
		 bpPersonProjectMapper.insert(entity);
	}

	@Override
	public void update(BpPersonProject entity) {
		bpPersonProjectMapper.update(entity);
	}

	@Override
	public void delete(BpPersonProject entity) {
		bpPersonProjectMapper.delete(entity);
	}


}
