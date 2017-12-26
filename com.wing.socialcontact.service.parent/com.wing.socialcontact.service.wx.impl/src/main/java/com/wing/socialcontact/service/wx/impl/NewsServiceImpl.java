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
import com.wing.socialcontact.service.wx.api.INewsService;
import com.wing.socialcontact.service.wx.bean.TjyNews;
import com.wing.socialcontact.service.wx.dao.NewsDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;


/**
 * 资讯分类管理
 * @author zhangfan
 *
 */
@Service
public class NewsServiceImpl extends BaseServiceImpl<TjyNews> implements INewsService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:News:\" + ";

	@Resource
	private NewsDao newsDao;

	@Override
	public DataGrid selectAllNews(PageParam param, TjyNews news,String startTime,String endTime) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		String types = news.getTypes();
		if(types!=null&&(types.equals("0")||types.equals(news.getClassRoot()))){
			types = null;
		}
		Map parm = new HashMap();
		parm.put("newsTitle", news.getNewsTitle());
		parm.put("types",types);
		parm.put("classRoot", news.getClassRoot());
		parm.put("viewUser", news.getViewUser());
		parm.put("isRecommend", news.getIsRecommend());
		parm.put("startTime", startTime);
		parm.put("endTime", endTime);
		parm.put("orderStr", orderStr);
		List lst = newsDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addNews(TjyNews news) {
		TjyNews parm = new TjyNews();
		parm.setNewsTitle(news.getNewsTitle());
		List lst = newsDao.select(parm);
		if(lst.size()==0){
			int res = newsDao.insert(news);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.news.unique";//名称已存在
		}
	}

	@Override
	public String updateNews(TjyNews news) {
		TjyNews parm = new TjyNews();
		parm.setNewsTitle(news.getNewsTitle());
		TjyNews obj = newsDao.selectOne(parm);
		if(obj==null || obj.getId().equals(news.getId())){
			if(super.updateByPrimaryKeyCache(news,news.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.news.unique";//此名称已存在
		}
	}
	
	@Override
	public String updateNews1(TjyNews news) {
			if(super.updateByPrimaryKeyCache(news,news.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}

	@Override
	public boolean deleteNews(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					TjyNews r=selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, TjyNews.class))count++;
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
	public TjyNews selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, TjyNews.class);
	}

	@Override
	public TjyNews selectById(String id) {
		return newsDao.selectByPrimaryKey(id);
	}

	@Override
	public DataGrid selectAllHotNews(PageParam param, TjyNews news,String startTime,String endTime,Integer isHot) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("newsTitle", news.getNewsTitle());
		parm.put("startTime", startTime);
		parm.put("endTime", endTime);
		parm.put("isHot", isHot);
		parm.put("orderStr", orderStr);
		List lst = newsDao.selectHotByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public Map<String, Object> selectNewsById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = null;
		list = newsDao.selectNewsById(id);
		if(list!=null&&list.size()>0){
			map = (Map<String, Object>) list.get(0);
		}
		return map;
	}

	@Override
	public List selectByParamFront(String types,String newsTitle,Integer page,Integer size,
			String classRoot,Integer isHot) {
		Map parm = new HashMap();
		parm.put("types", types);
		parm.put("newsTitle", newsTitle);
		if(page!=null&&size!=null){
			parm.put("start", (page-1)*size);
			parm.put("size", size);
		}
		parm.put("isHot", isHot);
		parm.put("classRoot", classRoot);
		List lst = newsDao.selectByParamFront(parm);
		return lst;
	}

	@Override
	public List selectViewNews(String classRoot,Integer page,Integer size) {
		Map parm = new HashMap();
		parm.put("classRoot",classRoot);
		if(page!=null&&size!=null){
			parm.put("start", (page-1)*size);
			parm.put("size", size);
		}
		parm.put("isView",1);
		List lst = newsDao.selectByParam(parm);
		return lst;
	}

	@Override
	public List selectHotNews() {
		Map parm = new HashMap();
		parm.put("isHot", 1);
		List lst = newsDao.selectHotByParam(parm);
		return lst;
	}

	@Override
	public List selectHtmlByType(String types) {
		Map parm = new HashMap();
		parm.put("types", types);
		List list = newsDao.selectHtmlByParam(parm);
		return list;
	}

	@Override
	public Map<String, Object> selectNewsByIdHt(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = null;
		list = newsDao.selectNewsByIdHt(id);
		if(list!=null&&list.size()>0){
			map = (Map<String, Object>) list.get(0);
		}
		return map;
	}


}
