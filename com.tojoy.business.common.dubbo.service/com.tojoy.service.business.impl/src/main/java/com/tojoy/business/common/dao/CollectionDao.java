package com.tojoy.business.common.dao;

import com.tojoy.business.common.bean.Collection;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author wangyansheng
 * @date 2017-12-24
 * @version 1.0
 */
@Repository
public interface CollectionDao {

    int insert(Collection collection);

    int delete(Collection collection);

    int queryCollectionCount(Collection collection);

    Collection queryByFkIdUserId(Collection collection);

}
