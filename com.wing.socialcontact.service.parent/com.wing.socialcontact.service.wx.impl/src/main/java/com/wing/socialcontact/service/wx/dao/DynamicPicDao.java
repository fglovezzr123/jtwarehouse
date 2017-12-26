package com.wing.socialcontact.service.wx.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.DynamicPic;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:06
 * @version 1.0
 */
@Repository
public interface DynamicPicDao extends Mapper<DynamicPic> {
	
	/**
	 * 获取动态图片列表
	 * @param dynamicId
	 * @return
	 */
	public List findDynamicPicListByDynamicId(String dynamicId);
	
	/**
	 * 删除动态图片
	 * @param dynamicId
	 */
	public void delAllDynamicPicList(String dynamicId);
	
}
