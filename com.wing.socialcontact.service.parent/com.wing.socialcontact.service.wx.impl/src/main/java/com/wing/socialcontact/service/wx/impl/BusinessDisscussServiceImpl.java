package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.BusinessDisscussDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IBusinessDisscussService;
import com.wing.socialcontact.service.wx.bean.BusinessDisscuss;

/**
 * 
 * @author zhangfan
 * @date 2017-04-21 10:59:49
 * @version 1.0
 */
@Service
public class BusinessDisscussServiceImpl extends BaseServiceImpl<BusinessDisscuss> implements IBusinessDisscussService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:BusinessDisscuss:\" + ";
	
	@Resource
	private BusinessDisscussDao businessDisscussDao;

	@Override
	public DataGrid selectAllBD(PageParam param, BusinessDisscuss bd, String titles,String startTime, String endTime) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("fkId", bd.getFkId());
		parm.put("titles", titles);
		parm.put("isShow",bd.getIsShow());
		parm.put("startTime", startTime);
		parm.put("endTime", endTime);
		parm.put("orderStr", orderStr);
		List lst = businessDisscussDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addBD(BusinessDisscuss bd) {
		int res = businessDisscussDao.insert(bd);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateBD(BusinessDisscuss bd) {
		if(super.updateByPrimaryKeyCache(bd,bd.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteBDs(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					BusinessDisscuss r = selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, BusinessDisscuss.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public BusinessDisscuss selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, BusinessDisscuss.class);
	}

	@Override
	public BusinessDisscuss selectById(String id) {
		return businessDisscussDao.selectByPrimaryKey(id);
	}

	@Override
	public List selectFrontBD(Integer queryRows, String titles, String bizType, Integer isRecommend) {
		return null;
	}

	@Override
	public Map<String, Object> selectBDById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = null;
		list = businessDisscussDao.selectBDById(id);
		if(list!=null&&list.size()>0){
			map = (Map<String, Object>) list.get(0);
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> selectBDByFkId(String fkId,Integer isAccept) {
		Map parm = new HashMap();
		parm.put("fkId", fkId);
		parm.put("isAccept", isAccept);
		List<Map<String, Object>> list = businessDisscussDao.selectBDByParam(parm);
		return list;
	}

	@Override
	public List selectMyBD(Integer page, Integer size, String curruserId) {
		Map parm = new HashMap();
		parm.put("curruserId", curruserId);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		List lst = businessDisscussDao.selectFrontByParam(parm);
		return lst;
	}

}