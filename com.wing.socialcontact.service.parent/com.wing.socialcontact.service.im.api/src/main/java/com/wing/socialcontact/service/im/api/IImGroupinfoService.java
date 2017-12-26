package com.wing.socialcontact.service.im.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.im.bean.ImGroupinfo;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 15:27:44
 * @version 1.0
 */
public interface IImGroupinfoService{

	/**
	 * 获取用户创建的群
	 * @param userId
	 * @return
	 */
	public List findMyGroupInfoListByUserId(String creator,int pageNum, int pageSize,String groupName) throws RuntimeException;
	
	/**
	 * 获取用户群组列表
	 * @param param
	 * @param nickname
	 * @return
	 */
	public DataGrid queryGroupListByparam(PageParam param,Map paramMap);
	/**
	 * 获取用户群组列表
	 * @param param
	 * @param nickname
	 * @return
	 */
	public DataGrid queryGroupListByparam2(PageParam param,Map paramMap);
	
	/**
	 * 获取用户群聊数量
	 * @param userId
	 * @return
	 */
	public int findMyGroupInfCountByUserId(String creator) throws RuntimeException;
	
	/**
	 * 创建用户群
	 * @param imGroupinfo
	 * @param userIds
	 * @return
	 */
	public String insertGroupInfo(ImGroupinfo imGroupinfo,String[] userIds,String isTop,String disturb,String[] favIds);
	
	/**
	 * 修改群信息
	 * @param imGroupinfo
	 * @param userIds
	 * @return
	 */
	public String updateGroupInfo(ImGroupinfo imGroupinfo,String userId);
	
	/**
	 * 根据id获取组信息
	 * @param id
	 * @return
	 */
	public ImGroupinfo findById(String id);
	
	/**
	 * 设置群是私有还是开放
	 * @param groupId
	 * @param status
	 * @return
	 */
	public String setGroupIsPrivate(String groupId,String status);
	
	/**
	 * 删除群
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public String delGroupInfo(String groupId,String userId);
	
	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public boolean delGroupInfo(String[] ids);
	
	//------------------------------ 人脉圈--------------------------------------
	
	/**
	 * 获取同好圈子列表
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selIdenticalHobbyWorld(String userId,String hobby,String groupName,int pageNum, int pageSize);
	
	/**
	 * 获取同趣商友列表
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selIdenticalHobbyFriends(String userId,String hobby,String groupName,int pageNum, int pageSize);
	
	/**
	 * 同行精英
	 * @param userId
	 * @param industry
	 * @param nikename
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selPeerElite(String userId,String industry,String nikename,int pageNum, int pageSize);
	public List selPeerElite2(String userId,String industry,String nickname);
	/**
	 * 同城精英
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selCityElite(String userId,int pageNum, int pageSize);
	public List selCityElite2(String userId);
	
	/**
	 * 智同道合
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selSameIdeasElite(String userId,int pageNum, int pageSize);
	
	/**
	 * 获取大咖列表
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selDKList(String userId,int pageNum, int pageSize);
	
	/**
	 * 获取二度人脉
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selTwoDegreeConnections(String userId,int pageNum, int pageSize);
	
	
	/**
	 * 根据群名称搜索群
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selGourpsListByName(String userId,String groupName,int pageNum, int pageSize);
	/**
	 * 初始化群组
	 */
	public void initGourps(Integer page,Integer size);
	/**
	 * 更新
	 * @param imGroupinfo
	 * @return
	 */
	public String updateGI(ImGroupinfo imGroupinfo);
	/**
	 * 根据网易群id查询群信息
	 * @param Tid
	 * @return
	 */
	public ImGroupinfo findByTid(String groupId);
	/**
	 * 获取二度人脉中，我的好友为其好友的个数
	 * 
	 * */
	public List selTwoDegreeConnectionsCount(String user_id,String friend_user);
}