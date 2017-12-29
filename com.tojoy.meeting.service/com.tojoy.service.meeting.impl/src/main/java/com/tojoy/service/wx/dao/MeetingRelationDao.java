package com.tojoy.service.wx.dao;

import com.tojoy.service.wx.bean.MeetingRelation;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 
 * @author wangyansheng
 * @date 2017-04-04 00:05:28
 * @version 1.0
 */
@Repository
public interface MeetingRelationDao extends Mapper<MeetingRelation> {

	/**
	 * 根据会议id查询关联会议
	 * @param meetingId
	 * @return
	 */
	List<MeetingRelation> queryMeetingRelationByMeetingId(String meetingId);

	/**
	 * 批量新增关联会议
	 * @param list
	 * @return
	 */
	 int addRecordBatch(List<MeetingRelation> list);

	/**
	 * 删除关联会议
	 * @param meetingId
	 * @return
	 */
	int deleteByMeetingId(String meetingId);
}
