package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor.LEMON_CHIFFON;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.dao.LeaveMsgDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.service.wx.api.ILeaveMsgService;
import com.wing.socialcontact.service.wx.bean.LeaveMsg;
import com.wing.socialcontact.service.wx.bean.LeaveMsg;
import com.wing.socialcontact.service.wx.bean.TjyUser;

/**
 * 
 * @author zengmin
 * @date 2017-04-28 23:41:19
 * @version 1.0
 */
@Service
public class LeaveMsgServiceImpl extends BaseServiceImpl<LeaveMsg> implements ILeaveMsgService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:LeaveMsg:\" + ";

	@Resource
	private LeaveMsgDao leaveMsgDao;
	
	

	@Override
	public DataGrid selectAllLeaveMsg(PageParam param, String nickname,
			LeaveMsg leaveMsg) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("nickname", nickname);
		parm.put("type", leaveMsg.getType());//反馈类型 (1:im 2:个人中心3：企服云app)
		parm.put("source", leaveMsg.getSource());//来源 （1：微信企业号 2：企服云app）
		List lst = leaveMsgDao.selectLeaveMsgs(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	
	

	@Override
	public boolean addLeaveMsg(LeaveMsg leaveMsg) {
		int res = leaveMsgDao.insert(leaveMsg);
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public LeaveMsg selectById(String id) {
		return leaveMsgDao.selectByPrimaryKey(id);
	}

	@Override
	public LeaveMsg selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, LeaveMsg.class);
	}

	@Override
	public boolean deleteMsgById(String id) {
		int c = leaveMsgDao.deleteByPrimaryKey(id);
		if (c > 0) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public boolean deleteMsgs(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					LeaveMsg leaveMsg=leaveMsgDao.selectByPrimaryKey(string);
					if(leaveMsg!=null){
						leaveMsg.setDeleted(1);
						leaveMsgDao.updateByPrimaryKey(leaveMsg);
						count++;
					}
				}
			}
		}
		return count>0;
	}



	

}