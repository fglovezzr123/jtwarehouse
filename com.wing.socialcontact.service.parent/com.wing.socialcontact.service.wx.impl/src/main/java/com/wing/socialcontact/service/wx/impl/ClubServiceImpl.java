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
import com.wing.socialcontact.service.wx.api.IClubService;
import com.wing.socialcontact.service.wx.bean.Club;
import com.wing.socialcontact.service.wx.bean.TjyLibrary;
import com.wing.socialcontact.service.wx.dao.ClubClassDao;
import com.wing.socialcontact.service.wx.dao.ClubDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
/**
 * 
 * @author zhangzheng
 *	会所管理
 */
@Service
public class ClubServiceImpl extends BaseServiceImpl<Club> implements IClubService {

	private static final String cache_name = "\"DB:Club:\" + ";
	
	@Autowired
	private ClubClassDao clubClassDao;
	@Autowired
	private ClubDao clubDao;
	/**
	 * 列表查询
	 */
	@Override
	public Object selectclub(PageParam param, Club club) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("title", club.getTitle());
		parm.put("classId", club.getClassId());
		List<Map<String,Object>> lst=clubDao.getMap(parm);

		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}
	/**
	 * 新增
	 */
	@Override
	public String addclub(Club dto) {
		int res = clubDao.insert(dto);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}
	/**
	 * 根据id获取
	 */
	@Override
	public Club getclubByid(String id) {
		return clubDao.selectByPrimaryKey(id);
	}
	/**
	 * 更新
	 */
	@Override
	public String updateclub(Club dto) {
		if(super.updateByPrimaryKeyCache(dto,dto.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}
	/**
	 * 批量删除
	 */
	@Override
	public boolean deleteclubs(String[] ids) {
		for(String id : ids){
			Club dto = super.selectByPrimaryKeyCache(id, Club.class);
			 dto.setDeleted(1);
			 super.updateByPrimaryKeyCache(dto, id);
		}
		
		return true;
	}
	/**
	 * 根据classid获取会所
	 */
	@Override
	public List<Map> getClubByclassid(String classid) {
		Map parm = new HashMap();
		parm.put("classid", classid);
		return clubDao.getClubByclassid(parm);
	}
	/**
	 * 根据id获取
	 */
	@Override
	public Map selectClubByid(String clubid) {
		Map parm = new HashMap();
		parm.put("clubid", clubid);
		return clubDao.selectClubByid(parm);
	}
	/**
	 * 查询所有会所信息
	 */
	@Override
	public List<Map<String, Object>> selectallclubs() {
		Map parm = new HashMap();
		return clubDao.getMap(parm);
	}
	/**
	 * 根据条件查询会所列表    
	 */
	@Override
	public List<Map> getClubByTerm(String classId, Integer page, Integer size,
			String key) {
		Map parm = new HashMap();
		parm.put("title", key);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		parm.put("classId", classId);
		List<Map> lst=clubDao.getClubByTerm(parm);
		return lst;
	}
}
