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
import com.wing.socialcontact.service.wx.api.IUserFavService;
import com.wing.socialcontact.service.wx.bean.UserFav;
import com.wing.socialcontact.service.wx.bean.UserFav;
import com.wing.socialcontact.service.wx.dao.UserFavDao;
import com.wing.socialcontact.service.wx.dao.UserFavDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author gaojun
 * @date 2017-04-11 15:34:07
 * @version 1.0
 */
@Service
public class UserFavServiceImpl extends BaseServiceImpl<UserFav> implements IUserFavService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:UserFav:\" +";
	
	@Resource
	private UserFavDao userFavDao;
	
	
	
	@Override
	public List selectAllUserFav(UserFav userFav) {
		return userFavDao.selectAllUserFavMap(userFav);
	}
	
	@Override
	public List selectAllUserFav2(UserFav userFav) {
		return userFavDao.selectAllUserFavMap2(userFav);
	}

	@Override
	public DataGrid selectUserFavs(PageParam param, UserFav userFav) {
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		///parm.put("tagName", userFav.getTagName());
		parm.put("orderStr", orderStr);
		List lst = userFavDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	@Override
	public String addUserFav(UserFav userFav) {
		int res = userFavDao.insert(userFav);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
		
	}

	@Override
	public String updateUserFav(UserFav userFav) {
		
		if(super.updateByPrimaryKeyCache(userFav,userFav.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
		
	}

	@Override
	public boolean deleteUserFavs(String[] ids) {
		//等待删除的对象集合
				int count = 0;
				for(String id:ids){
					if(StringUtils.isNotBlank(id)){
						String[] myids = id.split(",");
						for (String string : myids) {
							UserFav r=selectByPrimaryKey(string);
							if(r!=null){
								
								if(super.deleteByPrimaryKeyCache(string, UserFav.class))count++;
							}
						}
					}
				}
				return count>0;
	}
	
	
	
	@Override
	public UserFav selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, UserFav.class);
	}

	@Override
	public UserFav selectById(String id) {
		return userFavDao.selectByPrimaryKey(id);
	}
}