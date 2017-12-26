package com.wing.socialcontact.service.wx.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.TjyLibrary;

/**
 * 文库管理
 * @author zhangzheng
 *
 */
public interface ILibraryService {

	
	/**
	 * 文库列表查询
	 * @param param
	 * @param libraryc
	 * @param stime
	 * @param etime
	 * @return
	 */
	public DataGrid selectLibrary(PageParam param, TjyLibrary libraryc,Date stime,Date etime);

	/**
	 * 新增文库
	 * @param dto
	 * @return
	 */
	public String addTjyLibrary(TjyLibrary dto);

	/**
	 * 根据id获取文库
	 * @param id
	 * @return
	 */
	public TjyLibrary getTjyLibraryByid(String id);

	/**
	 * 更新文库
	 * @param dto
	 * @return
	 */
	public String updateTjyLibrary(TjyLibrary dto);

	/**
	 * 批量删除文库
	 * @param id
	 * @return
	 */
	public boolean deleteTjyLibrary(String[] id);

	/**
	 * 根据类别id获取文库list
	 * @param classid
	 * @return
	 */
	public List<Map> getTjyLibraryByclassid(String classid);

	/**
	 * 根据文库id获取详情
	 * @param libraryid
	 * @return
	 */
	public Map getLibraryByid(String libraryid);

	/**
	 * 根据文库id添加阅读次数
	 * @param libraryid
	 */
	public void addreadtimes(String libraryid);

	/**
	 * 接口条件分页查询文库列表
	 * @param classid
	 * @param page
	 * @param size
	 * @param today
	 * @param key
	 * @param readtimes
	 * @return
	 */
	public List<Map> getTjyLibraryByTerm(String classid, Integer page,
			Integer size, Integer today, String key, Integer readtimes);

	/**
	 * 根据一级分类id查询文章
	 * @param classid
	 * @return
	 */
	public List<Map> selbyonelevelid(String classid);

	public List<Map> getLibraryByoneLevel(String classid, Integer page,
			Integer size, Integer today, String key, Integer readtimes);

}
