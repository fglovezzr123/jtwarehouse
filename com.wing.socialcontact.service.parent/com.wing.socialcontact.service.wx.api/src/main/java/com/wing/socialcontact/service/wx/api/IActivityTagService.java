package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.ActivityTag;

/**
 * 
 * <p>Title:活动标签管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年4月18日 下午3:18:53
 */
public interface IActivityTagService {

    /**
     * laijz
     * //TODO 根据活动类别(以书会友，以玩会友),是否推荐( 0 不推荐   1 推荐),获取条数查询活动标签
     * @param classId 活动类别(以书会友，以玩会友)
     * @param recommnend 是否推荐( 0 不推荐   1 推荐)
     * @param topNum 获取条数
     * @return
     */
    public List<ActivityTag> selTags(String classId);

	/**
	 * 活动标签列表查询
	 * @param param
	 * @param activitytag
	 * @return
	 */
	Object selectactivitytag(PageParam param, ActivityTag activitytag);
	/**
	 * 
	 * @param dto
	 * @return
	 * * 活动标签新增
	 */
	 
	String addActivityTag(ActivityTag dto);
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	ActivityTag getActivityTagByid(String id);
	/**
	 * 
	 * @param dto
	 * @return
	 * * 活动标签修改
	 */
	String updateActivityTag(ActivityTag dto);
	
	/**
	 * 根据活动分类id查询活动标签
	 * @param cid
	 * @return
	 */
	List<Map<String,Object>> activityTag(String cid);

	/**
	 * 删除活动标签
	 * @param id
	 * @return
	 */
	public String deleteactivitytag(String id);

	/**
	 * 获取所有标签
	 * @return
	 */
	public List selectalltag();

}
