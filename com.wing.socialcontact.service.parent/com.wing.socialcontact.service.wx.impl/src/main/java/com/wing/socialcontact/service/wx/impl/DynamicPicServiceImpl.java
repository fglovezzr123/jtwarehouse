package com.wing.socialcontact.service.wx.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.DynamicPicDao;
import com.wing.socialcontact.service.wx.api.IDynamicPicService;
import com.wing.socialcontact.service.wx.bean.DynamicPic;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:06
 * @version 1.0
 */
@Service
public class DynamicPicServiceImpl implements IDynamicPicService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private DynamicPicDao dynamicPicDao;

	@Override
	public int insertDynamicPicList(List<DynamicPic> dynamicPicList)
			throws RuntimeException {
		int count = 0;
		if(dynamicPicList != null){
			for(int i = 0;i<dynamicPicList.size();i++){
				DynamicPic dynamicPic = dynamicPicList.get(i);
				dynamicPicDao.insert(dynamicPic);
				count++;
			}
		}
		return count;
	}

	public List<DynamicPic> selectAllDynamicPicList(String dynamicId) throws RuntimeException{
		return dynamicPicDao.findDynamicPicListByDynamicId(dynamicId);
	}
	
	/**
	 * 根据动态删除动态图片信息
	 * @param dynamicId
	 * @throws RuntimeException
	 */
	public void delAllDynamicPicList(String dynamicId)throws RuntimeException{
		dynamicPicDao.delAllDynamicPicList(dynamicId);
	}

}