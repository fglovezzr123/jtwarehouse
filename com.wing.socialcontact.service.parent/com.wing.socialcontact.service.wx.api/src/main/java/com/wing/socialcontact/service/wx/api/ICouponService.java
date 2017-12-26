package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Coupon;

/**
 * 
 * @author zhangfan
 * @date 2017-07-20 10:24:15
 * @version 1.0
 */
public interface ICouponService{

	/**
	 * 查询后台列表
	 * @return
	 */
	public DataGrid selectAllCoupon(PageParam param,Coupon coupon,String startTime,String endTime);

	/**
	 * 添加
	 * @param coupon
	 * @return
	 */
	public String addCoupon(Coupon coupon);
	/**
	 * 更新
	 * @return
	 */
	public String updateCoupon(Coupon coupon);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteCoupons(String[] ids);

	/**
	 * 根据id查询缓存
	 * @param key
	 * @return
	 */
	public Coupon selectByPrimaryKey(String key);
	
	public Coupon selectById(String id);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectCouponById(String id);
	/**
	 * 查询所有的店铺
	 * @return
	 */
	public DataGrid selectAllStore(PageParam param,String storeName);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public String deleteCoupon(String id);
}