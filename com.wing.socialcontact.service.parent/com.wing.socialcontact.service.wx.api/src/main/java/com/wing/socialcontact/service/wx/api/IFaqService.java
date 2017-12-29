package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.service.wx.bean.Faq;

/**
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
public interface IFaqService{
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int insertFaq(Faq t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int updateFaq(Faq t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int deleteFaq(Faq t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public List<Faq> queryFaq(Faq t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public Faq getFaq(String id);
}