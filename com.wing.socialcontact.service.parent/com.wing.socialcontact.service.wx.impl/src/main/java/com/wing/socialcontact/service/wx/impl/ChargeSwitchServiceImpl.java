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
import com.wing.socialcontact.service.wx.api.IChargeSwitchService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.ChargeSwitch;
import com.wing.socialcontact.service.wx.bean.OpenScreenAdvertis;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.service.wx.dao.ChargeSwitchDao;
import com.wing.socialcontact.service.wx.dao.OpenScreenAdvertisDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class ChargeSwitchServiceImpl extends BaseServiceImpl<ChargeSwitch> implements IChargeSwitchService
{
	@Resource
	private ChargeSwitchDao chargeSwitchDao;
	@Resource
	private IWxUserService wxUserService;
	@Resource
	private ITjyUserService tjyUserService;

	@Override
	public boolean deleteChargeSwitch(String id)
	{
		return super.deleteByPrimaryKeyCache(id, ChargeSwitch.class);
	}

	@Override
	public List selectChargeSwitchList()
	{
		return chargeSwitchDao.selectChargeSwitchList();
	}

	@Override
	public DataGrid selectAllChargeSwitch(PageParam param, ChargeSwitch chargeSwitch)
	{
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		if(null != chargeSwitch.getStatus()){
			parm.put("status", chargeSwitch.getStatus());
		}
		if (StringUtils.isNotEmpty(orderStr)) {
			parm.put("orderby", orderStr);
		} else {
			parm.put("orderby", "order_num asc");
		}
		List lst = chargeSwitchDao.query(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addChargeSwitch(ChargeSwitch chargeSwitch)
	{
		int res = chargeSwitchDao.insert(chargeSwitch);
		if (res > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateChargeSwitch(ChargeSwitch chargeSwitch)
	{
		if (super.updateByPrimaryKeyCache(chargeSwitch, chargeSwitch.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public ChargeSwitch selectByPrimaryKey(String key)
	{
		return super.selectByPrimaryKeyCache(key, ChargeSwitch.class);
	}

	@Override
	public ChargeSwitch selectById(String id)
	{
		return chargeSwitchDao.selectByPrimaryKey(id);
	}

	@Override
	public ChargeSwitch selectChargeSwitchByUserId(String userId)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", 1);
		param.put("orderby", "order_num asc");
		List<ChargeSwitch> lst = chargeSwitchDao.query(param);
		if (null == lst || lst.isEmpty()) {
			return null;
		}
		WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		if (null == tjyUser) {
			return null;
		}
		
		return null;
	}

	@Override
	public ChargeSwitch getChargeSwitchBySign(String sign)
	{
		Map paramMap = new HashMap();
		paramMap.put("sign", sign);
		return chargeSwitchDao.getChargeSwitchBySign(paramMap);
	}

}
