package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.service.wx.bean.Fund;

/**
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
public interface IFundService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int insertFund(Fund t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int updateFund(Fund t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int deleteFund(Fund t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public List<Fund> queryFund(Fund t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public Fund getFund(String id);
}