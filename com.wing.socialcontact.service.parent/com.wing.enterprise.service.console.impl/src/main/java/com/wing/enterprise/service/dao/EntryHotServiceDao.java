package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryHotService;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * //TODO 聚合页幻灯片数据实现
 * @author sino
 */

@Repository
public interface EntryHotServiceDao extends Mapper<EntryHotService>{

//    List<Map<String,Object>> selectByParentKey(Map map);
    
    List<Map> selectByParam(Map map);
}
