package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.WxUser;

/**
 * 
 * @author zengmin
 * @date 2017-04-03 23:44:43
 * @version 1.0
 */
@Repository
public interface WxUserDao extends Mapper<WxUser> {
	List<Map<String, Object>> selectAllUserMap();

	List selectByParam(Map parm);
	
	WxUser selectByWxUserId(String wxUserId);
	
	WxUser selectByUserId(String userId);
	
	Map<String, Object>  queryUsersByid(String userId);//获取user tjy_user
	
	WxUser selectByUserName(String userName);
	
	WxUser selectByMobile(String mobile);
	
	WxUser selectByNickName(String nickName);

	WxUser selectByOpenId(String reg_openId);

	List queryByHzbUser(Map<String, Object> parm);

	/**
	 * 降级用
	 * @Title: queryTaskHzbWxUser 
	 * @Description: TODO
	 * @return
	 * @return: List<Map<String,Object>>
	 * @author: zengmin   
	 * @date: 2017年8月4日 下午4:53:28
	 */
	List<Map<String,Object>> queryTaskHzbWxUser();

	/**
	 * 升级用
	 * @Title: queryHzbNdcByUserId 
	 * @Description: TODO
	 * @param userId
	 * @return
	 * @return: long
	 * @author: zengmin   
	 * @date: 2017年8月4日 下午4:53:17
	 */
	long queryHzbNdcByUserId(String userId);
}
