package com.wing.socialcontact.service.wx.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.FaqDao;
import com.wing.socialcontact.service.wx.api.IFaqService;
import com.wing.socialcontact.service.wx.bean.Faq;

/**
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Service
public class FaqServiceImpl implements IFaqService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private FaqDao faqDao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int insertFaq(Faq t) {
		return faqDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int updateFaq(Faq t) {
		return faqDao.updateByPrimaryKey(t);
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int deleteFaq(Faq t) {
		return faqDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public List<Faq> queryFaq(Faq t) {
		return faqDao.select(t);
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public Faq getFaq(String id) {
		return faqDao.selectByPrimaryKey(id);
	}
}