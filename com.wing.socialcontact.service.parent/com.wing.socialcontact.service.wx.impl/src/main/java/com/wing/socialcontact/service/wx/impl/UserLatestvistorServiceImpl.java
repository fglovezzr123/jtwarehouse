package com.wing.socialcontact.service.wx.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IUserLatestvistorService;
import com.wing.socialcontact.service.wx.bean.UserLatestvistor;
import com.wing.socialcontact.service.wx.dao.UserLatestvistorDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author gaojun
 * @date 2017-05-02 11:14:41
 * @version 1.0
 */
@Service
public class UserLatestvistorServiceImpl  extends BaseServiceImpl<UserLatestvistor> implements IUserLatestvistorService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:userLatestvistor:\" + ";
	
	@Resource
	private UserLatestvistorDao userLatestvistorDao;
	
	@Override
	public List selectLatestVistors(String userId, String vistorUserId, int pageNum, int pageSize) {
		Page page = null;
		if(pageNum != 0 && pageSize !=0){
			page = PageHelper.startPage(pageNum, pageSize,true);
		}
		UserLatestvistor ul = new UserLatestvistor();
		if(StringUtils.isNotBlank(userId)){
			ul.setUserId(userId);
		}
		if(StringUtils.isNotBlank(vistorUserId)){
			ul.setVistorUserId(vistorUserId);
		}
		List list  = userLatestvistorDao.selectLatestVistors(ul);
		if(pageNum != 0 && pageSize !=0){
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if(pageSize != 0){
				totalPageCount = (int)(totalCount/pageSize);
				if(totalCount % pageSize > 0){
					totalPageCount = totalPageCount +1;
				}
				if(totalPageCount < pageNum){
					return  new ArrayList();
				}
			}
		}
		
		// TODO Auto-generated method stub
		return list;
	}
	
	@Override
	public List selectAllUserLatestvistor(UserLatestvistor userLatestvistor) {
		return userLatestvistorDao.selectLatestVistors(userLatestvistor);
	}

	@Override
	public DataGrid selectUserLatestvistors(PageParam param, UserLatestvistor userLatestvistor) {
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		///parm.put("tagName", userLatestvistor.getTagName());
		parm.put("orderStr", orderStr);
		List lst = userLatestvistorDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	@Override
	public String addUserLatestvistor(UserLatestvistor userLatestvistor) {
		userLatestvistor.setDeleted(0);
		int res = userLatestvistorDao.insert(userLatestvistor);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
		
	}

	@Override
	public String updateUserLatestvistor(UserLatestvistor userLatestvistor) {
		
		if(super.updateByPrimaryKeyCache(userLatestvistor,userLatestvistor.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
		
	}

	@Override
	public boolean deleteUserLatestvistors(String ids) {
		//等待删除的对象集合
				int count = 0;
				if(StringUtils.isNotBlank(ids)){
					String[] myids = ids.split(",");
					for (String string : myids) {
						UserLatestvistor r=selectByPrimaryKey(string);
						if(r!=null){
							
							if(super.deleteByPrimaryKeyCache(string, UserLatestvistor.class))count++;
						}
					}
				}
				return count>0;
	}
	
	
	
	@Override
	public UserLatestvistor selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, UserLatestvistor.class);
	}

	@Override
	public UserLatestvistor selectById(String id) {
		return userLatestvistorDao.selectByPrimaryKey(id);
	}

}