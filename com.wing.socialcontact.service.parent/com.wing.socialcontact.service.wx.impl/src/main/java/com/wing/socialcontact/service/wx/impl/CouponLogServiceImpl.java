package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.CouponLogDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ICouponLogService;
import com.wing.socialcontact.service.wx.bean.CouponLog;

/**
 * 
 * @author zhangfan
 * @date 2017-07-20 11:03:44
 * @version 1.0
 */
@Service
public class CouponLogServiceImpl extends BaseServiceImpl<CouponLog> implements ICouponLogService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name =  "\"DB:CouponLog:\" + ";
	
	@Resource
	private CouponLogDao couponLogDao;

	@Override
	public DataGrid selectAllCL(PageParam param, CouponLog cl, String keyword, String couponName) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("fkId", cl.getFkId());
		parm.put("keyword", keyword);
		parm.put("couponName", couponName);
		parm.put("couponCode", cl.getCouponCode());
		parm.put("useStatus", cl.getUseStatus());
		parm.put("orderStr", orderStr);
		List lst = couponLogDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addCL(CouponLog cl) {
		int res = couponLogDao.insert(cl);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateCL(CouponLog cl) {
		if(super.updateByPrimaryKeyCache(cl,cl.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteCLs(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					CouponLog r = selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, CouponLog.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public CouponLog selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, CouponLog.class);
	}

	@Override
	public CouponLog selectById(String id) {
		return couponLogDao.selectByPrimaryKey(id);
	}

	@Override
	public List selectCanUseCoupon(String curruserId, Integer useStatus, String useRange, String orderMinBuy,Integer couponCoinType) {
		Map parm = new HashMap();
		parm.put("curruserId", curruserId);
		parm.put("useStatus", useStatus);
		parm.put("useRange", useRange);
		parm.put("orderMinBuy", orderMinBuy);
		parm.put("couponCoinType", couponCoinType);
		List lst = couponLogDao.selectByParam(parm);
		return lst;
	}

	@Override
	public List selMyCouponList(String curruserId, Integer type,Integer page, Integer size) {
		Map parm = new HashMap();
		parm.put("curruserId", curruserId);
		parm.put("type", type);
		if(page!=null&&size!=null){
			parm.put("start", (page-1)*size);
			parm.put("size", size);
		}
		List lst = couponLogDao.selectFrontByParam(parm);
		return lst;
	}

	/**
	 * 查询用户单张优惠券是否可用
	 * @param couponlogid  主键id
	 * @param userId  用户id
	 * @param price  支付金额
	 * @param useRange  使用位置    1全平台，2会议，3活动，4互助商城
	 * @param currency  币种   1:J币     2:RMB 
	 */
	@Override
	public boolean checkCanUse(String couponlogid, String userId, Double price, Integer useRange, Integer currency) {
		Map parm = new HashMap();
		parm.put("curruserId", userId);
		parm.put("id", couponlogid);
		parm.put("orderMinBuy", price);
		parm.put("useRange", useRange);
		parm.put("couponCoinType", currency);
		parm.put("useStatus", 2);//未使用
		List l = couponLogDao.selectByParam(parm);
		if(l.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public List<Map> selectMyCoupons(String userId, Integer type, Integer page, Integer size) {
		PageHelper.startPage(page, size);
		Map map = new HashMap();
		map.put("usetId", userId);
		map.put("type", type);
		return couponLogDao.selectMyCoupons(map);
	}

}