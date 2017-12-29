/**
 * 
 */
package org.tojoycloud.dubbo.rongba.provider.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.rongba.api.entity.BpPersonProject;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface BpPersonProjectMapper{

	BpPersonProject get(String id);

	BpPersonProject get(BpPersonProject entity);

	List<BpPersonProject> findList(BpPersonProject entity);

	void insert(BpPersonProject entity);

	void update(BpPersonProject entity);

	void delete(BpPersonProject entity);

}
