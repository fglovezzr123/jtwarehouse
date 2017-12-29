package com.wing.socialcontact.service.wx.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.RefundInstruction;
/**
 * 
 * <p>Title:退款说明设置 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月12日 下午3:15:49
 */
@Repository
public interface RefundInstructionDao extends Mapper<RefundInstruction> {

	List getclassMap();

}
