package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.InvestType;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface InvestTypeMapper  {

	InvestType get(String id);

	InvestType get(InvestType entity);

	List<InvestType> findList(InvestType entity);

	void insert(InvestType entity);

	void update(InvestType entity);

	void delete(InvestType entity);

}
