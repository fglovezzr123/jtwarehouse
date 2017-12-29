package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.ActivityDelay;

public interface IActivityDelayService {

	List<ActivityDelay> selectbyTerm(ActivityDelay parm);

	String  addDelay(ActivityDelay dto);

	String updateActivityDelay(ActivityDelay dto);

	Object selectList(PageParam param, ActivityDelay dto,String userName,String titles);

}
