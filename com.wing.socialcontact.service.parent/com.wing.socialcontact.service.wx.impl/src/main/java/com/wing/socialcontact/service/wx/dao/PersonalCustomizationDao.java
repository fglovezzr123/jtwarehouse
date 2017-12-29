package com.wing.socialcontact.service.wx.dao;

import com.wing.socialcontact.service.wx.bean.PersonalCustomization;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
@Repository
public interface PersonalCustomizationDao extends Mapper<PersonalCustomization> {
	/**
	 * 分页查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	List<PersonalCustomization> selectByParam(PersonalCustomization t);

}
