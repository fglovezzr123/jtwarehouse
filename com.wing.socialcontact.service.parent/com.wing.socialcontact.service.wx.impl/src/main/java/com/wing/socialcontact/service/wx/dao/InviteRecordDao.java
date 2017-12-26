package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.InviteRecord;

/**
 * 
 * @author zengmin
 * @date 2017-07-13 09:51:06
 * @version 1.0
 */
@Repository
public interface InviteRecordDao extends Mapper<InviteRecord> {
	List query(Map<String, Object> param);
	List queryByUserId(Map<String, Object> param);
	List queryByOpenId(Map<String, Object> param);
}
