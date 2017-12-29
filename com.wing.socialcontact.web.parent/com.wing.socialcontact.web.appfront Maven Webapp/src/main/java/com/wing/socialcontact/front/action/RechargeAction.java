package com.wing.socialcontact.front.action;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IHonorService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserHonorService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DateUtils;
import com.wing.socialcontact.util.PayDataBean;
import com.wing.socialcontact.util.RetBean;
import com.wing.socialcontact.util.SignUtil;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;
/**
 * 充值相关接口
 * @author zhangzheng
 *
 */
@Controller
@RequestMapping("/m/app/recharge")
public class RechargeAction extends BaseAppAction {
	
	
	@Autowired
	private IWxUserService wxUserService;

	@Autowired
	private ITjyUserService tjyUserService;
	
	@Autowired
	private IWalletLogService walletLogService;
	
	@Autowired
	private IUserHonorService userHonorService;

	@Autowired
	private IHonorService honorService;

	@Autowired
	private IMessageInfoService messageInfoService;
	
	@Autowired
	private IListValuesService listValuesService;

	/**
	 * 人民币Jb兑换比例说明  为空时为未设置兑换比例  禁止充值
	 */
	@RequestMapping("JbScale")
	public @ResponseBody ResponseReport JbScale(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response){
		try{
			String userId =rr.getUserProperty().getUserId();
			if(StringUtil.isEmpty(userId)){
				return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);		
			}
			Map res = new HashMap();
			ListValues listValue = listValuesService.selectByPrimaryKey("fbc8733aafed4fd9ac4f341b155b5311");
			res.put("JbScale", listValue.getListDesc());
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", res);
		}catch(Exception e){
			e.printStackTrace();
			return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
		}
	}
	/**
	 * 生成充值订单  返回支付所需信息
	 */
	@RequestMapping("payRecharge")
	public @ResponseBody ResponseReport payRecharge(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response){
		try{
			String userId =rr.getUserProperty().getUserId();
			if(StringUtil.isEmpty(userId)){
				return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);		
			}
			String rechargeamount = rr.getDataValue("amount");//充值金额   数字，最多两位小数
			String czType = rr.getDataValue("rechargeType");  //1 余额   2J币
			Double je = Double.parseDouble(rechargeamount);
			if (je <= 0) {
				return super.getAjaxResult(rr,ResponseCode.NotSupport, "充值金额须0！", null);
			}
			double czje = je;
			if ("2".equals(czType)) {
				// J币人民币换算
				ListValues listValue = listValuesService.selectByPrimaryKey("fbc8733aafed4fd9ac4f341b155b5311");
				if (StringUtils.isEmpty(listValue.getListValue())) {
					return super.getAjaxResult(rr,ResponseCode.NotSupport, "当前J币兑换比例尚未设置，不能充值，请联系客服人员", null);
				} else {
					double bl = Double.valueOf(listValue.getListValue());
					czje = je * bl;
				}
			}
			WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
			// 创建充值记录
			WalletLog walletLog = new WalletLog();
			walletLog.setAmount(czje);
			walletLog.setCreateTime(new Date());
			walletLog.setDeleted("0");
			walletLog.setPayStatus("0");// 待支付
			walletLog.setPdType("1");
			walletLog.setRemark("充值");
			walletLog.setType(czType);
			walletLog.setUserId(userId);
			walletLog.setBusinessType(1);
			walletLog.setRmbAmount(je);// 实际支付金额
			if ("1".equals(czType)) {
				walletLog.setYeAmount(wxUser.getAvailablebalance());
			} else if ("2".equals(czType)) {
				walletLog.setYeAmount(wxUser.getJbAmount());
			} else if ("3".equals(czType)) {
				walletLog.setYeAmount(wxUser.getHzbAmount());
			}
			String out_trade_no = walletLogService.addWalletLog(walletLog);
			if (StringUtils.isEmpty(out_trade_no)) {
				return super.getAjaxResult(rr,ResponseCode.Error, "获取订单号失败！", null);
			}
			DecimalFormat    df   = new DecimalFormat("######0.00"); 
			String tp = df.format(walletLog.getRmbAmount());
			/**
			 * 支付组装报文
	         *  oid_partner     支付交易商户编号
	         *  busi_partner    商户业务类型
	         *  no_order        商户订单号
	         *  dt_order        商户订单时间
	         *  name_goods      商品名称
	         *  money_order     交易金额
	         *  notify_url      服务器异步通知地址
	         */
			Map res = new HashMap();
			//订单号
			res.put("noOrder", out_trade_no);
			//时间戳
			res.put("dtOrder",walletLog.getCreateTime().getTime() );
			//商品类型   虚拟商品销售：101001, 实物商品销售：109001
			res.put("busiPartner", "109001");
			// 回调地址
			res.put("notifyUrl",Constants.APP_LIAN_NOTIFY_URL );
			//支付金额
			res.put("moneyOrder", walletLog.getRmbAmount()+"");
			//商户号
			res.put("oidPartner", Constants.LIAN_OID_PARTNERWAP);
			//商品类目
			res.put("goodsClass","钱包充值" );
			//私钥
			res.put("privateKey", Constants.LIAN_BUSINESS_PRIVATE_KEY);
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", res);
		}catch(Exception e){
			e.printStackTrace();
			return super.getAjaxResult(rr,ResponseCode.Error, "获取失败！", null);
		}
	}
	
	/**
	 * 网银充值回调
	 * 充值支付回调
	 * 
	 * @Title: notify_wy
	 * @param request
	 * @param response
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年4月10日 下午3:00:34
	 */
	@RequestMapping("notifyWy")
	public @ResponseBody RetBean notifyWy(HttpServletRequest request, HttpServletResponse resp) {
		RetBean retBean = new RetBean();
        try {
            resp.setCharacterEncoding("UTF-8");
            System.out.println("进入支付异步通知数据接收处理");
            String reqStr = SignUtil.readReqStr(request);

            if (SignUtil.isnull(reqStr)){
                retBean.setRet_code("9999");
                retBean.setRet_msg("交易失败");
                resp.getWriter().write(JSON.toJSONString(retBean));
                resp.getWriter().flush();
            }
            System.out.println("接收支付异步通知数据：【" + reqStr + "】");

            if (!SignUtil.checkSign(reqStr, Constants.LIAN_PUBLIC_KEY_ONLINE, Constants.LIAN_MD5_KEY)){
                retBean.setRet_code("9999");
                retBean.setRet_msg("交易失败");
                resp.getWriter().write(JSON.toJSONString(retBean));
                resp.getWriter().flush();
                System.out.println("支付异步通知验签失败");
            }

            retBean.setRet_code("0000");
            retBean.setRet_msg("交易成功");
            resp.getWriter().write(JSON.toJSONString(retBean));
            resp.getWriter().flush();
            System.out.println("支付异步通知数据接收处理成功");
            // 解析异步通知对象
            PayDataBean payDataBean = JSON.parseObject(reqStr, PayDataBean.class);
            
	         // 获取充值记录
	            String out_trade_no = payDataBean.getNo_order();
				System.out.println(out_trade_no + "-----------" );
				WalletLog walletLog = walletLogService.selectByPrimaryKey(out_trade_no);
	            if("1".equals(walletLog.getPayStatus())){
	            	//如果已经变成已付款，无需处理。
	            	return retBean;
	            }
	            //获取实际上支付的金额。
//	            Double payAmount = CommUtil.null2Double(payDataBean.getMoney_order());
//	            walletLog.setAmount(payAmount);
				WxUser wxUser = wxUserService.selectByPrimaryKey(walletLog.getUserId());
				TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
				// 修改钱包
				String czType = "";
				String dw = "";
				String ye_text = "";// 余额显示值
				double ye = 0;// 余额
				String cz_text = "";
				String end = "";
				if ("1".equals(walletLog.getType())) {
					wxUser.setAvailablebalance(CommUtil.add(wxUser.getAvailablebalance(), walletLog.getAmount()));
					czType = "RMB";
					dw = "元";
					ye = wxUser.getAvailablebalance();
					ye_text = CommUtil.formatNumStr(wxUser.getAvailablebalance());
					cz_text = CommUtil.formatNumStr(walletLog.getAmount());
					end = "。多多充值多多消费才能确保土豪形象";
					// 勋章
					// 钻石土豪 一次性充值100w人民币 honor_010
					// 金土豪 一次性充值10w人民币 honor_011
					// 银土豪 一次性充值1w人民币 honor_012
					Double rmb = walletLog.getAmount();
					if (rmb >= 1000000) {
						userHonorService.addUserAndHonor(walletLog.getUserId(), "honor_004");
					} else if (rmb >= 100000 && rmb < 1000000) {
						userHonorService.addUserAndHonor(walletLog.getUserId(), "honor_005");
					} else if (rmb >= 10000 && rmb < 100000) {
						userHonorService.addUserAndHonor(walletLog.getUserId(), "honor_006");
					}
	
				} else if ("2".equals(walletLog.getType())) {
					wxUser.setJbAmount(CommUtil.add(wxUser.getJbAmount(), walletLog.getAmount()));
					czType = "J币";
					dw = "个";
					ye = wxUser.getJbAmount();
					ye_text = Math.round(wxUser.getJbAmount()) + "";
					cz_text = Math.round(walletLog.getAmount()) + "";
					end = "。多打赏，多互动，朋友遍天下";
				} else {
					wxUser.setHzbAmount(CommUtil.add(wxUser.getHzbAmount(), walletLog.getAmount()));
					czType = "互助宝";
					dw = "元";
					ye = wxUser.getHzbAmount();
					ye_text = CommUtil.formatNumStr(wxUser.getHzbAmount());
					cz_text = CommUtil.formatNumStr(walletLog.getAmount());
					end = "。互助宝，帮您在刀刃上花钱";
				}
				wxUserService.updateWxUser(wxUser);
				// 发送短信
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(wxUser.getMobile());
				messageInfo.setType(1);// 短信
				messageInfo.setCreateTime(new Date());
				String name = tjyUser.getNickname();
				messageInfo.setContent("{name:\"" + name + "\",time:\""
						+ DateUtils.getCurrTimeStr("yyyy-MM-dd HH:mm") + "\",money:\"" + czType + cz_text + dw
						+ "\",czType:\"" + czType + "\",ye:\"" + ye_text + dw + end + "\"}");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.RECHARGE);
				messageInfoService.addMessageInfo(messageInfo);
				// 发送微信--给用户发送微信消息提醒，告知充值成功
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(wxUser.getId() + "");
				messageInfo.setCreateTime(new Date());
				// 组装内容
				// Sysconfig sysconfig = sysconfigService.getSysconfig();
				// String site_path = sysconfig.getWebSite();
				// String url = site_path + "/m/my/personal_wallet.do";
				String content = AldyMessageUtil.userwalletczsuccess(name, czType + cz_text + dw, czType,
						ye_text + dw + end);
				// String content = "【" + AldyMessageUtil.SMSPRE + "】尊敬的" +
				// name + "，您的账户于"
				// + DateUtils.getCurrTimeStr("yyyy-MM-dd HH:mm") + "成功充值" +
				// czType + cz_text + dw + "，当前"
				// + czType + "余额为" + ye_text + dw + "。";
				String con = WxMsmUtil.getTextMessageContent(content);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("cztx");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息） */
				messageInfoService.addMessageInfo(messageInfo);
				/**
				 * 发送系统消息
				 */
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(3);// 系统消息
				messageInfo.setToUserId(wxUser.getId() + "");
				messageInfo.setCreateTime(new Date());
				messageInfo.setContent(content);
				messageInfo.setStatus(0);// 不需要发送，未读
				messageInfoService.addMessageInfo(messageInfo);
	//			walletLog.setPaySn(transaction_id);
				walletLog.setPayStatus("1");
				walletLog.setPayType("3");
				walletLog.setYeAmount(ye);
				boolean bo = walletLogService.updateWalletLog(walletLog);
				if (bo) {
					System.out.println("状态更新成功");
				} else {
					System.out.println("状态更新失败");
				}
            
        } catch (Exception e) {
            e.printStackTrace();
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            System.out.println("网银支付回调出错  E-bank  Callback Error");
        }finally{
        	return retBean;
        }
        
	}

}
