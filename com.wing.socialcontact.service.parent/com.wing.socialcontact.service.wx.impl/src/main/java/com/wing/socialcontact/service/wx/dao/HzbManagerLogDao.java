package com.wing.socialcontact.service.wx.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.HzbManagerLog;

/**
 * 
 * @author liangwj
 * @date 2017-07-22 18:21:15
 * @version 1.0
 */
@Repository
public interface HzbManagerLogDao extends Mapper<HzbManagerLog> {
	/**
	 * 返回List<Map<String,Object>>
	 * 
	 * @Title: query
	 * @Description: TODO
	 * @param param
	 * @return
	 * @return: List
	 * @author: zengmin
	 * @date: 2017年7月25日 下午4:57:21
	 */
	List query(Map<String, Object> param);

	/**
	 * 返回List<HzbManagerLog>
	 * 
	 * @Title: query
	 * @Description: TODO
	 * @param param
	 * @return
	 * @return: List
	 * @author: zengmin
	 * @date: 2017年7月25日 下午4:57:21
	 */
	List list(Map<String, Object> param);

	/**
	 * 返回List<Map<String,Object>>前台
	 * 
	 * @Title: query
	 * @Description: TODO
	 * @param param
	 * @return
	 * @return: List
	 * @author: zengmin
	 * @date: 2017年7月25日 下午4:57:21
	 */
	List<Map<String, Object>> queryByQt(Map<String, Object> param);

	double queryHzbLcjeByUserIdAndTime(Map<String, Object> param);

	double queryHzbLxjeByUserIdAndTime(Map<String, Object> param);
}
