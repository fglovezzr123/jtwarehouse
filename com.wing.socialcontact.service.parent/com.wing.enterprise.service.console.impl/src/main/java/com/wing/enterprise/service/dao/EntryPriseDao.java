package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryPrise;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月5日 下午5:02:53
 */
@Repository
public interface EntryPriseDao extends Mapper<EntryPrise> {
    
    List<Map> selectByParam(Map map);
    
    List<Map> selEntryPrise(Map map);
    
    List<Map> selQuickEntrys(Map map);
    
    List<Map> selStaticEntry(Map map);
    
    List<Map> selCustomer(Map map);
}
