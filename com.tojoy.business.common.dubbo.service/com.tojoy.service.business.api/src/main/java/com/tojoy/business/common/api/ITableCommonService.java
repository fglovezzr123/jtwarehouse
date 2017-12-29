package com.tojoy.business.common.api;


/**
 *
 * @author
 * @date 2017-11-27
 * @version 1.0
 */
public interface ITableCommonService {

	int createTable(String key);

	boolean isExistTable(String key);
}