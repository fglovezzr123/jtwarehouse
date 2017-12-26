package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.LeaveMsg;

/**
 * 
 * @author zengmin
 * @date 2017-04-28 23:41:19
 * @version 1.0
 */
@Repository
public interface LeaveMsgDao extends Mapper<LeaveMsg> {
	List query(Map parm);
	
	
	/**
	 * 根据条件获取留言列表
	 * @param param
	 * @return
	 */
	public 	List<Map<String, Object>>  selectLeaveMsgs(Map parm);
	
}
