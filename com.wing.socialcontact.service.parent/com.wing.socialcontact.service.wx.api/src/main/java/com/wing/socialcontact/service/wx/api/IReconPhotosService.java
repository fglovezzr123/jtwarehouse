package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.service.wx.bean.ReconPhotos;

/**
 * 
 * @author zengmin
 * @date 2017-04-07 01:41:58
 * @version 1.0
 */
public interface IReconPhotosService{

	public ReconPhotos selectByPrimaryKey(String key);

	public ReconPhotos selectById(String id);

	public boolean addReconPhotos(ReconPhotos reconPhotos);

	public List<Map<String, Object>> selectByUserId(String id);
	
	public boolean deleteByPrimaryKey(String key);

	public void deleteByUserId(String id);

}