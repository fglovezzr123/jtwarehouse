package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Attention;
import com.wing.socialcontact.service.wx.bean.CommentThumbup;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 15:00:55
 * @version 1.0
 */
public interface ICommentThumbupService{

	/**
	 * 查询出所有分类
	 * @return
	 */
	public List selectAllCommentThumbup(CommentThumbup commentThumbup);
	/**
	 * 条件查询
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectCommentThumbups(PageParam param,CommentThumbup commentThumbup);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addCommentThumbup(CommentThumbup commentThumbup);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateCommentThumbup(CommentThumbup commentThumbup);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteCommentThumbups(String[] ids);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean deleteCommentThumbups(String id);

	public CommentThumbup selectByPrimaryKey(String key);
	
	public CommentThumbup selectById(String id);
	
	public int selectcount(CommentThumbup t);

}