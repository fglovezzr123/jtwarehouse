package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Activity;

/**
 * <p>Title: 活动管理</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年4月18日 下午2:47:20
 */
public interface IActivityService {
    
    public DataGrid selActivitys(PageParam param, Activity activity);


	/**
	 * 活动标签列表查询
	 * @param param
	 * @param activitytag
	 * @return
	 */
	Object selectactivity(PageParam param, Activity activity,String userId);
	/**
	 * 
	 * @param dto
	 * @return
	 * * 活动标签新增
	 */
	 
	String addActivity(Activity dto);
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	Activity getActivityByid(String id);
	/**
	 * 
	 * @param dto
	 * @return
	 * * 活动标签修改
	 */
	String updateActivity(Activity dto);


	/**
	 * 删除活动
	 * @param id
	 * @return
	 */
	public String deleteactivity(String id);


	/**
	 * 接口条件查询活动列表
	 * @param classid
	 * @param page
	 * @param size
	 * @param key
	 * @return
	 */
	public List<Map> getActivityByTerm(String tagid,String classid, Integer page,
			Integer size, String key);


	/**
	 * 接口条件查询活动列表
	 * @param activity
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map> getActivityByTermf(Activity activity, Integer page,
			Integer size,String userId);


	/**
	 * 根据活动id查询活动详情  包含省市
	 * @param id
	 * @return
	 */
	public Map getactivityDetailByid(String id);


	/**
	 * 根据用户id获取我发布的活动
	 * @param userId
	 * @param status 
	 * @param size 
	 * @param page 
	 * @return
	 */
	List getmyactivitiesbyid(String userId, Integer page, Integer size, Integer status);

	/**
	 * 根据时间获取进行中的活动
	 * @return
	 */
	List<Activity> getjxzActivity();

	/**
	 * 根据时间获取报名结束的活动
	 * @return
	 */
	List<Activity> getbmjsActivities();
	
	/**
	 * 根据时间获取报名中的活动
	 * @return
	 */
	List<Activity> getbmzActivities();


	public void updateActivityUseSql(Activity ja,int status);


	public void refuseSignup(Activity dto);


	public void jiesuanSignup(Activity dto);

}
