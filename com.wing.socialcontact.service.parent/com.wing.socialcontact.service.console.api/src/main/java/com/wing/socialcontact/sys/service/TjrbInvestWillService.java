package com.wing.socialcontact.sys.service;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.bean.TjrbBpPersonEntity;
import com.wing.socialcontact.sys.bean.TjrbInvestWill;

import java.util.List;


/**
 * 
 * @author Administrator
 *
 */
public interface TjrbInvestWillService {

	/**
	 * 条件查询
	 * @param param
	 * @return
	 */
	DataGrid select(PageParam param, TjrbInvestWill tjrbInvestWill);

	/**
	 *
	 * //TODO 获取所有的幻灯片所属的快捷入口
	 * @return
	 */
	List selectAll();
	/**
	 *
	 * //TODO 新增
	 * @return
	 */
	String add(TjrbInvestWill tjrbInvestWill);

	/**
	 *
	 * //TODO 更新
	 * @return
	 */
	String update(TjrbInvestWill tjrbInvestWill);

	/**
	 *
	 * //TODO 根据主键查询
	 * @param key
	 * @return
	 */
	TjrbInvestWill selectByPrimaryKey(Long key);

	/**
	 * 删除
	 * //TODO 添加方法功能描述
	 * @return
	 */
	boolean delete(String[] ids);
}
