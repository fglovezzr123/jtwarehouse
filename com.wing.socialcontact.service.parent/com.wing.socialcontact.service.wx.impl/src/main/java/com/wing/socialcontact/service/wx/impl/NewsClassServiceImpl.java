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
import com.wing.socialcontact.service.wx.api.INewsClassService;
import com.wing.socialcontact.service.wx.bean.TjyNews;
import com.wing.socialcontact.service.wx.bean.TjyNewsClass;
import com.wing.socialcontact.service.wx.dao.NewsClassDao;
import com.wing.socialcontact.service.wx.dao.NewsDao;
import com.wing.socialcontact.sys.bean.SyMenu;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;


/**
 * 资讯分类管理
 * @author zhangfan
 *
 */
@Service
public class NewsClassServiceImpl extends BaseServiceImpl<TjyNewsClass> implements INewsClassService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:NewsClass:\" + ";

	@Resource
	private NewsClassDao newsClassDao;
	@Resource
	private NewsDao newsDao;
	
	@Override
	public List<TjyNewsClass> selectAllNewsClass() {
		return newsClassDao.selectAllNewsClassMap();
	}

	@Override
	public DataGrid selectNewsClasses(PageParam param, TjyNewsClass newsClass,String startTime,String endTime) {
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("tagName", newsClass.getTagName());
		parm.put("status", newsClass.getStatus());
		parm.put("startTime", startTime);
		parm.put("endTime", endTime);
		parm.put("orderStr", orderStr);
		List lst = newsClassDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	@Override
	public String addNewsClass(TjyNewsClass newsClass) {
		TjyNewsClass parm = new TjyNewsClass();
		parm.setTagName(newsClass.getTagName());
		List lst = newsClassDao.select(parm);
		if(lst.size()==0){
			int res = newsClassDao.insert(newsClass);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.newsclass.unique";//标签名称已存在
		}
	}

	@Override
	public String updateNewsClass(TjyNewsClass newsClass) {
		TjyNewsClass parm = new TjyNewsClass();
		parm.setTagName(newsClass.getTagName());
		TjyNewsClass obj = newsClassDao.selectOne(parm);
		if(obj==null || obj.getId().equals(newsClass.getId())){
			if(super.updateByPrimaryKeyCache(newsClass,newsClass.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.newsclass.unique";//此名称已存在
		}
	}

	@Override
	public TjyNewsClass selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, TjyNewsClass.class);
	}

	@Override
	public TjyNewsClass selectById(String id) {
		return newsClassDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Map<String, Object>> selectAllclassMap(String classRoot) {
		Map parm = new HashMap();
		parm.put("classRoot", classRoot);
		return newsClassDao.selectAllclassMap(parm);
	}

	@Override
	public String deleteNewsClass(String id) {
		Map parm = new HashMap();
		parm.put("parentId", id);
		List lst = newsClassDao.selectByParam(parm);
		if(lst.size()>0){
			return MsgConfig.MSG_KEY_HASCHILD;//分类下属还有子分类，无法删除
		}else{
			//查询该分类下是否有信息
			Map parm1 = new HashMap();
			parm1.put("types", id);
			List list = newsDao.selectByParam(parm1);
			if(list!=null&&list.size()>0){
				return MsgConfig.MSG_KEY_ERROR_NODEL;
			}
			TjyNewsClass nc = selectByPrimaryKey(id);
			if(nc!=null){
				if(super.deleteByPrimaryKeyCache(nc.getId(), TjyNewsClass.class)){
					return MsgConfig.MSG_KEY_SUCCESS;
				}else{
					return MsgConfig.MSG_KEY_FAIL;
				}
			}else{
				return MsgConfig.MSG_KEY_NODATA;
			}
		}
	}

	@Override
	public List<Map<String, Object>> selectNewsclassMap() {
		return newsClassDao.selectNewsclassMap();
	}

	@Override
	public List<Map<String, Object>> selectFrontClass(String classRoot) {
		Map parm = new HashMap();
		parm.put("classRoot", classRoot);
		return newsClassDao.selectFrontClass(parm);
	}

}
