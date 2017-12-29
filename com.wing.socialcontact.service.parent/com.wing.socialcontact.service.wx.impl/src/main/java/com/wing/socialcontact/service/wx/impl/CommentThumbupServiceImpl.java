package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ICommentThumbupService;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.CommentThumbup;
import com.wing.socialcontact.service.wx.dao.CommentThumbupDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author liangwj
 * @date 2017-04-04 15:00:56
 * @version 1.0
 */
@Service
public class CommentThumbupServiceImpl extends BaseServiceImpl<CommentThumbup> implements ICommentThumbupService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:CommentThumbup:\" + ";
	
	@Resource
	private CommentThumbupDao commentThumbupDao;
	
	
	
	
	@Override
	public List selectAllCommentThumbup(CommentThumbup commentThumbup) {
		return commentThumbupDao.selectAllCommentThumbupMap(commentThumbup);
	}

	@Override
	public DataGrid selectCommentThumbups(PageParam param, CommentThumbup commentThumbup) {
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("orderStr", orderStr);
		List lst = commentThumbupDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	@Override
	public String addCommentThumbup(CommentThumbup commentThumbup) {
		
		int res = commentThumbupDao.insert(commentThumbup);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
		
	}

	@Override
	public String updateCommentThumbup(CommentThumbup commentThumbup) {
		
		if(super.updateByPrimaryKeyCache(commentThumbup,commentThumbup.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
		
	}

	@Override
	public boolean deleteCommentThumbups(String[] ids) {
		//等待删除的对象集合
				int count = 0;
				for(String id:ids){
					if(StringUtils.isNotBlank(id)){
						String[] myids = id.split(",");
						for (String string : myids) {
							CommentThumbup r=selectByPrimaryKey(string);
							if(r!=null){
								if(super.deleteByPrimaryKeyCache(string, CommentThumbup.class))count++;
							}
						}
					}
				}
				return count>0;
	}
	
	@Override
	public boolean deleteCommentThumbups(String id) {
		//等待删除的对象集合
		int count = 0;
		if(StringUtils.isNotBlank(id)){
			String[] myids = id.split(",");
			for (String string : myids) {
				CommentThumbup r=selectByPrimaryKey(string);
				if(r!=null){
					if(super.deleteByPrimaryKeyCache(string, CommentThumbup.class))count++;
				}
			}
		}
		return count>0;
	}

	@Override
	public CommentThumbup selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, CommentThumbup.class);
	}

	@Override
	public CommentThumbup selectById(String id) {
		return commentThumbupDao.selectByPrimaryKey(id);
	}
	
	@Override
	public int selectcount(CommentThumbup t){
		return commentThumbupDao.selectcount(t);
	}

}