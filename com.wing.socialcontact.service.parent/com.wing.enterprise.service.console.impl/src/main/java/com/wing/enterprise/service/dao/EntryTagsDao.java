package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryTags;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface EntryTagsDao extends Mapper<EntryTags>{
    
    List<Map> selTagsByEntryId(Map map);
}
