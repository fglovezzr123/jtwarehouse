package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.service.wx.bean.Dynamic;
import com.wing.socialcontact.service.wx.bean.DynamicOpLog;
import com.wing.socialcontact.service.wx.bean.DynamicPic;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:06
 * @version 1.0
 */
public interface IDynamicPicService{

	/**
	 * 新增
	 * @param t
	 * @author xuxinyuan
	 */
	public int insertDynamicPicList(List<DynamicPic> dynamicPicList) throws RuntimeException ;
	
	/**
	 * 
	 * @param dynamicPic
	 * @return
	 * @throws RuntimeException
	 */
	public List<DynamicPic> selectAllDynamicPicList(String dynamicId) throws RuntimeException;
	
	/**
	 * 根据动态删除动态图片信息
	 * @param dynamicId
	 * @throws RuntimeException
	 */
	public void delAllDynamicPicList(String dynamicId)throws RuntimeException;

}