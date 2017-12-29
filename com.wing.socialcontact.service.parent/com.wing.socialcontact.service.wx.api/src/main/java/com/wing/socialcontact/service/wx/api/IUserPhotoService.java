package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.TbLog;
import com.wing.socialcontact.service.wx.bean.UserPhoto;


/**
 *
 * @author wangyansheng
 * @date 2017-11-27
 * @version 1.0
 */
public interface IUserPhotoService {

	/**
	 * 新增
	 * @param t
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int insert(UserPhoto t);

	/**
	 * 修改
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int update(UserPhoto t);

	/**
	 * 删除
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int logicalDelete(UserPhoto t);

	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public DataGrid selectByParam(PageParam param, UserPhoto t);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public UserPhoto selectById(String id);

	/**
	 * 根据id查询
	 * @param userId
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public UserPhoto selectByUserId(String userId);
}