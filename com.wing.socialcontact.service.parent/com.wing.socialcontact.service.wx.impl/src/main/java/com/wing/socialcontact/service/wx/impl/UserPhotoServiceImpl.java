package com.wing.socialcontact.service.wx.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IUserPhotoService;
import com.wing.socialcontact.service.wx.bean.TbLog;
import com.wing.socialcontact.service.wx.bean.TbLogContent;
import com.wing.socialcontact.service.wx.bean.UserPhoto;
import com.wing.socialcontact.service.wx.dao.TbLogContentDao;
import com.wing.socialcontact.service.wx.dao.TbLogDao;
import com.wing.socialcontact.service.wx.dao.UserPhotoDao;
import com.wing.socialcontact.util.CompareFieldsUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户照片拍照
 * @author wangyansheng
 * @date 2017-11-27
 * @version 1.0
 */
@Service
public class UserPhotoServiceImpl implements IUserPhotoService {

	@Resource
	private UserPhotoDao userPhotoDao;

	@Resource
	private TbLogDao tbLogDao;

	@Resource
	private TbLogContentDao tbLogContentDao;

	/**
	 * 新增
	 * @param userPhoto
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public int insert(UserPhoto userPhoto) {
		userPhoto.setId(UUIDGenerator.getUUID());
		userPhoto.setDeleted(0);
		int n = userPhotoDao.insert(userPhoto);
		//记录日志
		TbLog tbLog = new TbLog();
		tbLog.setId(UUIDGenerator.getUUID());
		tbLog.setUserId(userPhoto.getUserId());
		tbLog.setType(1);
		tbLog.setTableName("tjy_user_photo");
		tbLog.setComment("用户照片表");
		tbLog.setCreateTime(new Date());
		tbLogDao.insert(tbLog);

		// 比较变更的数据，其中id忽略比较
		UserPhoto photo = new UserPhoto();
		Map<String, List<Object>> compareResult = CompareFieldsUtil.compareFields(userPhoto, photo, new String[]{"id"});
		Set<String> keySet = compareResult.keySet();
		for(String key : keySet){
			List<Object> list = compareResult.get(key);
			TbLogContent tbLogContent = new TbLogContent();
			tbLogContent.setId(UUIDGenerator.getUUID());
			tbLogContent.setLogId(tbLog.getId());
			tbLogContent.setTbKey(CompareFieldsUtil.camelToUnderline(key));
			tbLogContent.setOldTbValue("");
			tbLogContent.setCurrentTbValue(list.get(0).toString());
			tbLogContentDao.insert(tbLogContent);
		}

		return n;
	}

	/**
	 * 修改
	 * @param userPhoto
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public int update(UserPhoto userPhoto) {

		//记录日志
		TbLog tbLog = new TbLog();
		tbLog.setId(UUIDGenerator.getUUID());
		tbLog.setUserId(userPhoto.getUpdateUserId());
		tbLog.setType(2);
		tbLog.setTableName("tjy_user_photo");
		tbLog.setComment("用户照片表");
		tbLog.setCreateTime(new Date());
		tbLogDao.insert(tbLog);

		// 比较变更的数据，其中id忽略比较
		UserPhoto photo = userPhotoDao.selectById(userPhoto.getId());
		Map<String, List<Object>> compareResult = CompareFieldsUtil.compareFields(userPhoto, photo, new String[]{"id"});
		Set<String> keySet = compareResult.keySet();
		for(String key : keySet){
			List<Object> list = compareResult.get(key);
			TbLogContent tbLogContent = new TbLogContent();
			tbLogContent.setId(UUIDGenerator.getUUID());
			tbLogContent.setLogId(tbLog.getId());
			tbLogContent.setTbKey(CompareFieldsUtil.camelToUnderline(key));
			tbLogContent.setOldTbValue(list.get(1)==null? "":list.get(1).toString());
			tbLogContent.setCurrentTbValue(list.get(0).toString());
			tbLogContentDao.insert(tbLogContent);
		}

		int n = userPhotoDao.updateByPrimaryKey(userPhoto);
		return n;
	}

	/**
	 * 删除
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public int logicalDelete(UserPhoto t) {
		return userPhotoDao.updateByPrimaryKey(t);
	}

	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public DataGrid selectByParam(PageParam param, UserPhoto t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<UserPhoto> list = userPhotoDao.selectByParam(t);
		PageInfo<UserPhoto> page = new PageInfo<UserPhoto>(list);
		return new DataGrid(page);
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public UserPhoto selectById(String id) {
		//基本信息
		UserPhoto p = userPhotoDao.selectById(id);
		return p;
	}

	/**
	 * 根据UserId查询
	 * @param userId
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-27
	 */
	@Override
	public UserPhoto selectByUserId(String userId) {
		//基本信息
		UserPhoto p = userPhotoDao.selectByUserId(userId);
		return p;
	}
}