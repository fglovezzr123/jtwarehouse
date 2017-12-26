package com.wing.socialcontact.service.wx.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.TjyLibraryLive;

public interface ILibraryLiveService {

	/**
	 * 列表
	 * @param param
	 * @param t
	 * @return
	 */
	DataGrid selectAllLibraryLive(PageParam param, TjyLibraryLive t,Date stime ,Date etime);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	TjyLibraryLive getLibraryLive(String id);

	/**
	 * 新增
	 * @param t
	 */
	int insertTjyLibraryLive(TjyLibraryLive t);

	/**
	 * 修改
	 * @param t
	 */
	int updateTjyLibraryLive(TjyLibraryLive t);

	/**
	 * 
	 * @param page
	 * @param size
	 * @param type
	 * @param key
	 * @return
	 */
	List<Map> libraryLiveList(Integer page, Integer size, Integer type,
			String key);

	/**
	 *  当前时间大于开始时间 ，状态未未开始
	 * @return
	 */
	List<TjyLibraryLive> selectWksLives();

	/**
	 * 当前时间大于结束时间  状态为进行中
	 */
	List<TjyLibraryLive> selectJxzLives();

	/**
	 * 
	 * @param i
	 * @param id
	 */
	void updatestatusbyid(int i, String id);

}
