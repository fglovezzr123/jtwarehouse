package com.wing.socialcontact.service.wx.api;

import java.util.Date;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.OpenHzbOrder;

/**
 * 
 * @author liangwj
 * @date 2017-07-20 20:28:03
 * @version 1.0
 */
public interface IOpenHzbOrderService {
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllOpenHzbOrder(PageParam param, OpenHzbOrder openHzbOrder,Date stime,Date etime,Integer dfklow,Integer dfkhigh);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addOpenHzbOrder(OpenHzbOrder openHzbOrder);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updateOpenHzbOrder(OpenHzbOrder openHzbOrder);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteOpenHzbOrder(String id);

	public OpenHzbOrder selectByPrimaryKey(String key);

	public OpenHzbOrder selectById(String id);

	public OpenHzbOrder selectByUserId(String userId);
}