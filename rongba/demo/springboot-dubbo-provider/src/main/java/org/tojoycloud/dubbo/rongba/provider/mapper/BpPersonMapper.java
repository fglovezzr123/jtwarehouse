/**
 * 
 */
package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.BpPerson;


/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface BpPersonMapper  {

	BpPerson get(String id);

	BpPerson get(BpPerson entity);

	List<BpPerson> findList(BpPerson entity);

	void insert(BpPerson entity);

	void update(BpPerson entity);

	void delete(BpPerson entity);

}
