package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.InvestProductInsure;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface InvestProductInsureMapper {

	InvestProductInsure get(String id);

	InvestProductInsure get(InvestProductInsure entity);

	List<InvestProductInsure> findList(InvestProductInsure entity);

	void insert(InvestProductInsure entity);

	void update(InvestProductInsure entity);

	void delete(InvestProductInsure entity);

}
