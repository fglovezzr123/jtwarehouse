package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
import com.wing.socialcontact.service.wx.bean.WalletTx;

/**
 * 钱包提现审核记录表
 * 
 * @author zengmin
 * @date 2017-05-04 16:15:41
 * @version 1.0
 */
@Repository
public interface WalletTxDao extends Mapper<WalletTx> {
	List query(Map parm);
	
	List selectByParam(Map param);
}
