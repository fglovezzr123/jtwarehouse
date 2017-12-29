package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.Club;
/**
 * 
 * @author zhangzheng
 *
 */
@Repository
public interface ClubDao extends Mapper<Club> {

	List<Map<String, Object>> getMap(Map parm);

	List<Map> getClubByclassid(Map parm);

	Map selectClubByid(Map parm);

	List<Map> getClubByTerm(Map parm);

}
