package com.tojoy.business.common.dao;


import com.tojoy.business.common.bean.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangyansheng
 * @date 2017-03-27 09:01:08
 * @version 1.0
 */
@Repository
public interface CommentDao{

	List<Map<String, Object>> selectCommentList(Comment comment);

	List<Map<String, Object>> queryCommentByPId(Comment comment);

	int insert(Comment comment);

	Map<String, Object> selectById(Comment comment);

}
