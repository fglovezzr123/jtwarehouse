package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryServiceClass;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * //TODO 企服类别管理数据实现
 * @author sino
 */

@Repository
public interface EntryServiceClassDao extends Mapper<EntryServiceClass>{

//    List<Map<String,Object>> selectByParentKey(Map map);
    
    List<Map> selectByParam(Map map);
    
    List<Map> selectSecond();
}
