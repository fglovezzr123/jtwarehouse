package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.RewardClassDao;
import com.wing.socialcontact.service.wx.dao.RewardDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IRewardClassService;
import com.wing.socialcontact.service.wx.bean.RewardClass;

/**
 * 
 * @author zhangfan
 * @date 2017-06-12 13:25:02
 * @version 1.0
 */
@Service
public class RewardClassServiceImpl extends BaseServiceImpl<RewardClass> implements IRewardClassService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:RewardClass:\" + ";
	
	@Resource
	private RewardClassDao rewardClassDao;
	@Resource
	private RewardDao rewardDao;

	@Override
	public List selectAllClass(Integer queryRows,Integer isRecommend) {
		Map parm = new HashMap();
		parm.put("queryRows", queryRows);
		parm.put("isRecommend", isRecommend);
		return rewardClassDao.selectAllClassMap(parm);
	}

	@Override
	public DataGrid selectClass(PageParam param, RewardClass rClass) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("className", rClass.getClassName());
		parm.put("orderStr", orderStr);
		List lst = rewardClassDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addClass(RewardClass rClass) {
		RewardClass parm = new RewardClass();
		parm.setClassName(rClass.getClassName());
		List lst = rewardClassDao.select(parm);
		if(lst.size()==0){
			int res = rewardClassDao.insert(rClass);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.rewardClass.unique";//名称已存在
		}
	}

	@Override
	public String updateClass(RewardClass rClass) {
		RewardClass parm = new RewardClass();
		parm.setClassName(rClass.getClassName());
		RewardClass obj = rewardClassDao.selectOne(parm);
		if(obj==null || obj.getId().equals(rClass.getId())){
			if(super.updateByPrimaryKeyCache(rClass,rClass.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.rewardClass.unique";//此名称已存在
		}
	}

	@Override
	public boolean deleteClass(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					RewardClass r=selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, RewardClass.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public RewardClass selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, RewardClass.class);
	}

	@Override
	public RewardClass selectById(String id) {
		return rewardClassDao.selectByPrimaryKey(id);
	}

	@Override
	public String deleteClass(String id) {
		Map parm = new HashMap();
		parm.put("voType", id);
		List lst = rewardDao.selectByParam(parm);
		if(lst!=null&&lst.size()>0){
			return MsgConfig.MSG_KEY_ERROR_NODEL;
		}
		RewardClass nc = selectByPrimaryKey(id);
		if(nc!=null){
			if(super.deleteByPrimaryKeyCache(nc.getId(), RewardClass.class)){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return MsgConfig.MSG_KEY_NODATA;
		}
	}

}