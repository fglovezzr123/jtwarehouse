package com.wing.socialcontact.service.wx.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.ICommentService;
import com.wing.socialcontact.service.wx.api.IDynamicOpLogService;
import com.wing.socialcontact.service.wx.api.IDynamicService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.Dynamic;
import com.wing.socialcontact.service.wx.bean.DynamicOpLog;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.dao.DynamicDao;
import com.wing.socialcontact.service.wx.dao.DynamicOpLogDao;
import com.wing.socialcontact.service.wx.dao.DynamicPicDao;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:05
 * @version 1.0
 */
@Service
public class DynamicServiceImpl implements IDynamicService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private DynamicDao dynamicDao;
	
	@Resource
	private DynamicPicDao dynamicPicDao;
	
	@Resource
	private DynamicOpLogDao dynamicOpLogDao;
	
	@Autowired
	private ICommentService commentService;
	
	@Autowired
	private IDynamicOpLogService dynamicOpLogService;
	
	@Autowired
	private ITjyUserService tjyUserService;

	@Override
	public int insertDynamicSignup(Dynamic t) {
		return dynamicDao.insert(t);
	}

	@Override
	public int updateDynamicSignup(Dynamic t) {
		return dynamicDao.updateByPrimaryKeySelective(t);
	}

	@Override
	public int deleteDynamicSignup(Dynamic t) {
		return dynamicDao.delete(t);
	}

	@Override
	public List<Dynamic> queryDynamicSignup(Dynamic t) {
		return dynamicDao.select(t);
	}

	@Override
	public Dynamic getDynamicSignup(String id) {
		return dynamicDao.selectByPrimaryKey(id);
	}

	@Override
	public DataGrid selectAllDynamicSignup(PageParam param, String dyContent,String userName,String visitQuantity,String issuedDate,String user_id) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("dyContent", dyContent);
		parm.put("dyType", "0");
		parm.put("userName", userName);
		parm.put("visitQuantity", visitQuantity);
		parm.put("issuedDate", issuedDate);
		parm.put("user_id", user_id);
		List list = dynamicDao.selectByParam(parm);
		PageInfo page = new PageInfo(list);
		data.setRows(list);
		data.setTotal(page.getTotal());
		return data;
	}
	@Override
	public DataGrid selectAllDynamicSignup2(PageParam param, String dyContent,String userName,String visitQuantity,String issuedDate,String user_id) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("dyContent", dyContent);
		parm.put("userName", userName);
		parm.put("visitQuantity", visitQuantity);
		parm.put("issuedDate", issuedDate);
		parm.put("user_id", user_id);
		List list = dynamicDao.selectByParam2(parm);
		PageInfo page = new PageInfo(list);
		data.setRows(list);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public List selectMyFriendDynamic(String userId, int pageNum, int pageSize) {
		Page page = null;
		if(pageNum != 0 && pageSize !=0){
			page = PageHelper.startPage(pageNum, pageSize,true);
		}
		List list  = dynamicDao.selectFriendUserDynamicByUserId(userId);
		if(pageNum != 0 && pageSize !=0){
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if(pageSize != 0){
				totalPageCount = (int)(totalCount/pageSize);
				if(totalCount % pageSize > 0){
					totalPageCount = totalPageCount +1;
				}
				if(totalPageCount < pageNum){
					return  new ArrayList();
				}
			}
		}
		List resultList = new ArrayList();
		for(int i = 0;i<list.size();i++){
			Map valueMap = new HashMap();
			Map dynamicMap = (Map)list.get(i);
			String dynamicId = (String)dynamicMap.get("id");
			valueMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(dynamicId);
			valueMap.put("picList", picList);
			Map paramMap = new HashMap();
			paramMap.put("dynamicId", dynamicId);
			paramMap.put("type", 1);
			int lickCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("lickCount", lickCount);
			paramMap.put("type", 2);
			int forwardCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("forwardCount", forwardCount);
			
			Comment comment = new Comment();
			comment.setFkId(dynamicId);
			comment.setCmeType(String.valueOf(5));
			List commentList = commentService.selectAllComment(comment);
			
			//获取第一条评论
			if(commentList != null && commentList.size() != 0){
				valueMap.put("commonCount", commentList.size());
				
				Map pcomment = (Map)commentList.get(0);
				valueMap.put("pcomment", pcomment);
				String commentUserId = (String)pcomment.get("userId");
				if (!StringUtil.isEmpty(commentUserId)) {
					TjyUser tjyUser = tjyUserService.selectById(commentUserId);
					valueMap.put("commonUser", tjyUser);
				}
				
				//获取第一条评论的第一个回复
				Map subcomment = null;
				String pId = (String)pcomment.get("id");
				List<Map<String, Object>> subCommentList = commentService
						.queryCommentbyPid(pId);
				if(subCommentList != null && subCommentList.size() != 0){
					subcomment = (Map)subCommentList.get(0);
					valueMap.put("subcomment", subcomment);
					String subCommentUserId = (String)subcomment.get("userId");
					
					if (!StringUtil.isEmpty(subCommentUserId)) {
						TjyUser tjyUser = tjyUserService.selectById(subCommentUserId);
						valueMap.put("subCommonUser", tjyUser);
					}
					valueMap.put("subCommonCount", 1);
				}else{
					valueMap.put("subCommonCount", 0);
				}
				
			}else{
				valueMap.put("commonCount", 0);
			}
			
			resultList.add(valueMap);
		}
		
		// TODO Auto-generated method stub
		return resultList;
	}
	
	@Override
	public Map selectDynamicById(String userId,String dynamicId) {
		Map paramMap1 = new HashMap();
		paramMap1.put("userId", userId);
		paramMap1.put("dynamicId", dynamicId);
		Map dynamicMap = dynamicDao.selectDynamicById(paramMap1);
		Map valueMap = null;
		if(dynamicMap != null){
			valueMap = new HashMap();
			valueMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(dynamicId);
			valueMap.put("picList", picList);
			Map paramMap = new HashMap();
			paramMap.put("dynamicId", dynamicId);
			paramMap.put("type", 1);
			int lickCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("lickCount", lickCount);
			paramMap.put("type", 2);
			int forwardCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("forwardCount", forwardCount);
			
			Comment comment = new Comment();
			comment.setFkId(dynamicId);
			comment.setCmeType(String.valueOf(5));
			List commentList = commentService.selectAllComment(comment);
			
			//获取第一条评论
			if(commentList != null && commentList.size() != 0){
				valueMap.put("commonCount", commentList.size());
				
				Map pcomment = (Map)commentList.get(0);
				valueMap.put("pcomment", pcomment);
				String commentUserId = (String)pcomment.get("userId");
				if (!StringUtil.isEmpty(commentUserId)) {
					TjyUser tjyUser = tjyUserService.selectById(commentUserId);
					valueMap.put("commonUser", tjyUser);
				}
				
				//获取第一条评论的第一个回复
				Map subcomment = null;
				String pId = (String)pcomment.get("id");
				List<Map<String, Object>> subCommentList = commentService
						.queryCommentbyPid(pId);
				if(subCommentList != null && subCommentList.size() != 0){
					subcomment = (Map)subCommentList.get(0);
					valueMap.put("subcomment", subcomment);
					String subCommentUserId = (String)subcomment.get("userId");
					
					if (!StringUtil.isEmpty(subCommentUserId)) {
						TjyUser tjyUser = tjyUserService.selectById(subCommentUserId);
						valueMap.put("subCommonUser", tjyUser);
					}
					valueMap.put("subCommonCount", 1);
				}else{
					valueMap.put("subCommonCount", 0);
				}
				
			}else{
				valueMap.put("commonCount", 0);
			}
		}
		return valueMap;
	}
	@Override
	public List selectMyFollowDynamic(String userId, int pageNum, int pageSize) {
		Page page = null;
		if(pageNum != 0 && pageSize !=0){
			page = PageHelper.startPage(pageNum, pageSize,true);
		}
		List list  = dynamicDao.selectFollowUserDynamicByUserId(userId);
		if(pageNum != 0 && pageSize !=0){
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if(pageSize != 0){
				totalPageCount = (int)(totalCount/pageSize);
				if(totalCount % pageSize > 0){
					totalPageCount = totalPageCount +1;
				}
				if(totalPageCount < pageNum){
					return  new ArrayList();
				}
			}
		}
		List resultList = new ArrayList();
		for(int i = 0;i<list.size();i++){
			Map valueMap = new HashMap();
			Map dynamicMap = (Map)list.get(i);
			String dynamicId = (String)dynamicMap.get("id");
			valueMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(dynamicId);
			valueMap.put("picList", picList);
			Map paramMap = new HashMap();
			paramMap.put("dynamicId", dynamicId);
			paramMap.put("type", 1);
			int lickCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("lickCount", lickCount);
			paramMap.put("type", 2);
			int forwardCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("forwardCount", forwardCount);
			
			Comment comment = new Comment();
			comment.setFkId(dynamicId);
			comment.setCmeType(String.valueOf(5));
			List commentList = commentService.selectAllComment(comment);
			
			//获取第一条评论
			if(commentList != null && commentList.size() != 0){
				valueMap.put("commonCount", commentList.size());
				
				Map pcomment = (Map)commentList.get(0);
				valueMap.put("pcomment", pcomment);
				String commentUserId = (String)pcomment.get("userId");
				if (!StringUtil.isEmpty(commentUserId)) {
					TjyUser tjyUser = tjyUserService.selectById(commentUserId);
					valueMap.put("commonUser", tjyUser);
				}
				
				//获取第一条评论的第一个回复
				Map subcomment = null;
				String pId = (String)pcomment.get("id");
				List<Map<String, Object>> subCommentList = commentService
						.queryCommentbyPid(pId);
				if(subCommentList != null && subCommentList.size() != 0){
					subcomment = (Map)subCommentList.get(0);
					valueMap.put("subcomment", subcomment);
					String subCommentUserId = (String)subcomment.get("userId");
					
					if (!StringUtil.isEmpty(subCommentUserId)) {
						TjyUser tjyUser = tjyUserService.selectById(subCommentUserId);
						valueMap.put("subCommonUser", tjyUser);
					}
					valueMap.put("subCommonCount", 1);
				}else{
					valueMap.put("subCommonCount", 0);
				}
				
			}else{
				valueMap.put("commonCount", 0);
			}
			
			resultList.add(valueMap);
		}
		
		// TODO Auto-generated method stub
		return resultList;
	}

	@Override
	public Map getDynamicMapById(String id) {
		Map requstMap = new HashMap();
		Map parm = new HashMap();
		parm.put("dynamicId", id);
		List list = dynamicDao.selectByParam(parm);
		if(list != null && list.size() != 0){
			Map dynamicMap = (Map)list.get(0);
			requstMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(id);
			requstMap.put("picList", picList);
		}
		return requstMap;
	}
	@Override
	public Map getDynamicMapById2(String id) {
		Map requstMap = new HashMap();
		Map parm = new HashMap();
		parm.put("dynamicId", id);
		List list = dynamicDao.selectByParam2(parm);
		if(list != null && list.size() != 0){
			Map dynamicMap = (Map)list.get(0);
			requstMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(id);
			requstMap.put("picList", picList);
		}
		return requstMap;
	}

	@Override
	public void deleteDynamic(String id) throws RuntimeException {
		Dynamic dynamic = dynamicDao.selectByPrimaryKey(id);
		if(dynamic != null){
			dynamic.setStatus(0);
			dynamicDao.updateByPrimaryKeySelective(dynamic);
		}else{
			throw new RuntimeException("删除失败，未找到删除的对象！");
		}
	}

	@Override
	public List selectMyDynamicList(String userId, int pageNum, int pageSize) {
		Page page = null;
		if(pageNum != 0 && pageSize !=0){
			page = PageHelper.startPage(pageNum, pageSize,true);
		}
		List list  = dynamicDao.selectMyDynamicByUserId(userId);
		if(pageNum != 0 && pageSize !=0){
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if(pageSize != 0){
				totalPageCount = (int)(totalCount/pageSize);
				if(totalCount % pageSize > 0){
					totalPageCount = totalPageCount +1;
				}
				if(totalPageCount < pageNum){
					return  new ArrayList();
				}
			}
		}
		List resultList = new ArrayList();
		for(int i = 0;i<list.size();i++){
			Map valueMap = new HashMap();
			Map dynamicMap = (Map)list.get(i);
			String dynamicId = (String)dynamicMap.get("id");
			valueMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(dynamicId);
			valueMap.put("picList", picList);
			Map paramMap = new HashMap();
			paramMap.put("dynamicId", dynamicId);
			paramMap.put("type", 1);
			int lickCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("lickCount", lickCount);
			paramMap.put("type", 2);
			int forwardCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("forwardCount", forwardCount);
			
			Comment comment = new Comment();
			comment.setFkId(dynamicId);
			comment.setCmeType(String.valueOf(5));
			List commentList = commentService.selectAllComment(comment);
			
			//获取第一条评论
			if(commentList != null && commentList.size() != 0){
				valueMap.put("commonCount", commentList.size());
				
				Map pcomment = (Map)commentList.get(0);
				valueMap.put("pcomment", pcomment);
				String commentUserId = (String)pcomment.get("userId");
				if (!StringUtil.isEmpty(commentUserId)) {
					TjyUser tjyUser = tjyUserService.selectById(commentUserId);
					valueMap.put("commonUser", tjyUser);
				}
				
				//获取第一条评论的第一个回复
				Map subcomment = null;
				String pId = (String)pcomment.get("id");
				List<Map<String, Object>> subCommentList = commentService
						.queryCommentbyPid(pId);
				if(subCommentList != null && subCommentList.size() != 0){
					subcomment = (Map)subCommentList.get(0);
					valueMap.put("subcomment", subcomment);
					String subCommentUserId = (String)subcomment.get("userId");
					
					if (!StringUtil.isEmpty(subCommentUserId)) {
						TjyUser tjyUser = tjyUserService.selectById(subCommentUserId);
						valueMap.put("subCommonUser", tjyUser);
					}
					valueMap.put("subCommonCount", 1);
				}else{
					valueMap.put("subCommonCount", 0);
				}
				
			}else{
				valueMap.put("commonCount", 0);
			}
			
			resultList.add(valueMap);
		}
		
		// TODO Auto-generated method stub
		return resultList;
	}

	@Override
	public List selectAllUserDynamic(String userId, int pageNum, int pageSize,String dyContent,Long dynamicloadtime) {
		Page page = null;
		if(pageNum != 0 && pageSize !=0){
			page = PageHelper.startPage(pageNum, pageSize,true);
		}
		Map param = new HashMap();
		param.put("userId", userId);
		param.put("dyContent", dyContent);
		param.put("dynamicloadtime", dynamicloadtime);
		
		List list  = dynamicDao.selectAllUserDynamicByUserId(param);
		if(pageNum != 0 && pageSize !=0){
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if(pageSize != 0){
				totalPageCount = (int)(totalCount/pageSize);
				if(totalCount % pageSize > 0){
					totalPageCount = totalPageCount +1;
				}
				if(totalPageCount < pageNum){
					return  new ArrayList();
				}
			}
		}
		List resultList = new ArrayList();
		for(int i = 0;i<list.size();i++){
			Map valueMap = new HashMap();
			Map dynamicMap = (Map)list.get(i);
			String dynamicId = (String)dynamicMap.get("id");
			valueMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(dynamicId);
			valueMap.put("picList", picList);
			Map paramMap = new HashMap();
			paramMap.put("dynamicId", dynamicId);
			paramMap.put("type", 1);
			int lickCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("lickCount", lickCount);
			paramMap.put("type", 2);
			int forwardCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("forwardCount", forwardCount);
			
			Comment comment = new Comment();
			comment.setFkId(dynamicId);
			comment.setCmeType(String.valueOf(5));
			List commentList = commentService.selectAllComment(comment);
			
			//获取第一条评论
			if(commentList != null && commentList.size() != 0){
				valueMap.put("commonCount", commentList.size());
				
				Map pcomment = (Map)commentList.get(0);
				valueMap.put("pcomment", pcomment);
				String commentUserId = (String)pcomment.get("userId");
				if (!StringUtil.isEmpty(commentUserId)) {
					TjyUser tjyUser = tjyUserService.selectById(commentUserId);
					valueMap.put("commonUser", tjyUser);
				}
				
				//获取第一条评论的第一个回复
				Map subcomment = null;
				String pId = (String)pcomment.get("id");
				List<Map<String, Object>> subCommentList = commentService
						.queryCommentbyPid(pId);
				if(subCommentList != null && subCommentList.size() != 0){
					subcomment = (Map)subCommentList.get(0);
					valueMap.put("subcomment", subcomment);
					String subCommentUserId = (String)subcomment.get("userId");
					
					if (!StringUtil.isEmpty(subCommentUserId)) {
						TjyUser tjyUser = tjyUserService.selectById(subCommentUserId);
						valueMap.put("subCommonUser", tjyUser);
					}
					valueMap.put("subCommonCount", 1);
				}else{
					valueMap.put("subCommonCount", 0);
				}
				
			}else{
				valueMap.put("commonCount", 0);
			}
			
			resultList.add(valueMap);
		}
		
		// TODO Auto-generated method stub
		return resultList;
	}
	
	/**
	 * 获取商友最新动态
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List selectAllDynamic(int pageNum, int pageSize){
		Page page = null;
		if(pageNum != 0 && pageSize !=0){
			page = PageHelper.startPage(pageNum, pageSize,true);
		}
		List list  = dynamicDao.selectAllDynamic();
		if(pageNum != 0 && pageSize !=0){
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if(pageSize != 0){
				totalPageCount = (int)(totalCount/pageSize);
				if(totalCount % pageSize > 0){
					totalPageCount = totalPageCount +1;
				}
				if(totalPageCount < pageNum){
					return  new ArrayList();
				}
			}
		}
		List resultList = new ArrayList();
		for(int i = 0;i<list.size();i++){
			Map valueMap = new HashMap();
			Map dynamicMap = (Map)list.get(i);
			String dynamicId = (String)dynamicMap.get("id");
			valueMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(dynamicId);
			valueMap.put("picList", picList);
			Map paramMap = new HashMap();
			paramMap.put("dynamicId", dynamicId);
			paramMap.put("type", 1);
			int lickCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("lickCount", lickCount);
			paramMap.put("type", 2);
			int forwardCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("forwardCount", forwardCount);
			
			Comment comment = new Comment();
			comment.setFkId(dynamicId);
			comment.setCmeType(String.valueOf(5));
			List commentList = commentService.selectAllComment(comment);
			
			//获取第一条评论
			if(commentList != null && commentList.size() != 0){
				valueMap.put("commonCount", commentList.size());
				
				Map pcomment = (Map)commentList.get(0);
				valueMap.put("pcomment", pcomment);
				String commentUserId = (String)pcomment.get("userId");
				if (!StringUtil.isEmpty(commentUserId)) {
					TjyUser tjyUser = tjyUserService.selectById(commentUserId);
					valueMap.put("commonUser", tjyUser);
				}
				
				//获取第一条评论的第一个回复
				Map subcomment = null;
				String pId = (String)pcomment.get("id");
				List<Map<String, Object>> subCommentList = commentService
						.queryCommentbyPid(pId);
				if(subCommentList != null && subCommentList.size() != 0){
					subcomment = (Map)subCommentList.get(0);
					valueMap.put("subcomment", subcomment);
					String subCommentUserId = (String)subcomment.get("userId");
					
					if (!StringUtil.isEmpty(subCommentUserId)) {
						TjyUser tjyUser = tjyUserService.selectById(subCommentUserId);
						valueMap.put("subCommonUser", tjyUser);
					}
					valueMap.put("subCommonCount", 1);
				}else{
					valueMap.put("subCommonCount", 0);
				}
				
			}else{
				valueMap.put("commonCount", 0);
			}
			
			resultList.add(valueMap);
		}
		
		// TODO Auto-generated method stub
		return resultList;
	}

	@Override
	public List selectMyVisitUserDynamicList(String userId,String visitUserId,String isFriend,int pageNum, int pageSize) {
		Map paramMap1 = new HashMap();
		paramMap1.put("userId", userId);
		paramMap1.put("visitUserId", visitUserId);
		paramMap1.put("isFriend", isFriend);
		
		Page page = null;
		if(pageNum != 0 && pageSize !=0){
			page = PageHelper.startPage(pageNum, pageSize,true);
		}
		List list  = dynamicDao.selectVisitUserDynamicByParam(paramMap1);
		if(pageNum != 0 && pageSize !=0){
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if(pageSize != 0){
				totalPageCount = (int)(totalCount/pageSize);
				if(totalCount % pageSize > 0){
					totalPageCount = totalPageCount +1;
				}
				if(totalPageCount < pageNum){
					return  new ArrayList();
				}
			}
		}
		List resultList = new ArrayList();
		for(int i = 0;i<list.size();i++){
			Map valueMap = new HashMap();
			Map dynamicMap = (Map)list.get(i);
			String dynamicId = (String)dynamicMap.get("id");
			valueMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(dynamicId);
			valueMap.put("picList", picList);
			Map paramMap = new HashMap();
			paramMap.put("dynamicId", dynamicId);
			paramMap.put("type", 1);
			int lickCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("lickCount", lickCount);
			paramMap.put("type", 2);
			int forwardCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("forwardCount", forwardCount);
			
			Comment comment = new Comment();
			comment.setFkId(dynamicId);
			comment.setCmeType(String.valueOf(5));
			List commentList = commentService.selectAllComment(comment);
			
			//获取第一条评论
			if(commentList != null && commentList.size() != 0){
				valueMap.put("commonCount", commentList.size());
				
				Map pcomment = (Map)commentList.get(0);
				valueMap.put("pcomment", pcomment);
				String commentUserId = (String)pcomment.get("userId");
				if (!StringUtil.isEmpty(commentUserId)) {
					TjyUser tjyUser = tjyUserService.selectById(commentUserId);
					valueMap.put("commonUser", tjyUser);
				}
				
				//获取第一条评论的第一个回复
				Map subcomment = null;
				String pId = (String)pcomment.get("id");
				List<Map<String, Object>> subCommentList = commentService
						.queryCommentbyPid(pId);
				if(subCommentList != null && subCommentList.size() != 0){
					subcomment = (Map)subCommentList.get(0);
					valueMap.put("subcomment", subcomment);
					String subCommentUserId = (String)subcomment.get("userId");
					
					if (!StringUtil.isEmpty(subCommentUserId)) {
						TjyUser tjyUser = tjyUserService.selectById(subCommentUserId);
						valueMap.put("subCommonUser", tjyUser);
					}
					valueMap.put("subCommonCount", 1);
				}else{
					valueMap.put("subCommonCount", 0);
				}
				
			}else{
				valueMap.put("commonCount", 0);
			}
			
			resultList.add(valueMap);
		}
		
		// TODO Auto-generated method stub
		return resultList;
	}
	
	/**
	 * 获取未读商友动态数量
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public int selectCountAllUserDynamicByUserId(String userId,Long dynamicloadtime){
		Map param = new HashMap();
		param.put("userId", userId);
		param.put("dyContent",null );
		param.put("dynamicloadtime", dynamicloadtime);
		return dynamicDao.selectCountAllUserDynamicByUserId(param);
	}

	//=====================================================================APP方法
	
	@Override
	public List selectAllUserDynamic1(String userId, int pageNum, int pageSize,String dyContent,Long dynamicloadtime) {
		Page page = null;
		if(pageNum != 0 && pageSize !=0){
			page = PageHelper.startPage(pageNum, pageSize,true);
		}
		Map param = new HashMap();
		param.put("userId", userId);
		param.put("dyContent", dyContent);
		param.put("dynamicloadtime", dynamicloadtime);
		
		List list  = dynamicDao.selectAllUserDynamicByUserId(param);
		if(pageNum != 0 && pageSize !=0){
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if(pageSize != 0){
				totalPageCount = (int)(totalCount/pageSize);
				if(totalCount % pageSize > 0){
					totalPageCount = totalPageCount +1;
				}
				if(totalPageCount < pageNum){
					return  new ArrayList();
				}
			}
		}
		List resultList = new ArrayList();
		for(int i = 0;i<list.size();i++){
			Map valueMap = new HashMap();
			Map dynamicMap = (Map)list.get(i);
			String dynamicId = (String)dynamicMap.get("id");
			valueMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(dynamicId);
			valueMap.put("picList", picList);
			Map paramMap = new HashMap();
			paramMap.put("dynamicId", dynamicId);
			paramMap.put("type", 1);
			int lickCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("lickCount", lickCount);
			paramMap.put("type", 2);
			int forwardCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("forwardCount", forwardCount);
			//获取所有评论
			valueMap = getAllComment(valueMap,dynamicId);
			
			DynamicOpLog dynamicOpLogOld = new DynamicOpLog();
			dynamicOpLogOld.setUserId(userId);
			dynamicOpLogOld.setDynamicId(dynamicId);
			dynamicOpLogOld.setOpType(2);
			List dynamicOplogListOld = dynamicOpLogService.selectAllDynamicOpLog(dynamicOpLogOld);
			if (dynamicOplogListOld.size() > 0) {
				valueMap.put("isForward", 1);
			} else{
				valueMap.put("isForward", 0);
			}
			resultList.add(valueMap);
		}
		
		return resultList;
	}

	@Override
	public List selectMyFollowDynamic1(String userId, int pageNum, int pageSize) {
		Page page = null;
		if(pageNum != 0 && pageSize !=0){
			page = PageHelper.startPage(pageNum, pageSize,true);
		}
		List list  = dynamicDao.selectFollowUserDynamicByUserId(userId);
		if(pageNum != 0 && pageSize !=0){
			long totalCount = page.getTotal();
			int totalPageCount = 0;
			if(pageSize != 0){
				totalPageCount = (int)(totalCount/pageSize);
				if(totalCount % pageSize > 0){
					totalPageCount = totalPageCount +1;
				}
				if(totalPageCount < pageNum){
					return  new ArrayList();
				}
			}
		}
		List resultList = new ArrayList();
		for(int i = 0;i<list.size();i++){
			Map valueMap = new HashMap();
			Map dynamicMap = (Map)list.get(i);
			String dynamicId = (String)dynamicMap.get("id");
			valueMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(dynamicId);
			valueMap.put("picList", picList);
			Map paramMap = new HashMap();
			paramMap.put("dynamicId", dynamicId);
			paramMap.put("type", 1);
			int lickCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("lickCount", lickCount);
			paramMap.put("type", 2);
			int forwardCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("forwardCount", forwardCount);
			//获取所有评论
			valueMap = getAllComment(valueMap,dynamicId);
			
			DynamicOpLog dynamicOpLogOld = new DynamicOpLog();
			dynamicOpLogOld.setUserId(userId);
			dynamicOpLogOld.setDynamicId(dynamicId);
			dynamicOpLogOld.setOpType(2);
			List dynamicOplogListOld = dynamicOpLogService.selectAllDynamicOpLog(dynamicOpLogOld);
			if (dynamicOplogListOld.size() > 0) {
				valueMap.put("isForward", 1);
			} else{
				valueMap.put("isForward", 0);
			}
			resultList.add(valueMap);
		}
		return resultList;
	}
	
	@Override
	public Map selectDynamicById1(String userId,String dynamicId) {
		Map paramMap1 = new HashMap();
		paramMap1.put("userId", userId);
		paramMap1.put("dynamicId", dynamicId);
		Map dynamicMap = dynamicDao.selectDynamicById(paramMap1);
		Map valueMap = null;
		if(dynamicMap != null){
			valueMap = new HashMap();
			valueMap.put("dynamicMap", dynamicMap);
			List picList = dynamicPicDao.findDynamicPicListByDynamicId(dynamicId);
			valueMap.put("picList", picList);
			Map paramMap = new HashMap();
			paramMap.put("dynamicId", dynamicId);
			paramMap.put("type", 1);
			int lickCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("lickCount", lickCount);
			paramMap.put("type", 2);
			int forwardCount = dynamicOpLogDao.getCountByDynamicAndType(paramMap);
			valueMap.put("forwardCount", forwardCount);
			//获取所有评论
			valueMap = getAllComment(valueMap,dynamicId);
		}
		return valueMap;
	}

	//获取所有评论方法
			public Map getAllComment(Map valueMap,String dynamicId){
				Comment comment = new Comment();
				comment.setFkId(dynamicId);
				comment.setCmeType(String.valueOf(5));
				List commentList = commentService.selectAllComment(comment);
				int conmentSize  = 0;
				List<Map<String,Object>> commentListAll = new ArrayList();
				if(commentList != null && commentList.size() != 0){
					for(int x=0;x<commentList.size();x++){
						Map commentDetail = new HashMap();
						Map pcomment = (Map)commentList.get(x);
						TjyUser tjyUser = null;
						Map user1 = new HashMap();
						String commentUserId = (String)pcomment.get("userId");
						if (!StringUtil.isEmpty(commentUserId)) {
							tjyUser = tjyUserService.selectById(commentUserId);
							user1.put("id", tjyUser.getId());
							user1.put("nickname", tjyUser.getNickname());
							pcomment.put("user", user1);
							pcomment.put("trueName", tjyUser.getNickname());
						}
						commentDetail.put("pcomment", pcomment);
						conmentSize++;
						//获取回复
						Map subcomment = null;
						String pId = (String)pcomment.get("id");
						List<Map<String, Object>> subCommentList = commentService
								.queryCommentbyPid(pId);
						List<Map<String, Object>> subCommentListReturn = new ArrayList();
						for(int sx=0;sx<subCommentList.size();sx++){
							conmentSize++;
							subcomment = (Map)subCommentList.get(sx);
							String subCommentUserId = (String)subcomment.get("userId");
							if (!StringUtil.isEmpty(subCommentUserId)) {
								subcomment.put("user", user1);
							}
							subCommentListReturn.add(subcomment);
						}
						commentDetail.put("subComment", subCommentListReturn);
						commentListAll.add(commentDetail);
					}
					valueMap.put("commonCount", conmentSize);
					valueMap.put("comment", commentListAll);
				}else{
					valueMap.put("commonCount", 0);
				}
				return valueMap;
			}
}