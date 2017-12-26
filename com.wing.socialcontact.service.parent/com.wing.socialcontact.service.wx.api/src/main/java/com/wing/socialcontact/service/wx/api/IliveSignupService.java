package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.TjyLiveSignup;

public interface IliveSignupService {

	DataGrid selectAllLiveuser(PageParam param, String id, TjyLiveSignup t);

	int usersignupedorno(String userId, String id);

	void addSignup(TjyLiveSignup signup);

	List<Map> selectmysignups(String userId, Integer type, Integer page,
			Integer size);

	List<TjyLiveSignup> getunremindsignups();

	void update(TjyLiveSignup s);

}
