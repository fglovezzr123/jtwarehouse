package com.wing.socialcontact.service.wx.impl;

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
import com.wing.socialcontact.service.wx.dao.UserIntegralEmpiricalDao;
import com.wing.socialcontact.service.wx.dao.UserIntegralEmpiricalDao;
import com.wing.socialcontact.service.wx.api.IUserIntegralEmpiricalService;
import com.wing.socialcontact.service.wx.bean.UserIntegralEmpirical;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:54
 * @version 1.0
 */
@Service
public class UserIntegralEmpiricalServiceImpl extends BaseServiceImpl<UserIntegralEmpirical>  implements IUserIntegralEmpiricalService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:UserIntegralEmpirical:\" + ";
	
	@Resource
	private UserIntegralEmpiricalDao userIntegralEmpiricalDao;
	
	@Override
	public DataGrid selectUserIntegralEmpiricals(PageParam param, UserIntegralEmpirical userIntegralEmpirical) {
		DataGrid data = new DataGrid();

		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		/// parm.put("tagName", userIntegralEmpirical.getTagName());
		parm.put("orderStr", orderStr);
		List lst = userIntegralEmpiricalDao.selectByParam(parm);
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
	public boolean deleteUserIntegralEmpirical(String[] ids) {
		// 等待删除的对象集合
			int count = 0;
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					String[] myids = id.split(",");
					for (String string : myids) {
						UserIntegralEmpirical r = selectByPrimaryKey(string);
						if (r != null) {

							if (super.deleteByPrimaryKeyCache(string, UserIntegralEmpirical.class))
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
	public UserIntegralEmpirical selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, UserIntegralEmpirical.class);
	}
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	@Override
	public String addUserIntegralEmpirical(UserIntegralEmpirical dto) {
		Map parm = new HashMap();
			int res = userIntegralEmpiricalDao.insert(dto);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	@Override
	public String updateUserIntegralEmpirical(UserIntegralEmpirical dto) {
			if(super.updateByPrimaryKeyCache(dto,dto.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	
	@Override
	public UserIntegralEmpirical selectById(String id) {
		return userIntegralEmpiricalDao.selectByPrimaryKey(id);
	}
	

	@Override
	public UserIntegralEmpirical selectByIeType(String ieType) {
		return userIntegralEmpiricalDao.selectByIeType(ieType);
	}

}