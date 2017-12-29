package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.ActivityTag;
/**
 * 
 * <p>Title:活动标签管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年4月18日 下午3:21:00
 */
@Repository
public interface ActivityTagDao extends Mapper<ActivityTag> {

	List<Map<String, Object>> getclassMap(Map parm);
	
    
    List<ActivityTag> selTags(Map parm);

}
