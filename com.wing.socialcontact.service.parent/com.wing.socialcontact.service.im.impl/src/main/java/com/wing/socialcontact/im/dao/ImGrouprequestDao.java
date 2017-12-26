package com.wing.socialcontact.im.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.im.bean.ImGroupfav;
import com.wing.socialcontact.service.im.bean.ImGrouprequest;
import com.wing.socialcontact.service.wx.bean.DynamicPayLog;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 17:08:39
 * @version 1.0
 */
@Repository
public interface ImGrouprequestDao extends Mapper<ImGrouprequest> {
	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(String id);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public ImGrouprequest loadById(String id);

	/**
	 * 根据用户id获取该用户创建群的加群申请信息
	 * 
	 * @param userId
	 * @return
	 */
	public List findGroupRequestByUserid(String userId);

	/**
	 * 查询出所有
	 * 
	 * @return
	 */
	public List selectAllImGrouprequest(ImGrouprequest imGrouprequest);

	/**
	 * 获取我新群通知数量
	 * 
	 * @param userId
	 * @return
	 */
	public int findNewGroupRequestCountByUserid(String userId);

}
