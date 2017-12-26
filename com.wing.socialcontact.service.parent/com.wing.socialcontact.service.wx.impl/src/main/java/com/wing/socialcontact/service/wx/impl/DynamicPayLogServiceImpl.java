package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.dao.DynamicPayLogDao;
import com.wing.socialcontact.service.wx.api.IDynamicPayLogService;
import com.wing.socialcontact.service.wx.api.IDynamicService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Dynamic;
import com.wing.socialcontact.service.wx.bean.DynamicPayLog;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.DoubleUtil;

/**
 * 
 * @author xuxinyuan
 * @date 2017-04-17 09:54:05
 * @version 1.0
 */
@Service
public class DynamicPayLogServiceImpl implements IDynamicPayLogService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "";
	
	@Resource
	private DynamicPayLogDao dynamicPayLogDao;
	
	@Autowired
	private IDynamicService dynamicService;
	
	@Autowired
	private IWxUserService wxUserService;
	
	@Autowired
	private IWalletLogService walletLogService;

	@Override
	public List selectAllDynamicPayLog(DynamicPayLog dynamicPayLog) {
		// TODO Auto-generated method stub
		return dynamicPayLogDao.selectAllDynamicPayLogMap(dynamicPayLog);
	}

	@Override
	public DataGrid selectDynamicPayLog(PageParam param,
			DynamicPayLog dynamicPayLog) {
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		List lst = dynamicPayLogDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	@Override
	public String addDynamicPayLog(DynamicPayLog dynamicPayLog) {
		int res = dynamicPayLogDao.insert(dynamicPayLog);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateDynamicPayLog(DynamicPayLog dynamicPayLog) {
		int res = dynamicPayLogDao.updateByPrimaryKeySelective(dynamicPayLog);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		} 
	}

	@Override
	public DynamicPayLog selectById(String id) {
		return dynamicPayLogDao.selectByPrimaryKey(id);
	}

	@Override
	public String insertGratuity(String userId, String dynamicId,String jcount) throws RuntimeException{
		Dynamic dynamic = dynamicService.getDynamicSignup(dynamicId);
		
		//先减去自己的j币
		WxUser user = wxUserService.selectById(userId);
		
		Double doub = DoubleUtil.sub(user.getJbAmount() != null ? user.getJbAmount():0, Double.valueOf(jcount));
		if(doub<0){
			throw new RuntimeException("J币不足！");
		}else{
			user.setJbAmount(doub);
			wxUserService.updateWxUser(user);
			DynamicPayLog dynamicPayLog = new DynamicPayLog();
			dynamicPayLog.setActionTime(new Date());
			dynamicPayLog.setActionType(1);
			dynamicPayLog.setDynamicId(dynamicId);
			dynamicPayLog.setId(UUID.randomUUID().toString());
			dynamicPayLog.setPayAmount(Long.valueOf(jcount));
			dynamicPayLog.setUserId(userId);
			dynamicPayLog.setStatus(1);
			dynamicPayLogDao.insertSelective(dynamicPayLog);
			
		}
		
		WxUser userf = wxUserService.selectById(dynamic.getUserId());
		userf.setJbAmount(DoubleUtil.add(userf.getJbAmount(), Double.valueOf(jcount)));
		wxUserService.updateWxUser(userf);
		
		WalletLog log = new WalletLog();
		log.setCreateTime(new Date());
		log.setType("2");
		log.setPdType("2");
		log.setUserId(userId);
		log.setAmount(Double.valueOf(jcount));
		log.setRemark("打赏动态支出："+jcount+"币");
		log.setYeAmount(user.getJbAmount());
		log.setBusinessType(7);
		log.setSourceUser(userf.getId()+"");
		walletLogService.addWalletLog(log);
		
		WalletLog log2 = new WalletLog();
		log2.setCreateTime(new Date());
		log2.setType("2");
		log2.setPdType("1");
		log2.setUserId(userf.getId()+"");
		log2.setAmount(Double.valueOf(jcount));
		log2.setRemark("打赏动态收入："+jcount+"币");
		log2.setYeAmount(userf.getJbAmount());
		log2.setBusinessType(9);
		log2.setSourceUser(userId);
		walletLogService.addWalletLog(log2);
		
		// TODO Auto-generated method stub
		return MsgConfig.MSG_KEY_SUCCESS;
	}
	
	/**
	 * 动态媒体付费
	 * @param userId
	 * @param dynamicId
	 * @param jcount
	 * @return
	 */
	public String insertMediaPay(String userId,String dynamicId,String jcount){
		Dynamic dynamic = dynamicService.getDynamicSignup(dynamicId);
		
		//先减去自己的j币
		WxUser user = wxUserService.selectById(userId);
		
		Double doub = DoubleUtil.sub(user.getJbAmount()!= null ? user.getJbAmount():0, Double.valueOf(jcount));
		if(doub<0){
			throw new RuntimeException("J币不足，请充值之后再支付！");
		}else{
			user.setJbAmount(doub);
			wxUserService.updateWxUser(user);
			DynamicPayLog dynamicPayLog = new DynamicPayLog();
			dynamicPayLog.setActionTime(new Date());
			dynamicPayLog.setActionType(2);
			dynamicPayLog.setDynamicId(dynamicId);
			dynamicPayLog.setId(UUID.randomUUID().toString());
			dynamicPayLog.setPayAmount(Long.valueOf(jcount));
			dynamicPayLog.setUserId(userId);
			dynamicPayLog.setStatus(1);
			dynamicPayLog.setMediaId(dynamic.getMediaId());
			dynamicPayLogDao.insertSelective(dynamicPayLog);
			
		}
		
		WxUser userf = wxUserService.selectById(dynamic.getUserId());
		userf.setJbAmount(DoubleUtil.add(userf.getJbAmount(), Double.valueOf(jcount)));
		wxUserService.updateWxUser(userf);
		
		WalletLog log = new WalletLog();
		log.setCreateTime(new Date());
		log.setType("2");
		log.setPdType("2");
		log.setUserId(userId);
		log.setAmount(Double.valueOf(jcount));
		log.setRemark("语音付费");
		log.setYeAmount(user.getJbAmount());
		log.setBusinessType(13);
		log.setSourceUser(userf.getId()+"");
		walletLogService.addWalletLog(log);
		
		WalletLog log2 = new WalletLog();
		log2.setCreateTime(new Date());
		log2.setType("2");
		log2.setPdType("1");
		log2.setUserId(userf.getId()+"");
		log2.setAmount(Double.valueOf(jcount));
		log2.setRemark("语音收费");
		log2.setYeAmount(userf.getJbAmount());
		log2.setBusinessType(14);
		log2.setSourceUser(userId);
		walletLogService.addWalletLog(log2);
		
		// TODO Auto-generated method stub
		return MsgConfig.MSG_KEY_SUCCESS;
	}
	
	

}