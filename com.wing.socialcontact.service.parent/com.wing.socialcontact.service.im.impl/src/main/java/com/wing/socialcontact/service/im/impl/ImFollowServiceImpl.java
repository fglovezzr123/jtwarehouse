package com.wing.socialcontact.service.im.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.im.dao.ImFollowDao;
import com.wing.socialcontact.service.im.api.IImFollowService;
import com.wing.socialcontact.service.im.bean.ImFollow;
import com.wing.socialcontact.service.wx.api.IUserHonorService;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 15:33:12
 * @version 1.0
 */
@Service
public class ImFollowServiceImpl implements IImFollowService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private ImFollowDao imFollowDao;
	
	@Resource
	private IUserHonorService userHonorService;

	@Override
	public List findMyFollowUsers(String userId, int pageNum, int pageSize) throws RuntimeException {
		if(userId == null || pageNum == 0 || pageSize == 0){
			throw new RuntimeException("查询错误，参数无效，请检查！");
		}
		PageHelper.startPage(pageNum, pageSize);
		List lst = imFollowDao.findMyFollowUsers(userId);
		return lst;
	}

	@Override
	public List findMyFansUsers(String userId, int pageNum, int pageSize) throws RuntimeException {
		if(userId == null || pageNum == 0 || pageSize == 0){
			throw new RuntimeException("查询错误，参数无效，请检查！");
		}
		PageHelper.startPage(pageNum, pageSize);
		List lst = imFollowDao.findMyFansUsers(userId);
		return lst;
	}

	@Override
	public String insertFollowUsers(String userId, String followUser) {
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("followUser", followUser);
		ImFollow imFollow = imFollowDao.selectByUserIdAndFollowId(paramMap);
		if(imFollow != null){
			imFollow.setStatus(2);
			imFollowDao.updateByPrimaryKeySelective(imFollow);
		}else{
			imFollow = new ImFollow();
			imFollow.setId(UUID.randomUUID().toString());
			imFollow.setCreateTime(new Date());
			imFollow.setStatus(2);
			imFollow.setFollowUser(followUser);
			imFollow.setUserId(userId);
			imFollowDao.insert(imFollow);
		}
		//获取被关注数量 被关注数超过500   人气勋章
		int fans = imFollowDao.findMyFansUsersCount(followUser);
		if(fans>=500){
			userHonorService.addUserAndHonor(followUser, "honor_001");
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public String deleteFollowUsers(String userId, String followUser) throws RuntimeException{
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("followUser", followUser);
		ImFollow imFollow = imFollowDao.selectByUserIdAndFollowId(paramMap);
		if(imFollow != null){
			imFollow.setStatus(1);
			imFollow.setUpdateTime(new Date());
			imFollowDao.updateByPrimaryKeySelective(imFollow);
		}else{
			throw new RuntimeException("操作失败，未找关注用户信息！");
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public int findMyFollowUsersCount(String userId) throws RuntimeException {
		if(userId == null ){
			throw new RuntimeException("参数无效，请检查！");
		}
		return imFollowDao.findMyFollowUsersCount(userId);
	}

	@Override
	public int findMyFansUsersCount(String userId) throws RuntimeException {
		if(userId == null ){
			throw new RuntimeException("参数无效，请检查！");
		}
		return imFollowDao.findMyFansUsersCount(userId);
	}

	@Override
	public boolean isFollowUser(String userId, String followUser) {
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("followUser", followUser);
		ImFollow imFollow = imFollowDao.selectByUserIdAndFollowId(paramMap);
		if(imFollow != null){
			if(imFollow.getStatus() == 2){
				return true;
			}
		}
		return false;
	}

}