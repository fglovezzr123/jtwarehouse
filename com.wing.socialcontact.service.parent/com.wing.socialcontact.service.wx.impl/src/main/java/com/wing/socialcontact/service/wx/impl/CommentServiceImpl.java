package com.wing.socialcontact.service.wx.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.dao.CommentDao;
import com.wing.socialcontact.service.wx.api.ICommentService;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.Fund;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author liangwj
 * @date 2017-03-27 09:01:08
 * @version 1.0
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements ICommentService{
	

	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:Comment:\" + ";
	
	@Resource
	private CommentDao commentDao;
	
	@Override
	public List selectAllComment(Comment comment) {
		return commentDao.selectAllCommentMap(comment);
	}
	
	public List selectAllComment(Comment comment,int pageNum, int pageSize){
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("fkId", comment.getFkId());
		param.put("cmeType", comment.getCmeType());
		param.put("fyFlag", 1);
		param.put("start", (pageNum - 1) * pageSize);
		param.put("size", pageSize);
		//param.put("orderby", "create_time desc");
		// TODO Auto-generated method stub
		return commentDao.selectAllCommentMap2(param);
	}

	@Override
	public DataGrid selectComments(PageParam param, Comment comment,String startTime, String endTime,String trueName,String title,String mobile,String userId) {
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("cmeType", comment.getCmeType());
		parm.put("cmeDesc", comment.getCmeDesc());
		parm.put("startTime", startTime);
		parm.put("endTime", endTime);
		parm.put("trueName", trueName);
		parm.put("title", title);
		parm.put("mobile", mobile);
		parm.put("userId", userId);
		//parm.put("orderStr", orderStr);
		List lst = commentDao.selectAllCommentMap3(parm);
		
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	@Override
	public String addComment(Comment comment) {
		int res = commentDao.insert(comment);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateComment(Comment comment) {
		
		if(super.updateByPrimaryKeyCache(comment,comment.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
		
	}

	@Override
	public boolean deleteComments(String[] ids) {
		//等待删除的对象集合
				int count = 0;
				for(String id:ids){
					if(StringUtils.isNotBlank(id)){
						String[] myids = id.split(",");
						for (String string : myids) {
							Comment r=selectByPrimaryKey(string);
							if(r!=null){
								if(super.deleteByPrimaryKeyCache(string, Comment.class))count++;
							}
						}
					}
				}
				return count>0;
	}

	@Override
	public Comment selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Comment.class);
	}

	@Override
	public Comment selectById(String id) {
		return commentDao.selectByPrimaryKey(id);
	}
	@Override
	public List queryCommentbyPid(String pid) {
		return commentDao.queryCommentByPid(pid);
	}

	@Override
	public List selectAllCommentbd(Comment comment) {
		return commentDao.selectAllCommentMap4(comment);
	}
//===================================APP==============================
	@Override
	public void deleteCommentsApp(String commentId) {
		super.deleteByPrimaryKeyCache(commentId, Comment.class);
		Comment comment = new Comment();
		comment.setParentId(commentId);
		commentDao.delete(comment);
	}

	@Override
	public List<Map<String, Object>> selectAllCommentApp(Comment comment,
			int page, int size) {
		Map parm = new HashMap();
		parm.put("fkId", comment.getFkId());
		parm.put("parentId", comment.getParentId());
		parm.put("cmeType!=", comment.getCmeType());
		parm.put("userId", comment.getUserId());
		parm.put("cmeDesc!=", comment.getCmeDesc());
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		return commentDao.selectAllCommentMapApp(parm);
	}
}