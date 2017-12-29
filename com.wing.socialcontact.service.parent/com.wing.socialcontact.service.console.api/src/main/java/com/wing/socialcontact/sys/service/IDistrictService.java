/**  
 * @Project: tjy
 * @Title: IDistrictService.java
 * @Package com.oa.manager.system.service
 * @date 2016-6-19 下午2:03:01
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.bean.SyDistrict;

/**
 * 
 * 类名：IDistrictService
 * 功能：
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-6-19 下午2:03:01
 *
 */
public interface IDistrictService{
	
	/**
	 * 条件分页查询 地区
	 * @param param
	 * @param dis
	 * @return
	 */
	public DataGrid selectDistrict(PageParam param,SyDistrict dis);
	
	/**
	 * 添加地区
	 * @param dis
	 * @return
	 */
	public String addDistrict(SyDistrict dis);
	/**
	 * 修改
	 * @param dis
	 * @return
	 */
	public String updateDistrict(SyDistrict dis);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteDistrict(String[] ids);
	/**
	 * 查询出所有地区
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectAllDistrict();
	/**
	 * 根据id查询出下级地区
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List selectDistrictBySuperId(String id);

	public SyDistrict selectByPrimaryKey(String id);
	
	/**
	 * 根据级别获取地区
	 * @Title: selectDistrictByType 
	 * @Description: TODO
	 * @param type
	 * @return
	 * @return: List
	 * @author: zengmin   
	 * @date: 2017年4月15日 上午10:47:52
	 */
	public List selectDistrictByType(String type);
	
	/**
	 * 根据条件获取地区信息
	 * @param param
	 * @return
	 */
	public List selectByParam(Map param);
	
	
}
