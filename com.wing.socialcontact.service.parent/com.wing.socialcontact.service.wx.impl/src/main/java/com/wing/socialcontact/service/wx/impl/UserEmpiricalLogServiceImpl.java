package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IUserEmpiricalLogService;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.service.wx.bean.UserEmpiricalLog;
import com.wing.socialcontact.service.wx.dao.UserEmpiricalDao;
import com.wing.socialcontact.service.wx.dao.UserEmpiricalLogDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:52
 * @version 1.0
 */
@Service
public class UserEmpiricalLogServiceImpl extends BaseServiceImpl<UserEmpiricalLog> implements IUserEmpiricalLogService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:UserEmpiricalLog:\" + ";
	
	@Resource
	private UserEmpiricalLogDao userEmpiricalLogDao;
	@Resource
	private UserEmpiricalDao userEmpiricalDao;
	@Override
	public DataGrid selectUserEmpiricalLogs(PageParam param, UserEmpiricalLog userEmpiricalLog,String nickname,String mobile,String userId) {
		DataGrid data = new DataGrid();

		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("nickname",nickname);
		parm.put("mobile",mobile);
		parm.put("userId",userId);
		parm.put("orderStr", orderStr);
		List<Map<String, Object>> lst = userEmpiricalLogDao.selectByParam(parm);
		for(Map<String, Object> m:lst){
			int ye_empirical=(int)m.get("ye_empirical");
			//用户等级
			UserEmpirical userEmpirical_temp = userEmpiricalDao.selectByEmpirical(ye_empirical);
			if(null!=userEmpirical_temp){
				m.put("level", userEmpirical_temp.getLevel());
			}
		}
		
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());

		return data;
	}
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteUserEmpiricalLog(String[] ids) {
		// 等待删除的对象集合
			int count = 0;
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					String[] myids = id.split(",");
					for (String string : myids) {
						UserEmpiricalLog r = selectByPrimaryKey(string);
						if (r != null) {

							if (super.deleteByPrimaryKeyCache(string, UserEmpiricalLog.class))
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
	public UserEmpiricalLog selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, UserEmpiricalLog.class);
	}
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	@Override
	public String addUserEmpiricalLog(UserEmpiricalLog dto) {
		Map parm = new HashMap();
			int res = userEmpiricalLogDao.insert(dto);
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
	public String updateUserEmpiricalLog(UserEmpiricalLog dto) {
			if(super.updateByPrimaryKeyCache(dto,dto.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	
	@Override
	public UserEmpiricalLog selectById(String id) {
		return userEmpiricalLogDao.selectByPrimaryKey(id);
	}
	
	
	/**
	 *是否有记录（只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */

	@Override
	public UserEmpiricalLog selectOneTimes(String userId, String taskCode){
		return userEmpiricalLogDao.selectOneTimes(userId,taskCode);
	}
	/**
	 *当天是否有记录（当天只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */
	@Override
	public UserEmpiricalLog selectOneDay(String userId, String taskCode){
		return userEmpiricalLogDao.selectOneDay(userId,taskCode);
	}
	/**
	 *当年是否有记录（当年只有一条记录）
	 * @param userId
	 * @param code
	 * @return
	 */
	@Override
	public UserEmpiricalLog selectOneYear(String userId, String taskCode){
		return userEmpiricalLogDao.selectOneYear(userId,taskCode);
	}
	
	/**
	 *累积当天的积分
	 * @param userId
	 * @param code
	 * @return
	 */
	@Override
	public Integer  selectOneDaySum(String userId, String taskCode){
		return userEmpiricalLogDao.selectOneDaySum(userId,taskCode);
	}
	/**
	 *累积当年的积分
	 * @param userId
	 * @param code
	 * @return
	 */
	@Override
	public Integer  selectOneYearSum(String userId, String taskCode){
		return userEmpiricalLogDao.selectOneYearSum(userId,taskCode);
	}
	
	

}