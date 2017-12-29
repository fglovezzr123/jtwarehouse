package com.tojoy.business.common.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tojoy.business.common.api.IThumbUpService;
import com.tojoy.business.common.bean.ThumpUp;
import com.tojoy.business.common.dao.ThumbUpDao;
import com.tojoy.business.common.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ThumbUpServiceImpl implements IThumbUpService {

    @Resource
    private ThumbUpDao thumpUpDao;

    @Override
    public int insert(ThumpUp t) {

        t.setId(UUIDGenerator.getUUID());
        return thumpUpDao.insert(t);
    }

    @Override
    public int delete(ThumpUp t) {
        return thumpUpDao.delete(t);
    }

    @Override
    public int queryCount(ThumpUp thumpUp) {

        return thumpUpDao.queryCount(thumpUp);
    }

    @Override
    public ThumpUp queryByFkIdUserId(ThumpUp thumpUp) {

        if (StringUtils.isBlank(thumpUp.getUserId()) || StringUtils.isBlank(thumpUp.getFkId())) {
            return null;
        }

        ThumpUp c = thumpUpDao.queryByFkIdUserId(thumpUp);
        return c;
    }
}