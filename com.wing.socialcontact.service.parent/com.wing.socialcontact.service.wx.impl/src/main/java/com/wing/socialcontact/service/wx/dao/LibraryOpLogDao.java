package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.CommentThumbup;
import com.wing.socialcontact.service.wx.bean.LibraryOpLog;

/**
* @author zhangfan
 * @date 2017-10-12 
 * @version 1.0
 */
@Repository
public interface LibraryOpLogDao extends Mapper<LibraryOpLog> {
	
	/**
	 * 获取点赞或转发数量
	 */
	public int getCountByFkIdAndType(Map map);
	
	List<Map<String, Object>> selectAllOpLogMap(LibraryOpLog opLog);

	List selectByParam(Map parm);
	
}
