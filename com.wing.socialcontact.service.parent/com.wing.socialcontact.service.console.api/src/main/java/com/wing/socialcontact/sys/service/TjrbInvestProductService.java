package com.wing.socialcontact.sys.service;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.bean.TjrbInvestProduct;
import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;

import java.util.List;


/**
 * 
 * @author Administrator
 *
 */
public interface TjrbInvestProductService {

	/**
	 * 条件查询
	 * @param param
	 * @return
	 */
	DataGrid select(PageParam param, TjrbInvestProduct tjrbInvestProduct);

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
	String add(TjrbInvestProduct tjrbInvestProduct);

	/**
	 *
	 * //TODO 更新
	 * @return
	 */
	String update(TjrbInvestProduct tjrbInvestProduct);

	/**
	 *
	 * //TODO 根据主键查询
	 * @param key
	 * @return
	 */
	TjrbInvestProduct selectByPrimaryKey(Long key);

	/**
	 * 删除
	 * //TODO 添加方法功能描述
	 * @return
	 */
	boolean delete(String[] ids);
}
