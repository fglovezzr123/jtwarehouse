package com.tojoy.business.common.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tojoy.business.common.api.IShareService;
import com.tojoy.business.common.api.IThumbUpService;
import com.tojoy.business.common.bean.Share;
import com.tojoy.business.common.bean.ThumpUp;
import com.tojoy.business.common.dao.ShareDao;
import com.tojoy.business.common.dao.ThumbUpDao;
import com.tojoy.business.common.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShareServiceImpl implements IShareService{

    @Resource
    private ShareDao shareDao;

    @Override
    public int insert(Share t) {
        t.setId(UUIDGenerator.getUUID());
        return shareDao.insert(t);
    }

}