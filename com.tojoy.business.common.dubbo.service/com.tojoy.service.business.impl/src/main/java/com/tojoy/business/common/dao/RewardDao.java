package com.tojoy.business.common.dao;

import com.tojoy.business.common.bean.Reward;
import com.tojoy.business.common.bean.Share;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author wangyansheng
 * @date 2017-12-24
 * @version 1.0
 */
@Repository
public interface RewardDao {

    int insert(Reward t);

}
