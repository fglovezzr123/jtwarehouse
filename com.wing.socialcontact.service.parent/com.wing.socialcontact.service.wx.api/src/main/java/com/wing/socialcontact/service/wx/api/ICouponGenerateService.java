package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.CouponGenerate;

/**
 * 
 * @author zhangfan
 * @date 2017-07-20 11:00:18
 * @version 1.0
 */
public interface ICouponGenerateService{

	/**
	 * 查询后台列表
	 * @return
	 */
	public DataGrid selectAllCG(PageParam param,CouponGenerate cg,String startTime,String endTime,String couponName);
	
	/**
	 * 添加
	 * @param cg
	 * @return
	 */
	public String addCG(CouponGenerate cg);
	/**
	 * 更新
	 * @param cg
	 * @return
	 */
	public String updateCG(CouponGenerate cg);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteCGs(String[] ids);
	
	/**
	 * 根据id查询缓存
	 * @param key
	 * @return
	 */
	public CouponGenerate selectByPrimaryKey(String key);
	
	public CouponGenerate selectById(String id);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public String deleteCG(String id);

	DataGrid selectDuplicate(PageParam param, CouponGenerate cg);

}