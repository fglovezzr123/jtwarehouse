package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.QuickDoor;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * //TODO 企服快捷入口数据实现
 * @author sino
 */

@Repository
public interface EntryQuickDoorDao extends Mapper<QuickDoor>{

//    List<Map<String,Object>> selectByParentKey(Map map);
    
    List<Map> selectByParam(Map map);
    
    List<Map> selectH5IndexQuickDoors(Map map);
} 
