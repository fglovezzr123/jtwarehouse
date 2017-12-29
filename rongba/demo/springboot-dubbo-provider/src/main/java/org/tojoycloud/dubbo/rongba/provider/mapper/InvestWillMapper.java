package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.InvestWill;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface InvestWillMapper {

	InvestWill get(String id);

	InvestWill get(InvestWill entity);

	List<InvestWill> findList(InvestWill entity);

	void insert(InvestWill entity);

	void update(InvestWill entity);

	void delete(InvestWill entity);

}
