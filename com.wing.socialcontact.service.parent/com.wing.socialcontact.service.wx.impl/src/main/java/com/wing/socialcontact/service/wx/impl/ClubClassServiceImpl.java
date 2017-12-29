package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IClubClassService;
import com.wing.socialcontact.service.wx.bean.ClubClass;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;
import com.wing.socialcontact.service.wx.bean.TjyNewsClass;
import com.wing.socialcontact.service.wx.dao.ClubClassDao;
import com.wing.socialcontact.service.wx.dao.ClubDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class ClubClassServiceImpl extends BaseServiceImpl<ClubClass> implements
		IClubClassService {

	private static final String cache_name = "\"DB:ClubClass:\" + ";
	
	@Autowired
	private ClubClassDao clubClassDao;
	@Autowired
	private ClubDao clubDao;
	@Override
	public List<Map<String, Object>> selectAllclassMap(String classRoot) {
		return clubClassDao.selectAllclassMap();
	}
	/**
	 * 添加
	 */
	@Override
	public String addclubclass(ClubClass clubclass) {
		ClubClass parm = new ClubClass();
		parm.setName(clubclass.getName());
		List lst = clubClassDao.select(parm);
		if(lst.size()==0){
			int res = clubClassDao.insert(clubclass);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.newsclass.unique";//已存在
		}
	}
	/**
	 * 根据id获取
	 */
	@Override
	public ClubClass selectById(String id) {
		// TODO Auto-generated method stub
		return clubClassDao.selectByPrimaryKey(id);
	}
	/**
	 * 更新
	 */
	@Override
	public String updateclubclass(ClubClass clubclass) {
		ClubClass parm = new ClubClass();
		parm.setName(clubclass.getName());
		ClubClass obj = clubClassDao.selectOne(parm);
		if(obj==null || obj.getId().equals(clubclass.getId())){
			if(super.updateByPrimaryKeyCache(clubclass,clubclass.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.newsclass.unique";//已存在
		}
	}
	/**
	 * 删除
	 */
	@Override
	public String deleteclubclasses(String id) {
		ClubClass parm = new ClubClass();
		parm.setParentId(id);
		List lst = clubClassDao.select(parm);
		if(lst.size()>0){
			return "msg.newsClass.haschild";//存在子分类
		}else{
			Map parm1 = new HashMap();
			parm1.put("classid", id);
			List clubs = clubDao.getClubByclassid(parm1);
			if(clubs!=null&&clubs.size()>0){
				return MsgConfig.MSG_KEY_ERROR_NODEL;
			}else{
				ClubClass nc = selectByPrimaryKey(id);
				if(nc!=null){
					if(super.deleteByPrimaryKeyCache(nc.getId(), ClubClass.class)){
						return MsgConfig.MSG_KEY_SUCCESS;
					}else{
						return MsgConfig.MSG_KEY_FAIL;
					}
				}else{
					return MsgConfig.MSG_KEY_NODATA;
				}
			}
		}
	}
	/**
	 * 根据id获取
	 */
	@Override
	public ClubClass selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, ClubClass.class);
	}
	/**
	 * 查询所有分类
	 */
	@Override
	public Object selectAllClassMap() {
		return clubClassDao.selectAllclassMap();
	}
	/**
	 * 查询一级分类
	 */
	@Override
	public List<Map> selectonelevelclass() {
		// TODO Auto-generated method stub
		return clubClassDao.selectonelevelclass();
	}
	/**
	 * 根据一级分类id查询二级分类
	 */
	@Override
	public List<Map> querybyparent(String id) {
		Map parm = new HashMap();
		parm.put("id", id);
		return clubClassDao.querybyparent(parm);
	}
	
}
