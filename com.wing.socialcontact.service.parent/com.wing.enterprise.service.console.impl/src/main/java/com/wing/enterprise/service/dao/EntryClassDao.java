package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryClass;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface EntryClassDao extends Mapper<EntryClass>{
    
    List<Map> selClassesByEntryId(Map map);
}
