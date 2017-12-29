package com.wing.socialcontact.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IHzbManagerLogService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.IOpenHzbOrderService;
import com.wing.socialcontact.service.wx.api.IOpenHzbPayLogService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.HzbManagerLog;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.OpenHzbOrder;
import com.wing.socialcontact.service.wx.bean.OpenHzbPayLog;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.service.IUserService;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;

/**
 * 互助宝后台方法
 * 
 * @ClassName: HzbHtAction
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年6月29日 上午8:53:27
 */
@Controller
@RequestMapping("/hzb")
public class HzbHtAction extends BaseAction {

	@Autowired
	private IUserService userService;

	@Autowired
	private IWxUserService wxUserService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IOpenHzbOrderService openHzbOrderService;

	@Autowired
	private IOpenHzbPayLogService openHzbPayLogService;

	@Autowired
	private IHzbManagerLogService hzbManagerLogService;

	@Autowired
	private IMessageInfoService messageInfoService;

	/**
	 * 互助宝开通申请列表
	 * 
	 * @Title: load
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年6月29日 上午8:58:49
	 */
	@RequiresPermissions("hzb:read")
	@RequestMapping("open_list")
	public String open_list(ModelMap map) {
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "hzb/open_list";
	}

	/**
	 * ajax分页查询，互助宝开通列表
	 * 
	 * @param param
	 * @param activity
	 * @return
	 */
	@RequiresPermissions("hzb:read")
	@RequestMapping("order/query")
	public ModelAndView orderQuery(PageParam param, String pid, OpenHzbOrder openHzbOrder,Date stime,Date etime,Integer dfklow,Integer dfkhigh) {
		return ajaxJsonEscape(openHzbOrderService.selectAllOpenHzbOrder(param, openHzbOrder,stime,etime,dfklow,dfkhigh));
	}

	/**
	 * 互助宝用户列表
	 * 
	 * @Title: load
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年6月29日 上午8:58:49
	 */
	@RequiresPermissions("hzb:read")
	@RequestMapping("user_list")
	public String user_list(ModelMap map) {
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "hzb/user_list";
	}

	/**
	 * 互助宝线下付款审核列表
	 * 
	 * @Title: sh_list
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年6月29日 上午8:58:49
	 */
	@RequiresPermissions("hzb:sh")
	@RequestMapping("sh_list")
	public String sh_list(ModelMap map) {
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "hzb/sh_list";
	}

	/**
	 * ajax分页查询，互助宝充值审核列表
	 * 
	 * @param param
	 * @param activity
	 * @return
	 */
	@RequiresPermissions("hzb:sh")
	@RequestMapping("sh/query")
	public ModelAndView shQuery(PageParam param, OpenHzbPayLog openHzbPayLog) {
		openHzbPayLog.setFkType((short) 2);
		return ajaxJsonEscape(openHzbPayLogService.selectAllOpenHzbPayLog(param, openHzbPayLog));
	}

	/**
	 * ajax分页查询，互助宝用户列表
	 * 
	 * @param param
	 * @param activity
	 * @return
	 */
	@RequiresPermissions("hzb:read")
	@RequestMapping("user/query")
	public ModelAndView userQuery(PageParam param, WxUser wxUser,Integer amountlow,Integer amounthigh) {
		return ajaxJsonEscape(wxUserService.selectAllHzbWxUser(param, wxUser,amountlow,amounthigh));
	}

	/**
	 * ajax分页查询，订单充值列表
	 * 
	 * @param param
	 * @param activity
	 * @return
	 */
	@RequiresPermissions("hzb:read")
	@RequestMapping("payLog/query")
	public ModelAndView payLogQuery(PageParam param, String pid, String userId,OpenHzbPayLog openHzbPayLog) {
		openHzbPayLog.setOrderId(pid);
		openHzbPayLog.setUserId(userId);
		return ajaxJsonEscape(openHzbPayLogService.selectAllOpenHzbPayLogByOrder(param, openHzbPayLog));
	}

