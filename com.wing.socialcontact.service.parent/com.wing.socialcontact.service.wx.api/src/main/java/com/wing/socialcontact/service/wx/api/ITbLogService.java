package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.TbLog;
import com.wing.socialcontact.service.wx.bean.TbLogContent;

/**
 *
 * @author wangyansheng
 * @date 2017-11-27
 * @version 1.0
 */
public interface ITbLogService {

	/**
	 * 新增
	 * @param t
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int insert(TbLog t);

	/**
	 * 修改
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int update(TbLog t);

	/**
	 * 删除
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int logicalDelete(TbLog t);

	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public DataGrid selectLogByParam(PageParam param, TbLog t);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public TbLog selectById(String id);

	/**
	 * 分页查询变更数据
	 * @param param
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	public DataGrid selectLogContentByParam(PageParam param, TbLogContent t);
}