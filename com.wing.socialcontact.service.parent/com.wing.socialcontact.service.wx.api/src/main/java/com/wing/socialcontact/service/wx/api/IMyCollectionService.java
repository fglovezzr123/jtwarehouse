package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhangzheng
 *
 */
public interface IMyCollectionService {

	/**
	 * 判断是否收藏过
	 */
	boolean iscollected(String userId, String collectionId, int type);

	/**
	 * 添加到收藏
	 */
	boolean addcollect(String userId, String collectionId, int type);

	/**
	 * 根据type查询我的收藏列表
	 * @param userId
	 * @param type
	 * @param size 
	 * @param page 
	 * @return
	 */
	List<Map> getCollections(String userId, int type, Integer page, Integer size);

	/**
	 * 取消收藏
	 * @param id  收藏id
	 * @param userId 
	 * @return
	 */
	boolean delCollection(String id, String userId, int type);

}
