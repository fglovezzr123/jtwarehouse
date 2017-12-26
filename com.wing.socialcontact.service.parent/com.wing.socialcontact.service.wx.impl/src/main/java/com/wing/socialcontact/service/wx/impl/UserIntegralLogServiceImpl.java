package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IUserHonorService;
import com.wing.socialcontact.service.wx.api.IUserIntegralEmpiricalService;
import com.wing.socialcontact.service.wx.api.IUserIntegralLogService;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.service.wx.bean.UserEmpiricalLog;
import com.wing.socialcontact.service.wx.bean.UserIntegral;
import com.wing.socialcontact.service.wx.bean.UserIntegralEmpirical;
import com.wing.socialcontact.service.wx.bean.UserIntegralLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.service.wx.dao.UserEmpiricalDao;
import com.wing.socialcontact.service.wx.dao.UserEmpiricalLogDao;
import com.wing.socialcontact.service.wx.dao.UserIntegralDao;
import com.wing.socialcontact.service.wx.dao.UserIntegralLogDao;
import com.wing.socialcontact.service.wx.dao.WxUserDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:54
 * @version 1.0
 */
@Service
public class UserIntegralLogServiceImpl  extends BaseServiceImpl<UserIntegralLog> implements IUserIntegralLogService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:UserIntegralLog:\" + ";
	
	@Resource
	private UserIntegralLogDao userIntegralLogDao;
	
	@Resource
	private UserEmpiricalLogDao userEmpiricalLogDao;
	
	@Resource
	private UserIntegralDao userIntegralDao;
	
	@Resource
	private UserEmpiricalDao userEmpiricalDao;

	@Resource
	private WxUserDao wxUserDao;
	

	@Autowired
	private IUserHonorService userHonorService;
	
	@Autowired
	private IUserIntegralEmpiricalService userIntegralEmpiricalService; 	
	
	
	
	
	@Override
	public DataGrid selectUserIntegralLogs(PageParam param, UserIntegralLog userIntegralLog,String nickname,String mobile,String userId) {
		DataGrid data = new DataGrid();

		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("nickname",nickname);
		parm.put("mobile",mobile);
		parm.put("userId",userId);
		parm.put("orderStr", orderStr);
		List lst = userIntegralLogDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());

		return data;
	}
	
	@Override
	public List<Map<String, Object>> selectIntegralLogPageByUserId(String userId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);// 分页
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		///param.put("orderby", "HML.manager_time desc");
		return userIntegralLogDao.selectByParam(param);
	}
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteUserIntegralLog(String[] ids) {
		// 等待删除的对象集合
			int count = 0;
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					String[] myids = id.split(",");
					for (String string : myids) {
						UserIntegralLog r = selectByPrimaryKey(string);
						if (r != null) {

							if (super.deleteByPrimaryKeyCache(string, UserIntegralLog.class))
								count++;
						}
					}
				}
			}
			return count > 0;
	}
	
	/**
	 * 调用缓存机制
	 */
	@Override
	public UserIntegralLog selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, UserIntegralLog.class);
	}
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	@Override
	public String addUserIntegralLog(UserIntegralLog dto) {
		Map parm = new HashMap();
			int res = userIntegralLogDao.insert(dto);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	@Override
	public String updateUserIntegralLog(UserIntegralLog dto) {
			if(super.updateByPrimaryKeyCache(dto,dto.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	
	@Override
	public UserIntegralLog selectById(String id) {
		return userIntegralLogDao.selectByPrimaryKey(id);
	}
	

	/**
	 *是否有记录（只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */

	@Override
	public UserIntegralLog selectOneTimes(String userId, String taskCode){
		return userIntegralLogDao.selectOneTimes(userId,taskCode);
	}
	/**
	 *当天是否有记录（当天只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */
	@Override
	public UserIntegralLog selectOneDay(String userId, String taskCode){
		return userIntegralLogDao.selectOneDay(userId,taskCode);
	}
	/**
	 *当年是否有记录（当年只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */
	@Override
	public UserIntegralLog selectOneYear(String userId, String taskCode){
		return userIntegralLogDao.selectOneYear(userId,taskCode);
	}
	
	/**
	 *累积当天的积分
	 * @param userId
	 * @param code
	 * @return
	 */
	@Override
	public Integer  selectOneDaySum(String userId, String taskCode){
		return userIntegralLogDao.selectOneDaySum(userId,taskCode);
	}
	/**
	 *累积当年的积分
	 * @param userId
	 * @param code
	 * @return
	 */
	@Override
	public Integer  selectOneYearSum(String userId, String taskCode){
		return userIntegralLogDao.selectOneYearSum(userId,taskCode);
	}
	
	
	/**
	 * 根据任务编码和用户修积分和经验值
	 * @param 
	 * @return
	 */
	@Override
	public boolean addLntAndEmp(String userId,String code) {
		try {	
			if(StringUtils.isEmpty(userId)){
				return false;
			}
			//只有一次
			if("task_0001".equals(code)//首次登录:task_0001
					||"task_0019".equals(code)//完善个人信息:task_0019
					||"task_0020".equals(code)//完善企业信息:task_0020
					){
				UserIntegralLog userIntegralLog = userIntegralLogDao.selectOneTimes(userId, code);
				if(null !=userIntegralLog){
					return false;
				}
			}
			//每日一次
			if("task_0002".equals(code)//banner点击:task_0002
					||"task_0003".equals(code)//资讯点击:task_0003
					||"task_0005".equals(code)//动态/关注评论:task_0005
					||"task_0006".equals(code)//悬赏:task_0006
					||"task_0007".equals(code)//创建活动:task_0007
					||"task_0009".equals(code)//话题创建:task_0009
					||"task_0010".equals(code)//话题讨论:task_0010
					||"task_0011".equals(code)//用户评价:task_0011
					||"task_0013".equals(code)//用户投票:task_0013
					||"task_0015".equals(code)//资讯讨论:task_0015
				    ){
				UserIntegralLog userIntegralLog = userIntegralLogDao.selectOneDay(userId, code);
				if(null !=userIntegralLog){
					return false;
				}
			}
			//每年一次
			if("task_0018".equals(code)//认证:task_0018
					){
				UserIntegralLog userIntegralLog = userIntegralLogDao.selectOneTimes(userId, code);
				if(null !=userIntegralLog){
					return false;
				}
			}
			//每日一次 达到10级不记分
			if("task_0004".equals(code)//动态/关注发布:task_0004
					||"task_0014".equals(code)//资讯点赞:task_0014
				
					){
				UserIntegralLog userIntegralLog = userIntegralLogDao.selectOneDay(userId, code);
				if(null !=userIntegralLog){
					return false;
				}
				//用户信息
				WxUser user = wxUserDao.selectByPrimaryKey(Long.parseLong(userId));
				int empiricalTotal = 0;//经验值
				if(null != user.getEmpiricalTotal()){
					empiricalTotal =  user.getEmpiricalTotal();
				}
				//用户等级
				UserEmpirical userEmpirical_temp = userEmpiricalDao.selectBylevel("LV10");
				int empirical = 0;//经验值
				if(null != userEmpirical_temp){
					if(null != userEmpirical_temp.getEmpiricalLow()){
						empirical =  userEmpirical_temp.getEmpiricalLow();
					}
				}
				
				if(empiricalTotal>=empirical){
					return false;
				}
			}
			
			
			//只有一次 此项每日积分上限为90
			if("task_0012".equals(code)//项目报名:task_0012
					||"task_0008".equals(code)//活动分享:task_0008
					||"task_0016".equals(code)//资讯分享:task_0016
					){
				int oneDaySum = userIntegralLogDao.selectOneDaySum(userId, code);
				if(oneDaySum >= 90){
					return false;
				}
			}
			
			//只有一次 此项每日积分上限为300
			if("task_0022".equals(code)//邀请新用户注册:task_0022
					||"task_0023".equals(code)//邀请新用户注册+认证:task_0023
					){
				int oneDaySum = userIntegralLogDao.selectOneDaySum(userId, code);
				if(oneDaySum >= 300){
					return false;
				}
			}
			
			//只有一次 此项每年积分上限为100
			if("task_0017".equals(code)//创建群聊:task_0017
					){
				int oneDaySum = userIntegralLogDao.selectOneYearSum(userId, code);
				if(oneDaySum >= 100){
					return false;
				}
			}
			
			//获取每日积分上下限
			UserIntegralEmpirical userIntegralEmpirical = userIntegralEmpiricalService.selectByIeType("3");
			int oneDayIntegral = userIntegralLogDao.selectOneDaySum(userId,null);
			boolean integralFlag=true;
			boolean empiricalFlag=true;
			int IntegralLimit = 300;//积分
			int empiricalLimit = 500;//经验
			if(null!=userIntegralEmpirical){
				if(!StringUtils.isEmpty(userIntegralEmpirical.getRuleExplain())){
					IntegralLimit =Integer.parseInt(userIntegralEmpirical.getRuleExplain());
				}
				if(!StringUtils.isEmpty(userIntegralEmpirical.getRemark())){
					empiricalLimit =Integer.parseInt(userIntegralEmpirical.getRemark());
				}
			}
			if(oneDayIntegral>=IntegralLimit){//一日积分增加上限为300
				integralFlag =false;
			}
			int oneDayEmpirical = userEmpiricalLogDao.selectOneDaySum(userId,null);
			if(oneDayEmpirical>=empiricalLimit){//一日经验值增加上限为500
				empiricalFlag=false;
			}
			if(oneDayIntegral>=IntegralLimit && oneDayEmpirical>=empiricalLimit){
				return false;
			}
			//等级信息
			UserIntegral userIntegral = userIntegralDao.selectByCode(code);
			if(null == userIntegral){
				return false;
			}
			int integral = userIntegral.getIntegralTotal();//积分
			int empirical = userIntegral.getEmpiricalTotal();//经验值
			String taskSystem = userIntegral.getTaskSystem();//任务名
			//用户信息
			WxUser user = wxUserDao.selectByPrimaryKey(Long.parseLong(userId));
			if(null == user){
				return false;
			}
			int integralTotal = 0 ;//积分
			if(null != user.getEmpiricalTotal()){
				integralTotal = user.getIntegralTotal();
			}
			int empiricalTotal = 0;//经验值
			if(null != user.getEmpiricalTotal()){
				empiricalTotal =  user.getEmpiricalTotal();
			}
			String level_old = user.getLevel();//等级
			int get_level_integral = 0;
			if(empiricalFlag){
				//经验明细
				UserEmpiricalLog userEmpiricalLog = new UserEmpiricalLog();
				userEmpiricalLog.setCreateTime(new Date());
				userEmpiricalLog.setEmpirical(empirical);
				userEmpiricalLog.setTaskName(taskSystem);
				userEmpiricalLog.setRemark(taskSystem);
				userEmpiricalLog.setTaskCode(code);
				userEmpiricalLog.setUserId(userId);
				userEmpiricalLog.setYeEmpirical(empiricalTotal+empirical);
				userEmpiricalLogDao.insert(userEmpiricalLog);
				user.setEmpiricalTotal(empiricalTotal+empirical);
				
			}
			
			Date now = new Date();
			if(integralFlag){
				//积分明细
				UserIntegralLog userIntegralLog = new UserIntegralLog();
				userIntegralLog.setCreateTime(now);
				userIntegralLog.setIntegral(integral);
				userIntegralLog.setTaskName(taskSystem);
				userIntegralLog.setRemark(taskSystem);
				userIntegralLog.setTaskCode(code);
				userIntegralLog.setUserId(userId);
				userIntegralLog.setYeIntegral(integralTotal+integral);
				userIntegralLogDao.insert(userIntegralLog);
				
				
				//用户等级
				UserEmpirical userEmpirical_temp = userEmpiricalDao.selectByEmpirical(empiricalTotal+empirical);
				if(null != userEmpirical_temp){
					user.setLevel(userEmpirical_temp.getLevel());
					if(!userEmpirical_temp.getLevel().equals(level_old)){
						get_level_integral=userEmpirical_temp.getIntegralTotal();
						//升级积分明细
						UserIntegralLog userIntegralLog2 = new UserIntegralLog();
						userIntegralLog2.setCreateTime(new Date(now.getTime()+1000));
						userIntegralLog2.setIntegral(get_level_integral);
						//userIntegralLog2.setTaskName(taskSystem);
						userIntegralLog2.setRemark("升级到"+userEmpirical_temp.getLevel()+"奖励积分");
						//userIntegralLog2.setTaskCode(code);
						userIntegralLog2.setUserId(userId);
						userIntegralLog2.setYeIntegral(integralTotal+integral+get_level_integral);
						userIntegralLogDao.insert(userIntegralLog2);
						user.setIntegralTotal(integralTotal+integral+get_level_integral);
						if(integralTotal+integral+get_level_integral > 10000){
							//积分达人	积分累计达到10000 honor_014
							userHonorService.addUserAndHonor(userId, "honor_002");
						}
					}else{
						user.setIntegralTotal(integralTotal+integral);
						if(integralTotal+integral > 10000){
							//积分达人	积分累计达到10000 honor_014
							userHonorService.addUserAndHonor(userId, "honor_002");
						}
					}
				}
			}
			int t = wxUserDao.updateByPrimaryKey(user);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 根据任务编码和用户修积分和经验值
	 * @param 
	 * @return
	 */
	@Override
	public boolean addLntAndEmp2(String userId,String code,Double amount) {
		//只有一次
		if(!"task_0021".equals(code)//账户充值:task_021
				||amount<1000
				){
				return false;
		}
	
		//等级信息
		UserIntegral userIntegral = userIntegralDao.selectByCode(code);
		if(null == userIntegral){
			return false;
		}
		//int integral = userIntegral.getIntegralTotal();//积分
		//int empirical = userIntegral.getEmpiricalTotal();//经验值
		int integral = (int) Math.floor(amount/10);//积分
		int empirical = (int) Math.floor(amount);;//经验值
		String taskSystem = userIntegral.getTaskSystem();//任务名
		//用户信息
		WxUser user = wxUserDao.selectByPrimaryKey(Long.parseLong(userId));
		if(null == user){
			return false;
		}
		int integralTotal = 0 ;//积分
		if(null != user.getEmpiricalTotal()){
			integralTotal = user.getIntegralTotal();
		}
		int empiricalTotal = 0;//经验值
		if(null != user.getEmpiricalTotal()){
			empiricalTotal =  user.getEmpiricalTotal();
		}
		String level_old = user.getLevel();//等级
		int get_level_integral = 0;
		//经验明细
		UserEmpiricalLog userEmpiricalLog = new UserEmpiricalLog();
		userEmpiricalLog.setCreateTime(new Date());
		userEmpiricalLog.setEmpirical(empirical);
		userEmpiricalLog.setTaskName(taskSystem);
		userEmpiricalLog.setRemark(taskSystem);
		userEmpiricalLog.setTaskCode(code);
		userEmpiricalLog.setUserId(userId);
		userEmpiricalLog.setYeEmpirical(empiricalTotal+empirical);
		userEmpiricalLogDao.insert(userEmpiricalLog);
		
		//积分明细
		UserIntegralLog userIntegralLog = new UserIntegralLog();
		userIntegralLog.setCreateTime(new Date());
		userIntegralLog.setIntegral(integral);
		userIntegralLog.setTaskName(taskSystem);
		userIntegralLog.setRemark(taskSystem);
		userIntegralLog.setTaskCode(code);
		userIntegralLog.setUserId(userId);
		userIntegralLog.setYeIntegral(integralTotal+integral);
		userIntegralLogDao.insert(userIntegralLog);
		//用户等级
		UserEmpirical userEmpirical_temp = userEmpiricalDao.selectByEmpirical(empiricalTotal+empirical);
		if(null != userEmpirical_temp){
			user.setLevel(userEmpirical_temp.getLevel());
			user.setEmpiricalTotal(empiricalTotal+empirical);
			
			if(!userEmpirical_temp.getLevel().equals(level_old)){
				get_level_integral=userEmpirical_temp.getIntegralTotal();
				//升级积分明细
				UserIntegralLog userIntegralLog2 = new UserIntegralLog();
				userIntegralLog2.setCreateTime(new Date());
				userIntegralLog2.setIntegral(get_level_integral);
				//userIntegralLog2.setTaskName(taskSystem);
				userIntegralLog2.setRemark("升级到"+userEmpirical_temp.getLevel()+"奖励积分");
				//userIntegralLog2.setTaskCode(code);
				userIntegralLog2.setUserId(userId);
				userIntegralLog2.setYeIntegral(integralTotal+integral+get_level_integral);
				userIntegralLogDao.insert(userIntegralLog2);
				user.setIntegralTotal(integralTotal+integral+get_level_integral);
			}else{
				user.setIntegralTotal(integralTotal+integral);
			}
			wxUserDao.updateByPrimaryKey(user);
		}
		
		return true;
	}
	
	

}