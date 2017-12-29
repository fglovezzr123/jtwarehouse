package com.tojoy.business.common.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tojoy.business.common.dao.CollectionDao;
import com.tojoy.business.common.api.ICollectionService;
import com.tojoy.business.common.bean.Collection;
import com.tojoy.business.common.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CollectionServiceImpl implements ICollectionService {

    @Resource
    private CollectionDao collectionDao;

    @Override
    public int insert(Collection t) {

        t.setId(UUIDGenerator.getUUID());
        return collectionDao.insert(t);
    }

    @Override
    public int delete(Collection t) {
        return collectionDao.delete(t);
    }

    @Override
    public int queryCollectionCount(Collection attention) {

        return collectionDao.queryCollectionCount(attention);
    }

    @Override
    public Collection queryByFkIdUserId(Collection collection) {

        if (StringUtils.isBlank(collection.getUserId()) || StringUtils.isBlank(collection.getFkId())) {
            return null;
        }

        Collection c = collectionDao.queryByFkIdUserId(collection);
        return c;
    }
}