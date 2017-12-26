package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.OpenHzbPayLog;

/**
 * 
 * @author liangwj
 * @date 2017-07-20 20:28:03
 * @version 1.0
 */
public interface IOpenHzbPayLogService {
	/**
	 * 获取所有充值记录
	 * 
	 * @Title: selectAllOpenHzbPayLog
	 * @Description: TODO
	 * @param param
	 * @param openHzbPayLog
	 * @return
	 * @return: DataGrid
	 * @author: zengmin
	 * @date: 2017年7月26日 上午11:47:43
	 */
	public DataGrid selectAllOpenHzbPayLog(PageParam param, OpenHzbPayLog openHzbPayLog);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addOpenHzbPayLog(OpenHzbPayLog openHzbPayLog);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updateOpenHzbPayLog(OpenHzbPayLog openHzbPayLog);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteOpenHzbPayLog(String id);

	public OpenHzbPayLog selectByPrimaryKey(String key);

	public OpenHzbPayLog selectById(String id);

	public List<OpenHzbPayLog> selectByOrderId(String orderId);

	/**
	 * 根据互助宝开通订单获取充值记录
	 * 
	 * @Title: selectAllOpenHzbPayLogByOrder
	 * @Description: TODO
	 * @param param
	 * @param openHzbPayLog
	 * @return
	 * @return: DataGrid
	 * @author: zengmin
	 * @date: 2017年7月26日 上午11:48:09
	 */
	DataGrid selectAllOpenHzbPayLogByOrder(PageParam param, OpenHzbPayLog openHzbPayLog);

}