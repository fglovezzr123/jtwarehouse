package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.Comment;

/**
 * 
 * @author liangwj
 * @date 2017-03-27 09:01:08
 * @version 1.0
 */
@Repository
public interface CommentDao extends Mapper<Comment> {
	
	List<Map<String, Object>> selectAllCommentMap(Comment comment);
	
	List<Map<String, Object>> selectAllCommentMap2(Map parm);
	
	List<Map<String, Object>> selectAllCommentMap3(Map parm);

	List selectByParam(Map parm);
	
	List<Map<String, Object>> queryCommentByPid(String pid);
	
	Integer  selectSumShafa(@Param("userId") String userId, @Param("cmeType") String cmeType);
	
	List<Map<String, Object>> selectAllCommentMap4(Comment comment);

	List<Map<String, Object>> selectAllCommentMapApp(Map parm
			);
	
}
