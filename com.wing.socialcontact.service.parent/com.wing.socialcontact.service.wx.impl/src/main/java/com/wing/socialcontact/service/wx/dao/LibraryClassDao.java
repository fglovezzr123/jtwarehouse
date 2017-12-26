package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;
@Repository
public interface LibraryClassDao extends Mapper<TjyLibraryClass> {

	List<Map<String, Object>> getclassMap(Map parm);

	List<Map<String, Object>> selectAllclassMap();

	List selectByName(Map parm);

	List<Map> selectByparent(Map parm);

	List<Map> selectonelevelclass();

	List<Map> recommendclass();

	List<Map> onelevelclass(Map parm);

}
