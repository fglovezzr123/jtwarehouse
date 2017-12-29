package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.ClubClass;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;

/**
 * 
 * @author zhangzheng
 * 会所分类管理
 */
public interface IClubClassService {

	/**
	 * 添加
	 * @param clubclass
	 * @return
	 */
	String addclubclass(ClubClass clubclass);

	/**
	 * 修改
	 * @param clubclass
	 * @return
	 */
	String updateclubclass(ClubClass clubclass);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	String deleteclubclasses(String id);

	/**
	 * 根据id获取
	 * @param key
	 * @return
	 */
	public ClubClass selectByPrimaryKey(String key);
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	public ClubClass selectById(String id);
	
	/**
	 * 查询所有分类list
	 * @param classRoot
	 * @return
	 */
	public List<Map<String, Object>> selectAllclassMap(String classRoot);

	/**
	 * 查询所有分类list
	 * @return
	 */
	Object selectAllClassMap();

	/**
	 * 查询一级分类list
	 * @return
	 */
	List<Map> selectonelevelclass();

	/**
	 * 根据上级id查下级分类
	 * @param id
	 * @return
	 */
	List<Map> querybyparent(String id);
	
}
