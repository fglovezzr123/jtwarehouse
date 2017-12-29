package com.wing.socialcontact.service.im.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.im.dao.ImFriendrequestDao;
import com.wing.socialcontact.service.im.api.IImFriendService;
import com.wing.socialcontact.service.im.api.IImFriendrequestService;
import com.wing.socialcontact.service.im.bean.ImFriendrequest;

import tk.mybatis.mapper.util.StringUtil;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-26 19:21:38
 * @version 1.0
 */
@Service
public class ImFriendrequestServiceImpl implements IImFriendrequestService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private ImFriendrequestDao imFriendrequestDao;
	
	@Resource
	private IImFriendService iImFriendServiceImpl;
	
	/**
	 * 根据用户id查询好友列表并已首字母排序
	 * @param userId
	 * @return
	 */
	public List findMyNewFriendListByUserId(String userId,int pageNum, int pageSize) throws RuntimeException{
		if(userId == null || pageNum == 0 || pageSize == 0){
			throw new RuntimeException("查询错误，参数无效，请检查！");
		}
		PageHelper.startPage(pageNum, pageSize);
		List lst = imFriendrequestDao.findMyNewFriendListByUserId(userId);
		return lst;
	}
	
	/**
	 * 获取我的新好友数量
	 * @param userId
	 * @return
	 */
	public int findMyNewfriendCountByUserId(String userId) throws RuntimeException{
		if(userId == null){
			throw new RuntimeException("参数无效，请检查！");
		}
		int count = imFriendrequestDao.findMyNewfriendCountByUserId(userId);
		return count;
	}
	
	/**
	 * 
	 * @param userId 用户id
	 * @param friendUserId 好友id
	 * @return
	 * @throws RuntimeException
	 */
	public String saveFriendRequest(String userId,String friendUserId,String askmessage) throws RuntimeException{
		if(StringUtil.isEmpty(userId) || StringUtil.isEmpty(friendUserId)){
			throw new RuntimeException("参数无效，请检查！");
		}
		Map paramMap = new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("friendUser", friendUserId);
		ImFriendrequest requestOld = imFriendrequestDao.findRequestByParam(paramMap);
		if(requestOld != null){
			requestOld.setAskmessage(askmessage);
			requestOld.setStatus(1);
			requestOld.setCreateTime(new Date());
			requestOld.setUpdateTime(new Date());
			imFriendrequestDao.updateByPrimaryKeySelective(requestOld);
		}else{
			ImFriendrequest request = new ImFriendrequest();
			request.setId(UUID.randomUUID().toString());
			request.setUserId(userId);
			request.setFriendUser(friendUserId);
			request.setCreateTime(new Date());
			request.setStatus(1);
			request.setAskmessage(askmessage);
			imFriendrequestDao.insert(request);
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}
	
	/**
	 * 
	 * @param friendRequestId 请求id
	 * @param status 状态：1待确认2已同意3已拒绝
	 * @return
	 */
	public String updateFriendRequestStatus(String friendRequestId,int status,String friendMemo)throws RuntimeException{
		if(friendRequestId == null || (status != 3 && status != 2)){
			throw new RuntimeException("查询错误，参数无效，请检查！");
		}
		ImFriendrequest request = imFriendrequestDao.loadById(friendRequestId);
		if(request != null){
			request.setStatus(status);
			request.setUpdateTime(new Date());
			imFriendrequestDao.updateByPrimaryKeySelective(request);
			
			if(status == 2){
				iImFriendServiceImpl.addEachOtherFriendByRequestId(friendRequestId,friendMemo);
			}
		}else{
			throw new RuntimeException("操作失败，未找到该用户请求！");
		}
		
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public Map findUserByUserId(String userId) throws RuntimeException{
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(userId)){
			throw new RuntimeException("参数无效，请检查！");
		}
		Map tjyUser = imFriendrequestDao.findUserMapByUserId(userId);
		List favList = imFriendrequestDao.findUserFavListByUserId(userId);
		tjyUser.put("favList", favList);
		return tjyUser;
	}

	@Override
	public List findNoFriendUserListByUserId(String userId,int pageNum, int pageSize,String trueName,String cityId,String industryId)
			throws RuntimeException {
		if(userId == null || pageNum == 0 || pageSize == 0){
			throw new RuntimeException("查询错误，参数无效，请检查！");
		}
		PageHelper.startPage(pageNum, pageSize);
		Map paramMap = new HashMap();
		paramMap.put("trueName", trueName);
		paramMap.put("userId", userId);
		paramMap.put("cityId", cityId);
		paramMap.put("industryId", industryId);
		
		List lst = imFriendrequestDao.findNofriendUsersByUserId(paramMap);
		return lst;
	}

	@Override
	public ImFriendrequest loadById(String friendRequestId) {
		ImFriendrequest request = imFriendrequestDao.loadById(friendRequestId);
		return request;
	}

	/**
	 *累积当天发送的好友请求
	 * @param userId
	 * @return
	 */
	@Override
	public Integer selectOneDaySendSum(String userId) {
		int t = imFriendrequestDao.selectOneDaySendSum(userId);
		return t;
	}
	
	/**
	 *累积当天接收的好友请求
	 * @param friendUser
	 * @return
	 */
	@Override
	public Integer selectOneDayGetSum(String friendUser) {
		int t = imFriendrequestDao.selectOneDayGetSum(friendUser);
		return t;
	}

	@Override
	public ImFriendrequest findRequestByParam(Map paramMap)
	{
		return imFriendrequestDao.findRequestByParam(paramMap);
	}
}