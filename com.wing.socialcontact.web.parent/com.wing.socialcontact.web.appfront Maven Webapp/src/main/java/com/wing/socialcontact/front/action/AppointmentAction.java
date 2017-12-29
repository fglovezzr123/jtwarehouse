/**
 * 
 */
package com.wing.socialcontact.front.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoy.util.ApplicationPath;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sensitive.conf.Config;
import com.wing.socialcontact.service.im.api.IImGroupinfoService;
import com.wing.socialcontact.service.wx.api.IAppointmentDetailsService;
import com.wing.socialcontact.service.wx.api.IAppointmentService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.IPaymentAppointmentService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecord;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecordDetails;
import com.wing.socialcontact.service.wx.bean.UserPaymentAppointment;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.im.IMUtil;
import tk.mybatis.mapper.util.StringUtil;

/**
 * 同城、同行、同趣 -- 商友约见action
 * 
 * @author zhangpengzhi
 *
 */
@Controller
@RequestMapping("/m/app/appointment")
public class AppointmentAction extends BaseAppAction
{

	@Autowired
	private IAppointmentService appointmentService;
	@Autowired
	private IPaymentAppointmentService paymentAppointmentService;
	@Autowired
	private IAppointmentDetailsService appointmentDetailsService;
	@Autowired
	private IMessageInfoService messageInfoService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IImGroupinfoService imGroupinfoService;
	// public final static String IM_MESSAGE_PUSH_ACCOUNT =
	// "tojoycloudadmin9999";
	private static final String content = ApplicationPath.getParameter("appointment.message.content");
	private static final String cancel_content = ApplicationPath.getParameter("cancel.appointment.message.content");
	private static final String launch_content = ApplicationPath.getParameter("launch.appointment.message.content");
	
	
	/**
	 * 生成待支付订单
	 * @author 刘涛
	 * @param expectTime期望约见时间 yyyy-MM-dd
	 * @return ResponseReport
	 */
	@RequestMapping("pendingpayment")
	public @ResponseBody ResponseReport pendingpayment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		//判断以前约见订单中是否有未结算的费用
		List listUnpaid = paymentAppointmentService.getUnpaidByUserId(userId);
		if(listUnpaid.size()>0) {//有未结算的订单
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "有未完成的支付", listUnpaid);
		}else {//判断是否可约（判断依据：期望约见时间是否跟其他约见人有冲突）
			String expectTime = rr.getDataValue("expectTime");//期望约见时间
			String msg = rr.getDataValue("msg");//约见理由
			String estimateTimeLength = rr.getDataValue("estimateTimeLength");//约见时长
			Date expectTimeDate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try
			{
				expectTimeDate = sdf.parse(expectTime);
			}
			catch (ParseException e)
			{
				e.printStackTrace();
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "期望约见时间格式错误", null);
			}
			List listUserAppointmentRecord = appointmentService.getUserAppointmentRecordByExpecttime(expectTimeDate);
			if(listUserAppointmentRecord.size()>0) {//时间冲突
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "约见时间冲突", listUserAppointmentRecord);
			}else {//生成待支付订单
				UserPaymentAppointment upa = new UserPaymentAppointment();
				upa.setStatus("0");
				upa.setUser_Id(Long.parseLong(userId));
				upa.setIsPayment("0");
//				if(!StringUtil.isEmpty(msg))
//					upa.setMsg(msg);
//				if(null!=expectTimeDate&&!"".equals(expectTimeDate))
//					upa.setExpect_Time(expectTimeDate);
//				if(StringUtil.isEmpty(estimateTimeLength)&&Long.parseLong(estimateTimeLength)>0)
//					upa.setEstimateTimeLength(Long.parseLong(estimateTimeLength));
				
				upa = paymentAppointmentService.addAppointment(upa);
				if(upa.getId()>0&&!"".equals(upa.getId())) {
					//生成待支付订单成功
					return super.getAjaxResult(rr, ResponseCode.OK, "生成待支付订单成功", upa);
				}else {
					//生成待支付订单失败
					return super.getAjaxResult(rr, ResponseCode.NotSupport, "生成待支付订单失败", null);
				}
			}
		}
	}
	
	/**
	 * 取消待支付订单
	 * @author 刘涛
	 * @param id 订单id
	 * @return ResponseReport
	 */
	@RequestMapping("cancelPendingpayment")
	public @ResponseBody ResponseReport cancelPendingpayment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String id = rr.getDataValue("id");//订单id
		if (id == null || "".equals(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		UserPaymentAppointment upa = new UserPaymentAppointment();
		upa = paymentAppointmentService.getUnpaidById(Long.parseLong(id));
		//判断订单支付状态status:0 待支付；1 已支付；2 已取消
		if("0".equals(upa.getStatus())) {
			//取消待支付订单
			upa.setStatus("2");
			int up = paymentAppointmentService.updateUnpaidById(upa);
			if(up>0) {
				return super.getAjaxResult(rr, ResponseCode.OK, "订单取消成功", upa);
			}else {
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "订单取消失败", upa);
			}
		}else if("2".equals(upa.getStatus())) {
			//已取消（认为或系统超过10分钟未支付自动取消）
			return super.getAjaxResult(rr, ResponseCode.OK, "该待支付订单已经被取消", upa);
		}else {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "取消待支付订单失败", null);
		}
	}
	
	/**
	 * 判断是否可支付
	 * @author 刘涛
	 * @param id 订单id
	 * @return ResponseReport
	 */
	@RequestMapping("goPayment")
	public @ResponseBody ResponseReport goPayment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String id = rr.getDataValue("id");//订单id
		if (id == null || "".equals(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		//判断订单支付状态status:0 待支付；1 已支付；2 已取消
		 UserPaymentAppointment upa = paymentAppointmentService.getUnpaidById(Long.parseLong(id));
		if("0".equals(upa.getStatus())) {
			//判断订单是否超过10分钟
			long time = new Date().getTime()-upa.getCreate_Time().getTime();
			if(time>600000) {//超过10分钟
				upa.setStatus("2");
				int up = paymentAppointmentService.updateUnpaidById(upa);
				return super.getAjaxResult(rr, ResponseCode.OK, "不可进行付款", upa);
			}else {
				//处于待支付状态，可以进行付款操作
				return super.getAjaxResult(rr, ResponseCode.OK, "可以进行付款操作", upa);
			}
		}else if("2".equals(upa.getStatus())) {
			//已取消（认为或系统超过10分钟未支付自动取消）
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "该待支付订单已经被取消，不可以进行付款操作", upa);
		}else {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "不可以进行付款操作", upa);
		}
	}
	
	
	/**
	 * 支付
	 * @author 刘涛
	 * @param id 订单id
	 * @param toId 被约人UserId
	 * @param expectTime 期望约见时间yyyy-MM-dd
	 * @return ResponseReport
	 */
	@RequestMapping("paymentJB")
	public @ResponseBody ResponseReport paymentJB(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String id = rr.getDataValue("id");//订单id
		if (id == null || "".equals(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		//jb支付
		if(true) {//支付成功，更新支付状态status:0 待支付；1 已支付；2 已取消
			UserPaymentAppointment upa = paymentAppointmentService.getUnpaidById(Long.parseLong(id));
			upa.setStatus("1");
			int up = paymentAppointmentService.updateUnpaidById(upa);
			if(up>0) {//更新成功，生成待约见订单并推送消息给被约见人
				//被约见人userId
				String toId = rr.getDataValue("toId");
				if (toId == null || "".equals(toId))
				{
					return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
				}
				//期望约见时间
				String expectTime = rr.getDataValue("expectTime");
				UserAppointmentRecord uar = new UserAppointmentRecord();
				uar.setFrom_Id(Long.parseLong(userId));
				uar.setTo_Id(Long.parseLong(toId));
				uar.setStatus("0");// 待约见
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try
				{
					if (null != expectTime && !"".equals(expectTime))
						uar.setExpect_Time(sdf.parse(expectTime));
				}
				catch (ParseException e)
				{
					e.printStackTrace();
					return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数格式错误", null);
				}
				uar = appointmentService.addAppointment(uar);
				if(!"".equals(uar.getId())&&uar.getId()!=0){//生成待约见订单成功
					// 给被约见人发送系统通知
					// String con = "";
					// IMUtil.sendAttachMsg("tojoycloudadmin9999", "0", toId, con, "", "",
					// "", "2", "");
					TjyUser fromUser = tjyUserService.selectByPrimaryKey(userId);
					TjyUser toUser = tjyUserService.selectByPrimaryKey(toId);
					MessageInfo mi = new MessageInfo();
					mi.setId(UUIDGenerator.getUUID());
					mi.setStatus(0);
					mi.setType(3);
					String str[] = content.split("XX");
					String toName = toUser.getTrueName();
					String name = fromUser.getTrueName();
					if (StringUtil.isEmpty(toName))
						toName = toUser.getNickname();
					if (StringUtil.isEmpty(name))
						name = fromUser.getNickname();
					String content = str[0] + toName + str[1] + name + str[2];
					mi.setContent(content);
					mi.setToUserId(toId);

					messageInfoService.addMessageInfo(mi);
					return super.getAjaxResult(rr, ResponseCode.OK, "生成约见订单成功", uar);
				}else {
					return super.getAjaxResult(rr, ResponseCode.NotSupport, "生成约见订失败", null);
				}
				
				
			}else {
				return super.getAjaxResult(rr, ResponseCode.OK, "支付失败", upa);
			}
		}else {
			//支付失败
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "支付失败", null);
		}
	}
	
	/**
	 * 取消约见
	 * 
	 * @param id
	 */
	@RequestMapping("cancel")
	public @ResponseBody ResponseReport cancel(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String id = rr.getDataValue("id");
		if (id == null || "".equals(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		UserAppointmentRecord uar = appointmentService.getUserAppointmentRecordByid(Long.parseLong(id));
		uar.setStatus("3");
		int cancel = appointmentService.updateAppointmentRecord(uar);
		if(cancel>0) {//取消约见成功
			if(userId.equals(uar.getTo_Id())) {//被约见人取消约见，给约见人发送系统消息
				// 给被约见人发送系统通知
				// String con = "";
				// IMUtil.sendAttachMsg("tojoycloudadmin9999", "0", toId, con, "", "",
				// "", "2", "");
				TjyUser fromUser = tjyUserService.selectByPrimaryKey(userId);
				TjyUser toUser = tjyUserService.selectByPrimaryKey(uar.getTo_Id()+"");
				MessageInfo mi = new MessageInfo();
				mi.setId(UUIDGenerator.getUUID());
				mi.setStatus(0);
				mi.setType(6);
				String str[] = cancel_content.split("XX");
				String toName = toUser.getTrueName();
				String name = fromUser.getTrueName();
				if (StringUtil.isEmpty(toName))
					toName = toUser.getNickname();
				if (StringUtil.isEmpty(name))
					name = fromUser.getNickname();
				String content = str[0] + name + str[1] + toName + str[2];
				mi.setContent(content);
				mi.setToUserId(userId);
				messageInfoService.addMessageInfo(mi);
				return super.getAjaxResult(rr, ResponseCode.OK, "取消约见成功", uar);
			}
			return super.getAjaxResult(rr, ResponseCode.OK, "取消约见成功", uar);
		}else {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "取消约见失败", null);
		}
	}

	
	/**
	 * 发起约见
	 * 
	 * @param id
	 * @param expectTime期望时间
	 * 
	 */
	@RequestMapping("launchAppointment")
	public @ResponseBody ResponseReport launchAppointment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String id = rr.getDataValue("id");
		if (id == null || "".equals(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		//被约见人发起约见，给约见人发送系统消息
		// String con = "";
		// IMUtil.sendAttachMsg("tojoycloudadmin9999", "0", toId, con, "", "",
		// "", "2", "");
		UserAppointmentRecord uar = appointmentService.getUserAppointmentRecordByid(Long.parseLong(id));
		TjyUser fromUser = tjyUserService.selectByPrimaryKey(userId);
		TjyUser toUser = tjyUserService.selectByPrimaryKey(uar.getTo_Id()+"");
		MessageInfo mi = new MessageInfo();
		mi.setId(UUIDGenerator.getUUID());
		mi.setStatus(0);
		mi.setType(6);
		String str[] = cancel_content.split("XX");
		String toName = toUser.getTrueName();
		String name = fromUser.getTrueName();
		if (StringUtil.isEmpty(toName))
			toName = toUser.getNickname();
		if (StringUtil.isEmpty(name))
			name = fromUser.getNickname();
		String content = str[0] + name + str[1] + toName + str[2];
		mi.setContent(content);
		mi.setToUserId(userId);
		String mess = messageInfoService.addMessageInfo(mi);
		if("msg.operation.success".equals(mess)) {
			return super.getAjaxResult(rr, ResponseCode.OK, "发起约见成功", mess);
		}else {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "发起约见失败", mess);
		}
	}
	
	/**
	 * 约见人确认约见
	 * 
	 * @param toId被约见人
	 * @param expectTime期望时间
	 * 
	 */
	@RequestMapping("confirmAppointment")
	public @ResponseBody ResponseReport confirmAppointment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String id = rr.getDataValue("id");
		if (id == null || "".equals(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		//判断以前约见订单中是否有未结算的费用
		List listUnpaid = paymentAppointmentService.getUnpaidByUserId(userId);
		if(listUnpaid.size()>0) {
			//有未完成的订单
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "有未完成的支付", listUnpaid);
		}else {
			return super.getAjaxResult(rr, ResponseCode.OK, "没有未完成的支付，可以连接视频通话", listUnpaid);
		}

	}
	
	
	/**
	 * 视频连接成功调用该接口
	 * 将每次连接记录存入约见详表中
	 * @author 刘涛
	 * @param 
	 * 
	 */
	@RequestMapping("details")
	public @ResponseBody ResponseReport details(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String id = rr.getDataValue("id");
		if (id == null || "".equals(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		
		
		UserAppointmentRecordDetails uard = new UserAppointmentRecordDetails();
		uard.setAr_Id(Long.parseLong(id));
		uard.setStatus("0");
		uard = appointmentDetailsService.addAppointmentRecordDetails(uard);
		if(0!=uard.getId()&&!"".equals(uard.getId())) {//添加成功
			//更新主表
			UserAppointmentRecord uar = appointmentService.getUserAppointmentRecordByid(Long.parseLong(id));
			uar.setStatus("1");
			int up = appointmentService.updateAppointmentRecord(uar);
			if(up>0) {//主表更新成功
				return super.getAjaxResult(rr, ResponseCode.OK, "主表更新成功", uar);
			}else {
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "主表更新失败", null);
			}
		}else {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "添加失败", null);
		}
	}

	/**
	 * 视频连接任意一方中断时调用该接口
	 * 更新状态，计算视频时常，更新主表总时长
	 * 
	 * @param toId被约见人
	 * @param expectTime期望时间
	 * 
	 */
	@RequestMapping("gangUp")
	public @ResponseBody ResponseReport gangUp(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		//主表id
		String id = rr.getDataValue("id");
		//本次视频的id
		String aid = rr.getDataValue("aid");
		if (id == null || "".equals(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		if (aid == null || "".equals(aid))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		//更新记录表状态，更新时间，统计总用时
		UserAppointmentRecordDetails uard = appointmentDetailsService.getAppointmentRecordDetails(Long.parseLong(aid));
		uard.setStatus("1");
		Date endDate = new Date();
		uard.setEnd_Time(endDate);
		long duration = endDate.getTime()-uard.getStart_Time().getTime();
		uard.setDuration(duration);
		int ds = appointmentDetailsService.updateAppointmentDetails(uard);
		if(ds>0) {
			//更新主表信息
			UserAppointmentRecord uar = appointmentService.getUserAppointmentRecordByid(Long.parseLong(id));
			uar.setStatus("0");
			List list = appointmentDetailsService.getAppointmentRecordDetailsList(Long.parseLong(aid));
			long time = 0;
			if(list.size()>0) {
				for(int i=0;i<list.size();i++) {
					time += ((UserAppointmentRecordDetails)list.get(i)).getDuration();
				}
			}
			if(time>0)
				uar.setDuration(time);
			appointmentService.updateAppointmentRecord(uar);
		}
		return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
	}
	
	
	
	
	/**
	 * 结束约见
	 * 
	 * @param toId被约见人
	 * @param expectTime期望时间
	 * 
	 */
	@RequestMapping("closingAppointment")
	public @ResponseBody ResponseReport closingAppointment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String id = rr.getDataValue("id");
		if (id == null || "".equals(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		//结算费用
		//是否购买等于时间
		//是否大于购买时间
		//系统退还超出费用
		//更新表状态
		return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);

	}
	
	
	
	
	//------------以下是简易版约见接口-------------------------------------------------------
	/**
	 * 获取同城、同行、同趣好友接口
	 * @author 刘涛
	 * @param key(必选)
	 * @param pageNum(页码)
	 * @param pageSize(条数)
	 * @return ResponseReport
	 * @throws
	 */
	@RequestMapping("getFriend")
	public @ResponseBody ResponseReport getFriend(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		List userList = tjyUserService.getUserListById(userId);
		if(userList.size()>0) {
			Map ulMap = (HashMap)userList.get(0);
			String cityName = ulMap.get("city_name").toString();
			String industryName = ulMap.get("industry_name").toString();
			String hobbys = appointmentService.getUserHobby(userId);
			String key = rr.getDataValue("key");
			if (StringUtil.isEmpty(key))
			{
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
			}
			String page = rr.getDataValue("pageNum");
			String size = rr.getDataValue("pageSize");
			if (StringUtil.isEmpty(page))
			{
				page = "1";// 默认第一页
			}
			if (StringUtil.isEmpty(size))
			{
				size = "10"; // 默认10条
			}
			List res = new ArrayList();
			if("cityFriend".equals(key)) {
				//同城商友
				res= imGroupinfoService.selCityElite(userId, Integer.parseInt(page), Integer.parseInt(size));
				Map map = new HashMap();
				map.put("ms", cityName);
				map.put("obj", res);
				return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", map);
			}else if("peerElite".equals(key)){
				//同行商友：   peerElite
				res= imGroupinfoService.selPeerElite(userId, null, null,Integer.parseInt(page), Integer.parseInt(size));
				Map map = new HashMap();
				map.put("ms", industryName);
				map.put("obj", res);
				return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", map);
			}else if("sameHobby".equals(key)){
				//同趣商友
				String hobby =rr.getDataValue("hobbyId");
				String groupName = rr.getDataValue("nickName");
				res= imGroupinfoService.selIdenticalHobbyFriends(userId, hobby,groupName,Integer.parseInt(page), Integer.parseInt(size));
				Map map = new HashMap();
				map.put("ms", hobbys);
				map.put("obj", res);
				return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", map);
			}else {
				return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数key错误", null);
			}
		}else {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "用户不存在", null);
		}
		
	}
	
	/**
	 * 发起约见
	 * @author 刘涛
	 * @param toId 被约见人(必选)
	 * @param expectTime (可选，格式：yyyy-MM-dd HH:mm:ss)
	 * @param estimateTimeLength 预计约见时长(可选，单位：秒)
	 * @param msg 约见理由(可选)
	 * @return ResponseReport
	 * @throws 
	 */
	@RequestMapping("send")
	public @ResponseBody ResponseReport send(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}

		String toId = rr.getDataValue("toId");
		if (StringUtil.isEmpty(toId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		//期望约见时间
		String expectTime = rr.getDataValue("expectTime");
		//预计约见时常
		String estimateTimeLength = rr.getDataValue("estimateTimeLength");
		//约见理由
		String msg = rr.getDataValue("msg");
		UserAppointmentRecord uar = new UserAppointmentRecord();
		uar.setFrom_Id(Long.parseLong(userId));
		uar.setTo_Id(Long.parseLong(toId));
		uar.setStatus("0");// 待约见
		if(!StringUtil.isEmpty(expectTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try
			{
				uar.setExpect_Time(sdf.parse(expectTime));
			}
			catch (ParseException e)
			{
				e.printStackTrace();
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数格式错误", null);
			}
		}
		if(!StringUtil.isEmpty(estimateTimeLength))
			uar.setEstimateTimeLength(Long.parseLong(estimateTimeLength));
		if(!StringUtil.isEmpty(msg))
			uar.setMsg(msg);
		uar.setModule("0");
		uar = appointmentService.addAppointment(uar);
		if(uar.getId()>0) {
			// 给被约见人发送系统通知
			TjyUser fromUser = tjyUserService.selectByPrimaryKey(userId);
			TjyUser toUser = tjyUserService.selectByPrimaryKey(toId);
			String name = fromUser.getTrueName();
			String toName = toUser.getTrueName();
			if (StringUtil.isEmpty(name))
				name = fromUser.getNickname();
			if (StringUtil.isEmpty(toName))
				toName = toUser.getNickname();
			String str[] = content.split("XX");
			String content = str[0] + toName + str[1] + name + str[2];
			MessageInfo mi = new MessageInfo();
			mi.setId(UUIDGenerator.getUUID());
			mi.setContent(content);
			mi.setType(5);
			mi.setTemplateId("");
			mi.setStatus(0);
			mi.setCreateTime(new Date());
			mi.setToUserId(toId);
			String mess = messageInfoService.addMessageInfo(mi);
			if("msg.operation.success".equals(mess)){
				String pageNum = rr.getDataValue("pageNum");
				String pageSize = rr.getDataValue("pageSize");
				if (StringUtil.isEmpty(pageNum))
				{
					pageNum = "1";// 默认第一页
				}
				if (StringUtil.isEmpty(pageSize))
				{
					pageSize = "10"; // 默认10条
				}
				//被约见列表
				List byjList = appointmentService.getByjList(Long.parseLong(userId),Integer.parseInt(pageNum),Integer.parseInt(pageSize));
				Map map = new HashMap();
				map.put("data", uar);
				map.put("dyj", byjList);
				return super.getAjaxResult(rr, ResponseCode.OK, "发起约见成功", map);
			}else {
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "消息发送失败", null);
			}
			
		}
		return super.getAjaxResult(rr, ResponseCode.NotSupport, "发起约见失败", null);
	}
	
	/**
	 * 取消约见
	 * @author 刘涛
	 * @param id (必选)
	 * @return ResponseReport
	 * @throws 
	 */
	@RequestMapping("canc")
	public @ResponseBody ResponseReport canc(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String id = rr.getDataValue("id");
		if (StringUtil.isEmpty(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		UserAppointmentRecord uar = appointmentService.getUserAppointmentRecordByid(Long.parseLong(id));
		uar.setStatus("3");
		int cancel = appointmentService.updateAppointmentRecord(uar);
		if(cancel>0) {//取消约见成功
			if(userId.equals(uar.getTo_Id()+"")) {//判断是约见人取消约见还是被约见人取消约见，被约见人取消约见，给约见人发送系统消息
				// 给约见人发送系统通知
				TjyUser fromUser = tjyUserService.selectByPrimaryKey(userId);
				TjyUser toUser = tjyUserService.selectByPrimaryKey(uar.getFrom_Id()+"");
				String name = fromUser.getTrueName();
				String toName = toUser.getTrueName();
				String str[] = cancel_content.split("XX");
				String content = str[0] + name + str[1] + toName + str[2];
				if (StringUtil.isEmpty(name))
					name = fromUser.getNickname();
				if (StringUtil.isEmpty(toName))
					toName = toUser.getNickname();
				MessageInfo mi = new MessageInfo();
				mi.setId(UUIDGenerator.getUUID());
				mi.setStatus(0);
				mi.setType(5);
				mi.setContent(content);
				mi.setToUserId(toUser.getId()+"");
				mi.setTemplateId("");
				mi.setCreateTime(new Date());
				String mess = messageInfoService.addMessageInfo(mi);
				if("msg.operation.success".equals(mess)){
					return super.getAjaxResult(rr, ResponseCode.OK, "消息发送成功", null);
				}else {
					return super.getAjaxResult(rr, ResponseCode.NotSupport, "消息发送失败成功", null);
				}
			}
			return super.getAjaxResult(rr, ResponseCode.OK, "取消约见成功", null);
		}else {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "取消约见失败", null);
		}
	}
	
	/**
	 * 视频连接成功调用该接口
	 * 将每次连接记录存入约见详表中
	 * @author 刘涛
	 * @param id (必选)
	 * @return ResponseReport
	 * @throws 
	 */
//	@RequestMapping("confirm")
//	public @ResponseBody ResponseReport confirm(@RequestBody RequestReport rr)
//	{
//		String userId = rr.getUserProperty().getUserId();
//		if (StringUtil.isEmpty(userId))
//		{
//			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
//		}
//		String id = rr.getDataValue("id");
//		if (id == null || "".equals(id))
//		{
//			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
//		}
//		//被约见人发起约见，给约见人发送系统消息
//		UserAppointmentRecord uar = appointmentService.getUserAppointmentRecordByid(Long.parseLong(id));
//		UserAppointmentRecordDetails uard = new UserAppointmentRecordDetails();
//		uard.setAr_Id(Long.parseLong(id));
//		uard.setStatus("0");
//		uard.setCreate_Time(new Date());
//		uard.setStart_Time(new Date());
//		uard = appointmentDetailsService.addAppointmentRecordDetails(uard);
//		if(uard.getId()>0) {
//			TjyUser fromUser = tjyUserService.selectByPrimaryKey(userId);
//			TjyUser toUser = tjyUserService.selectByPrimaryKey(uar.getFrom_Id()+"");
//			MessageInfo mi = new MessageInfo();
//			mi.setId(UUIDGenerator.getUUID());
//			String str[] = content.split("XX");
//			String toName = toUser.getTrueName();
//			String name = fromUser.getTrueName();
//			if (StringUtil.isEmpty(toName))
//				toName = toUser.getNickname();
//			if (StringUtil.isEmpty(name))
//				name = fromUser.getNickname();
//			String content = str[0] + toName + str[1] + name + str[2];
//			mi.setContent(content);
//			mi.setType(5);
//			mi.setTemplateId("");
//			mi.setStatus(0);
//			mi.setToUserId(toUser.getId());
//			mi.setCreateTime(new Date());
//			messageInfoService.addMessageInfo(mi);
//			//更新主表
//			uar.setStatus("1");
//			int up = appointmentService.updateAppointmentRecord(uar);
//			if(up>0) {//主表更新成功
//				return super.getAjaxResult(rr, ResponseCode.OK, "主表更新成功", uard);
//			}else {
//				return super.getAjaxResult(rr, ResponseCode.NotSupport, "主表更新失败", null);
//			}
//		}else {
//			return super.getAjaxResult(rr, ResponseCode.NotSupport, "视频失败", null);
//		}
//	}
	/**
	 * 视频连接任意一方中断时调用该接口
	 * 更新状态，计算视频时常，更新主表总时长
	 * @author 刘涛
	 * @param id (必选)
	 * @param startTime yyyy-MM-dd HH:mm:ss
	 * @param endTime yyyy-MM-dd HH:mm:ss
	 * @param duration时长 秒
	 * @return ResponseReport
	 * @throws 
	 */
	@RequestMapping("interrupt")
	public @ResponseBody ResponseReport interrupt(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String id = rr.getDataValue("id");
		if (StringUtil.isEmpty(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		String startTime = rr.getDataValue("startTime");
		if (StringUtil.isEmpty(startTime))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		String endTime = rr.getDataValue("endTime");
		if (StringUtil.isEmpty(endTime))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		String duration = rr.getDataValue("duration");
		if (StringUtil.isEmpty(duration))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		UserAppointmentRecordDetails uard = new UserAppointmentRecordDetails();
		uard.setAr_Id(Long.parseLong(id));
		uard.setStatus("1");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			uard.setStart_Time(sdf.parse(startTime));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数格式错误", null);
		}
		try
		{
			uard.setEnd_Time(sdf.parse(endTime));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数格式错误", null);
		}
		uard.setDuration(Long.parseLong(duration));
		uard = appointmentDetailsService.addAppointmentRecordDetails(uard);
		if(uard.getId()>0) {
			//更新主表信息
			UserAppointmentRecord uar = appointmentService.getUserAppointmentRecordByid(Long.parseLong(id));
			uar.setStatus("1");
			List list = appointmentDetailsService.getAppointmentRecordDetailsList(Long.parseLong(id));
			long time = 0;
			if(list.size()>0) {
				for(int i=0;i<list.size();i++) {
					long dur = Long.parseLong(((Map)list.get(i)).get("duration").toString());
					time = time + dur;
				}
			}
			if(time>0)
				uar.setDuration(time);
			int cos = appointmentService.updateAppointmentRecord(uar);
			if(cos>0) {
				return super.getAjaxResult(rr, ResponseCode.OK, "主表时长更新成功", null);
			}else {
				return super.getAjaxResult(rr, ResponseCode.NotSupport, "主表时长更新失败", null);
			}
			
		}
		return super.getAjaxResult(rr, ResponseCode.NotSupport, "更新失败", null);
	}
	/**
	 * 结束
	 * @author 刘涛
	 * @param id(必选)
	 * @return ResponseReport
	 * @throws
	 */
	@RequestMapping("overAppointment")
	public @ResponseBody ResponseReport overAppointment(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		//主表id
		String id = rr.getDataValue("id");
		if (StringUtil.isEmpty(id))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		UserAppointmentRecord uar = appointmentService.getUserAppointmentRecordByid(Long.parseLong(id));
		uar.setStatus("2");
		int over = appointmentService.updateAppointmentRecord(uar);
		if(over>0) {
			return super.getAjaxResult(rr, ResponseCode.OK, "成功", null);
		}else {
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "失败", null);
		}
	}
	/**
	 * 获取约见列表
	 * 
	 */
	@RequestMapping("getAppointment")
	public @ResponseBody ResponseReport getAppointmentList(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String bs = rr.getDataValue("bs");
		if (StringUtil.isEmpty(bs))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		String pageSize = rr.getDataValue("pageSize");
		String pageNum = rr.getDataValue("pageNum");
		if (StringUtil.isEmpty(pageNum))
		{
			pageNum = "1";// 默认第一页
		}
		if (StringUtil.isEmpty(pageSize))
		{
			pageSize = "10"; // 默认10条
		}
		List list = null;
		if ("yyj".equals(bs))
			list = appointmentService.getYyjList(Integer.parseInt(userId), Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		if ("dyj".equals(bs))
			list = appointmentService.getDyjList(Long.parseLong(userId),Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		if ("byj".equals(bs))
			list = appointmentService.getByjList(Integer.parseInt(userId), Integer.parseInt(pageNum), Integer.parseInt(pageSize));

		return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", list);
	}
}
