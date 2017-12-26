package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.CommentThumbup;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 15:00:56
 * @version 1.0
 */
@Repository
public interface CommentThumbupDao extends Mapper<CommentThumbup> {
	List<Map<String, Object>> selectAllCommentThumbupMap(CommentThumbup commentThumbup);

	List selectByParam(Map parm);
	
	int selectcount(CommentThumbup t);
}
