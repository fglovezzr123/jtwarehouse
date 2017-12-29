/**  
 * @Title: DataPermissionsServiceImpl.java
 * @date 2016-10-18 下午4:06:27
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ILibraryClassService;
import com.wing.socialcontact.sys.bean.SyDataPermissions;
import com.wing.socialcontact.sys.bean.SyRole;
import com.wing.socialcontact.sys.dao.DataPermissionsDao;
import com.wing.socialcontact.sys.service.IDataPermissionsService;
import com.wing.socialcontact.sys.service.ISystemLogService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * 
 * @author	dijuli
 * @version	 1.0
 *
 */
@Service
public class DataPermissionsServiceImpl extends BaseServiceImpl<SyDataPermissions> implements IDataPermissionsService{

    private static Logger logger = LoggerFactory.getLogger(DataPermissionsServiceImpl.class);
	/**
	 * 缓存的key值
	 */

	@Resource
	private DataPermissionsDao dataPermissionsDao;
	@Resource
	private ISystemLogService systemLogServiceImpl;
	
	public SyDataPermissions updateSelectOne(SyDataPermissions per){
		SyDataPermissions parm = new SyDataPermissions();
		parm.setType(parm.getType());
		List<SyDataPermissions> lst = dataPermissionsDao.select(parm);
		SyDataPermissions old=null;
		if(lst.size()>0)old=lst.get(0);
		if(old==null){
			dataPermissionsDao.insert(per);
			return per;
		}else{
			if(!per.getName().equals(old.getName())){
				old.setName(per.getName());
				super.updateByPrimaryKeyCache(old,old.getId());
			}
		}
		return old;
		
	}

	public SyDataPermissions selectByPrimaryKey(String id){
		return super.selectByPrimaryKeyCache(id, SyDataPermissions.class);
	}

	public String updateSyDataPermissions(SyDataPermissions per){
		SyDataPermissions parm = new SyDataPermissions();
		parm.setType(per.getType());
		parm.setId(per.getId());
		List<SyDataPermissions> lst = dataPermissionsDao.select(parm);
		SyDataPermissions obj=null;
		if(lst.size()>0)obj=lst.get(0);
		
		if(obj==null){
			SyDataPermissions old=dataPermissionsDao.selectByPrimaryKey( per.getId());
			old.setDesc(per.getDesc());
			old.setRules(per.getRules());
			systemLogServiceImpl.saveLog("修改数据权限", "名称:"+old.getName()+",类型:"+old.getType());
			super.updateByPrimaryKeyCache(old,old.getId());
			
			return MsgConfig.MSG_KEY_SUCCESS;
			
		}else{
			return "msg.dataPermissions.unique";//已有此数据权限
		}
	}
	
	
	
	public String selectRules(String type){
		SyDataPermissions parm = new SyDataPermissions();
		parm.setType(type);
		List<SyDataPermissions> lst = dataPermissionsDao.select(parm);
		SyDataPermissions dp=null;
		if(lst.size()>0)dp=lst.get(0);
		
		if(dp==null){
			return null;
		}else{
			return dp.getRules();
		}
	}

	
}
