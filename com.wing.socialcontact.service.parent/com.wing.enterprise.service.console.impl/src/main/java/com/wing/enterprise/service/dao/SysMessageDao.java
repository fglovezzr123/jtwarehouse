package com.wing.enterprise.service.dao;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.SysMessage;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
@Repository
public interface SysMessageDao extends Mapper<SysMessage> {

	List selsysMessage(Map parm);

}
