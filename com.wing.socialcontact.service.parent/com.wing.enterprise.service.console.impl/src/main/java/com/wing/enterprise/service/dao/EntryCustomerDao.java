package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryCustomer;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface EntryCustomerDao extends Mapper<EntryCustomer>{
    
    List<Map> selCustomerByEntryId(String entryId);

	List<Map<String, Object>> getclassMap(Map parm);
}
