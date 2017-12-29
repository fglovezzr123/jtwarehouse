package com.tojoy.service.wx.dao;

import java.util.List;

import com.tojoy.service.wx.bean.MeetingWhitelist;
import org.springframework.stereotype.Repository;



import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
@Repository
public interface MeetingWhitelistDao extends Mapper<MeetingWhitelist> {
	public List<MeetingWhitelist> selectByParam(MeetingWhitelist t);
}
