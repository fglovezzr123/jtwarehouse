package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.service.wx.bean.FundWill;

/**
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
public interface IFundWillService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int insertFundWill(FundWill t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int updateFundWill(FundWill t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int deleteFundWill(FundWill t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public List<FundWill> queryFundWill(FundWill t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public FundWill getFundWill(String id);
}