	/**
	 * ajax分页查询，互助宝操作日志列表
	 * 
	 * @param param
	 * @param activity
	 * @return
	 */
	@RequiresPermissions("hzb:read")
	@RequestMapping("managerLog/query")
	public ModelAndView managerLogQuery(PageParam param, String pid, HzbManagerLog hzbManagerLog) {
		hzbManagerLog.setUserId(pid);
		return ajaxJsonEscape(hzbManagerLogService.selectAllHzbManagerLog(param, hzbManagerLog));
	}

	/**
	 * 后台管理人员操作互助宝页面
	 * 
	 * @return
	 */
	@RequiresPermissions("hzb:manager")
	@RequestMapping("managerPage")
	public String managerPage(ModelMap map, String id) {
		WxUser wxUser = wxUserService.selectByPrimaryKey(id);
		map.addAttribute("wxUser", wxUser);
		return "hzb/manager";
	}

	/**
	 * 后台人员操作互助宝保存
	 * 
	 * @param page
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("hzb:manager")
	@RequestMapping("manager")
	public ModelAndView manager(HzbManagerLog hzbManagerLog, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		String managerUserId = ServletUtil.getMember().getId();
		hzbManagerLog.setManagerTime(new Date());
		hzbManagerLog.setManagerUser(managerUserId);
		WxUser wxUser = wxUserService.selectByPrimaryKey(hzbManagerLog.getUserId());
		OpenHzbOrder order = null;
		// 修改订单标识--暂时不用
		boolean updateOrderFlag = false;
		String rk = "";
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
		String templateStr = "";
		String sms_contentStr = "";
		String wx_contentStr = "";
		String levelStr = "";
		String name = tjyUser.getNickname();
		String time = DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm");
		boolean sendMsgFlag = false;
		switch (hzbManagerLog.getType()) {
		case 1:
			if (wxUser.getHzbOpenFlag() == 1) {
				System.out.println("互助宝已开通，请勿重复操作");
				return ajaxDone(true);
			}
			rk = "开通互助宝";
			wxUser.setHzbOpenFlag(1);
			wxUser.setHzbOpenShUserId(managerUserId);
			wxUser.setHzbOpenTime(new Date());
			order = openHzbOrderService.selectByUserId(hzbManagerLog.getUserId());
			if (null == order) {
				System.out.println("获取互助宝订单失败，无法开通互助宝");
				return ajaxDone(false);
			}
			wxUser.setHzbLevel(order.getLevel().intValue());
			updateOrderFlag = true;
			if (order.getLevel().intValue() == 1) {
				levelStr = "黄金级";
			} else if (order.getLevel().intValue() == 2) {
				levelStr = "白金级";
			} else {
				levelStr = "钻石级";
			}
			templateStr = AldyMessageUtil.MsmTemplateId.HZB_KT;
			sms_contentStr = "{name:\"" + name + "\",level:\"互助宝" + levelStr + "\",type:\"互助宝，\"}";
			wx_contentStr = AldyMessageUtil.userhzbkt(name, levelStr);
			sendMsgFlag = true;
			break;
		case 2:
			rk = "停用互助宝";
			wxUser.setHzbOpenFlag(2);
			templateStr = AldyMessageUtil.MsmTemplateId.HZB_TY;
			sms_contentStr = "{name:\"" + name + "\",time:\"" + time + "\",type:\"互助宝，\"}";
			wx_contentStr = AldyMessageUtil.userhzbty(name, time);
			sendMsgFlag = true;
			break;
		case 3:
			rk = "启用互助宝";
			wxUser.setHzbOpenFlag(1);
			//order = openHzbOrderService.selectByUserId(hzbManagerLog.getUserId());
			//if (null == order) {
			//	System.out.println("获取互助宝订单失败，无法启用互助宝");
			//	return ajaxDone(false);
			//}
			//wxUser.setHzbLevel(order.getLevel().intValue());
			templateStr = AldyMessageUtil.MsmTemplateId.HZB_QY;
			sms_contentStr = "{name:\"" + name + "\",time:\"" + time + "\",type:\"互助宝，\"}";
			wx_contentStr = AldyMessageUtil.userhzbqy(name, time);
			sendMsgFlag = true;
			break;
		case 4:
			hzbManagerLog.setPdType(1);
			rk = "增加互助宝余额：" + CommUtil.formatNumStr(hzbManagerLog.getManagerMoney());
			hzbManagerLog.setCurrYe(wxUser.getHzbAmount() + hzbManagerLog.getManagerMoney());
			wxUser.setHzbAmount(hzbManagerLog.getCurrYe());
			break;
		case 5:
			hzbManagerLog.setPdType(2);
			rk = "减少互助宝余额：" + CommUtil.formatNumStr(hzbManagerLog.getManagerMoney());
			hzbManagerLog.setCurrYe(wxUser.getHzbAmount() - hzbManagerLog.getManagerMoney());
			wxUser.setHzbAmount(hzbManagerLog.getCurrYe());
			if(hzbManagerLog.getCurrYe().doubleValue() < 0 ){
				return ajaxDone(false);
			}
			break;
		case 6:
			if (hzbManagerLog.getPdType() == 3) {
				rk = "调整等级至：黄金级";
				wxUser.setHzbLevel(1);
			} else if (hzbManagerLog.getPdType() == 4) {
				rk = "调整等级至：白金级";
				wxUser.setHzbLevel(2);
			} else if (hzbManagerLog.getPdType() == 5) {
				rk = "调整等级至：钻石级";
				wxUser.setHzbLevel(3);
			}
			break;
		}
		if (StringUtils.isEmpty(hzbManagerLog.getRemark())) {
			hzbManagerLog.setRemark(rk);
		}
		String result = hzbManagerLogService.addHzbManagerLog(hzbManagerLog);
		if (StringUtils.isNotEmpty(result)) {
			wxUserService.updateWxUser(wxUser);
			// 发送提醒
			if (sendMsgFlag) {
				/**
				 * 短信
				 */
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(wxUser.getMobile());
				messageInfo.setType(1);// 短信
				messageInfo.setCreateTime(new Date());
				messageInfo.setContent(sms_contentStr);
				messageInfo.setStatus(0);// 未发送
				messageInfo.setTemplateId(templateStr);
				messageInfoService.addMessageInfo(messageInfo);
				/**
				 * 微信
				 */
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(wxUser.getId() + "");
				messageInfo.setCreateTime(new Date());
				String con = WxMsmUtil.getTextMessageContent(wx_contentStr);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("cztx");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息）
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
				messageInfo.setContent(wx_contentStr);
				messageInfo.setStatus(0);// 不需要发送，未读
				messageInfoService.addMessageInfo(messageInfo);
			}
			if (hzbManagerLog.getType() == 4) {
				executeSj(wxUser);
			}
		}
		System.out.println("manager");
		return ajaxDone(result);
	}

	/**
	 * 充值记录审核通过
	 * 
	 * @param pageQuickEntry
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("hzb:sh")
	@RequestMapping("sh_1")
	public ModelAndView sh_1(String[] ids) {
		if (null == ids || ids.length == 0) {
			return ajaxDone(false);
		}
		String managerUserId = ServletUtil.getMember().getId();
		OpenHzbPayLog payLog = openHzbPayLogService.selectByPrimaryKey(ids[0]);
		if (null == payLog) {
			return ajaxDone(false);
		}
		// 线上付款不需要审核
		if (payLog.getFkType() == 1) {
			return ajaxDone(true);
		}
		if (null != payLog.getShStatus() && payLog.getShStatus().intValue() != 0) {
			return ajaxDone(true);
		}
		payLog.setShStatus((short) 1);
		payLog.setShTime(new Date());
		payLog.setShUserId(managerUserId);
		String result = openHzbPayLogService.updateOpenHzbPayLog(payLog);
		if (MsgConfig.MSG_KEY_SUCCESS.equals(result)) {
			if (payLog.getLogType() == 1) {
				// 审核通过需要修改互助宝可用余额
				OpenHzbOrder openHzbOrder = openHzbOrderService.selectByPrimaryKey(payLog.getOrderId());
				// 检测用户是否已完成还款
				List<OpenHzbPayLog> orderPayLogList = openHzbPayLogService.selectByOrderId(payLog.getOrderId());
				double ljhk = 0;// 累计还款
				double levelMoney = 0;// 等级总还款
				for (OpenHzbPayLog openHzbPayLog : orderPayLogList) {
					if (openHzbPayLog.getShStatus() == 1) {
						ljhk += openHzbPayLog.getFkMoney();
					}
				}
				if (openHzbOrder.getLevel().intValue() == 1) {
					levelMoney = 200000;
				} else if (openHzbOrder.getLevel().intValue() == 2) {
					levelMoney = 500000;
				} else {
					levelMoney = 1000000;
				}
				if (ljhk >= levelMoney) {
					// 修改订单状态为已付尾款
					openHzbOrder.setStatus(3);
					openHzbOrderService.updateOpenHzbOrder(openHzbOrder);
				} else {
					if (openHzbOrder.getStatus().intValue() < 2) {
						openHzbOrder.setStatus(2);
						openHzbOrderService.updateOpenHzbOrder(openHzbOrder);
					}
				}
			}
			// 增加互助宝充值记录
			HzbManagerLog hzbManagerLog = new HzbManagerLog();
			WxUser wxUser = wxUserService.selectByPrimaryKey(payLog.getUserId());
			double hzbMoney = wxUser.getHzbAmount() == null ? 0 : wxUser.getHzbAmount();
			hzbManagerLog.setCurrYe(hzbMoney + payLog.getFkMoney());
			hzbManagerLog.setManagerMoney(payLog.getFkMoney());
			hzbManagerLog.setManagerTime(new Date());
			hzbManagerLog.setManagerUser(managerUserId);
			hzbManagerLog.setPdType(1);
			if (payLog.getLogType() == 1) {
				hzbManagerLog.setRemark("订单线下充值");
			} else {
				hzbManagerLog.setRemark("线下充值");
			}
			hzbManagerLog.setType(7);
			hzbManagerLog.setUserId(payLog.getUserId());
			hzbManagerLogService.addHzbManagerLog(hzbManagerLog);
			wxUser.setHzbAmount(hzbManagerLog.getCurrYe());
			wxUserService.updateWxUser(wxUser);
			executeSj(wxUser);
			// 发送消息提醒
		}
		System.out.println("sh_1");
		return ajaxDone(result);

	}

	/**
	 * 充值记录驳回
	 * 
	 * @param pageQuickEntry
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("hzb:sh")
	@RequestMapping("sh_2")
	public ModelAndView sh_2(String[] ids) {
		if (null == ids || ids.length == 0) {
			return ajaxDone(false);
		}
		OpenHzbPayLog payLog = openHzbPayLogService.selectByPrimaryKey(ids[0]);
		if (null == payLog) {
			return ajaxDone(false);
		}
		// 线上付款不需要审核
		if (payLog.getFkType() == 1) {
			return ajaxDone(true);
		}
		if (null != payLog.getShStatus() && payLog.getShStatus().intValue() != 0) {
			return ajaxDone(true);
		}
		payLog.setShStatus((short) 2);
		payLog.setShTime(new Date());
		payLog.setShUserId(ServletUtil.getMember().getId());
		String result = openHzbPayLogService.updateOpenHzbPayLog(payLog);
		if (MsgConfig.MSG_KEY_SUCCESS.equals(result)) {
			// 消息提醒--充值被驳回
		}
		System.out.println("sh_2");
		return ajaxDone(result);

	}

	/**
	 * 升级
	 * 
	 * @Title: executeSj
	 * @Description: TODO
	 * @return: void
	 * @author: zengmin
	 * @date: 2017年7月27日 上午9:32:03
	 */
	public void executeSj(WxUser wxUser) {
		try {
			int hzbLevel = wxUser.getHzbLevel();
			// 最高级钻石级
			if (hzbLevel == 3) {
				return;
			}
			Long ndc = wxUserService.selectHzbNdcByUserId(wxUser.getId() + "");
			if (ndc == -1) {
				System.out.println("用户开通时间为空，跳过自动升级[" + wxUser.getId() + "]");
				return;
			}
			Date hzbOpenTime = wxUser.getHzbOpenTime();
			Date startDate = null, endDate = null;
			if (ndc > 0) {
				Calendar s1 = Calendar.getInstance();
				s1.setTime(hzbOpenTime);
				s1.set(Calendar.YEAR, s1.get(Calendar.YEAR) + ndc.intValue());
				startDate = s1.getTime();
			} else {
				startDate = hzbOpenTime;
			}
			Calendar s2 = Calendar.getInstance();
			s2.setTime(hzbOpenTime);
			s2.set(Calendar.YEAR, s2.get(Calendar.YEAR) + ndc.intValue() + 1);
			s2.set(Calendar.SECOND, s2.get(Calendar.SECOND) - 1);
			endDate = s2.getTime();
			sj(wxUser, startDate, endDate);
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	private void sj(WxUser wxUser, Date startDate, Date endDate) {
		String userId = wxUser.getId() + "";
		double lc = hzbManagerLogService.selectHzbLcjeByUserIdAndTime(userId, startDate, endDate);
		int hzbLevel = wxUser.getHzbLevel();
		double sj_money = 0;
		int level = 0;
		if (hzbLevel == 1) {
			sj_money = 500000;
			if (lc >= sj_money) {
				// 达到白金等级升级条件
				level = 2;
			}
			sj_money = 1000000;
			if (lc >= sj_money) {
				// 达到钻石等级升级条件
				level = 3;
			}
		} else if (hzbLevel == 2) {
			sj_money = 1000000;
			if (lc >= sj_money) {
				// 达到钻石等级升级条件
				level = 3;
			}
		}
		if (level != 0) {
			HzbManagerLog hzbManagerLog = new HzbManagerLog();
			hzbManagerLog.setType(6);// 调整等级
			hzbManagerLog.setManagerTime(new Date());
			hzbManagerLog.setPdType(level + 2);
			if (level == 2) {
				hzbManagerLog.setRemark("达到升级条件自动调整等级至：白金级");
			} else {
				hzbManagerLog.setRemark("达到升级条件自动调整等级至：钻石级");
			}
			hzbManagerLog.setUserId(userId);
			String result = hzbManagerLogService.addHzbManagerLog(hzbManagerLog);
			if (MsgConfig.MSG_KEY_SUCCESS.equals(result)) {
				WxUser user = wxUserService.selectByPrimaryKey(userId);
				user.setHzbLevel(level);
				wxUserService.updateWxUser(user);
				// 推送消息
				TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
				// 推送消息
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setMobile(wxUser.getMobile());
				messageInfo.setType(1);// 短信
				messageInfo.setCreateTime(new Date());
				String name = tjyUser.getNickname();
				String moneyStr = "";
				String levelStr = "";
				String typeStr = "互助宝";
				if (level == 2) {
					moneyStr = "50W";
					levelStr = "白金级";
				} else {
					moneyStr = "100W";
					levelStr = "钻石级";
				}
				messageInfo.setContent("{name:\"" + name + "\",type:\"" + typeStr + "\",money:\"" + moneyStr
						+ "，互助宝\",level:\"" + levelStr + "\"}");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setTemplateId(AldyMessageUtil.MsmTemplateId.HZB_SJ);
				messageInfoService.addMessageInfo(messageInfo);
				// 发送微信--给用户发送微信消息提醒，告知支付成功
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(wxUser.getId() + "");
				messageInfo.setCreateTime(new Date());
				String content = AldyMessageUtil.userhzbsj(name, typeStr, moneyStr, levelStr);
				String con = WxMsmUtil.getTextMessageContent(content);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("cztx");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息）
				// */
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
			}
		}
	}
}
