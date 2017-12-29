package com.wing.socialcontact.service.wx.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.HzbManagerLog;

/**
 * 
 * @author liangwj
 * @date 2017-07-22 18:21:15
 * @version 1.0
 */
public interface IHzbManagerLogService {
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllHzbManagerLog(PageParam param, HzbManagerLog hzbManagerLog);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addHzbManagerLog(HzbManagerLog hzbManagerLog);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updateHzbManagerLog(HzbManagerLog hzbManagerLog);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteHzbManagerLog(String id);

	public HzbManagerLog selectByPrimaryKey(String key);

	public HzbManagerLog selectById(String id);

	public List<HzbManagerLog> selectByUserId(String userId);

	/**
	 * 前台查询互助宝操作记录（type=4\5\7\8）
	 * 
	 * @Title: selectHzbLogPageByUserId
	 * @Description: TODO
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @return: List<Map<String,Object>>
	 * @author: zengmin
	 * @date: 2017年7月26日 下午4:29:39
	 */
	public List<Map<String, Object>> selectHzbLogPageByUserId(String userId, int pageNum, int pageSize);

	/**
	 * 统计年度累充
	 * 
	 * @Title: selectHzbLcjeByUserIdAndTime
	 * @Description: TODO
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @return: double
	 * @author: zengmin
	 * @date: 2017年7月27日 上午11:41:42
	 */
	public double selectHzbLcjeByUserIdAndTime(String userId, Date startDate, Date endDate);

	/**
	 * 统计年度累消
	 * 
	 * @Title: selectHzbLcjeByUserIdAndTime
	 * @Description: TODO
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @return: double
	 * @author: zengmin
	 * @date: 2017年7月27日 上午11:41:42
	 */
	public double selectHzbLxjeByUserIdAndTime(String userId, Date startDate, Date endDate);

}