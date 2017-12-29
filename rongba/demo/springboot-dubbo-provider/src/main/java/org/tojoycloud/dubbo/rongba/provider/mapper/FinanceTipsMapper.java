package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceTips;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface FinanceTipsMapper   {

	FinanceTips get(String id);

	FinanceTips get(FinanceTips entity);

	List<FinanceTips> findList(FinanceTips entity);

	void insert(FinanceTips entity);

	void update(FinanceTips entity);

	void delete(FinanceTips entity);

}
