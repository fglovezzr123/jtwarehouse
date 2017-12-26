package com.wing.socialcontact.service.wx.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IProjectSupermarketCustomerService;
import com.wing.socialcontact.service.wx.bean.ProjectSupermarketCustomer;
import com.wing.socialcontact.service.wx.dao.ProjectSupermarketCustomerDao;
import com.wing.socialcontact.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目超市意向客户
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
@Service
public class ProjectSupermarketCustomerServiceImpl implements IProjectSupermarketCustomerService {

	@Resource
	private ProjectSupermarketCustomerDao projectSupermarketCustomerDao;

	/**
	 * 新增
	 * @param t
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public int insert(ProjectSupermarketCustomer t) {
		t.setId(UUIDGenerator.getUUID());
		int n = projectSupermarketCustomerDao.insert(t);
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
	public int update(ProjectSupermarketCustomer t) {
		int n = projectSupermarketCustomerDao.updateByPrimaryKey(t);
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
	public int delete(ProjectSupermarketCustomer t) {
		return projectSupermarketCustomerDao.delete(t);
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
	public DataGrid selectByParam(PageParam param, ProjectSupermarketCustomer t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<ProjectSupermarketCustomer> list = projectSupermarketCustomerDao.selectByParam(t);
		PageInfo<ProjectSupermarketCustomer> page = new PageInfo<ProjectSupermarketCustomer>(list);
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
	public ProjectSupermarketCustomer selectById(String id) {
		//基本信息
		ProjectSupermarketCustomer p = projectSupermarketCustomerDao.selectByPrimaryKey(id);
		return p;
	}

}