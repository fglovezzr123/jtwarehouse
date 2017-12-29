package com.wing.socialcontact.service.wx.dao;

import com.wing.socialcontact.service.wx.bean.TbLog;
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
public interface TbLogDao extends Mapper<TbLog> {

	/**
	 * 分页查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	List<TbLog> selectByParam(TbLog t);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author wangyansheng
	 */
	TbLog selectById(String id);

}
