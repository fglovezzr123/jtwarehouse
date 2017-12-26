package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.ClubClass;
/**
 * 
 * @author zhangzheng
 *
 */
@Repository
public interface ClubClassDao extends Mapper<ClubClass> {

	List<Map<String, Object>> selectAllclassMap();

	List selectByName(Map parm);

	List<Map> selectonelevelclass();

	List<Map> querybyparent(Map parm);

}
