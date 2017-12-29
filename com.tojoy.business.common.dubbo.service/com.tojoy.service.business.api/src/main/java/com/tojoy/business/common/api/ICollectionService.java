package com.tojoy.business.common.api;


import com.tojoy.business.common.bean.Collection;

/**
 * 
 * @author wangyansheng
 * @date 2017-03-26
 * @version 1.0
 */
public interface ICollectionService {
	/**
	 * 新增
	 * @param t
	 * @author wangyansheng
	 * @date 2017-03-26
	 */
	public int insert(Collection t);

	/**
	 * 删除
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-03-26
	 */
	public int delete(Collection t);

	/**
	 * 查询关注总人数
	 * @param collection
	 * @return
	 * @author wangyansheng
	 */
	public int queryCollectionCount(Collection collection);

	/**
	 * 查询当前用户的关注情况
	 * @param collection
	 * @return
	 * @author wangyansheng
	 */
	public Collection queryByFkIdUserId(Collection collection);
}