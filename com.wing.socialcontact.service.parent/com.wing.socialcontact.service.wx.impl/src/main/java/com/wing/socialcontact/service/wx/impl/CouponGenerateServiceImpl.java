package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.CouponGenerateDao;
import com.wing.socialcontact.service.wx.dao.CouponLogDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ICouponGenerateService;
import com.wing.socialcontact.service.wx.bean.CouponGenerate;

/**
 * 
 * @author zhangfan
 * @date 2017-07-20 11:00:18
 * @version 1.0
 */
@Service
public class CouponGenerateServiceImpl extends BaseServiceImpl<CouponGenerate> implements ICouponGenerateService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name =  "\"DB:CouponGenerate:\" + ";
	
	@Resource
	private CouponGenerateDao couponGenerateDao;
	@Resource
	private CouponLogDao couponLogDao;

	@Override
	public DataGrid selectAllCG(PageParam param, CouponGenerate cg, String startTime, String endTime,
			String couponName) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("fkId", cg.getFkId());
		parm.put("batchNum", cg.getBatchNum());
		parm.put("couponName", couponName);
		parm.put("startTime", startTime);
		parm.put("endTime", endTime);
		parm.put("orderStr", orderStr);
		List lst = couponGenerateDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addCG(CouponGenerate cg) {
		int res = couponGenerateDao.insert(cg);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateCG(CouponGenerate cg) {
		if(super.updateByPrimaryKeyCache(cg,cg.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteCGs(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					CouponGenerate r = selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, CouponGenerate.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public CouponGenerate selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, CouponGenerate.class);
	}

	@Override
	public CouponGenerate selectById(String id) {
		return couponGenerateDao.selectByPrimaryKey(id);
	}

	@Override
	public String deleteCG(String id) {
		Map parm = new HashMap();
		parm.put("fkId", id);
		List lst = couponLogDao.selectByParam(parm);
		if(lst!=null&&lst.size()>0){
			return MsgConfig.MSG_KEY_HASCOUPONLOG;
		}
		CouponGenerate nc = selectByPrimaryKey(id);
		if(nc!=null){
			if(super.deleteByPrimaryKeyCache(nc.getId(), CouponGenerate.class)){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return MsgConfig.MSG_KEY_NODATA;
		}
	}
	

}