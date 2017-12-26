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
import com.wing.socialcontact.service.wx.api.IActivityRefundService;
import com.wing.socialcontact.service.wx.bean.ActivityRefund;
import com.wing.socialcontact.service.wx.dao.ActivityRefundDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月4日 下午4:38:31
 */
@Service
public class ActivityRefundServiceImpl  extends BaseServiceImpl<ActivityRefund> implements IActivityRefundService {

	private static final String cache_name = "\"DB:ActivityRefund:\" +";
	
	@Resource
	private ActivityRefundDao	activityRefundDao;
	
	/**
	 * 添加退款记录
	 */
	@Override
	public boolean insertRefund(ActivityRefund refund) {
		// TODO Auto-generated method stub
		int i =  activityRefundDao.insert(refund);
		if(i>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 查询需要退款的列表
	 */
	@Override
	public List<ActivityRefund> selectNoRefundList() {
		// TODO Auto-generated method stub
		return activityRefundDao.selectNoRefundList();
	}

	@Override
	public Object selectList(PageParam param, ActivityRefund dto, String titles) {
		DataGrid data=new DataGrid();
        
        String orderStr = param.getOrderByString();
        PageHelper.startPage(param.getPage(), param.getRows());
        Map parm = new HashMap();
        //parm.put("createTime",dto.getCreateTime() );
        //parm.put("userName", userName);
        parm.put("titles", titles);
        List lst = activityRefundDao.selectList(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
	}

	@Override
	public void updateRefund(ActivityRefund activityRefund) {
		activityRefundDao.updateByPrimaryKey(activityRefund);
		
	}

}
