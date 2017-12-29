package com.wing.socialcontact.service.im.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.wing.socialcontact.im.dao.ImToprelatDao;
import com.wing.socialcontact.service.im.api.IImToprelatService;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:37:16
 * @version 1.0
 */
@Service
public class ImToprelatServiceImpl implements IImToprelatService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private ImToprelatDao imToprelatDao;

	@Override
	public List findTopFriendListByUserId(String userId, int pageNum,
			int pageSize) throws RuntimeException {
		if(userId == null){
			throw new RuntimeException("参数无效，请检查！");
		}
		if(pageNum != 0 && pageSize != 0){
			PageHelper.startPage(pageNum, pageSize);
		}
		
		return imToprelatDao.loadTopUserListByUserId(userId);
	}
	
	@Override
	public List findTopGroupListByUserId(String userId, int pageNum,
			int pageSize) throws RuntimeException {
		if(userId == null){
			throw new RuntimeException("参数无效，请检查！");
		}
		if(pageNum != 0 && pageSize != 0){
			PageHelper.startPage(pageNum, pageSize);
		}
		
		return imToprelatDao.loadTopGroupListByUserId(userId);
	}
	
	

	@Override
	public List findTopFriendListByGroupId(String groupId, int pageNum,
			int pageSize) throws RuntimeException {
		if(groupId == null){
			throw new RuntimeException("参数无效，请检查！");
		}
		if(pageNum != 0 && pageSize != 0){
			PageHelper.startPage(pageNum, pageSize);
		}
		return imToprelatDao.loadTopUserListByGroupId(groupId);
	}

}