package com.tojoy.business.common.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *
 * @author wangyansheng
 * @date 2017-11-27
 * @version 1.0
 */
@Repository
public interface TableCommonDao{

	int createTable(Map<String, String> map);

	int isExistTable(Map<String, String> map);

}
