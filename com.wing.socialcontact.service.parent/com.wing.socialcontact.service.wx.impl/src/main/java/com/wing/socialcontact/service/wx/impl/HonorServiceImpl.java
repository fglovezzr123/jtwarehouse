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
import com.wing.socialcontact.service.wx.dao.HonorDao;
import com.wing.socialcontact.service.wx.dao.HonorDao;
import com.wing.socialcontact.service.wx.api.IHonorService;
import com.wing.socialcontact.service.wx.bean.Honor;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author gaojun
 * @date 2017-04-14 22:34:35
 * @version 1.0
 */
@Service
public class HonorServiceImpl  extends BaseServiceImpl<Honor>  implements IHonorService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:honor:\" + ";
	
	@Resource
	private HonorDao honorDao;
	

	@Override
	public DataGrid selectAllHonor(PageParam param, Honor honor,String startTime,String endTime) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("orderStr", orderStr);
		parm.put("title", honor.getTitle());
		parm.put("hornorType", honor.getHornorType());
		parm.put("startTime", startTime);
		parm.put("endTime", endTime);
		List lst = honorDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	
	@Override
	public List selectAllHonor(Honor honor){
		List lst = honorDao.selectAllHonor(honor);
		return lst;
	}

	@Override
	public String addHonor(Honor honor) {
		int res = honorDao.insert(honor);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateHonor(Honor honor) {
		if(super.updateByPrimaryKeyCache(honor,honor.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteHonor(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					Honor r=selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, Honor.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public Honor selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Honor.class);
	}

	@Override
	public Honor selectById(String id) {
		return honorDao.selectByPrimaryKey(id);
	}

}