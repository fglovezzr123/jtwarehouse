package com.tojoy.business.common.api;


import com.tojoy.business.common.bean.CommentThumbUp;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangyansheng
 * @date 2017-12-24 15:00:55
 * @version 1.0
 */
public interface ICommentThumbUpService {

	/**
	 * 查询出所有分类
	 * @return
	 */
	 List selectCommentThumbUpList(CommentThumbUp t);

	int insert(CommentThumbUp t);
	Map<String, Object> queryById(CommentThumbUp commentThumbUp);
	int queryCount(CommentThumbUp t);
	int delete(CommentThumbUp t);

}