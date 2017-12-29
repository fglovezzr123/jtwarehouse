package com.tojoy.business.common.impl;

import com.tojoy.business.common.api.IRewardService;
import com.tojoy.business.common.api.IShareService;
import com.tojoy.business.common.bean.Reward;
import com.tojoy.business.common.bean.Share;
import com.tojoy.business.common.dao.RewardDao;
import com.tojoy.business.common.dao.ShareDao;
import com.tojoy.business.common.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RewardServiceImpl implements IRewardService{

    @Resource
    private RewardDao rewardDao;

    @Override
    public int insert(Reward t) {
        t.setId(UUIDGenerator.getUUID());
        return rewardDao.insert(t);
    }

}