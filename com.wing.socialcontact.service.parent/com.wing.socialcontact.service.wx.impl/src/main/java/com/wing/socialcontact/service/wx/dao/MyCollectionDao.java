package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.MyCollection;
@Repository
public interface MyCollectionDao extends Mapper<MyCollection> {

	List<Map> getLibraryCollections(Map parm);

	List<Map> getClubCollections(Map parm);

	List<Map> getActivityCollections(Map parm);

	List<Map> getLiveCollections(Map parm);

	
}
