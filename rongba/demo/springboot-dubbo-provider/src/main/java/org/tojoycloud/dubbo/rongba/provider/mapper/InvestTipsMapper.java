package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.InvestTips;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface InvestTipsMapper{

	InvestTips get(String id);

	InvestTips get(InvestTips entity);

	List<InvestTips> findList(InvestTips entity);

	void insert(InvestTips entity);

	void update(InvestTips entity);

	void delete(InvestTips entity);

}
