package com.tojoy.business.common.dao;

import com.tojoy.business.common.bean.Comment;
import com.tojoy.business.common.bean.CommentThumbUp;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangyansheng
 * @date 2017-12-25 15:00:56
 * @version 1.0
 */
@Repository
public interface CommentThumbUpDao {

	List<Map<String, Object>> queryList(CommentThumbUp t);

	Map<String, Object> queryById(CommentThumbUp t);

	int delete(CommentThumbUp t);

	int insert(CommentThumbUp t);

	int queryCount(CommentThumbUp t);
}
