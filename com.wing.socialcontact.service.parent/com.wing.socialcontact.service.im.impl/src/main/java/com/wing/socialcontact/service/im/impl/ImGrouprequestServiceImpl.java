package com.wing.socialcontact.service.im.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
//import com.taobao.api.internal.util.StringUtils;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.im.dao.ImGrouprequestDao;
import com.wing.socialcontact.service.im.api.IImGrouprequestService;
import com.wing.socialcontact.service.im.api.IImGroupusersService;
import com.wing.socialcontact.service.im.bean.ImGrouprequest;

/**
 * 
 * @author xuxinyuan
 * @date 2017-03-29 17:08:39
 * @version 1.0
 */
@Service
public class ImGrouprequestServiceImpl implements IImGrouprequestService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";

	@Resource
	private ImGrouprequestDao imGrouprequestDao;

	@Resource
	private IImGroupusersService imGroupusersService;

	@Override
	public String insertGroupUserRequest(String groupId, String userId, String askmessage) {
		if (StringUtil.isEmpty(groupId) || StringUtil.isEmpty(userId)) {
			throw new RuntimeException("参数无效，请检查！");
		}
		ImGrouprequest request = new ImGrouprequest();

		request.setGroupId(groupId);
		request.setUserId(userId);
		request.setStatus(1);
		List requestList = imGrouprequestDao.selectAllImGrouprequest(request);
		if (requestList != null && requestList.size() != 0) {
			return "已提交过申请，正在审核中";
		}
		request.setId(UUID.randomUUID().toString());
		request.setAskmessage(askmessage);
		request.setCreateTime(new Date());
		request.setGroupId(groupId);
		request.setUserId(userId);

		imGrouprequestDao.insert(request);
		return "请求发送成功";
	}

	@Override
	public String updateDualGroupUserRequest(String requestId, String status) throws RuntimeException {
		if (StringUtil.isEmpty(requestId) || StringUtil.isEmpty(requestId)
				|| (!"2".equals(status) && !"3".equals(status))) {
			throw new RuntimeException("参数无效，请检查！");
		}
		ImGrouprequest request = imGrouprequestDao.selectByPrimaryKey(requestId);
		if (request != null) {
			if (request.getStatus() != 1) {
				throw new RuntimeException("该请求已处理，无需再次处理！");
			}
			request.setStatus(Integer.parseInt(status));
			request.setUpdateTime(new Date());
			imGrouprequestDao.updateByPrimaryKeySelective(request);
			if ("2".equals(status)) {
				// 群用户表中增加用户
				String userId = request.getUserId();
				String groupId = request.getGroupId();
				try {
					imGroupusersService.insertUser(groupId, new String[] { userId });
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		} else {
			throw new RuntimeException("该用户请求不存在，不能处理！");
		}
		// TODO Auto-generated method stub
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public List findMyGroupRequest(String creator, int pageNum, int pageSize) {
		if (creator == null || pageNum == 0 || pageSize == 0) {
			throw new RuntimeException("查询错误，参数无效，请检查！");
		}
		PageHelper.startPage(pageNum, pageSize);
		List groupRequestList = imGrouprequestDao.findGroupRequestByUserid(creator);
		return groupRequestList;
	}

	/**
	 * 根据Id获取加群申请信息
	 * 
	 * @param requestId
	 * @return
	 */
	public ImGrouprequest findGroupRequestByID(String requestId) throws RuntimeException {
		if(requestId==null){//if (StringUtils.isEmpty(requestId)) {
			throw new RuntimeException("参数无效，请检查！");
		}
		ImGrouprequest request = imGrouprequestDao.selectByPrimaryKey(requestId);
		return request;
	}

	/**
	 * 获取我新群通知数量
	 * 
	 * @param userId
	 * @return
	 */
	public int findNewGroupRequestCountByUserid(String userId) {
		if (userId == null) {
			throw new RuntimeException("参数无效，请检查！");
		}
		return imGrouprequestDao.findNewGroupRequestCountByUserid(userId);
	}

	@Override
	public List selectAllImGrouprequest(ImGrouprequest imGrouprequest) {

		return imGrouprequestDao.selectAllImGrouprequest(imGrouprequest);
	}

}