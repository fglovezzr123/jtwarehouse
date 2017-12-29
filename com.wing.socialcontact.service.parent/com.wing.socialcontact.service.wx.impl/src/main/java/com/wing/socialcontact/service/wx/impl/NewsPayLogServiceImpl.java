package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.NewsPayLogDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.INewsPayLogService;
import com.wing.socialcontact.service.wx.bean.NewsPayLog;

/**
 * 
 * @author zhangfan
 * @date 2017-06-28 09:31:35
 * @version 1.0
 */
@Service
public class NewsPayLogServiceImpl extends BaseServiceImpl<NewsPayLog> implements INewsPayLogService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name =  "\"DB:NewsPayLog:\" + ";
	
	@Resource
	private NewsPayLogDao newsPayLogDao;

	@Override
	public List selectPayLog(String userId, String fkId) {
		Map parm = new HashMap();
		parm.put("userId", userId);
		parm.put("fkId", fkId);
		List lst = newsPayLogDao.selectByParam(parm);
		return lst;
	}
	@Override
	public String addNewsPayLog(NewsPayLog npl) {
		int res = newsPayLogDao.insert(npl);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateNewsPayLog(NewsPayLog npl) {
		if(super.updateByPrimaryKeyCache(npl,npl.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public NewsPayLog selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, NewsPayLog.class);
	}

	@Override
	public NewsPayLog selectById(String id) {
		return newsPayLogDao.selectByPrimaryKey(id);
	}
}