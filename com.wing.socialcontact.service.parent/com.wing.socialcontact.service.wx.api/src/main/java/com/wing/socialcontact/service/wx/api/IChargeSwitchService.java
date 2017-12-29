package com.wing.socialcontact.service.wx.api;

import java.util.List;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.ChargeSwitch;
import com.wing.socialcontact.service.wx.bean.OpenScreenAdvertis;

public interface IChargeSwitchService
{
	/**
	 * 条件查询
	 * 
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllChargeSwitch(PageParam param, ChargeSwitch chargeSwitch);

	/**
	 * 添加
	 * 
	 * @param newsClass
	 * @return
	 */
	public String addChargeSwitch(ChargeSwitch chargeSwitch);

	/**
	 * 更新
	 * 
	 * @param newsClass
	 * @return
	 */
	public String updateChargeSwitch(ChargeSwitch chargeSwitch);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteChargeSwitch(String id);

	public ChargeSwitch selectByPrimaryKey(String key);

	public ChargeSwitch selectById(String id);

	public ChargeSwitch selectChargeSwitchByUserId(String userId);
	
	public List selectChargeSwitchList();
	
	public ChargeSwitch getChargeSwitchBySign(String sign);
}
