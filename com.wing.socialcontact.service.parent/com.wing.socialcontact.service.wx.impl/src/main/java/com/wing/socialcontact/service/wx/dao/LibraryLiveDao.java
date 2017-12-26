package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.TjyLibraryLive;
@Repository
public interface LibraryLiveDao extends Mapper<TjyLibraryLive> {

	List<Map<String, Object>> selectByParam(Map parm);

	List libraryLiveList(Map parm);

	List<TjyLibraryLive> selectWksLives();

	List<TjyLibraryLive> selectJxzLives();

	void updatestatusbyid(Map parm);

}
