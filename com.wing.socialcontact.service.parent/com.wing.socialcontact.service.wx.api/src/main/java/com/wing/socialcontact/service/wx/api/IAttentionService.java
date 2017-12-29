package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.service.wx.bean.Attention;

/**
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
public interface IAttentionService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int insertAttention(Attention t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int updateAttention(Attention t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int deleteAttention(Attention t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public List<Attention> queryAttention(Attention t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public Attention getAttention(String id);
	/**
	 * zhangfan
	 * 关注或取消
	 * @param fkId
	 * @param userId
	 */
	public String saveOrDelAttention(Attention attention);
	/**
	 * 查询关注总人数
	 * @param attention
	 * @param fkId
	 * @param userId 
	 * @return
	 * @author liangwj
	 */
	public int selectCount(Attention attention);
	/**
	 * 查询当前用户的关注情况
	 * @param fkId
	 * @param userId 
	 * @return
	 * @author liangwj
	 */
	public Attention getAttentionByFkIdAndUserId(String userId, String fkId);
}