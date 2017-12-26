package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.CommentThumbup;
import com.wing.socialcontact.service.wx.bean.DynamicOpLog;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-19 14:19:54
 * @version 1.0
 */
@Repository
public interface DynamicOpLogDao extends Mapper<DynamicOpLog> {
	
	/**
	 * 获取点赞或转发数量
	 */
	public int getCountByDynamicAndType(Map map);
	
	List<Map<String, Object>> selectAllDynamicOpLogMap(DynamicOpLog dynamicOpLog);

	List selectByParam(Map parm);
	
}
