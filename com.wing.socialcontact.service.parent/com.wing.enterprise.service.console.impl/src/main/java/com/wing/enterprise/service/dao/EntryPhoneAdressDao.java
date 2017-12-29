package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryPhoneAdress;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface EntryPhoneAdressDao extends Mapper<EntryPhoneAdress>{
    
    List<Map> selectPhoneAdress(Map param);
    
    boolean insertPhoneAdressBatch(List<EntryPhoneAdress> entryPhoneAdressList);
}
