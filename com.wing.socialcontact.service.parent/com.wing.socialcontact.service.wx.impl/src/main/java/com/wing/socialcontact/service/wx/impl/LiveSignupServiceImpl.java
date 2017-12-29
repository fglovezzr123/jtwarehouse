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
import com.wing.socialcontact.service.wx.api.IliveSignupService;
import com.wing.socialcontact.service.wx.bean.TjyLiveSignup;
import com.wing.socialcontact.service.wx.dao.LiveSignupDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class LiveSignupServiceImpl extends BaseServiceImpl<TjyLiveSignup> implements
		IliveSignupService {

	@Resource
	private LiveSignupDao liveSignupDao;

	@Override
	public DataGrid selectAllLiveuser(PageParam param, String id,
			TjyLiveSignup t) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("liveid", id);
		parm.put("name", t.getName());
		parm.put("mobile", t.getMobile());
		parm.put("createtime", t.getCreatetime());
		List<Map<String,Object>> lst=liveSignupDao.getuserlist(parm);

		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	@Override
	public int usersignupedorno(String userId, String id) {
		Map parm = new HashMap();
		parm.put("userid", userId);
		parm.put("liveid", id);
		List l = liveSignupDao.usersignupedorno(parm);
		if(l.size()>0){
			return 1;
		}
		return 0;
	}

	@Override
	public void addSignup(TjyLiveSignup signup) {
		liveSignupDao.insert(signup);
		
	}

	@Override
	public List<Map> selectmysignups(String userId, Integer type, Integer page,
			Integer size) {
		Map parm = new HashMap();
		parm.put("userid", userId);
		parm.put("type", type);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		List lst=liveSignupDao.selectmysignups(parm);
		return lst;
	}

	@Override
	public List<TjyLiveSignup> getunremindsignups() {
		// TODO Auto-generated method stub
		return liveSignupDao.getunremindsignups();
	}

	@Override
	public void update(TjyLiveSignup s) {
		super.updateByPrimaryKeyCache(s, s.getId());
	}
}
