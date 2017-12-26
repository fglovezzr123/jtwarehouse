package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryStaticClick;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface EntryStaticClickDao extends Mapper<EntryStaticClick>{
    
    List<EntryStaticClick> selStaticClick(Map map);
}
