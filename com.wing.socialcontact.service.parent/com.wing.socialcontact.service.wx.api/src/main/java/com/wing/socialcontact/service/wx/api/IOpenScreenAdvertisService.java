package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.OpenScreenAdvertis;

public interface IOpenScreenAdvertisService
{
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllAdvertis(PageParam param, OpenScreenAdvertis advertis);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addAdvertis(OpenScreenAdvertis advertis);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updateAdvertis(OpenScreenAdvertis advertis);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteAdvertis(String id);

	public OpenScreenAdvertis selectByPrimaryKey(String key);

	public OpenScreenAdvertis selectById(String id);

	public OpenScreenAdvertis selectAdvertisByUserId(String userId);
	
	public List selectAdvertisList();
}
