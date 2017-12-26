package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.ActivityCancel;

public interface IActivityCancelService {

	/**
	 * 添加取消申请
	 * @param dto
	 */
	String addCancel(ActivityCancel dto);

	/**
	 * 查询是否有未审核的取消申请
	 * @return
	 */
	List<ActivityCancel> selectbyTerm(ActivityCancel parm);

	String updateActivityCancel(ActivityCancel dto);

	Object selectList(PageParam param, ActivityCancel dto,String userName,String titles);

}
