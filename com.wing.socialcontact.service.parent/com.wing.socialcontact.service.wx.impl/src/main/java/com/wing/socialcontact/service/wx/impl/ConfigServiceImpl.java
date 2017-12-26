package com.wing.socialcontact.service.wx.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IConfigService;
import com.wing.socialcontact.service.wx.bean.Config;
import com.wing.socialcontact.service.wx.bean.Config;
import com.wing.socialcontact.service.wx.dao.ConfigDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author gaojun
 * @date 2017-09-18 15:40:53
 * @version 1.0
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl<Config> implements IConfigService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:Config:\" + ";
	
	@Resource
	private ConfigDao configDao;
	
	

	@Override
	public String addConfig(Config config) {
		int res = configDao.insert(config);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateConfig(Config config) {
		if(super.updateByPrimaryKeyCache(config,config.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteConfig(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					Config r=selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, Config.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public Config selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Config.class);
	}

	@Override
	public Config selectById(String id) {
		return configDao.selectByPrimaryKey(id);
	}
	
	@Override
	public Config selectByType(String type) {
		return configDao.selectByType(type);
	}

	@Override
	public List selectAllConfig(Config config) {
		// TODO Auto-generated method stub
		return null;
	}


}