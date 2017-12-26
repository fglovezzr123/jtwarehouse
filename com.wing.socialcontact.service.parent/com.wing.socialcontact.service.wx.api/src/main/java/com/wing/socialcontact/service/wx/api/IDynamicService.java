package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Dynamic;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:05
 * @version 1.0
 */
public interface IDynamicService{

	/**
	 * 新增
	 * @param t
	 * @author xuxinyuan
	 */
	public int insertDynamicSignup(Dynamic t);
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author xuxinyuan
	 */
	public int updateDynamicSignup(Dynamic t);
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author xuxinyuan
	 */
	public int deleteDynamicSignup(Dynamic t);
	/**
	 * 查询 
	 * @param t
	 * @return
	 * @author xuxinyuan
	 */
	public List<Dynamic> queryDynamicSignup(Dynamic t);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author xuxinyuan
	 */
	public Dynamic getDynamicSignup(String id);
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author xuxinyuan
	 */
	public DataGrid selectAllDynamicSignup(PageParam param, String dyContent,String userName,String visitQuantity,String issuedDate,String user_id);
	
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author xuxinyuan
	 */
	public DataGrid selectAllDynamicSignup2(PageParam param, String dyContent,String userName,String visitQuantity,String issuedDate,String user_id);
	
	/**
	 * 获取的商友最新动态
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selectMyFriendDynamic(String userId,int pageNum, int pageSize);
	
	/**
	 * 获取商友最新动态
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selectAllUserDynamic(String userId,int pageNum, int pageSize,String dyContent,Long dynamicloadtime);
	
	/**
	 * 获取商友最新动态
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selectAllDynamic(int pageNum, int pageSize);
	
	/**
	 * 获取动态信息
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Map selectDynamicById(String userId,String dynamicId);
	
	/**
	 * 获取的关注用户动态
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selectMyFollowDynamic(String userId,int pageNum, int pageSize);
	
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author xuxinyuan
	 */
	public Map getDynamicMapById(String id);
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author xuxinyuan
	 */
	public Map getDynamicMapById2(String id);
	
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author xuxinyuan
	 */
	public void deleteDynamic(String id);
	
	/**
	 * 获取我的动态列表
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selectMyDynamicList(String userId,int pageNum, int pageSize);
	
	/**
	 * 获取我访问用户的动态列表
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selectMyVisitUserDynamicList(String userId,String visitUserId,String isFriend,int pageNum, int pageSize);
	
	/**
	 * 获取未读商友动态数量
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public int selectCountAllUserDynamicByUserId(String userId,Long dynamicloadtime);
	
	
	//============================================APP方法================================================
	
	/**
	 * 商友动态列表/搜索
	 * @param userId
	 * @param parseInt
	 * @param parseInt2
	 * @param dyContent
	 * @param dynamicloadtime
	 * @return
	 */
	public List selectAllUserDynamic1(String userId, int parseInt,
			int parseInt2, String dyContent, Long dynamicloadtime);
	/**
	 * 我关注的好友动态
	 * @param userId
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	public List selectMyFollowDynamic1(String userId, int parseInt,
			int parseInt2);
	/**
	 * 动态详情
	 * @param userId
	 * @param dynamicId
	 * @return
	 */
	public Map selectDynamicById1(String userId, String dynamicId);

}