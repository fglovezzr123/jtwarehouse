package com.tojoy.business.common.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tojoy.business.common.api.ICommentService;
import com.tojoy.business.common.bean.Comment;
import com.tojoy.business.common.dao.CommentDao;
import com.tojoy.business.common.model.DataGrid;
import com.tojoy.business.common.model.PageParam;
import com.tojoy.business.common.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangyansheng
 * @date 2017-12-27 09:01:08
 * @version 1.0
 */
@Service
public class CommentServiceImpl implements ICommentService {

	@Resource
	private CommentDao commentDao;
	
	@Override
	public List selectCommentList(Comment comment) {
		return commentDao.selectCommentList(comment);
	}

	@Override
	public List selectCommentList(PageParam param,Comment comment){
		PageHelper.startPage(param.getPage(), param.getRows());
		return commentDao.selectCommentList(comment);
	}

	@Override
	public int addComment(Comment comment) {
		comment.setId(UUIDGenerator.getUUID());
		comment.setDeleted(0);
		return commentDao.insert(comment);
	}

	@Override
	public Map<String, Object> selectById(Comment comment) {
		return commentDao.selectById(comment);
	}

	@Override
	public List queryCommentByPId(Comment comment) {
		return commentDao.queryCommentByPId(comment);
	}

}