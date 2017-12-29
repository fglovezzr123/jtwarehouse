package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Report;
import com.wing.socialcontact.service.wx.bean.TjyUser;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-07 01:41:07
 * @version 1.0
 */
public interface IDaKaService {

	/**
	 * 获取大咖列表
	 * @param param
	 * @param nickname
	 * @return
	 */
	public DataGrid queryUserListByparam(PageParam param,int isDk, String nickname,String job,String industry);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteDakas(String[] ids);
	
	/**
	 * 添加
	 * @param report
	 * @return
	 */
	public String addDaks(String[] ids);

	public TjyUser loadById(String id);

	public String updatesort(String id, int sort);

	public Object queryZtdhUserListByparam(PageParam param, int parseInt,
			String nickname, String job, String industry, String level, String comname, String place);

	public String addZtdh(String[] ids);

	public boolean deleteZtdhs(String[] ids);

	public String updateztdhsort(String id, int sort);

}