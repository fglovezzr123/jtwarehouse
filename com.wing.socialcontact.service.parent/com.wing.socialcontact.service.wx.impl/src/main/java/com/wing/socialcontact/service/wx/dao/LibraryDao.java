package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.TjyLibrary;
@Repository
public interface LibraryDao extends Mapper<TjyLibrary> {

	List<Map<String, Object>> getMap(Map parm);

	List<Map> getTjyLibraryByclassid(String classid);

	Map getLibraryByid(String libraryid);

	List<Map> getTjyLibraryByTerm(Map parm);

	List<Map> selbyonelevelid(Map parm);

	List<Map> getLibraryByoneLevel(Map parm);

}
