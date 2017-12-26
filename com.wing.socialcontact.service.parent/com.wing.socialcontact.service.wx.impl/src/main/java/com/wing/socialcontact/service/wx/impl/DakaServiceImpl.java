package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
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
import com.wing.socialcontact.service.wx.api.IDaKaService;
import com.wing.socialcontact.service.wx.bean.Banner;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.dao.TjyUserDao;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-07 01:41:07
 * @version 1.0
 */
@Service
public class DakaServiceImpl implements IDaKaService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:TjyDaKa:\" + ";
	@Resource
	private TjyUserDao tjyUserDao;

	@Override
	public DataGrid queryUserListByparam(PageParam param,int isDk, String nickname,String job,String industry){
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("isDk", isDk);
		parm.put("nickname", nickname);
		parm.put("job", job);
		parm.put("industry", industry);
		List<Map<String,Object>> lst=tjyUserDao.getUserListByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public boolean deleteDakas(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					TjyUser tjyUser=tjyUserDao.loadById(string);
					if(tjyUser!=null){
						TjyUser tjyUserUpdate = new TjyUser();
						tjyUserUpdate.setId(tjyUser.getId());
						tjyUserUpdate.setAppSynMsgToTjy(tjyUser.getAppSynMsgToTjy());
						tjyUserUpdate.setIsdk(0);
						tjyUserDao.update(tjyUserUpdate);
						count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public String addDaks(String[] ids) {
		if(ids == null || ids.length == 0){
			return MsgConfig.MSG_KEY_FAIL;
		}
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					TjyUser tjyUser=tjyUserDao.loadById(string);
					if(tjyUser!=null){
						TjyUser tjyUserUpdate = new TjyUser();
						tjyUserUpdate.setId(tjyUser.getId());
						tjyUserUpdate.setIsdk(1);
						tjyUserUpdate.setDkDate(new Date());
						tjyUserUpdate.setAppSynMsgToTjy(tjyUser.getAppSynMsgToTjy());
						tjyUserUpdate.setSort(0);
						tjyUserDao.update(tjyUserUpdate);
					}
				}
			}
		}
		
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public TjyUser loadById(String id) {
		// TODO Auto-generated method stub
		return tjyUserDao.loadById(id);
	}

	@Override
	public String updatesort(String id, int sort) {
		TjyUser tjyUser=tjyUserDao.loadById(id);
		if(tjyUser!=null){
			TjyUser tjyUserUpdate = new TjyUser();
			tjyUserUpdate.setId(tjyUser.getId());
			tjyUserUpdate.setSort(sort);
			tjyUserUpdate.setAppSynMsgToTjy(tjyUser.getAppSynMsgToTjy());
			tjyUserDao.update(tjyUserUpdate);
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public DataGrid queryZtdhUserListByparam(PageParam param, int isztdh,
			String nickname, String job, String industry,String level, String comname, String place) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("isztdh", isztdh);
		parm.put("nickname", nickname);
		parm.put("job", job);
		parm.put("industry", industry);
		parm.put("level", level);
		parm.put("comname", comname);
		parm.put("place", place);
		List<Map<String,Object>> lst=tjyUserDao.getUserListByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addZtdh(String[] ids) {
		if(ids == null || ids.length == 0){
			return MsgConfig.MSG_KEY_FAIL;
		}
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					TjyUser tjyUser=tjyUserDao.loadById(string);
					if(tjyUser!=null){
						TjyUser tjyUserUpdate = new TjyUser();
						tjyUserUpdate.setId(tjyUser.getId());
						tjyUserUpdate.setIsztdh(1);
						tjyUserUpdate.setAppSynMsgToTjy(tjyUser.getAppSynMsgToTjy());
						tjyUserDao.update(tjyUserUpdate);
					}
				}
			}
		}
		
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public boolean deleteZtdhs(String[] ids) {
		//等待删除的对象集合
				int count = 0;
				for(String id:ids){
					if(StringUtils.isNotBlank(id)){
						String[] myids = id.split(",");
						for (String string : myids) {
							TjyUser tjyUser=tjyUserDao.loadById(string);
							if(tjyUser!=null){
								TjyUser tjyUserUpdate = new TjyUser();
								tjyUserUpdate.setId(tjyUser.getId());
								tjyUserUpdate.setIsztdh(0);
								tjyUserDao.update(tjyUserUpdate);
								count++;
							}
						}
					}
				}
				return count>0;
	}

	@Override
	public String updateztdhsort(String id, int sort) {
		TjyUser tjyUser=tjyUserDao.loadById(id);
		if(tjyUser!=null){
			TjyUser tjyUserUpdate = new TjyUser();
			Integer dksort = tjyUser.getSort();
			if(dksort==null){
				tjyUserUpdate.setSort(0);
			}
			tjyUserUpdate.setId(tjyUser.getId());
			tjyUserUpdate.setZtdhsort(sort);
			tjyUserUpdate.setAppSynMsgToTjy(tjyUser.getAppSynMsgToTjy());
			tjyUserDao.update(tjyUserUpdate);
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}
	

}