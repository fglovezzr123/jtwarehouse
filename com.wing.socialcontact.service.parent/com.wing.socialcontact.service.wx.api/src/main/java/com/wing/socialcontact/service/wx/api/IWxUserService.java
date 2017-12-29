package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;

/**
 * 
 * @author zengmin
 * @date 2017-04-03 23:44:43
 * @version 1.0
 */
public interface IWxUserService {

	/**
	 * 获取互助宝用户列表
	 * 
	 * @param param
	 * @param wxUser
	 * @param amounthigh 
	 * @param amountlow 
	 * @return
	 */
	public DataGrid selectAllHzbWxUser(PageParam param, WxUser wxUser, Integer amountlow, Integer amounthigh);

	public WxUser selectByPrimaryKey(String key);

	public WxUser selectById(String id);

	public WxUser selectByWxUserId(String wxUserId);

	public WxUser selectByUserId(String userId);

	public Map<String, Object> queryUsersByid(String userId);// 获取user tjy_user

	public WxUser selectByUserName(String userName);

	public WxUser selectByMobile(String mobile);

	public boolean addWxUser(WxUser wxUser);

	public boolean updateWxUser(WxUser wxUser);

	public String addWxUser(WxUser wxUser, TjyUser tjyUser);

	public String updateWxUser(WxUser wxUser, TjyUser tjyUser);

	/**
	 * 获取后台管理员
	 * 
	 * @Title: selectByManager
	 * @Description: TODO
	 * @param string
	 * @return
	 * @return: List<String,Object>
	 * @author: zengmin
	 * @date: 2017年4月17日 下午5:22:46
	 */
	public List<Map<String, Object>> selectByManager(String string);

	public WxUser selectByNickName(String nickName);

	public WxUser selectByOpenId(String reg_openId);

	public void updateMobile(Long userid, String mobile);

	/**
	 * 获取已开通互助宝的用户列表，不包含过期用户
	 * 
	 * @Title: selectTaskHzbWxUser
	 * @Description: TODO
	 * @return
	 * @return: List<Map<String,Object>>
	 * @author: zengmin
	 * @date: 2017年7月27日 上午9:36:52
	 */
	public List<Map<String, Object>> selectTaskHzbWxUser();

	/**
	 * 根据用户编号获取用户互助宝开通年度差
	 * 
	 * @Title: selectHzbNdcByUserId
	 * @Description: TODO
	 * @param userId
	 * @return
	 * @return: long
	 * @author: zengmin
	 * @date: 2017年7月27日 下午2:50:36
	 */
	public long selectHzbNdcByUserId(String userId);

}