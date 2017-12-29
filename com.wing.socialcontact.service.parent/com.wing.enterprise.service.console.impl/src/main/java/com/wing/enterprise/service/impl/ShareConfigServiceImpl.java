package com.wing.enterprise.service.impl;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.ShareConfig;
import org.com.wing.enterprise.service.IShareConfigService;
import org.springframework.stereotype.Service;

import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.ShareConfigDao;
import com.wing.socialcontact.config.MsgConfig;
/**
 * 
 * <p>Title:分享配置管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月10日 下午2:32:53
 */
@Service
public class ShareConfigServiceImpl extends BaseServiceImpl<ShareConfig> implements
		IShareConfigService {
	
	@Resource
	private ShareConfigDao shareConfigDao;

	@Override
	public String updateShareConfig(ShareConfig dto) {
		if(super.updateByPrimaryKeyCache(dto, dto.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
		}
		
	}

	@Override
	public ShareConfig getById(String id) {
		// TODO Auto-generated method stub
		return super.selectByPrimaryKeyCache(id, ShareConfig.class);
	}

}
