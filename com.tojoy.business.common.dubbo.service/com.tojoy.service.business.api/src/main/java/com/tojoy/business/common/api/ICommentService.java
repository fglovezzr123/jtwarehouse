package com.tojoy.business.common.api;


import com.tojoy.business.common.bean.Comment;
import com.tojoy.business.common.model.DataGrid;
import com.tojoy.business.common.model.PageParam;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangyansheng
 * @date 2017-03-27 09:01:08
 * @version 1.0
 */
public interface ICommentService{

	/**
	 * 查询出所有分类
	 * @return
	 */
	public List selectCommentList(Comment comment);
	/**
	 * 查询出所有分类
	 * @return
	 */
	public List selectCommentList(PageParam param,Comment comment);

	/**
	 * 添加
	 * @param comment
	 * @return
	 */
	public int addComment(Comment comment);

	public Map<String, Object> selectById(Comment comment);
	
	public List queryCommentByPId(Comment comment);


}