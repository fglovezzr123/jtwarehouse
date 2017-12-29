package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IUserEmpiricalService;
import com.wing.socialcontact.service.wx.bean.UserEmpirical;
import com.wing.socialcontact.service.wx.dao.UserEmpiricalDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:52
 * @version 1.0
 */
@Service
public class UserEmpiricalServiceImpl  extends BaseServiceImpl<UserEmpirical> implements IUserEmpiricalService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:UserEmpirical:\" + ";
	
	@Resource
	private UserEmpiricalDao userEmpiricalDao;
	
	@Override
	public DataGrid selectUserEmpiricals(PageParam param, UserEmpirical userEmpirical) {
		DataGrid data = new DataGrid();

		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("level", userEmpirical.getLevel());
		parm.put("orderStr", orderStr);
		List lst = userEmpiricalDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());

		return data;
	}
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteUserEmpirical(String[] ids) {
		// 等待删除的对象集合
			int count = 0;
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					String[] myids = id.split(",");
					for (String string : myids) {
						UserEmpirical r = selectByPrimaryKey(string);
						if (r != null) {

							if (super.deleteByPrimaryKeyCache(string, UserEmpirical.class))
								count++;
						}
					}
				}
			}
			return count > 0;
	}
	
	/**
	 * 调用缓存机制
	 */
	@Override
	public UserEmpirical selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, UserEmpirical.class);
	}
	
	@Override
	public UserEmpirical selectByEmpirical(Integer Empirical){
		return userEmpiricalDao.selectByEmpirical(Empirical);
	}
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	@Override
	public String addUserEmpirical(UserEmpirical dto) {
		UserEmpirical u = userEmpiricalDao.selectBylevel(dto.getLevel());
		if(null != u){
			return "msg.level.unique";
		}else{
			int res = userEmpiricalDao.insert(dto);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}
			
	}
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	@Override
	public String updateUserEmpirical(UserEmpirical dto) {
			if(super.updateByPrimaryKeyCache(dto,dto.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	
	@Override
	public UserEmpirical selectById(String id) {
		return userEmpiricalDao.selectByPrimaryKey(id);
	}
	@Override
	public UserEmpirical selectBylevel(String level) {
		return userEmpiricalDao.selectBylevel(level);
	}
	
	@Override
	public List<UserEmpirical> selectAllUserEmpirical() {
		Map parm = new HashMap();
		return userEmpiricalDao.selectByParam(parm);
	}

}