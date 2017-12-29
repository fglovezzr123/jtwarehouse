package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.BusinessDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IBusinessService;
import com.wing.socialcontact.service.wx.bean.Business;

/**
 * 
 * @author zhangfan
 * @date 2017-04-18 12:01:49
 * @version 1.0
 */
@Service
public class BusinessServiceImpl extends BaseServiceImpl<Business> implements IBusinessService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name =  "\"DB:Business:\" + ";
	
	@Resource
	private BusinessDao businessDao;

	@Override
	public DataGrid selectAllBusiness(PageParam param, Business business, String startTime, String endTime,String createUserId,String userId) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("titles", business.getTitles());
		parm.put("bizType", business.getBizType());
		parm.put("status", business.getStatus());
		parm.put("startTime", startTime);
		parm.put("createUserId", createUserId);
		parm.put("endTime", endTime);
		parm.put("orderStr", orderStr);
		List lst = businessDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	/*我关注的合作*/
	@Override
	public DataGrid selectAllBusiness2(PageParam param, Business business, String startTime, String endTime,String createUserId,String userId) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("titles", business.getTitles());
		parm.put("bizType", business.getBizType());
		parm.put("status", business.getStatus());
		parm.put("startTime", startTime);
		parm.put("createUserId", createUserId);
		parm.put("endTime", endTime);
		parm.put("userId", userId);
		parm.put("orderStr", orderStr);
		List lst = businessDao.selectByParam2(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addBusiness(Business business) {
		int res = businessDao.insert(business);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateBusiness(Business business) {
		if(super.updateByPrimaryKeyCache(business,business.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteBusiness(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					Business r = selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, Business.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public Business selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Business.class);
	}

	@Override
	public Business selectById(String id) {
		return businessDao.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateStatus(String[] ids) {
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					Business r = selectById(string);
					if(r!=null){
						r.setStatus(3);	
						updateBusiness(r);
						count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public List selectFrontBusiness(Integer page,Integer size, String titles,
			String bizType,Integer isRecommend,String createUserId,Integer isReward,String curruserId) {
		Map parm = new HashMap();
		parm.put("titles", titles);
		parm.put("bizType", bizType);
		if(page!=null&&size!=null){
			parm.put("start", (page-1)*size);
			parm.put("size", size);
		}
		parm.put("isRecommend", isRecommend);
		parm.put("createUserId", createUserId);
		parm.put("isReward", isReward);
		parm.put("curruserId", curruserId);
		List lst = businessDao.selectFrontByParam(parm);
		return lst;
	}

	@Override
	public Map<String, Object> selectBusinessById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = null;
		list = businessDao.selectBusinessById(id);
		if(list!=null&&list.size()>0){
			map = (Map<String, Object>) list.get(0);
		}
		return map;
	}

	@Override
	public List selectMyBusiness(String createUserId,Integer page,Integer size) {
		Map parm = new HashMap();
		parm.put("createUserId", createUserId);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		List lst = businessDao.selectByParam(parm);
		return lst;
	}

	@Override
	public List selectMyAttention(String userId,Integer page,Integer size) {
		Map parm = new HashMap();
		parm.put("userId", userId);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		List lst = businessDao.selectMyAttention(parm);
		return lst;
	}

	@Override
	public List selectPastBusiness() {
		return businessDao.selectPastBusiness();
	}

	
}