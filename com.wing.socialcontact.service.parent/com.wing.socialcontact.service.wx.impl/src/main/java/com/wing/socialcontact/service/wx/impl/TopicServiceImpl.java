package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.TopicDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ITopicService;
import com.wing.socialcontact.service.wx.bean.Topic;

/**
 * 
 * @author zhangfan
 * @date 2017-03-29 11:34:46
 * @version 1.0
 */
@Service
public class TopicServiceImpl extends BaseServiceImpl<Topic> implements ITopicService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:Topic:\" + ";
	
	@Resource
	private TopicDao topicDao;

	@Override
	public DataGrid selectAllTopic(PageParam param, Topic topic,String startTime,String endTime,String createUserId,
			String userId,Integer isAd) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("topicDesc", topic.getTopicDesc());
		parm.put("isShow", topic.getIsShow());
		parm.put("isRecommend", topic.getIsRecommend());
		parm.put("allowComment", topic.getAllowComment());
		parm.put("startTime", startTime);
		parm.put("createUserId", createUserId);
		parm.put("userId", userId);
		parm.put("endTime", endTime);
		parm.put("orderStr", orderStr);
		parm.put("status", topic.getStatus());
		parm.put("isAd", isAd);
		List lst = topicDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}
	
	@Override
	public DataGrid selectMyVote(PageParam param, String userId) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("userId", userId);
		List lst = topicDao.selectMyVote(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	
	@Override
	public String addTopic(Topic topic) {
		Topic parm = new Topic();
		parm.setTopicDesc(topic.getTopicDesc());
		List lst = topicDao.select(parm);
		if(lst.size()==0){
			int res = topicDao.insert(topic);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.topic.unique";//名称已存在
		}
	}

	@Override
	public String updateTopic(Topic topic) {
		Topic parm = new Topic();
		parm.setTopicDesc(topic.getTopicDesc());
		Topic obj = topicDao.selectOne(parm);
		if(obj==null || obj.getId().equals(topic.getId())){
			if(super.updateByPrimaryKeyCache(topic,topic.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.topic.unique";//此名称已存在
		}
	}

	@Override
	public boolean deleteTopics(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					Topic r = selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, Topic.class))count++;
					}
				}
			}
		}
		return count>0;
	}
	/**
	 * 调用缓存机制
	 */
	@Override
	public Topic selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Topic.class);
	}

	@Override
	public Topic selectById(String id) {
		return topicDao.selectByPrimaryKey(id);
	}

	@Override
	public List selectHotList(String titles,String curruserId,Integer page,Integer size) {
		Map parm = new HashMap();
		parm.put("titles", titles);
		parm.put("curruserId", curruserId);
		if(page!=null&&size!=null){
			parm.put("start", (page-1)*size);
			parm.put("size", size);
		}
		return topicDao.selectHotList(parm);
	}

	@Override
	public List selectNewList(String titles,String curruserId,Integer page,Integer size) {
		Map parm = new HashMap();
		parm.put("titles", titles);
		parm.put("curruserId", curruserId);
		if(page!=null&&size!=null){
			parm.put("start", (page-1)*size);
			parm.put("size", size);
		}
		return topicDao.selectNewList(parm);
	}

	@Override
	public List selectFireList(String titles,String curruserId,Integer page,Integer size) {
		Map parm = new HashMap();
		parm.put("titles", titles);
		parm.put("curruserId", curruserId);
		if(page!=null&&size!=null){
			parm.put("start", (page-1)*size);
			parm.put("size", size);
		}
		return topicDao.selectFireList(parm);
	}

	@Override
	public Map<String, Object> selectTopicById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = null;
		list = topicDao.selectTopicById(id);
		if(list!=null&&list.size()>0){
			map = (Map<String, Object>) list.get(0);
		}
		return map;
	}

	@Override
	public List selectMyTopic(String createUserId,Integer page,Integer size) {
		Map parm = new HashMap();
		parm.put("createUserId", createUserId);
		if(page!=null&&size!=null){
			parm.put("start", (page-1)*size);
			parm.put("size", size);
		}
		List lst = topicDao.selectMyTopic(parm);
		return lst;
	}

	@Override
	public Integer attentionCount(String fkId) {
		Map parm = new HashMap();
		parm.put("fkId", fkId);
		return topicDao.attentionCount(parm);
	}

	@Override
	public List selectJxTopic(String curruserId) {
		Map parm = new HashMap();
		parm.put("curruserId", curruserId);
		return topicDao.selectJxTopic(parm);
	}

	@Override
	public List selectMyAttention(String userId) {
		Map parm = new HashMap();
		parm.put("userId", userId);
		List lst = topicDao.selectMyAttention(parm);
		return lst;
	}

	@Override
	public List selectMyVote(String userId) {
		Map parm = new HashMap();
		parm.put("userId", userId);
		List lst = topicDao.selectMyVote(parm);
		return lst;
	}

	@Override
	public List selectMyTopicMy(String createUserId, Integer page, Integer size) {
		Map parm = new HashMap();
		parm.put("createUserId", createUserId);
		if(page!=null&&size!=null){
			parm.put("start", (page-1)*size);
			parm.put("size", size);
		}
		List lst = topicDao.selectMyTopicMy(parm);
		return lst;
	}
//============================================APP=======================================
	/**
	 * 我参加的话题
	 */
	@Override
	public List selectMyVoteApp(String userId, int page, int size) {
		Map parm = new HashMap();
		parm.put("userId", userId);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		List lst = topicDao.selectMyVoteApp(parm);
		return lst;
	}
	/**
	 * 我收藏的话题
	 */
	@Override
	public List selectMyAttentionApp(String userId, int page, int size) {
		Map parm = new HashMap();
		parm.put("userId", userId);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		List lst = topicDao.selectMyAttentionApp(parm);
		return lst;
	}

}