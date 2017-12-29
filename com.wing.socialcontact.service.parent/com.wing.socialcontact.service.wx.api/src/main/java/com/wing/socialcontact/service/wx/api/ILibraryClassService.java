package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;

/**
 * 文库分类管理
 * @author zhangzheng
 *
 */
public interface ILibraryClassService {

	/**
	 * 文库分类列表
	 * @param param
	 * @param librarycla
	 * @return
	 */
	public DataGrid selectLibraryClass(PageParam param, TjyLibraryClass librarycla);

	/**
	 * 添加文库分类
	 * @param dto
	 * @return
	 */
	public String addTjyLibraryClass(TjyLibraryClass dto);
	
	/**
	 * 根据id获取文库分类详情
	 * @param id
	 * @return
	 */
	public TjyLibraryClass getTjyLibraryClassByid(String id);
	
	/**
	 * 更新文库分类
	 * @param dto
	 * @return
	 */
	public String updateTjyLibraryClass(TjyLibraryClass dto);
	
	/**
	 * 删除文库分类
	 * @param id
	 * @return
	 */
	public String deleteTjyLibraryClass(String id);
	
	/**
	 * 查询所有文库分类 返回实体类list
	 * @return
	 */
	public List<TjyLibraryClass> getAllClass();

	/**
	 * 查询所有文库分类 返回Map   list
	 * @return
	 */
	public List<Map<String,Object>> selectAllClassMap();

	/**
	 * 根据id获取文库分类详情
	 * @param id
	 * @return
	 */
	public TjyLibraryClass selectByPrimaryKey(String id);

	/**
	 * 根据一级分类id查询子分类
	 * @param id
	 * @return
	 */
	public List<Map> querybyparent(String id);
	/**
	 * 查询文库分类标签选择页数据
	 * @param param
	 * @param dto
	 * @return
	 */
	public Object selectClass(PageParam param, TjyLibraryClass dto);

	/**
	 * 查询一级分类
	 * @return
	 */
	public List<Map> selectonelevelclass();

	/**
	 * 查询推荐二级分类
	 * @return
	 */
	public List<Map> recommendclass();

	public List selectLevelOne(TjyLibraryClass parm);

	public List<Map> onelevelclass(Integer position);

	
}
