package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.DynamicPayLog;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:05
 * @version 1.0
 */
public interface IDynamicPayLogService{

	/**
	 * 查询出所有
	 * @return
	 */
	public List selectAllDynamicPayLog(DynamicPayLog dynamicPayLog);
	/**
	 * 条件查询
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectDynamicPayLog(PageParam param,DynamicPayLog dynamicPayLog);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addDynamicPayLog(DynamicPayLog dynamicPayLog);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateDynamicPayLog(DynamicPayLog dynamicPayLog);


	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public DynamicPayLog selectById(String id);
	
	/**
	 * 动态打赏
	 * @return
	 */
	public String insertGratuity(String userId,String dynamicId,String jcount);
	
	/**
	 * 动态媒体付费
	 * @param userId
	 * @param dynamicId
	 * @param jcount
	 * @return
	 */
	public String insertMediaPay(String userId,String dynamicId,String jcount);

}