package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.MySysMessage;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface MySysMessageDao extends Mapper<MySysMessage>{
    
    List<Map> selectMyMessage(Map param);
    
    boolean insertMyMsgBatch(List<MySysMessage> myMsgs);
    
    boolean updateMyMsgBatch(Map param);
    
    List<Map> selWillPushUser();
    
}
