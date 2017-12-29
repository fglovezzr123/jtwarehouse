package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.IndexAd;

/**
 * 
 * @author zengmin
 * @date 2017-07-07 15:45:48
 * @version 1.0
 */
public interface IIndexAdService {

	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllIndexAd(PageParam param, IndexAd indexAd);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addIndexAd(IndexAd indexAd);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updateIndexAd(IndexAd indexAd);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteIndexAd(String id);

	public IndexAd selectByPrimaryKey(String key);

	public IndexAd selectById(String id);

	public IndexAd selectIndexAdByUserId(String userId);

}