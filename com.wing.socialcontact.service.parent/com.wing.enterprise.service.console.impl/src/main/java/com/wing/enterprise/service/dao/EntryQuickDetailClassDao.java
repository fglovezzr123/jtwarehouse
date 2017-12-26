package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.QuickDetailClass;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * //TODO 聚合页分类数据实现
 * @author sino
 */

@Repository
public interface EntryQuickDetailClassDao extends Mapper<QuickDetailClass>{

//    List<Map<String,Object>> selectByParentKey(Map map);
    
    List<Map> selectByParam(Map map);
    
    List<Map> selectAllQucikDoor();
    
    List<Map> selectAllClasses();
    
    List<Map> isExist(Map map);
    
    List<Map> selectFristByQdId(String quickDoorId);
    
    List<Map> selectClassByQdId(Map map);
    
}
