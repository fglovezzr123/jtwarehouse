package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.ActivityRefund;
/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月4日 下午4:35:50
 */
@Repository
public interface ActivityRefundDao extends Mapper<ActivityRefund> {

	List<ActivityRefund> selectNoRefundList();

	List selectList(Map parm);

}
