package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Report;

/**
 * 
 * @author zhangfan
 * @date 2017-03-30 15:05:41
 * @version 1.0
 */
public interface IReportService{

	/**
	 * 条件查询
	 * @param param
	 * @param report
	 * @return
	 */
	public DataGrid selectAllTopicReport(PageParam param, String title,String type,String startTime,String endTime,String isShow);
	
	/**
	 * 添加
	 * @param report
	 * @return
	 */
	public String addReport(Report report);
	/**
	 * 更新
	 * @param report
	 * @return
	 */
	public String updateReport(Report report);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteReport(String[] ids);

	public Report selectByPrimaryKey(String key);
	
	public Report selectById(String id);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectReportById(String id);

}