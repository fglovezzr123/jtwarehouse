package com.wing.socialcontact.service.wx.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IPersonalCustomizationCustomerService;
import com.wing.socialcontact.service.wx.bean.PersonalCustomizationCustomer;
import com.wing.socialcontact.service.wx.dao.PersonalCustomizationCustomerDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 意向客户
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
@Service
public class PersonalCustomizationCustomerServiceImpl implements IPersonalCustomizationCustomerService {

	@Resource
	private PersonalCustomizationCustomerDao personalCustomizationCustomerDao;

	/**
	 * 新增
	 * @param t
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public int insert(PersonalCustomizationCustomer t) {
		int n = personalCustomizationCustomerDao.insert(t);
		return n;
	}

	/**
	 * 修改
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public int update(PersonalCustomizationCustomer t) {
		int n = personalCustomizationCustomerDao.updateByPrimaryKey(t);
		return n;
	}

	/**
	 * 删除
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public int delete(PersonalCustomizationCustomer t) {
		return personalCustomizationCustomerDao.delete(t);
	}

	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public DataGrid selectByParam(PageParam param, PersonalCustomizationCustomer t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<PersonalCustomizationCustomer> list = personalCustomizationCustomerDao.selectByParam(t);
		PageInfo<PersonalCustomizationCustomer> page = new PageInfo<PersonalCustomizationCustomer>(list);
		return new DataGrid(page);
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public PersonalCustomizationCustomer selectById(Long id) {
		//基本信息
		PersonalCustomizationCustomer p = personalCustomizationCustomerDao.selectByPrimaryKey(id);
		return p;
	}

}