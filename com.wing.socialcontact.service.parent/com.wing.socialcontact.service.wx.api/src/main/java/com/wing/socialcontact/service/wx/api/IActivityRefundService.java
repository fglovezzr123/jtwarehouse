package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.ActivityRefund;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月4日 下午4:37:28
 */
public interface IActivityRefundService {

	/**
	 * 添加需要退款的记录信息
	 * @param refund
	 * @return
	 */
	boolean insertRefund(ActivityRefund refund);

	/**
	 * 查询所有需要退款的记录
	 * @return
	 */
	List<ActivityRefund> selectNoRefundList();

	Object selectList(PageParam param, ActivityRefund dto, String titles);

	void updateRefund(ActivityRefund activityRefund);

}
