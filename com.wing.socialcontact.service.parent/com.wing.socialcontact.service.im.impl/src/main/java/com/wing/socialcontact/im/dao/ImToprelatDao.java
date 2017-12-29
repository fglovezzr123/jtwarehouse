package com.wing.socialcontact.im.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.im.bean.ImGroupfav;
import com.wing.socialcontact.service.im.bean.ImToprelat;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:37:16
 * @version 1.0
 */
@Repository
public interface ImToprelatDao extends Mapper<ImToprelat> {
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
	public ImToprelat loadById(String id);
	
	/**
	 * 根据条件获取置顶对象
	 * @param map
	 * @return
	 */
	public ImToprelat loadByCondition(Map map);
	
	/**
	 * 根据用户id获取用户置顶列表
	 * @param map
	 * @return
	 */
	public List loadTopUserListByUserId(String userId);
	
	/**
	 * 根据用户id 获取置顶群列表
	 * @param userId
	 * @return
	 */
	public List loadTopGroupListByUserId(String userId);
	
	/**
	 * 根据群id获取用户置顶列表
	 * @param map
	 * @return
	 */
	public List loadTopUserListByGroupId(String groupId);
}
