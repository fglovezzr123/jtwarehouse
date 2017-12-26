package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.service.wx.bean.Config;

/**
 * 
 * @author gaojun
 * @date 2017-09-18 15:40:53
 * @version 1.0
 */
public interface IConfigService{
	/**
	 * 查询出所有
	 * @return
	 */
	public List selectAllConfig(Config config);
	/**
	 * 添加
	 * @param config
	 * @return
	 */
	public String addConfig(Config config);
	/**
	 * 更新
	 * @param config
	 * @return
	 */
	public String updateConfig(Config config);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteConfig(String[] ids);

	public Config selectByPrimaryKey(String key);
	
	public Config selectById(String id);
	
	public Config selectByType(String type);
	

}