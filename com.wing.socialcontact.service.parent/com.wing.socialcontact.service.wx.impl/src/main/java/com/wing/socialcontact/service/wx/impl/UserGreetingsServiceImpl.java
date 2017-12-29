package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IUserGreetingsService;
import com.wing.socialcontact.service.wx.bean.UserGreetings;
import com.wing.socialcontact.service.wx.dao.UserGreetingsDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author gaojun
 * @date 2017-04-22 20:19:46
 * @version 1.0
 */
@Service
public class UserGreetingsServiceImpl extends BaseServiceImpl<UserGreetings>  implements IUserGreetingsService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:userGreetings:\" + ";
	
	@Resource
	private UserGreetingsDao userGreetingsDao;
	
	@Override
	public DataGrid selectAllUserGreetings(PageParam param, UserGreetings userGreetings) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("orderStr", orderStr);
		List lst = userGreetingsDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	
	@Override
	public List selectAllUserGreetings(UserGreetings userGreetings){
		List lst = userGreetingsDao.selectAllUserGreetings(userGreetings);
		return lst;
	}

	@Override
	public String addUserGreetings(UserGreetings userGreetings) {
		int res = userGreetingsDao.insert(userGreetings);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateUserGreetings(UserGreetings userGreetings) {
		if(super.updateByPrimaryKeyCache(userGreetings,userGreetings.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteUserGreetings(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					UserGreetings r=selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, UserGreetings.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public UserGreetings selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, UserGreetings.class);
	}

	@Override
	public UserGreetings selectById(String id) {
		return userGreetingsDao.selectByPrimaryKey(id);
	}
	
	
	@Override
	public List selectUserGreetings(UserGreetings userGreetings) {
		List lst = userGreetingsDao.selectByType(userGreetings);
		return lst;
	}
	
	@Override
	public Integer getCountOneDay(String userId) {
		return userGreetingsDao.getCountOneDay(userId);
	}
	
}