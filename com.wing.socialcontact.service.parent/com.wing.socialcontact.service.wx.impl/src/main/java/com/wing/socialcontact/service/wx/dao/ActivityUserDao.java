package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.ActivityUser;
/**
 * 
 * <p>Title:用户报名活动管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年4月19日 下午7:12:25
 */
@Repository
public interface ActivityUserDao extends Mapper<ActivityUser> {

	List<Map<String, Object>> getclassMap(Map parm);

	List getmysignupbyid(Map parm);

	void updatesignupstatus(Map parm);

	void updatestatusbyid(Map parm);

	List<Map<String, Object>> getusersbyactivityid(Map parm);

	List<ActivityUser> getsignupusers(Map parm);

	List<Map<String, Object>> getclassMap1(Map parm);

	Integer getcount(Map parm);

}
