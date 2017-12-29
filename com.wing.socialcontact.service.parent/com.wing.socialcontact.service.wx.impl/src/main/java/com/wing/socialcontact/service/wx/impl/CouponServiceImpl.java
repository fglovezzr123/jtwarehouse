package com.wing.socialcontact.service.wx.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.wing.socialcontact.service.wx.bean.CouponLog;
import com.wing.socialcontact.service.wx.dao.CouponLogDao;
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
import org.springframework.util.CollectionUtils;

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
	@Resource
	private CouponLogDao couponLogDao;

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
	public List<Coupon> selectCoupons(Coupon coupon) {
		return couponDao.select(coupon);
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

	@Override
	public int receiveMeetingCoupon(String userId, String couponId) {
		//查询会议券
		Coupon coupon = selectByPrimaryKey(couponId);
		//非有效的会议券不能领取
		if (coupon.getCouponType() == 4 && coupon.getState() != 1) {
			return -1;
		}
		//查询会议券生成记录
		Date now = new Date();
		CouponGenerate couponGenerate = new CouponGenerate();
		couponGenerate.setFkId(couponId);
		couponGenerate.setStartTime(now);
		couponGenerate.setEndTime(now);
		PageHelper.startPage(1, 1);
		List<CouponGenerate> couponGenerateList = couponGenerateDao.selectDuplicate(couponGenerate);
		if (!CollectionUtils.isEmpty(couponGenerateList)) {
			CouponGenerate data = couponGenerateList.get(0);
			//查询是否领取过
			CouponLog searchModel = new CouponLog();
			searchModel.setUserId(userId);
			searchModel.setFkId(data.getId());
			List<CouponLog> logList = couponLogDao.select(searchModel);
			if (!CollectionUtils.isEmpty(logList)) {
				return -2;
			}
			//生成领取记录
			CouponLog couponLog = new CouponLog();
			couponLog.setUserId(userId);
			couponLog.setFkId(data.getId());
			couponLog.setReceiveTime(now);
			couponLog.setCreateTime(now);
			couponLog.setUseStatus(2);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String code = sdf.format(now);
			Random r = new Random();
			couponLog.setCouponCode(code+r.nextInt(10000));
			couponLogDao.insert(couponLog);
			return 1;
		}
		return 0;
	}

}