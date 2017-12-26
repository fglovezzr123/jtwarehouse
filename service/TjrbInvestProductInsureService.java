package com.wing.socialcontact.sys.service;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;



/**
 * 
 * @author Administrator
 *
 */
public interface TjrbInvestProductInsureService {

	/**
	 * 查询所有保险信息
	 * @return
	 */
	List<TjrbInvestProductInsure> selectTjrbInvestProductInsure();
	/**
	 * 删除单行保险信息
	 * @param id
	 * @return
	 */
	boolean deleteTjrbInvestProductInsureById(int id);
	/**
	 * 单行查询TjrbInvestProductInsure中的数据
	 * @param id
	 * @return
	 */
	TjrbInvestProductInsure selectTjrbInvestProductInsureById(int id);
	/**
	 * 修改TjrbInvestProductInsure中的数据	 
	 * @param map
	 * @return
	 */
	boolean updateTjrbInvestProductInsure(Map<String,Object> map);
	/**
	 * 新增TjrbInvestProductInsure中的数据
	 * @param map
	 * @return
	 */
	Integer saveTjrbInvestProductInsure(Map<String,Object> map);
	/**
	 * 模糊查询
	 */
	List<TjrbInvestProductInsure> selectTjrbInvestProductInsureLike(Map<String, String> map);
	

}
