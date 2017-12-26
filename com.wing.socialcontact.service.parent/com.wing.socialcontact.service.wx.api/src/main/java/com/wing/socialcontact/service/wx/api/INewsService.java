package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.TjyNews;

/**
 * 
 * @author zhangfan
 *
 */
public interface INewsService{
	/**
	 * 条件查询
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllNews(PageParam param,TjyNews news,String startTime,String endTime);
	/**
	 * 添加
	 * @param newsClass
	 * @return
	 */
	public String addNews(TjyNews news);
	/**
	 * 更新
	 * @param newsClass
	 * @return
	 */
	public String updateNews(TjyNews news);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteNews(String[] ids);

	public TjyNews selectByPrimaryKey(String key);
	
	public TjyNews selectById(String id);
	
	/**
	 * 首页热推条件查询
	 * @param param
	 * @param newsClass
	 * @return
	 */
	public DataGrid selectAllHotNews(PageParam param,TjyNews news,String startTime,String endTime,Integer isHot);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectNewsById(String id);
	
	/**
	 * 根据分类查询(前台)
	 * @return
	 */
	public List selectByParamFront(String types,String newsTitle,Integer page,Integer size,String classRoot,
			Integer isHot);
	
	/**
	 * 查询商友访谈列表
	 * @param classRoot
	 * @return
	 */
	public List selectViewNews(String classRoot,Integer page,Integer size);
	/**
	 * 查询首页新闻轮播
	 * @return
	 */
	public List selectHotNews();
	/**
	 * 根据类型查询html
	 * @param types
	 * @return
	 */
	public List selectHtmlByType(String types);
	
	public Map<String, Object> selectNewsByIdHt(String id);
	public String updateNews1(TjyNews news);
}
