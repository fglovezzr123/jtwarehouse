package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Comment;

/**
 * 
 * @author liangwj
 * @date 2017-03-27 09:01:08
 * @version 1.0
 */
public interface ICommentService{

	/**
	 * 查询出所有分类
	 * @return
	 */
	public List selectAllComment(Comment comment);
	/**
	 * 查询出所有分类
	 * @return
	 */
	public List selectAllComment(Comment comment,int pageNum, int pageSize);
	
	
	/**
	 * 条件查询
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectComments(PageParam param,Comment comment,String startTime, String endTime,String trueName,String title,String mobile,String userId);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addComment(Comment comment);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateComment(Comment comment);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteComments(String[] ids);

	public Comment selectByPrimaryKey(String key);
	
	public Comment selectById(String id);
	
	public List queryCommentbyPid(String pid);
	/**
	 * 查出合作商洽评论
	 * @param comment
	 * @return
	 */
	public List selectAllCommentbd(Comment comment);
	
	//================================APP=================================
	/**
	 * 删除评论
	 * @param commentId
	 */
	public void deleteCommentsApp(String commentId);
	/**
	 * 分页查询评论
	 * @param comment
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	public List<Map<String, Object>> selectAllCommentApp(Comment comment,
			int page, int size);
}