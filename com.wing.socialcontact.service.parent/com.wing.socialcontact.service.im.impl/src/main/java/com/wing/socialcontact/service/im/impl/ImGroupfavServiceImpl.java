package com.wing.socialcontact.service.im.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.im.dao.ImGroupfavDao;
import com.wing.socialcontact.service.im.api.IImGroupfavService;
import com.wing.socialcontact.service.im.bean.ImGroupfav;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-03 18:37:16
 * @version 1.0
 */
@Service
public class ImGroupfavServiceImpl implements IImGroupfavService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private ImGroupfavDao imGroupfavDao;

	@Override
	public List findFavListByGroupId(String groupId) {
		if(StringUtil.isEmpty(groupId)){
			throw new RuntimeException("组信息无效，请检查！");
		}
		return imGroupfavDao.findGroupFavsByGroupId(groupId);
	}

	@Override
	public String updateFavListByGroupId(String groupId, String[] favs) throws RuntimeException{
		if(favs == null || favs.length == 0 || groupId == null){
			throw new RuntimeException("参数无效，请检查！");
		}
		imGroupfavDao.delFavsByGroupId(groupId);
		this.insertFavs(groupId, favs);
		return MsgConfig.MSG_KEY_SUCCESS;
	}
	
	public String insertFavs(String groupId,String[] favs) throws RuntimeException{
		if(favs == null || favs.length == 0 || groupId == null){
			throw new RuntimeException("参数无效，请检查！");
		}
		for(int i = 0;i<favs.length;i++){
			Map paramMap = new HashMap();
			paramMap.put("groupId", groupId);
			paramMap.put("favId", favs[i]);
			ImGroupfav favOld = imGroupfavDao.loadByGroupIdAndFavId(paramMap);
			if(favOld == null){
				Map favValue = imGroupfavDao.findListValueByFavId(favs[i]);
				if(favValue != null){
					ImGroupfav fav = new ImGroupfav();
					fav.setId(UUID.randomUUID().toString());
					fav.setGroupId(groupId);
					fav.setFavId(favs[i]);
					fav.setSortno(i);
					imGroupfavDao.insert(fav);
				}else{
					throw new RuntimeException(favs[i]+"值错误，请检查！");
				}
			}
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}

}