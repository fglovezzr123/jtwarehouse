package com.wing.socialcontact.service.wx.dao;

import com.wing.socialcontact.service.wx.bean.TbLogContent;
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
public interface TbLogContentDao extends Mapper<TbLogContent> {
	/**
	 * 分页查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	List<TbLogContent> selectByParam(TbLogContent t);

}
