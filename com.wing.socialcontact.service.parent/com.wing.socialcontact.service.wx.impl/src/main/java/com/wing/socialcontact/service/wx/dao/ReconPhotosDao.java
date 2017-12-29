package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.ReconPhotos;

/**
 * 
 * @author zengmin
 * @date 2017-04-07 01:41:58
 * @version 1.0
 */
@Repository
public interface ReconPhotosDao extends Mapper<ReconPhotos> {
	List query(Map parm);
}
