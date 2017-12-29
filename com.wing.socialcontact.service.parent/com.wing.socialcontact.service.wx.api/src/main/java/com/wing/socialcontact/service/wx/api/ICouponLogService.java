package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.CouponLog;

/**
 * 
 * @author zhangfan
 * @date 2017-07-20 11:03:44
 * @version 1.0
 */
public interface ICouponLogService{

	/**
	 * 查询后台列表
	 * @return
	 */
	public DataGrid selectAllCL(PageParam param,CouponLog cl,String keyword,String couponName);
	
	/**
	 * 添加
	 * @param cg
	 * @return
	 */
	public String addCL(CouponLog cl);
	/**
	 * 更新
	 * @param cg
	 * @return
	 */
	public String updateCL(CouponLog cl);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteCLs(String[] ids);
	
	/**
	 * 根据id查询缓存
	 * @param key
	 * @return
	 */
	public CouponLog selectByPrimaryKey(String key);
	
	public CouponLog selectById(String id);
	/**
	 * 查询该用户可用优惠券
	 * @param curruserId
	 * @param useStatus
	 * @param useRange
	 * @param orderMinBuy
	 * @return
	 */
	public List selectCanUseCoupon(String curruserId,Integer useStatus,String useRange,String orderMinBuy,Integer couponCoinType);
	
	public List selMyCouponList(String curruserId,Integer type,Integer page, Integer size);

	/**
	 * 查询用户单张优惠券是否可用
	 * @param couponlogid  主键id
	 * @param userId  用户id
	 * @param price  支付金额
	 * @param useRange  使用位置    1全平台，2会议，3活动，4互助商城
	 * @param currency  币种   1:J币     2:RMB 
	 */
	public boolean checkCanUse(String couponid, String id, Double price, Integer useRange, Integer currency);

	/**
	 * 获取领取的优惠券列表
	 * @param userId
	 * @param type 优惠券类型 4：会议券
	 * @param page
	 * @param size
	 * @return
	 */
	List<Map> selectMyCoupons(String userId, Integer type, Integer page, Integer size);

}