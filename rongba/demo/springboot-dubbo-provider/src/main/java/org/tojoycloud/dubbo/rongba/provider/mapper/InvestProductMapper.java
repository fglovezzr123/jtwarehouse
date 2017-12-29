package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.InvestProduct;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface InvestProductMapper  {

	InvestProduct get(String id);

	InvestProduct get(InvestProduct entity);

	List<InvestProduct> findList(InvestProduct entity);

	void insert(InvestProduct entity);

	void update(InvestProduct entity);

	void delete(InvestProduct entity);

}
