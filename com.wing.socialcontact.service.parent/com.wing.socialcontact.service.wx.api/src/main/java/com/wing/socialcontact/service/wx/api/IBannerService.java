package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Banner;

/**
 * 
 * @author zhangfan
 * @date 2017-03-31 17:43:02
 * @version 1.0
 */
public interface IBannerService{

	/**
	 * 条件查询
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllBanner(PageParam param,Banner banner);
	/**
	 * 添加
	 * @param newsClass
	 * @return
	 */
	public String addBanner(Banner banner);
	/**
	 * 更新
	 * @param newsClass
	 * @return
	 */
	public String updateBanner(Banner banner);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteBanner(String[] ids);

	public Banner selectByPrimaryKey(String key);
	
	public Banner selectById(String id);
	/**
	 * 前台banner图展示
	 * @param columnType
	 * @return
	 */
	public List<Banner> selectByColumnType(String columnType);
	/**
	 * 根据用户查询banner
	 * @param userId
	 * @return
	 */
	public List<Banner> selectBannerByUserId(String userId,String columnType);
	public List selBannerListFords(String columnType);

}