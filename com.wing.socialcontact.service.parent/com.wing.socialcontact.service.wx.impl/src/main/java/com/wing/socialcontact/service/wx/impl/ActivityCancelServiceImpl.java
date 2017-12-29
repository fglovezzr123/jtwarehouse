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
import com.wing.socialcontact.service.wx.api.IActivityCancelService;
import com.wing.socialcontact.service.wx.bean.ActivityCancel;
import com.wing.socialcontact.service.wx.dao.ActivityCancelDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月15日 上午11:01:07
 */
@Service
public class ActivityCancelServiceImpl extends BaseServiceImpl<ActivityCancel> implements
		IActivityCancelService {

	@Resource
	private ActivityCancelDao	activityCancelDao;

	@Override
	public String addCancel(ActivityCancel dto) {
		int res = activityCancelDao.insert(dto);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public List<ActivityCancel> selectbyTerm(ActivityCancel parm) {
		
		return activityCancelDao.select(parm);
	}

	@Override
	public String updateActivityCancel(ActivityCancel dto) {
		
		boolean res =super.updateByPrimaryKeyCache(dto, dto.getId());
		if(res ){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public Object selectList(PageParam param, ActivityCancel dto,String userName,String titles) {
		DataGrid data=new DataGrid();
        
        String orderStr = param.getOrderByString();
        PageHelper.startPage(param.getPage(), param.getRows());
        Map parm = new HashMap();
        parm.put("createTime",dto.getCreateTime() );
        parm.put("userName", userName);
        parm.put("titles", titles);
        List lst = activityCancelDao.selectList(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
	}
}
