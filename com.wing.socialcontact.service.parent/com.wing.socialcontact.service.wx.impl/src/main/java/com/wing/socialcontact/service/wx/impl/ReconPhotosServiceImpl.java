package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.ReconPhotosDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.service.wx.api.IReconPhotosService;
import com.wing.socialcontact.service.wx.bean.ReconPhotos;
import com.wing.socialcontact.service.wx.bean.TjyUser;

/**
 * 
 * @author zengmin
 * @date 2017-04-07 01:41:58
 * @version 1.0
 */
@Service
public class ReconPhotosServiceImpl extends BaseServiceImpl<ReconPhotos> implements IReconPhotosService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:ReconPhotos:\" + ";

	@Resource
	private ReconPhotosDao reconPhotosDao;

	@Override
	public boolean addReconPhotos(ReconPhotos reconPhotos) {
		int res = reconPhotosDao.insert(reconPhotos);
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ReconPhotos selectById(String id) {
		return reconPhotosDao.selectByPrimaryKey(id);
	}

	@Override
	public ReconPhotos selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, ReconPhotos.class);
	}

	@Override
	public List<Map<String, Object>> selectByUserId(String userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return reconPhotosDao.query(param);
	}
	
	@Override
	public boolean deleteByPrimaryKey(String key) {
		return super.deleteByPrimaryKeyCache(key, ReconPhotos.class);
	}
//===========================APP=================
	@Override
	public void deleteByUserId(String id) {
		ReconPhotos  record = new ReconPhotos();
		record.setUserId(id);
		reconPhotosDao.delete(record);
	}
}