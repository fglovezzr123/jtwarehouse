package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.InvestPerson;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface InvestPersonMapper  {

	InvestPerson get(String id);

	InvestPerson get(InvestPerson entity);

	List<InvestPerson> findList(InvestPerson entity);

	void insert(InvestPerson entity);

	void updateAccreditatedInvset(InvestPerson entity);

	void delete(InvestPerson entity);

}
