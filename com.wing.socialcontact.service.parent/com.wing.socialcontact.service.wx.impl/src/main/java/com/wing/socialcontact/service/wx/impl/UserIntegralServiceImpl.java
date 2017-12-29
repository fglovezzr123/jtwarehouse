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
import com.wing.socialcontact.service.wx.dao.UserIntegralDao;
import com.wing.socialcontact.service.wx.api.IUserIntegralService;
import com.wing.socialcontact.service.wx.bean.UserIntegral;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author gaojun
 * @date 2017-07-04 11:25:53
 * @version 1.0
 */
@Service
public class UserIntegralServiceImpl  extends BaseServiceImpl<UserIntegral>  implements IUserIntegralService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:UserIntegral:\" + ";
	
	@Resource
	private UserIntegralDao userIntegralDao;
	
	@Override
	public DataGrid selectUserIntegrals(PageParam param, UserIntegral userIntegral) {
		DataGrid data = new DataGrid();

		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("taskSystem", userIntegral.getTaskSystem());
		parm.put("integralTotal", userIntegral.getIntegralTotal());
		parm.put("empiricalTotal", userIntegral.getEmpiricalTotal());
		parm.put("orderStr", orderStr);
		List lst = userIntegralDao.selectByParam(parm);
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
	public boolean deleteUserIntegral(String[] ids) {
		// 等待删除的对象集合
			int count = 0;
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					String[] myids = id.split(",");
					for (String string : myids) {
						UserIntegral r = selectByPrimaryKey(string);
						if (r != null) {

							if (super.deleteByPrimaryKeyCache(string, UserIntegral.class))
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
	public UserIntegral selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, UserIntegral.class);
	}
	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	@Override
	public String addUserIntegral(UserIntegral dto) {
		UserIntegral u = userIntegralDao.selectByCode(dto.getCode());
		if(null!=u){
			return "msg.code.unique";
		}else{
			int res = userIntegralDao.insert(dto);
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
	public String updateUserIntegral(UserIntegral dto) {
			if(super.updateByPrimaryKeyCache(dto,dto.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	
	@Override
	public UserIntegral selectById(String id) {
		return userIntegralDao.selectByPrimaryKey(id);
	}
	
	
	@Override
	public UserIntegral selectByCode(String code) {
		return userIntegralDao.selectByCode(code);
	}


}