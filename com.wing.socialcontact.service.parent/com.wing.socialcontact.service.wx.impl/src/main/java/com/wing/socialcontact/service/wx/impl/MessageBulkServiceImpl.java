package com.wing.socialcontact.service.wx.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IMessageBulkService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.bean.MessageBulk;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.service.wx.dao.MessageBulkDao;
import com.wing.socialcontact.service.wx.dao.MessageInfoDao;
import com.wing.socialcontact.service.wx.dao.TjyUserDao;
import com.wing.socialcontact.service.wx.dao.WxUserDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;

/**
 * 
 * @author gaojun
 * @date 2017-06-28 11:18:36
 * @version 1.0
 */
@Service
public class MessageBulkServiceImpl  extends BaseServiceImpl<MessageBulk> implements IMessageBulkService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:MessageBulk:\" + ";
	
	@Resource
	private MessageBulkDao messageBulkDao;
	@Resource
	private TjyUserDao tjyUserDao;
	@Resource
	private WxUserDao wxUserDao;
	@Resource
	private MessageInfoDao messageInfoDao;
	@Autowired
	private IMessageInfoService messageInfoService;

	@Override
	public DataGrid selectMessageBulks(PageParam param, MessageBulk messageBulk,String startTime,String endTime) {
		DataGrid data = new DataGrid();

		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		/// parm.put("tagName", messageBulk.getTagName());
		parm.put("orderStr", orderStr);
		parm.put("startTime", startTime);
		parm.put("endTime", endTime);
		parm.put("msgType", messageBulk.getMsgType());
		parm.put("content", messageBulk.getContent());
		
		List lst = messageBulkDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());

		return data;
	}
	
	/**
	 * 批量删除消息群发
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteMessageBulk(String[] ids) {
		for(String id : ids){
			MessageBulk dto = super.selectByPrimaryKeyCache(id, MessageBulk.class);
			 dto.setDeleted(1);
			 super.updateByPrimaryKeyCache(dto, id);
		}
		
		// 等待删除的对象集合
		int count = 0;
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				String[] myids = id.split(",");
				for (String string : myids) {
					MessageBulk r = super.selectByPrimaryKeyCache(string, MessageBulk.class);
					if (r != null) {

						if (super.deleteByPrimaryKeyCache(string, MessageBulk.class))
							count++;
					}
				}
			}
		}
		return count > 0;
		
	}
	/**
	 * 新增消息群发
	 * @param dto
	 * @return
	 */
	@Override
	public String addMessageBulk(MessageBulk dto) {
		Map parm = new HashMap();
		
	    //群发短信息信息
	    if("1".equals(dto.getMsgType())){
	    	List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
	    	if("4".equals(dto.getSendType())){
	    		String[] userIds=null;
				if (dto.getToUserIds() != null && !"".equals(dto.getToUserIds())) {
					userIds = dto.getToUserIds().split(",");
				}
				if (userIds != null && !"".equals(userIds)) {
					for (int i = 0; i < userIds.length; i++) {
						if (userIds[i] != null && !"".equals(userIds[i])) {
	                        WxUser user = wxUserDao.selectByUserId(userIds[i]);
	                        if(null != user.getMobile() ){
	                        	// 发送短信
							    //System.out.println(_mobile+"**template*"+dto.getContent()+"#####"+dto.getTemplateId());
				    			// 发送短信
								MessageInfo messageInfo = new MessageInfo();
								messageInfo.setId(UUIDGenerator.getUUID());
								messageInfo.setDeleted(0);
								messageInfo.setMobile(user.getMobile());
								messageInfo.setType(1);// 短信
								messageInfo.setCreateTime(new Date());
								messageInfo.setContent(dto.getContent());
								messageInfo.setStatus(0);// 未发送
								messageInfo.setTemplateId(dto.getTemplateId());
								messageInfoService.addMessageInfo(messageInfo);
	                        }
						}

					}
				}
	    	}else{
	    		if("0".equals(dto.getSendType())){
		    		//全部用户
		    		parm.put("sendType", "0");
		    	}else if("1".equals(dto.getSendType())){
		    		//认证用户
		    		parm.put("sendType", "1");
		    	}else if("2".equals(dto.getSendType())){
		    		//注册用户
		    		parm.put("sendType", "2");
		    	}else if("3".equals(dto.getSendType())){
		    		//企服云用户
		    		parm.put("sendType", "3");
		    	}
		    	ls=tjyUserDao.selUserBySendType(parm);
			    	for(Map<String, Object> m :ls){
			    		if(null!=m && null!=m.get("mobile")){
			    			String _mobile =(String)m.get("mobile");
			    			// 发送短信
						    //System.out.println(_mobile+"**template*"+dto.getContent()+"#####"+dto.getTemplateId());
			    			// 发送短信
							MessageInfo messageInfo = new MessageInfo();
							messageInfo.setId(UUIDGenerator.getUUID());
							messageInfo.setDeleted(0);
							messageInfo.setMobile(_mobile);
							messageInfo.setType(1);// 短信
							messageInfo.setCreateTime(new Date());
							messageInfo.setContent(dto.getContent());
							messageInfo.setStatus(0);// 未发送
							messageInfo.setTemplateId(dto.getTemplateId());
							messageInfoService.addMessageInfo(messageInfo);
			    		}
			    	}
			    }
	    	}
	    	
			
	    	
		    
	    //群发微信信息
	    if("2".equals(dto.getMsgType())){
	    	List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>(); 
	    	if("4".equals(dto.getSendType())){
	    		String[] userIds=null;
				if (dto.getToUserIds() != null && !"".equals(dto.getToUserIds())) {
					userIds = dto.getToUserIds().split(",");
				}
				if (userIds != null && !"".equals(userIds)) {
					for (int i = 0; i < userIds.length; i++) {
						if (userIds[i] != null && !"".equals(userIds[i])) {
                        	// 发送微信
    						MessageInfo messageInfo = new MessageInfo();
    						messageInfo.setId(UUIDGenerator.getUUID());
    						messageInfo.setDeleted(0);
    						messageInfo.setType(2);// 微信
    						messageInfo.setToUserId(userIds[i]);
    						messageInfo.setCreateTime(new Date());
    						// 组装内容
    						String content = "您的认证资料已提交，请等待审核";
    						String con = WxMsmUtil.getTextMessageContent(dto.getContent());
    						messageInfo.setContent(con);
    						messageInfo.setTemplateId("RECON_USER");
    						messageInfo.setStatus(0);// 未发送
    						messageInfo.setWxMsgType(1);///  微信消息类型（1：文本消息2：图文消息）
    						messageInfoService.addMessageInfo(messageInfo);
						}

					}
				}
	    	}else{
		    	if("0".equals(dto.getSendType())){
		    		//全部用户
		    		parm.put("sendType", "0");
		    	}else if("1".equals(dto.getSendType())){
		    		//注册用户
		    		parm.put("sendType", "1");
		    	}else if("2".equals(dto.getSendType())){
		    		//认证用户
		    		parm.put("sendType", "2");
		    	}else if("3".equals(dto.getSendType())){
		    		//企服云用户
		    		parm.put("sendType", "3");
		    	}
		    	ls=tjyUserDao.selUserBySendType(parm);
		    	for(Map<String, Object> m :ls){
		    		if(null!=m && null!=m.get("mobile")){
		    			// 发送微信
		    			String _user_id =(String)m.get("id");
						MessageInfo messageInfo = new MessageInfo();
						messageInfo.setId(UUIDGenerator.getUUID());
						messageInfo.setDeleted(0);
						messageInfo.setType(2);// 微信
						messageInfo.setToUserId(_user_id);
						messageInfo.setCreateTime(new Date());
						// 组装内容
						//String content = "您的认证资料已提交，请等待审核";
						String con = WxMsmUtil.getTextMessageContent(dto.getContent());
						messageInfo.setContent(con);
						messageInfo.setTemplateId("");
						messageInfo.setStatus(0);// 未发送
						messageInfo.setWxMsgType(1);///  微信消息类型（1：文本消息2：图文消息）
						messageInfoService.addMessageInfo(messageInfo);
		    		}
		    	}
		    }
	    }
	    //群发系统消息
	    if("3".equals(dto.getMsgType())){
	    	List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>(); 
	    	if("4".equals(dto.getSendType())){
	    		String[] userIds=null;
				if (dto.getToUserIds() != null && !"".equals(dto.getToUserIds())) {
					userIds = dto.getToUserIds().split(",");
				}
				if (userIds != null && !"".equals(userIds)) {
					for (int i = 0; i < userIds.length; i++) {
						if (userIds[i] != null && !"".equals(userIds[i])) {
							String _user_id =userIds[i];
			    			//String _mobile =(String)m.get("mobile");
			    			
			    			MessageInfo messageInfo = new MessageInfo();
							messageInfo.setId(UUIDGenerator.getUUID());
							messageInfo.setDeleted(0);
							messageInfo.setType(3);// 系统
							messageInfo.setCreateTime(new Date());
							messageInfo.setToUserId(_user_id);
							messageInfo.setContent(dto.getContent());
							messageInfo.setStatus(0);
							messageInfoService.addMessageInfo(messageInfo);
						}

					}
				}
	    	}else{
		    	if("0".equals(dto.getSendType())){
		    		//全部用户
		    		parm.put("sendType", "0");
		    	}else if("1".equals(dto.getSendType())){
		    		//注册用户
		    		parm.put("sendType", "1");
		    	}else if("2".equals(dto.getSendType())){
		    		//认证用户
		    		parm.put("sendType", "2");
		    	}else if("3".equals(dto.getSendType())){
		    		//企服云用户
		    		parm.put("sendType", "3");
		    	}
		    	ls=tjyUserDao.selUserBySendType(parm);
		    	for(Map<String, Object> m :ls){
		    		if(null!=m && null!=m.get("mobile")){
		    			// 发送系统消息
		    			String _user_id =(String)m.get("id");
		    			//String _mobile =(String)m.get("mobile");
		    			
		    			MessageInfo messageInfo = new MessageInfo();
						messageInfo.setId(UUIDGenerator.getUUID());
						messageInfo.setDeleted(0);
						messageInfo.setType(3);// 系统
						messageInfo.setCreateTime(new Date());
						messageInfo.setToUserId(_user_id);
						messageInfo.setContent(dto.getContent());
						messageInfo.setStatus(0);
						messageInfoService.addMessageInfo(messageInfo);
		    		}
		    	}
		    }
	    }
		int res = messageBulkDao.insert(dto);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}
	/**
	 * 更新消息群发
	 * @param dto
	 * @return
	 */
	@Override
	public String updateMessageBulk(MessageBulk dto) {
			if(super.updateByPrimaryKeyCache(dto,dto.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	
	@Override
	public MessageBulk selectById(String id) {
		return messageBulkDao.selectByPrimaryKey(id);
	}
}