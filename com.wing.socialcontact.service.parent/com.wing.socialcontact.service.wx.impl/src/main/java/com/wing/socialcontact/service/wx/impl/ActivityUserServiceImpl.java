package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IActivityUserService;
import com.wing.socialcontact.service.wx.bean.Activity;
import com.wing.socialcontact.service.wx.bean.ActivityUser;
import com.wing.socialcontact.service.wx.dao.ActivityUserDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
/**
 * 
 * <p>Title: 用户报名活动管理</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年4月19日 下午7:13:32
 */
@Service
public class ActivityUserServiceImpl extends BaseServiceImpl<ActivityUser> implements IActivityUserService{

	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:ActivityUser:\" +";
	
	@Resource
	private ActivityUserDao	activityUserDao;

	/**
	 * 后台页面数据查询
	 * @param param
	 * @param activityUser
	 * @return
	 */
	@Override
	public Object selectactivityuser(PageParam param, ActivityUser activityUser) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("activityId",activityUser.getActivityId());
		parm.put("userName",activityUser.getUserName());
		parm.put("phone",activityUser.getPhone());
		parm.put("createTime",activityUser.getCreateTime());
		parm.put("userId",activityUser.getUserId());
		List<Map<String,Object>> lst=activityUserDao.getclassMap(parm);

		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}
	/**
	 * 后台页面数据查询
	 * @param param
	 * @param activityUser
	 * @return
	 */
	@Override
	public Object selectactivityuser2(PageParam param, ActivityUser activityUser) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		
		Map parm = new HashMap();
		parm.put("userId",activityUser.getUserId());
		parm.put("start", 0);
		parm.put("size", 1000);
		List lst=activityUserDao.getmysignupbyid(parm);
		
		PageInfo page = new PageInfo(lst);
		
		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	/**
	 * 导出报表数据查询
	 * @param acid1
	 * @param userName1
	 * @param phone1
	 * @param createTime1
	 * @return
	 */
	@Override
	public List<Map<String, Object>> exporactivityUsers(String acid1,
			String userName1, String phone1, Date createTime1) {
		Map parm = new HashMap();
		parm.put("activityId",acid1);
		parm.put("userName",userName1);
		parm.put("phone",phone1);
		parm.put("createTime",createTime1);
		List<Map<String,Object>> lst=activityUserDao.getclassMap(parm);
		return lst;
	}

	@Override
	public boolean addActivityUser(ActivityUser user) {
		
		int res = activityUserDao.insert(user);
		if(res>0){
			return true;
		}
		return false;
	}
	/**
	 * 根据用户id获取我的报名列表
	 * @param userId
	 * @return
	 */
	@Override
	public List getmysignupbyid(String userId, Integer page, Integer size) {
		Map parm = new HashMap();
		parm.put("userId",userId);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		List lst=activityUserDao.getmysignupbyid(parm);
		return lst;
	}
	/**
	 * 取消活动时根据活动id更新报名状态  1、待确认  2 用户取消  3 活动取消   4通过  5 拒绝（只在取消活动时调用）
	 * @param id
	 * @param i
	 * @return
	 */
	@Override
	public String updatesignupstatus(String id, int i) {
		Map parm = new HashMap();
		parm.put("activityId",id);
		parm.put("status",i);
		activityUserDao.updatesignupstatus(parm);
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	/**
	 * 修改单条报名信息状态   5未通过审核   2用户取消报名
	 */
	@Override
	public String updatestatusbyid(String signupid, int i) {
		
		
		ActivityUser signup = super.selectByPrimaryKeyCache(signupid, ActivityUser.class);
		signup.setStatus(i);
		if(i==5){
			signup.setOrderStatus(3);
		}
		super.updateByPrimaryKeyCache(signup, signupid);
		return MsgConfig.MSG_KEY_SUCCESS;
	}


	/**
	 * 获取单条报名记录
	 */
	@Override
	public ActivityUser getactivityUserByid(String id) {
		return super.selectByPrimaryKeyCache(id, ActivityUser.class);
	}

	@Override
	public List getusersbyactivityid(String id) {
		Map parm = new HashMap();
		parm.put("activityId",id);
		List<Map<String,Object>> lst=activityUserDao.getusersbyactivityid(parm);
		return lst;
	}

	/**
	 * 查询是否报名记录（报名有效    状态为 1：未审核   4 已通过）
	 * @param activityid
	 * @param userid
	 * @return
	 */
	@Override
	public List<ActivityUser> getsignupusers(String activityid, String userid,int orderstatus) {
		Map parm = new HashMap();
		parm.put("activityId",activityid);
		parm.put("userid",userid);
		parm.put("orderStatus",orderstatus);
		List<ActivityUser> lst=activityUserDao.getsignupusers(parm);
		return lst;
	}

	/**
	 * 更新报名信息
	 */
	@Override
	public void updatesignup(ActivityUser signup) {
		
		super.updateByPrimaryKeyCache(signup, signup.getId());
		
	}

	/**
	 * 新增报名
	 */
	@Override
	public void insertsignup(ActivityUser signup) {
		// TODO Auto-generated method stub
		activityUserDao.insert(signup);
	}

	/**
	 * 根据活动id和报名状态获取报名记录
	 */
	@Override
	public List<ActivityUser> getUncheckedUsersByActivityid(String id, int status) {
		ActivityUser  record  = new ActivityUser();
		record.setActivityId(id);
		record.setStatus(status);
		record.setOrderStatus(2);
		if(status ==4 ){
			record.setBalance(0);
		}
		return activityUserDao.select(record);
	}

	@Override
	public ActivityUser getactivityUserByorderid(String out_trade_no) {
		ActivityUser  record  = new ActivityUser();
		record.setOrderId(out_trade_no);
		List<ActivityUser> user = activityUserDao.select(record);
		
		return user.get(0);
	}

	@Override
	public List<ActivityUser> getsignuphistory(String activityid, String mobile, int i) {
		ActivityUser user= new ActivityUser();
		user.setActivityId(activityid);
		//user.setUserId(mobile);
		user.setPhone(mobile);
		user.setOrderStatus(i);
		return activityUserDao.select(user);
	}

	@Override
	public Map selectbyactivityid1(String id, Integer page, Integer size,
			Integer status) {
		Map res = new HashMap();
		Map parm = new HashMap();
		parm.put("activityId",id);
		parm.put("status",status);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		List<Map<String,Object>> lst=activityUserDao.getclassMap1(parm);

		Integer cou = activityUserDao.getcount(parm);
		res.put("cou",cou);
		res.put("list",lst);
		return res;
	}
}
