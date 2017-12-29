package com.wing.socialcontact.service.wx.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.ITbLogService;
import com.wing.socialcontact.service.wx.api.IUserPhotoService;
import com.wing.socialcontact.service.wx.bean.TbLog;
import com.wing.socialcontact.service.wx.bean.TbLogContent;
import com.wing.socialcontact.service.wx.bean.UserPhoto;
import com.wing.socialcontact.service.wx.dao.TbLogContentDao;
import com.wing.socialcontact.service.wx.dao.TbLogDao;
import com.wing.socialcontact.service.wx.dao.UserPhotoDao;
import com.wing.socialcontact.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 表数据修改日志
 * @author wangyansheng
 * @date 2017-11-27
 * @version 1.0
 */
@Service
public class TbLogServiceImpl implements ITbLogService {

	@Resource
	private TbLogDao tbLogDao;

	@Resource
	private TbLogContentDao tbLogContentDao;

	/**
	 * 新增
	 * @param t
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public int insert(TbLog t) {
		t.setId(UUIDGenerator.getUUID());
		int n = tbLogDao.insert(t);
		return n;
	}

	/**
	 * 修改
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public int update(TbLog t) {
		int n = tbLogDao.updateByPrimaryKey(t);
		return n;
	}

	/**
	 * 删除
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public int logicalDelete(TbLog t) {
		return tbLogDao.updateByPrimaryKey(t);
	}

	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public DataGrid selectLogByParam(PageParam param, TbLog t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<TbLog> list = tbLogDao.selectByParam(t);
		PageInfo<TbLog> page = new PageInfo<TbLog>(list);
		return new DataGrid(page);
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public TbLog selectById(String id) {
		//基本信息
		TbLog p = tbLogDao.selectById(id);
		return p;
	}

	/**
	 * 分页查询变更数据
	 * @param param
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public DataGrid selectLogContentByParam(PageParam param, TbLogContent t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<TbLogContent> list = tbLogContentDao.selectByParam(t);
		PageInfo<TbLogContent> page = new PageInfo<TbLogContent>(list);
		return new DataGrid(page);
	}

}