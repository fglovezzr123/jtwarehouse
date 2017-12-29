package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceProduct;


/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface FinanceProductMapper{

	FinanceProduct get(String id);

	FinanceProduct get(FinanceProduct entity);

	List<FinanceProduct> findList(FinanceProduct entity);

	void insert(FinanceProduct entity);

	void update(FinanceProduct entity);

	void delete(FinanceProduct entity);

}
