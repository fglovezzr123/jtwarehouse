package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceWill;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface FinanceWillMapper  {

	FinanceWill get(String id);

	FinanceWill get(FinanceWill entity);

	List<FinanceWill> findList(FinanceWill entity);

	void insert(FinanceWill entity);

	void update(FinanceWill entity);

	void delete(FinanceWill entity);

}
