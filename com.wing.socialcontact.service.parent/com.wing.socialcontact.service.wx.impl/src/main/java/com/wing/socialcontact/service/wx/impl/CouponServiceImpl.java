package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.CouponDao;
import com.wing.socialcontact.service.wx.dao.CouponGenerateDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ICouponService;
import com.wing.socialcontact.service.wx.bean.Coupon;
import com.wing.socialcontact.service.wx.bean.CouponGenerate;

/**
 * 
 * @author zhangfan
 * @date 2017-07-20 10:24:15
 * @version 1.0
 */
@Service
public class CouponServiceImpl extends BaseServiceImpl<Coupon> implements ICouponService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:Coupon:\" + ";
	
	@Resource
	private CouponDao couponDao;
	@Resource
	private CouponGenerateDao couponGenerateDao;

	@Override
	public DataGrid selectAllCoupon(PageParam param, Coupon coupon, String startTime, String endTime) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("couponName", coupon.getCouponName());
		parm.put("couponCoinType", coupon.getCouponCoinType());
		parm.put("startTime", startTime);
		parm.put("endTime", endTime);
		parm.put("orderStr", orderStr);
		List lst = couponDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addCoupon(Coupon coupon) {
		int res = couponDao.insert(coupon);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateCoupon(Coupon coupon) {
		if(super.updateByPrimaryKeyCache(coupon,coupon.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteCoupons(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					Coupon r = selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, Coupon.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public Coupon selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Coupon.class);
	}

	@Override
	public Coupon selectById(String id) {
		return couponDao.selectByPrimaryKey(id);
	}

	@Override
	public Map<String, Object> selectCouponById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataGrid selectAllStore(PageParam param,String storeName) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("storeName",storeName);
		List lst = couponDao.selectAllStore(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	@Override
	public String deleteCoupon(String id) {
		Map parm = new HashMap();
		parm.put("fkId", id);
		List lst = couponGenerateDao.selectByParam(parm);
		if(lst!=null&&lst.size()>0){
			return MsgConfig.MSG_KEY_HASCOUPONCG;
		}
		Coupon nc = selectByPrimaryKey(id);
		if(nc!=null){
			if(super.deleteByPrimaryKeyCache(nc.getId(), Coupon.class)){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return MsgConfig.MSG_KEY_NODATA;
		}
	}

}