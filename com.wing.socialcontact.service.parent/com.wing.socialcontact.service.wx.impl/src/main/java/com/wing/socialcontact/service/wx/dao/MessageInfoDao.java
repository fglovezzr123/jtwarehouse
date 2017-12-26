package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.MessageInfo;

/**
 * 
 * @author liangwj
 * @date 2017-03-28 15:00:44
 * @version 1.0
 */
@Repository
public interface MessageInfoDao extends Mapper<MessageInfo> {
	List<Map<String, Object>> selectAllMessageInfoMap();

	List selectByParam(Map parm);

	List<Map> selOneToOneDnd(Map parm);

	Map selToken(Map parm);

	boolean updateStatusByUserIdAndType(Map parm);
	
	Integer count(Map parm);
}
