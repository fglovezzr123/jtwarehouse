package com.wing.socialcontact.service.wx.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.FundWillDao;
import com.wing.socialcontact.service.wx.api.IFundWillService;
import com.wing.socialcontact.service.wx.bean.FundWill;

/**
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Service
public class FundWillServiceImpl implements IFundWillService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private FundWillDao fundWillDao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int insertFundWill(FundWill t) {
		return fundWillDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int updateFundWill(FundWill t) {
		return fundWillDao.updateByPrimaryKey(t);
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int deleteFundWill(FundWill t) {
		return fundWillDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public List<FundWill> queryFundWill(FundWill t) {
		return fundWillDao.select(t);
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public FundWill getFundWill(String id) {
		return fundWillDao.selectByPrimaryKey(id);
	}
}