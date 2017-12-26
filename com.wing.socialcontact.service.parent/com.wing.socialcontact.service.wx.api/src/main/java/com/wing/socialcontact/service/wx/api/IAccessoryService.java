package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Accessory;

/**
 * 
 * @author liangwj
 * @date 2017-03-27 11:24:18
 * @version 1.0
 */
public interface IAccessoryService{

	/**
	 * 查询出所有分类
	 * @return
	 */
	public List selectAllAccessory();
	/**
	 * 条件查询
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectAccessorys(PageParam param,Accessory accessory);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addAccessory(Accessory accessory);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateAccessory(Accessory accessory);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteAccessorys(String[] ids);
	

	public Accessory selectByPrimaryKey(String key);
	
	public Accessory selectById(String id);
	
	public void deleteAccessoryByTerm(Accessory parm);
	
	public List<Accessory> selectByTerm(Accessory parm);
	

}