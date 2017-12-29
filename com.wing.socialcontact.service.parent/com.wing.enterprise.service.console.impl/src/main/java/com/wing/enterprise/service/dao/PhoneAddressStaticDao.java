package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.PhoneAdressStatic;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface PhoneAddressStaticDao extends Mapper<PhoneAdressStatic>{
    List<Map> selectByParam(Map map);
}
