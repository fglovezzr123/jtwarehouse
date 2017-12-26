package com.wing.socialcontact.im.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.im.bean.ImFriend;
import com.wing.socialcontact.service.im.bean.ImGroupfav;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:37:16
 * @version 1.0
 */
@Repository
public interface ImGroupfavDao extends Mapper<ImGroupfav> {
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
	public ImGroupfav loadById(String id);
	
	
	/**
	 * 获取群的标签信息
	 * @param groupId
	 * @return
	 */
	public List findGroupFavsByGroupId(String groupId);
	
	/**
	 * 根据组id删除组标签信息
	 * @param groupId
	 */
	public int delFavsByGroupId(String groupId);
	
	/**
	 * 根据组id和标签id获取
	 * @param paramMap
	 * @return
	 */
	public ImGroupfav loadByGroupIdAndFavId(Map paramMap);
	
	/**
	 * 检查爱好是否在数据字典表中
	 * @param favId
	 * @return
	 */
	public Map findListValueByFavId(String favId);
	
	
}
