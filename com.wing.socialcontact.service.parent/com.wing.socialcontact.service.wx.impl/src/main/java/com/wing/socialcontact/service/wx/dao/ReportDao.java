package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.Report;

/**
 * 
 * @author zhangfan
 * @date 2017-03-30 15:05:41
 * @version 1.0
 */
@Repository
public interface ReportDao extends Mapper<Report> {
	//查询话题举报
	List selectTopicReportByParam(Map parm);
	
	List<Map<String, Object>> selectReportById(String id);
	
}
