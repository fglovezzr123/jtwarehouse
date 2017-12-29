package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceProductStock;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface FinanceProductStockMapper  {

	FinanceProductStock get(String id);

	FinanceProductStock get(FinanceProductStock entity);

	List<FinanceProductStock> findList(FinanceProductStock entity);

	void insert(FinanceProductStock entity);

	void update(FinanceProductStock entity);

	void delete(FinanceProductStock entity);

}
