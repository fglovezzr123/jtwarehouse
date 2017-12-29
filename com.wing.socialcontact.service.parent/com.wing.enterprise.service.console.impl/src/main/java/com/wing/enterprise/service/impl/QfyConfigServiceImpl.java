package com.wing.enterprise.service.impl;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.QfyConfig;
import org.com.wing.enterprise.service.IQfyConfigService;
import org.springframework.stereotype.Service;

import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.QfyConfigDao;
import com.wing.socialcontact.config.MsgConfig;

@Service
public class QfyConfigServiceImpl extends BaseServiceImpl<QfyConfig> implements IQfyConfigService{

    @Resource
    private QfyConfigDao qfyConfigDao;
    
    @Override
    public String updateDescConfig(QfyConfig c) {
        if(super.updateByPrimaryKeyCache(c,c.getId())){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public QfyConfig selConfig(String id) {
        return super.selectByPrimaryKeyCache(id, QfyConfig.class);
    }
}
