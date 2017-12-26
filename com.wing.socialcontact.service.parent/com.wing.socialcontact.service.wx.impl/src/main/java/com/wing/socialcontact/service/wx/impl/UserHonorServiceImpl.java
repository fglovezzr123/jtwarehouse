package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IHonorService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserHonorService;
import com.wing.socialcontact.service.wx.bean.Honor;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.UserHonor;
import com.wing.socialcontact.service.wx.bean.UserIntegral;
import com.wing.socialcontact.service.wx.dao.HonorDao;
import com.wing.socialcontact.service.wx.dao.TjyUserDao;
import com.wing.socialcontact.service.wx.dao.UserHonorDao;
import com.wing.socialcontact.service.wx.dao.UserIntegralDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 
 * @author gaojun
 * @date 2017-04-14 22:34:36
 * @version 1.0
 */
@Service
public class UserHonorServiceImpl extends BaseServiceImpl<UserHonor>   implements IUserHonorService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:userHonor:\" + ";
	
	@Resource
	private UserHonorDao userHonorDao;
	@Resource
	private UserIntegralDao userIntegralDao;
	@Resource
	private HonorDao honorDao;
	@Resource
	private TjyUserDao tjyUserDao;
	@Autowired
	private ITjyUserService tjyUserService;
	@Resource
	private IHonorService honorService;
	
	

	@Override
	public DataGrid selectAllUserHonor(PageParam param, UserHonor userHonor) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("orderStr", orderStr);
		parm.put("userId", userHonor.getUserId());
		List lst = userHonorDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	
	@Override
	public List<Map> selectAllUserHonor(UserHonor userHonor) {
		Map parm = new HashMap();
		parm.put("userId", userHonor.getUserId());
		List<Map> lst = userHonorDao.selectByParam(parm);
		return lst;
	}
	
	@Override
	public DataGrid queryUserListByparam(PageParam param, String nickname, String true_name,String job,String industry, String mobile,String recon_mobile,String bind_phone,String orderBy,String level,String address){
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("nickname", nickname);
		parm.put("true_name", true_name);
		parm.put("job", job);
		parm.put("industry", industry);
		parm.put("mobile", mobile);
		parm.put("recon_mobile", recon_mobile);
		parm.put("bind_phone", bind_phone);
		parm.put("level", level);
		parm.put("address", address);
		if(StringUtils.isEmpty(orderBy)){
			orderBy="1";
		}
		parm.put("orderBy", orderBy);
		List<Map<String,Object>> lst=tjyUserDao.getUserListByParam2(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	@Override
	public DataGrid queryUserListByparam_select(PageParam param, String nickname, String true_name,String job,String industry, String mobile,String recon_mobile,String bind_phone){
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("nickname", nickname);
		parm.put("true_name", true_name);
		parm.put("job", job);
		parm.put("industry", industry);
		parm.put("mobile", mobile);
		parm.put("recon_mobile", recon_mobile);
		parm.put("bind_phone", bind_phone);
		List<Map<String,Object>> lst=tjyUserDao.getUserListByParam_select(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	
	@Override
	public List<Map<String, Object>>  queryUserListByparam(String id){
		Map parm = new HashMap();
		parm.put("id", id);
		List<Map<String, Object>>  lst=tjyUserDao.getUserListByParam2(parm);
		return lst;
	}

	@Override
	public String addUserHonor(UserHonor userHonor) {
		int res = 0;
		if(null!=userHonor.getHonorId()&&userHonor.getHonorId()!=""){
			res = userHonorDao.insert(userHonor);
		}
		if(res > 0){
			Honor honor = honorDao.selectByPrimaryKey(userHonor.getHonorId());
			if(null!=honor && "1".equals(honor.getHornorType())){
				//TjyUser user =  tjyUserDao.selectByPrimaryKey(userHonor.getUserId());
				TjyUser user =  tjyUserService.selectByPrimaryKey(userHonor.getUserId());
				user.setHonorTitle(honor.getTitle());
				user.setHonorFlag(honor.getHonorCode());
			
				tjyUserService.updateTjyUser(user);
			}
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}
	
	@Override
	public boolean addUserAndHonor(String userId,String taskCode) {
		try {	
			if(StringUtils.isEmpty(userId)){
				return false;
			}
			if(StringUtils.isEmpty(taskCode)){
				return false;
			}
			//Honor honor = honorDao.selectByCode(honorCode);
			UserIntegral userIntegral=userIntegralDao.selectByCode(taskCode);
			if(null == userIntegral){
				return false;
			}
			String[] userHonorIds=null;
			if (userIntegral.getRemark() != null && !"".equals(userIntegral.getRemark())) {
				userHonorIds = userIntegral.getRemark().split(",");
				for(int i=0;i<userHonorIds.length;i++){
					Honor honor = honorService.selectById(userHonorIds[i]);
					
					UserHonor userHonor =  userHonorDao.selectByUserIdAndHonorId(userId, honor.getId());
					String userType = honor.getHornorType();
					if(null != userHonor){
						if("2".equals(userType)){//互助宝勋章不能叠加
							if(super.deleteByPrimaryKeyCache(userHonor.getId(), UserHonor.class)){
								UserHonor uh = new UserHonor();
								uh.setUserId(userId);
								uh.setHonorId(honor.getId());
								uh.setCreateTime(new Date());
								userHonorDao.insert(uh);
								return true;
							}else{
								return false;
							}
						}else{
							userHonor.setCreateTime(new Date());
							if(super.updateByPrimaryKeyCache(userHonor,userHonor.getId())){
								return true;
							}else{
								return false;
							}
						}
					}else{
						UserHonor uh = new UserHonor();
						uh.setUserId(userId);
						uh.setHonorId(honor.getId());
						uh.setCreateTime(new Date());
						userHonorDao.insert(uh);
						return true;
					}
				}
				
			}else{
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public String updateUserHonor(UserHonor userHonor) {
		if(super.updateByPrimaryKeyCache(userHonor,userHonor.getId())){
			Honor honor = honorDao.selectByPrimaryKey(userHonor.getHonorId());
			if(null!=honor && "1".equals(honor.getHornorType())){
				TjyUser user =  tjyUserService.selectByPrimaryKey(userHonor.getUserId());
				user.setHonorTitle(honor.getTitle());
				user.setHonorFlag(honor.getHonorCode());
				tjyUserService.updateTjyUser(user);
			}
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}


	@Override
	public boolean deleteUserHonor(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					UserHonor r=selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, UserHonor.class)){
							count++;
							Honor honor = honorDao.selectByPrimaryKey(r.getHonorId());
							if(null!=honor && "1".equals(honor.getHornorType())){
								TjyUser user =  tjyUserService.selectByPrimaryKey(r.getUserId());
								List<Map<String, Object>> userHonor_list = userHonorDao.selectByUserId(r.getUserId());
								if(null!=userHonor_list && userHonor_list.size() > 0 ){
									user.setHonorTitle((String)userHonor_list.get(0).get("title"));
									user.setHonorFlag((String)userHonor_list.get(0).get("honor_code"));
								}else{
									user.setHonorTitle(null);
									user.setHonorFlag(null);
								}
								tjyUserService.updateTjyUser(user);
								///tjyUserDao.updateByPrimaryKey(user);
							}
						}
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public UserHonor selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, UserHonor.class);
	}

	@Override
	public UserHonor selectById(String id) {
		return userHonorDao.selectByPrimaryKey(id);
	}
	
	@Override
	public boolean updateStatus(String id, int status) {
		TjyUser r=tjyUserService.selectByPrimaryKey(id);
		String loginid = ServletUtil.getMember().getId();// 获得登录id
		if(status==2){
			r.setReconStatus(status);
			r.setReconUserId(loginid);
			r.setReconDate(new Date());
			r.setIsRealname(1);
		}else if(status==3){
			r.setReconStatus(status);
			r.setReconUserId(loginid);
			r.setReconDate(new Date());
		}
		tjyUserService.updateTjyUser(r);
	
			///return MsgConfig.MSG_KEY_SUCCESS;
		
		
		return true;
	}
	
	@Override
	public boolean isExistsByUserId(String userId, String honorId) {
		if(StringUtils.isEmpty(userId)){
			return false;
		}
		if(StringUtils.isEmpty(honorId)){
			return false;
		}
		Map parm = new HashMap();
		parm.put("userId", userId);
		parm.put("userId", honorId);
		List lst = userHonorDao.selectByParam(parm);
		if(null == lst ){
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public List<UserHonor> selectByUserIdAndHonorId(String userId, String honorId) {
		UserHonor userHonor = new UserHonor();
		userHonor.setUserId(userId);
		userHonor.setHonorId(honorId);
		return userHonorDao.select(userHonor);
	}
	
	@Override
	public List<Map<String, Object>>  selectByUserId(String userId) {
		return userHonorDao.selectByUserId(userId);
	}
	
	@Override
	public Object queryShUserListByParam(PageParam param, Map<String, Object> searchMap) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		System.out.println("-----------"+orderStr);
		searchMap.put("orderby", orderStr);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<Map<String,Object>> lst=tjyUserDao.getShUserListByParam(searchMap);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public List<UserHonor> getAllHonors() {
		return userHonorDao.selectByType(new HashMap());
	}


}