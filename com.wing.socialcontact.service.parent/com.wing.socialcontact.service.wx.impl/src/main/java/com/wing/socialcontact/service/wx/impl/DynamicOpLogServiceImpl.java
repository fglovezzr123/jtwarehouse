package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.dao.DynamicOpLogDao;
import com.wing.socialcontact.service.wx.api.IDynamicOpLogService;
import com.wing.socialcontact.service.wx.bean.CommentThumbup;
import com.wing.socialcontact.service.wx.bean.DynamicOpLog;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-19 14:19:54
 * @version 1.0
 */
@Service
public class DynamicOpLogServiceImpl implements IDynamicOpLogService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private DynamicOpLogDao dynamicOpLogDao;

	@Override
	public List selectAllDynamicOpLog(DynamicOpLog dynamicOpLog) {
		// TODO Auto-generated method stub
		return dynamicOpLogDao.selectAllDynamicOpLogMap(dynamicOpLog);
	}

	@Override
	public DataGrid selectDynamicOpLog(PageParam param,
			DynamicOpLog dynamicOpLog) {
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("orderStr", orderStr);
		List lst = dynamicOpLogDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	@Override
	public String addDynamicOpLog(DynamicOpLog dynamicOpLog) {
		int res = dynamicOpLogDao.insert(dynamicOpLog);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateDynamicOpLog(DynamicOpLog dynamicOpLog) {
		int res = dynamicOpLogDao.updateByPrimaryKeySelective(dynamicOpLog);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		} 
	}

	@Override
	public DynamicOpLog selectById(String id) {
		return dynamicOpLogDao.selectByPrimaryKey(id);
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteOpLogById(String id){
		return dynamicOpLogDao.deleteByPrimaryKey(id);
	}

}