package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Honor;

/**
 * 
 * @author gaojun
 * @date 2017-04-14 22:34:35
 * @version 1.0
 */
public interface IHonorService{

	/**
	 * 条件查询
	 * @param param
	 * @param honor
	 * @return
	 */
	public DataGrid selectAllHonor(PageParam param,Honor honor,String startTimef,String endTimef);
	/**
	 * 查询出所有
	 * @return
	 */
	public List selectAllHonor(Honor honor);
	/**
	 * 添加
	 * @param honor
	 * @return
	 */
	public String addHonor(Honor honor);
	/**
	 * 更新
	 * @param honor
	 * @return
	 */
	public String updateHonor(Honor honor);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteHonor(String[] ids);

	public Honor selectByPrimaryKey(String key);
	
	public Honor selectById(String id);

}