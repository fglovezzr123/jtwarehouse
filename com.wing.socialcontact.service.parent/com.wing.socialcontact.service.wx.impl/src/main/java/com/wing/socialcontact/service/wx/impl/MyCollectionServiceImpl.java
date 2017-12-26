package com.wing.socialcontact.service.wx.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.api.IMyCollectionService;
import com.wing.socialcontact.service.wx.bean.MyCollection;
import com.wing.socialcontact.service.wx.dao.MyCollectionDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class MyCollectionServiceImpl extends BaseServiceImpl<MyCollection> implements
		IMyCollectionService {

	private static final String cache_name = "\"DB:MyCollection:\" + ";
	@Autowired
	private MyCollectionDao myCollectionDao;

	/**
	 * 判断是否收藏过
	 */
	@Override
	public boolean iscollected(String userId, String collectionId, int type) {
		MyCollection dto = new MyCollection();
		dto.setUserId(userId);
		dto.setCollectionId(collectionId);
		dto.setType(type);
		List<MyCollection> list =myCollectionDao.select(dto);
		if(list.size()>0){
		return true;
		}
		return false;
	}

	/**
	 * 添加收藏
	 */
	@Override
	public boolean addcollect(String userId, String collectionId, int type) {
		MyCollection dto = new MyCollection();
		dto.setUserId(userId);
		dto.setCollectionId(collectionId);
		dto.setType(type);
		dto.setCreateTime(new Date());
		int res = myCollectionDao.insert(dto);
		if(res > 0){
			return true;
		}
		return false;
	}

	/**
	 * 收藏列表
	 */
	@Override
	public List<Map> getCollections(String userId, int type, Integer page, Integer size) {
		Map parm = new HashMap<>();
		parm.put("userId", userId);
		parm.put("type", type);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		List<Map> list = new ArrayList<Map>();
		
		if(type == 1){
			/**
			 * 文库
			 */
			list =	myCollectionDao.getLibraryCollections(parm);
		}else if(type == 2){
			/**
			 * 会所
			 */
			list =	myCollectionDao.getClubCollections(parm);
		}else if(type==3){
			/**
			 * 活动
			 */
			list =	myCollectionDao.getActivityCollections(parm);
		}else if(type==4){
			/**
			 * 直播
			 */
			list =	myCollectionDao.getLiveCollections(parm);
		}
		
		return list;
	}

	/**
	 * 取消收藏
	 */
	@Override
	public boolean delCollection(String id,String userId, int type) {
		boolean flag = false;
		MyCollection dto = new MyCollection();
		dto.setUserId(userId);
		dto.setCollectionId(id);
		dto.setType(type);
		List<MyCollection> list =myCollectionDao.select(dto);
		if(list.size()>0){
			for (MyCollection m : list) {
				flag = super.deleteByPrimaryKeyCache(m.getId(), MyCollection.class);
			}
		}
		return flag;
	}
}
