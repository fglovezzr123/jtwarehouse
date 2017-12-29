package com.wing.socialcontact.sys.service;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.bean.FinanceProduct;
import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;



/**
 * 
 * @author Administrator
 *
 */
public interface TjrbInvestProductInsureService {

	/**
	 * 条件查询
	 * @param param
	 * @return
	 */
	DataGrid select(PageParam param, TjrbInvestProductInsure tjrbInvestProductInsure);

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
	String add(TjrbInvestProductInsure tjrbInvestProductInsure);

	/**
	 *
	 * //TODO 更新
	 * @return
	 */
	String update(TjrbInvestProductInsure tjrbInvestProductInsure);

	/**
	 *
	 * //TODO 根据主键查询
	 * @param key
	 * @return
	 */
	TjrbInvestProductInsure selectByPrimaryKey(Long key);

	/**
	 * 删除
	 * //TODO 添加方法功能描述
	 * @return
	 */
	boolean delete(String[] ids);
}
