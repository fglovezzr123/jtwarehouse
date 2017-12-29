package com.tojoy.business.common.dao;

import com.tojoy.business.common.bean.Collection;
import com.tojoy.business.common.bean.ThumpUp;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author wangyansheng
 * @date 2017-12-24
 * @version 1.0
 */
@Repository
public interface ThumbUpDao {

    int insert(ThumpUp thumpUp);

    int delete(ThumpUp thumpUp);

    int queryCount(ThumpUp thumpUp);

    ThumpUp queryByFkIdUserId(ThumpUp thumpUp);

}
