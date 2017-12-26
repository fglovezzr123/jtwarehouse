package com.wing.socialcontact.service.wx.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IPersonalCustomizationService;
import com.wing.socialcontact.service.wx.api.IProjectService;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.service.wx.dao.*;
import com.wing.socialcontact.util.UUIDGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个性定制
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
@Service
public class PersonalCustomizationServiceImpl implements IPersonalCustomizationService{

	@Resource
	private PersonalCustomizationDao personalCustomizationDao;

	/**
	 * 新增
	 * @param t
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public int insert(PersonalCustomization t) {
		t.setId(UUIDGenerator.getUUID());
		int n = personalCustomizationDao.insert(t);
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
	public int update(PersonalCustomization t) {
		int n = personalCustomizationDao.updateByPrimaryKey(t);
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
	public int delete(PersonalCustomization t) {
		return personalCustomizationDao.delete(t);
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
	public DataGrid selectByParam(PageParam param, PersonalCustomization t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<PersonalCustomization> list = personalCustomizationDao.selectByParam(t);
		PageInfo<PersonalCustomization> page = new PageInfo<PersonalCustomization>(list);
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
	public PersonalCustomization selectById(String id) {
		//基本信息
		PersonalCustomization p = personalCustomizationDao.selectByPrimaryKey(id);
		return p;
	}

}