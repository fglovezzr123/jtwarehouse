package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.OrgConsultant;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface OrgConsultantMapper  {

	OrgConsultant get(String id);

	OrgConsultant get(OrgConsultant entity);

	List<OrgConsultant> findList(OrgConsultant entity);

	void insert(OrgConsultant entity);

	void update(OrgConsultant entity);

	void delete(OrgConsultant entity);

}
