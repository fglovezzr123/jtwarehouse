package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.Org;
import org.tojoycloud.dubbo.rongba.api.service.OrgService;
import org.tojoycloud.dubbo.rongba.provider.mapper.OrgMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class OrgServiceImpl implements OrgService{

	@Autowired
	private OrgMapper orgMapper;

	@Override
	public Org get(String id) {
		return orgMapper.get(id);
	}

	@Override
	public Org get(Org entity) {
		return orgMapper.get(entity);
	}

	@Override
	public List<Org> findList(Org entity) {
		return orgMapper.findList(entity);
	}

	@Override
	public void insert(Org entity) {
		orgMapper.insert(entity);
	}

	@Override
	public void update(Org entity) {
		orgMapper.update(entity);
	}

	@Override
	public void delete(Org entity) {
		orgMapper.delete(entity);
	}


}
