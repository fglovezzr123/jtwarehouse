package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.Org;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface OrgMapper  {

	Org get(Org entity);

	Org get(String id);

	List<Org> findList(Org entity);

	void insert(Org entity);

	void update(Org entity);

	void delete(Org entity);

}
