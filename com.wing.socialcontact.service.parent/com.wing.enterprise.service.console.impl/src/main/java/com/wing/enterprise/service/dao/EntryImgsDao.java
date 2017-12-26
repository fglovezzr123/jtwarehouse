package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryImgs;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月5日 下午4:05:57
 */
@Repository
public interface EntryImgsDao extends Mapper<EntryImgs> {
    List<Map> selectByParam(Map map);
}
