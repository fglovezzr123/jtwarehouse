package com.wing.socialcontact.service.wx.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.ActivityUser;

/**
 * 
 * <p>Title:�������� </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017��4��19�� ����7:19:50
 */
public interface IActivityUserService {

	/**
	 * 后台页面数据查询
	 * @param param
	 * @param activityUser
	 * @return
	 */
	Object selectactivityuser(PageParam param, ActivityUser activityUser);
	
	Object selectactivityuser2(PageParam param, ActivityUser activityUser);

	/**
	 * 导出报表数据查询
	 * @param acid1
	 * @param userName1
	 * @param phone1
	 * @param createTime1
	 * @return
	 */
	List<Map<String, Object>> exporactivityUsers(String acid1,
			String userName1, String phone1, Date createTime1);

	/**
	 * 添加报名信息
	 * @param user
	 * @return
	 */
	boolean addActivityUser(ActivityUser user);

	/**
	 * 根据用户id获取我的报名列表
	 * @param userId
	 * @param size 
	 * @param page 
	 * @return
	 */
	List getmysignupbyid(String userId, Integer page, Integer size);

	/**
	 * 根据活动id更新报名状态  1、审核中  2 用户取消  3 活动取消  4审核通过  5审核拒绝
	 * @param id
	 * @param i
	 * @return
	 */
	String updatesignupstatus(String id, int i);

	/**
	 * 根据报名信息id更新报名状态
	 * @param signupid
	 * @param i
	 * @return
	 */
	String updatestatusbyid(String signupid, int i);

	/**
	 * 根据id获取报名信息
	 * @param id
	 * @return
	 */
	ActivityUser getactivityUserByid(String id);
	 /**
	  * 根据活动id获取所有已报名且 未取消  为拒绝的报名记录
	  */
	List getusersbyactivityid(String id);

	/**
	 * 查询是否报名记录（报名有效    状态为 1：未审核   4 已通过）
	 * @param activityid
	 * @param userid
	 * @return
	 */
	List<ActivityUser> getsignupusers(String activityid, String userid,int orderstatus);

	/**
	 * 更新报名信息
	 * @param signup
	 */
	void updatesignup(ActivityUser signup);

	/**
	 * 添加报名信息
	 * @param signup
	 */
	void insertsignup(ActivityUser signup);

	/**
	 * 根据活动id和报名状态获取报名记录
	 * @param id
	 * @param status 
	 * @return
	 */
	List<ActivityUser> getUncheckedUsersByActivityid(String id, int status);

	/**
	 * 根据订单id查询报名记录
	 * @param out_trade_no
	 * @return
	 */
	ActivityUser getactivityUserByorderid(String out_trade_no);

	/**
	 * 查询用户报名历史（已提交订单未付款）
	 * @param id
	 * @param id2
	 * @param i
	 * @return
	 */
	List<ActivityUser> getsignuphistory(String activityid, String mobile, int i);

	/**
	 * 分页按审核状态查询报名记录
	 * @param id
	 * @param page
	 * @param size
	 * @param status
	 * @return
	 */
	Map selectbyactivityid1(String id, Integer page, Integer size,
			Integer status);

	

}
