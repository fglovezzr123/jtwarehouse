package com.wing.socialcontact.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IActivityRefundService;
import com.wing.socialcontact.service.wx.api.IActivityService;
import com.wing.socialcontact.service.wx.api.IActivityUserService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Activity;
import com.wing.socialcontact.service.wx.bean.ActivityRefund;
import com.wing.socialcontact.service.wx.bean.ActivityUser;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.ApplicationPath;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.ConfigUtil;
import com.wing.socialcontact.util.PayCommonUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;
import com.wing.socialcontact.wechat.api.PayMchAPI;
import com.wing.socialcontact.wechat.entity.ApplyBack;
import com.wing.socialcontact.wechat.resp.ApplyBackResult;

@Service
public class ActivityTask {

	
	private static boolean isrun=false;
	
	@Autowired
	private IActivityService activityService;

	@Autowired
	private IActivityUserService activityUserService;

	@Autowired
	private IMessageInfoService messageInfoService;

	@Autowired
	private IWxUserService wxUserService;

	@Autowired
	private IWalletLogService walletLogService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IActivityRefundService activityRefundService;

	// 任务一 更新活动状态
	@Scheduled(cron = "0 0/2 * * * ?")
	public void updateActivityStatus() {
		System.out.println("---------------------------活动任务开始------------------------------");
		if(!isrun){
			isrun=true;
			// 获取 2-4 报名中 报名截止 进行中 活动 根据时间
			// 1、报名中的活动
			List<Activity> bmzActivities = activityService.getbmzActivities();
			System.out.println("bmzActivities++++++++++++++++++++:"+bmzActivities.size());
			for (Activity ba : bmzActivities) {
				ba.setStatus(3);
				activityService.updateActivityUseSql(ba,3);
			}
			// 2、报名结束的活动
			List<Activity> bmjsActivities = activityService.getbmjsActivities();
			System.out.println("bmjsActivities++++++++++++++++++++:"+bmjsActivities.size());
			for (Activity sa : bmjsActivities) {
				sa.setStatus(4);
				sa.setIscod(1);
				activityService.updateActivityUseSql(sa,4);
				
			}
			// 3、获取进行中的活动 更新活动状态（发消息给发布用户
			List<Activity> jxzActivities = activityService.getjxzActivity();
			System.out.println("jxzActivities++++++++++++++++++++:"+jxzActivities.size());
			for (Activity ja : jxzActivities) {
				TjyUser user = tjyUserService.selectById(ja.getCreateUserId());
				if (user != null && user.getId() != null) {
					ja.setStatus(5);
					ja.setSort(1);//权重1
					ja.setRecommendEnable(0);//首页推荐 否
					ja.setRecommendList(0);//列表推荐  否
					ja.setShowEnable(1);//是否显示  是
					activityService.updateActivityUseSql(ja,5);
				}
			}
			isrun=false;
		}
		
		System.out.println("-----------------------------------活动任务结束---------------------------");
	}
	
	/**
	 * 发消息方法
	 */
	public void sendmessage(String touser, String content, int type, String templateid) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setId(UUIDGenerator.getUUID());
		messageInfo.setDeleted(0);
		messageInfo.setType(type);// 微信
		messageInfo.setToUserId(touser);
		messageInfo.setCreateTime(new Date());
		// 组装内容
		if(type==2){
			String con = WxMsmUtil.getTextMessageContent(content);
			messageInfo.setContent(con);
		}else{
			messageInfo.setContent(content);
		}
		messageInfo.setTemplateId(templateid);
		messageInfo.setStatus(0);// 未发送
		messageInfo.setWxMsgType(1);///** 微信消息类型（1：文本消息2：图文消息） */
		messageInfoService.addMessageInfo(messageInfo);
	}
	

	/**
	 * //任务二 退款
	 * 
	 **/
	@Scheduled(cron = "0 0/1 * * * ?")
	public void activityRefund() {
		/**
		 * 获取所有需要退款的记录
		 */

		List<ActivityRefund> refunds = activityRefundService.selectNoRefundList();

		for (ActivityRefund activityRefund : refunds) { // 退款金额（微信分为单位，变为100倍）
			Double amountDouble = activityRefund.getAmount() * 100;
			String amountString = String.valueOf(amountDouble.intValue());
			// 退款操作
			ApplyBack applyBack = new ApplyBack();
			applyBack.setAppid(ApplicationPath.getParameter("wx_appid"));// 微信分配的公众账号ID（企业号corpid即为此appId）
			applyBack.setMch_id(ConfigUtil.MCH_ID);// 微信支付分配的商户号
			applyBack.setNonce_str(PayCommonUtil.CreateNoncestr());// 随机字符串
			applyBack.setOut_trade_no(activityRefund.getOrderId());// 订单号
			applyBack.setOut_refund_no(activityRefund.getId());// 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@
																// ，同一退款单号多次请求只退一笔。
			applyBack.setTotal_fee(amountString);// 支付金额
			applyBack.setRefund_fee(amountString);// 退款金额
			applyBack.setOp_user_id(ConfigUtil.MCH_ID);// 操作员帐号, 默认为商户号
			applyBack.setTransaction_id(activityRefund.getTransactionId());

			ApplyBackResult res = PayMchAPI.applyBack(applyBack);
			if (res.isSuccess()) {
				activityRefund.setRefundTime(new Date());
				activityRefund.setStatus(1);
				activityRefundService.updateRefund(activityRefund);
				
				Activity act = activityService.getActivityByid(activityRefund.getActivityId());
				
				//发送退款消息
				//【AldyMessageUtil.SMSPRE】尊敬的***，您在平台报名的****活动已退款，退款金额：98元，款项已到账，望尽快核实。
				TjyUser tjyUser = tjyUserService.selectByPrimaryKey(activityRefund.getUserId());
				// 组装内容
				String content = AldyMessageUtil.activityrefund(tjyUser.getTrueName(), act.getTitles(), activityRefund.getAmount());
//				String content = "【"+AldyMessageUtil.SMSPRE+"】尊敬的" + tjyUser.getTrueName() + "，您在平台报名的" + act.getTitles() + "活动已退款，退款金额：" + activityRefund.getAmount()
//						+ "元，款项已到账，望尽快核实。";
				//微信
				sendmessage(tjyUser.getId(),content,2,"ACTIVITY_REFUND");
				//im
				sendmessage(tjyUser.getId(),content,4,"ACTIVITY_REFUND");
			}else{
				System.out.println("==============================活动退款失败,"
						+ "活动id："+activityRefund.getActivityId()+
						"退款id："+activityRefund.getId()+
						"微信订单号："+activityRefund.getTransactionId()+"==================================");
			}
		}
	}
	

}
