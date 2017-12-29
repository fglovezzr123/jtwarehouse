package com.tojoy.service.wx.impl;


import com.tojoy.service.wx.api.IMeetingRelationService;
import com.tojoy.service.wx.bean.*;
import com.tojoy.service.wx.dao.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 00:12:35
 * @version 1.0
 */
@Service
public class MeetingRelationServiceImpl implements IMeetingRelationService {

	@Resource
	private MeetingRelationDao meetingRelationDao;

	@Override
	public int addRecordBatch(List<MeetingRelation> list) {
		return meetingRelationDao.addRecordBatch(list);
	}

	@Override
	public int deleteByMeetingId(String meetingId) {
		return meetingRelationDao.deleteByMeetingId(meetingId);
	}

	@Override
	public List<MeetingRelation> queryMeetingRelationByMeetingId(String meetingId) {
		return meetingRelationDao.queryMeetingRelationByMeetingId(meetingId);
	}
}