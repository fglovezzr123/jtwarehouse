package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IActivityDelayService;
import com.wing.socialcontact.service.wx.bean.ActivityDelay;
import com.wing.socialcontact.service.wx.dao.ActivityDelayDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class ActivityDelayServiceImpl extends BaseServiceImpl<ActivityDelay> implements
		IActivityDelayService {

	@Resource
	private ActivityDelayDao	activityDelayDao;

	@Override
	public List<ActivityDelay> selectbyTerm(ActivityDelay parm) {
		
		return activityDelayDao.select(parm);
	}

	@Override
	public String addDelay(ActivityDelay dto) {
		int res = activityDelayDao.insert(dto);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateActivityDelay(ActivityDelay dto) {
		// TODO Auto-generated method stub
		boolean res = super.updateByPrimaryKeyCache(dto, dto.getId());
		if(res){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public Object selectList(PageParam param, ActivityDelay dto,String userName,String titles) {
		 
	    DataGrid data=new DataGrid();
	        
        String orderStr = param.getOrderByString();
        PageHelper.startPage(param.getPage(), param.getRows());
        Map parm = new HashMap();
        parm.put("createTime",dto.getCreateTime() );
        parm.put("userName", userName);
        parm.put("titles", titles);
        
        List lst = activityDelayDao.selectList(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
	}
}
