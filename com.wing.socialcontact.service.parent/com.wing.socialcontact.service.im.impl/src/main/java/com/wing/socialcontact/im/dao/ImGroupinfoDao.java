package com.wing.socialcontact.im.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.im.bean.ImGroupfav;
import com.wing.socialcontact.service.im.bean.ImGroupinfo;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 15:31:02
 * @version 1.0
 */
@Repository
public interface ImGroupinfoDao extends Mapper<ImGroupinfo> {
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteById(String id);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ImGroupinfo loadById(String id);
	/**
	 * 根据用户id查询好友列表并已首字母排序
	 * @param userId
	 * @return
	 */
	public List findMyGroupInfoListByUserId(Map paramMap);
	
	/**
	 * 查询好友列表
	 * @param userId
	 * @return
	 */
	public List findGroupListByParam(Map paramMap);
	/**
	 * 查询好友列表
	 * @param userId
	 * @return
	 */
	public List findGroupListByParam2(Map paramMap);
	
	/**
	 * 根据用户id查询我的群组数量
	 * @param userId
	 * @return
	 */
	public int findMyGroupInfoCounttByUserId(String userId);
	
	/**
	 * 根据创建人和群名称查询
	 * @param paramMap
	 * @return
	 */
	public ImGroupinfo findByCreaterAndName(Map paramMap);
	
	
	//------------------------------ 人脉圈--------------------------------------
	/**
	 * 获取与用户相同爱好的公开群组
	 * @param userId
	 * @return
	 */
	public List findHobbyGroupByUser(Map paramMap);
	
	/**
	 * 获取与用户相同爱好的商友
	 * @param userId
	 * @return
	 */
	public List findHobbyFriendsByUser(Map paramMap);
	
	/**
	 * 获取同行精英
	 * @param userId
	 * @return
	 */
	public List findPeerEliteByUser(Map paraMap);
	
	/**
	 * 获取同城精英
	 * @param userId
	 * @return
	 */
	public List findCityEliteByUser(Map paramMap);
	
	/**
	 * 获取智同道合
	 * @param userId
	 * @return
	 */
	public List findSameIdeasEliteByUser(String userId);
	
	/**
	 * 获取大咖
	 * @param userId
	 * @return
	 */
	public List findDKListByUser(String userId);
	
	/**
	 * 二度人脉
	 * @param userId
	 * @return
	 */
	public List findTwoDegreeConnectionsByUser(String userId);

	/**
	 * 获取推荐的智同道合好友
	 * @return
	 */
	public List getRecommendZtdhList(String userId);
	
	/**
	 * 根据群组名称获取群组列表
	 * @return
	 */
	public List selGourpsListByName(Map paramMap);
	/**
	 * 查询所有群组
	 * @return
	 */
	public List selectAllGroup(Map paramMap);
	/**
	 * 根据网易群id查询群组信息
	 * @param id
	 * @return
	 */
	public ImGroupinfo findByTid(String id);
	/**
	 * 获取二度人脉中，好友是其好友的个数
	 * 
	 * */
	public List selTwoDegreeConnectionsCount(Map map);
}
