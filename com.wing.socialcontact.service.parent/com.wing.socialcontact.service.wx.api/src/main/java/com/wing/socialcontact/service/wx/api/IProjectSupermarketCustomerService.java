package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.ProjectSupermarketCustomer;


/**
 *
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
public interface IProjectSupermarketCustomerService {

	/**
	 * 新增
	 * @param t
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int insert(ProjectSupermarketCustomer t);

	/**
	 * 修改
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int update(ProjectSupermarketCustomer t);

	/**
	 * 删除
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int delete(ProjectSupermarketCustomer t);

	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public DataGrid selectByParam(PageParam param, ProjectSupermarketCustomer t);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public ProjectSupermarketCustomer selectById(String id);

}