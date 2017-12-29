package com.tojoy.business.common.api;


import com.tojoy.business.common.bean.Collection;
import com.tojoy.business.common.bean.ThumpUp;

/**
 * 
 * @author wangyansheng
 * @date 2017-03-26
 * @version 1.0
 */
public interface IThumbUpService {
	/**
	 * 新增
	 * @param t
	 * @author wangyansheng
	 * @date 2017-03-26
	 */
	public int insert(ThumpUp t);

	/**
	 * 删除
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-03-26
	 */
	public int delete(ThumpUp t);

	/**
	 * 查询关注总人数
	 * @param t
	 * @return
	 * @author wangyansheng
	 */
	public int queryCount(ThumpUp t);

	/**
	 * 查询当前用户的关注情况
	 * @param t
	 * @return
	 * @author wangyansheng
	 */
	public ThumpUp queryByFkIdUserId(ThumpUp t);
}