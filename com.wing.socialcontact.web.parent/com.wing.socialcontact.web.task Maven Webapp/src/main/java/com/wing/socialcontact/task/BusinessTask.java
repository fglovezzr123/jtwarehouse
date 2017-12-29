package com.wing.socialcontact.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.api.IBusinessDisscussService;
import com.wing.socialcontact.service.wx.api.IBusinessService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Business;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.DoubleUtil;
/**
 * 合作洽谈定时任务
 * @author zhangfan
 */
@Service
public class BusinessTask {
	private static final Logger logger = LoggerFactory.getLogger(BusinessTask.class);
	@Autowired
	private IBusinessService businessService; 
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IBusinessDisscussService businessDisscussService;
	@Autowired
	private IWalletLogService walletLogService;
	/**
	 * 合作洽谈修改完成悬赏状态定时任务
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void updateRewardFinish() {
		//查询过期的合作，修改状态
		 List<Business> list = businessService.selectPastBusiness();  
		 for(Business b:list){
			//如果未采纳则退回J币，修改悬赏状态为过期
			WxUser user = wxUserService.selectById(b.getCreateUserId());
			Double doub = DoubleUtil.add(user.getJbAmount()==null?0:user.getJbAmount(), Double.valueOf(b.getReward()));
			user.setJbAmount(doub);
			wxUserService.updateWxUser(user);
			WalletLog log = new WalletLog();
			log.setCreateTime(new Date());
			log.setType("2");
			log.setPdType("1");
			log.setUserId(b.getCreateUserId());
			log.setAmount(Double.valueOf(b.getReward()));
			log.setRemark("合作洽谈悬赏退回");
			log.setPayStatus("1");
			log.setBusinessType(6);
			log.setYeAmount(doub);
			walletLogService.addWalletLog(log);
			b.setRewardFinish(3);
			businessService.updateBusiness(b);
		 }
	}
}
