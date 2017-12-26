package com.wing.socialcontact.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.api.IRewardAnswerService;
import com.wing.socialcontact.service.wx.api.IRewardService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Reward;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.DoubleUtil;
/**
 * 悬赏定时任务
 * @author zhangfan
 */
@Service
public class RewardTask {
	private static final Logger logger = LoggerFactory.getLogger(RewardTask.class);
	@Autowired
	private IRewardService rewardService; 
	@Autowired
	private IRewardAnswerService rewardAnswerService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IWalletLogService walletLogService;
	/**
	 * 悬赏修改完成悬赏状态定时任务
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void updateRewardFinish() {
		//查询过期的悬赏，修改状态
		 List<Reward> list = rewardService.selectPastReward();  
		 for(Reward r:list){
			//如果未采纳则退回J币，修改悬赏状态为过期
			WxUser user = wxUserService.selectById(r.getCreateUserId());
			Double doub = DoubleUtil.add(user.getJbAmount()==null?0:user.getJbAmount(), Double.valueOf(r.getReward()));
			user.setJbAmount(doub);
			wxUserService.updateWxUser(user);
			WalletLog log = new WalletLog();
			log.setCreateTime(new Date());
			log.setType("2");
			log.setPdType("1");
			log.setUserId(r.getCreateUserId());
			log.setAmount(Double.valueOf(r.getReward()));
			log.setRemark("诸葛解惑悬赏退回");
			log.setPayStatus("1");
			log.setBusinessType(6);
			log.setYeAmount(doub);
			walletLogService.addWalletLog(log);
			r.setStatus(5);
			rewardService.updateReward(r);
		 }
	}
}
