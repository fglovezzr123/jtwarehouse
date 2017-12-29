package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.service.wx.bean.WalletLog;

/**
 * 
 * @author zengmin
 * @date 2017-04-11 08:52:35
 * @version 1.0
 */
@Repository
public interface WalletLogDao extends Mapper<WalletLog> {
	List query(Map parm);

	List selectByParam(Map param);
	
	/**
	 *累积用户累积的J币
	 * @param userId
	 * @param code
	 * @return
	 */
	Integer  selectJbSum(@Param("userId") String userId);
	/**
	 * 获取文章打赏的总次数
	 * @param param
	 * @return
	 */
	Integer  selectRewardSum(Map param);
	
	
}
