package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.BusinessClassDao;
import com.wing.socialcontact.service.wx.dao.BusinessDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IBusinessClassService;
import com.wing.socialcontact.service.wx.bean.BusinessClass;

/**
 * 
 * @author zhangfan
 * @date 2017-04-18 21:18:06
 * @version 1.0
 */
@Service
public class BusinessClassServiceImpl extends BaseServiceImpl<BusinessClass> implements IBusinessClassService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:BusinessClass:\" + ";
	
	@Resource
	private BusinessClassDao businessClassDao;
	@Resource
	private BusinessDao businessDao;

	@Override
	public List selectAllClass(Integer queryRows,Integer isRecommend) {
		Map parm = new HashMap();
		parm.put("queryRows", queryRows);
		parm.put("isRecommend", isRecommend);
		return businessClassDao.selectAllClassMap(parm);
	}

	@Override
	public DataGrid selectClasses(PageParam param, BusinessClass bclass) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("className", bclass.getClassName());
		parm.put("orderStr", orderStr);
		List lst = businessClassDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addClass(BusinessClass bclass) {
		BusinessClass parm = new BusinessClass();
		parm.setClassName(bclass.getClassName());
		List lst = businessClassDao.select(parm);
		if(lst.size()==0){
			int res = businessClassDao.insert(bclass);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.businessClass.unique";//名称已存在
		}
	}

	@Override
	public String updateClass(BusinessClass bclass) {
		BusinessClass parm = new BusinessClass();
		parm.setClassName(bclass.getClassName());
		BusinessClass obj = businessClassDao.selectOne(parm);
		if(obj==null || obj.getId().equals(bclass.getId())){
			if(super.updateByPrimaryKeyCache(bclass,bclass.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.businessClass.unique";//此名称已存在
		}
	}

	@Override
	public boolean deleteClasses(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					BusinessClass r=selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, BusinessClass.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public BusinessClass selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, BusinessClass.class);
	}

	@Override
	public BusinessClass selectById(String id) {
		return businessClassDao.selectByPrimaryKey(id);
	}

	@Override
	public String deleteClass(String id) {
		Map parm = new HashMap();
		parm.put("bizType", id);
		List lst = businessDao.selectByParam(parm);
		if(lst!=null&&lst.size()>0){
			return MsgConfig.MSG_KEY_ERROR_NODEL;
		}
		BusinessClass nc = selectByPrimaryKey(id);
		if(nc!=null){
			if(super.deleteByPrimaryKeyCache(nc.getId(), BusinessClass.class)){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return MsgConfig.MSG_KEY_NODATA;
		}
	}

}