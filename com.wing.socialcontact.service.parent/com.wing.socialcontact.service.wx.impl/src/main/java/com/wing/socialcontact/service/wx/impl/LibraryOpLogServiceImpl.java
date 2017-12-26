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
import com.wing.socialcontact.service.wx.api.ILibraryOpLogService;
import com.wing.socialcontact.service.wx.bean.LibraryOpLog;
import com.wing.socialcontact.service.wx.dao.LibraryOpLogDao;

/**
* @author zhangfan
 * @date 2017-10-12 
 * @version 1.0
 */
@Service
public class LibraryOpLogServiceImpl implements ILibraryOpLogService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private LibraryOpLogDao libraryOpLogDao;

	@Override
	public List selectAllOpLog(LibraryOpLog opLog) {
		return libraryOpLogDao.selectAllOpLogMap(opLog);
	}

	@Override
	public DataGrid selectOpLog(PageParam param, LibraryOpLog opLog) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("orderStr", orderStr);
		List lst = libraryOpLogDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	@Override
	public String addOpLog(LibraryOpLog opLog) {
		int res = libraryOpLogDao.insert(opLog);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateOpLog(LibraryOpLog opLog) {
		int res = libraryOpLogDao.updateByPrimaryKeySelective(opLog);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		} 
	}

	@Override
	public LibraryOpLog selectById(String id) {
		return libraryOpLogDao.selectByPrimaryKey(id);
	}
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteOpLogById(String id){
		return libraryOpLogDao.deleteByPrimaryKey(id);
	}

	@Override
	public int getCountByFkIdAndType(String fkId, String opType, String type) {
		int count = 0;
		Map parm = new HashMap();
		parm.put("fkId", fkId);
		parm.put("opType", opType);
		parm.put("type", type);
		count = libraryOpLogDao.getCountByFkIdAndType(parm);
		return count;
	}
}