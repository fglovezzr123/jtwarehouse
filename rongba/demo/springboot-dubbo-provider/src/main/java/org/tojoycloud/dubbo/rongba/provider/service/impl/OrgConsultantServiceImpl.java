package org.tojoycloud.dubbo.rongba.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.rongba.api.entity.OrgConsultant;
import org.tojoycloud.dubbo.rongba.api.service.OrgConsultantService;
import org.tojoycloud.dubbo.rongba.provider.mapper.OrgConsultantMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class OrgConsultantServiceImpl implements OrgConsultantService{

	@Autowired
	private OrgConsultantMapper orgConsultantMapper;

	@Override
	public OrgConsultant get(String id) {
		return orgConsultantMapper.get(id);
	}

	@Override
	public OrgConsultant get(OrgConsultant entity) {
		return orgConsultantMapper.get(entity);
	}

	@Override
	public List<OrgConsultant> findList(OrgConsultant entity) {
		return orgConsultantMapper.findList(entity);
	}

	@Override
	public void insert(OrgConsultant entity) {
		orgConsultantMapper.insert(entity);
	}

	@Override
	public void update(OrgConsultant entity) {
		orgConsultantMapper.update(entity);
	}

	@Override
	public void delete(OrgConsultant entity) {
		orgConsultantMapper.delete(entity);
	}


}
