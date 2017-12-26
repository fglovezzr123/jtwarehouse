package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Club;

/**
 * 
 * @author zhangzheng
 * 会所管理
 */
public interface IClubService {

	/**
	 * 查询会所列表
	 * @param param
	 * @param club
	 * @return
	 */
	Object selectclub(PageParam param, Club club);

	/**
	 * 添加
	 * @param dto
	 * @return
	 */
	String addclub(Club dto);
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	Club getclubByid(String id);

	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	String updateclub(Club dto);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	boolean deleteclubs(String[] ids);

	/**
	 * 根据分类查询
	 * @param classid
	 * @return
	 */
	List<Map> getClubByclassid(String classid);

	/**
	 * 根据id获取
	 * @param clubid
	 * @return
	 */
	Map selectClubByid(String clubid);

	/**
	 * 获取所有会所列表
	 * @return
	 */
	List<Map<String, Object>> selectallclubs();

	/**
	 * 根据条件查询会所列表    
	 */
	List<Map> getClubByTerm(String classid, Integer page, Integer size,
			String key);

}
