package com.wing.socialcontact.service.wx.dao;

import com.wing.socialcontact.service.wx.bean.PersonalCustomization;
import com.wing.socialcontact.service.wx.bean.UserPhoto;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 * @author wangyansheng
 * @date 2017-11-27
 * @version 1.0
 */
@Repository
public interface UserPhotoDao extends Mapper<UserPhoto> {
	/**
	 * 分页查询
	 * @param t
	 * @return
	 * @author wangyansheng
	 */
	List<UserPhoto> selectByParam(UserPhoto t);


	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author wangyansheng
	 */
	UserPhoto selectById(String id);

	/**
	 * 根据id查询
	 * @param userId
	 * @return
	 * @author wangyansheng
	 */
	UserPhoto selectByUserId(String userId);

}
