package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryServiceTag;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * //TODO 企服标签管理数据实现
 * @author sino
 */

@Repository
public interface EntryServiceTagDao extends Mapper<EntryServiceTag>{

//    List<Map<String,Object>> selectByParentKey(Map map);
    
    List<Map> selectByParam(Map map);
    
    List selectAllTags();

	List selectHotByParam(Map parm);

	List selectHotServiceBySize(Map parm);
}
