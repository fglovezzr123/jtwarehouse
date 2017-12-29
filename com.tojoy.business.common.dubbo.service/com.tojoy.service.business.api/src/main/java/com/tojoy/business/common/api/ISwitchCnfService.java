package com.tojoy.business.common.api;


import com.tojoy.business.common.bean.SwitchCnf;
import com.tojoy.business.common.model.PageParam;
import com.tojoy.business.common.model.DataGrid;

/**
 *
 * @author
 * @date 2017-11-27
 * @version 1.0
 */
public interface ISwitchCnfService {

	DataGrid queryByParam(PageParam param, SwitchCnf switchCnf);

	int insert(SwitchCnf switchCnf);

	int update(SwitchCnf switchCnf);

	SwitchCnf queryById(String id);

	SwitchCnf queryByKeyWord(SwitchCnf switchCnf);
}