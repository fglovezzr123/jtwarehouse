package com.wing.socialcontact.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.commons.util.ApplicationPath;
import com.wing.socialcontact.service.wx.api.IActivityRefundService;
import com.wing.socialcontact.service.wx.api.IActivityService;
import com.wing.socialcontact.service.wx.api.IActivityUserService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.bean.Activity;
import com.wing.socialcontact.service.wx.bean.ActivityRefund;
import com.wing.socialcontact.service.wx.bean.ActivityUser;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.POIUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;
/**
 * 
 * <p>Title:活动报名管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年4月19日 下午7:18:36
 */
@Controller
@RequestMapping("activityUser/")
public class ActivityUserAction extends BaseAction {

	@Autowired
	private IActivityUserService activityUserService;
	@Autowired
	private IMessageInfoService messageInfoService;
    @Autowired
    private IActivityService activityService;
    @Autowired
	private IActivityRefundService activityRefundService;
    
	
	/**
	 * 活动报名列表
	 */
	@RequiresPermissions("activityuser:read")
	@RequestMapping("load")
	public String classload(ModelMap map,String id){
		
		map.put("id", id);
		return "system/activity/loaduser";
	}
	
	/**
	 * 活动报名列表数据查询
	 * @param param
	 * @param activitytag
	 * @return
	 */
	@RequiresPermissions("activityuser:read")
	@RequestMapping("query")
	public ModelAndView classquery(PageParam param,ActivityUser activityUser,String acid,String userId){
		activityUser.setActivityId(acid);
		activityUser.setUserId(userId);
		return ajaxJsonEscape(activityUserService.selectactivityuser(param, activityUser));
	}
	/**
	 * 活动报名列表数据查询
	 * @param param
	 * @param activitytag
	 * @return
	 */
	@RequiresPermissions("activityuser:read")
	@RequestMapping("query2")
	public ModelAndView classquery2(PageParam param,ActivityUser activityUser,String userId){
		activityUser.setUserId(userId);
		return ajaxJsonEscape(activityUserService.selectactivityuser(param, activityUser));
	}
	
	/**
	 * 活动报名列表导出
	 */
	
	@RequiresPermissions("activityuser:read")
	@RequestMapping("export")
	public void query(HttpServletResponse response, HttpServletRequest request,String acid1,
			String userName1,String phone1,Date createTime1) throws IOException{
		acid1 = new String(acid1.getBytes("ISO-8859-1"), "utf-8");
		userName1 = new String(userName1.getBytes("ISO-8859-1"), "utf-8");
		phone1 = new String(phone1.getBytes("ISO-8859-1"), "utf-8");
		List<Map<String,Object>> lst= activityUserService.exporactivityUsers(acid1, userName1, phone1, createTime1);
		
		  HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet0 = wb.createSheet("报名用户列表");
			HSSFRow row0 = sheet0.createRow(0);

			HSSFCellStyle titleStyle = POIUtil.getTitleStyle(wb);
			HSSFCellStyle cellStyle = POIUtil.getCellStyle(wb);

			POIUtil.setCellValue(row0.createCell(0), titleStyle, "ID");
			POIUtil.setCellValue(row0.createCell(1), titleStyle, "活动主题");
			POIUtil.setCellValue(row0.createCell(2), titleStyle, "活动类型");
			POIUtil.setCellValue(row0.createCell(3), titleStyle, "姓名");
			POIUtil.setCellValue(row0.createCell(4), titleStyle, "手机号码");
			POIUtil.setCellValue(row0.createCell(5), titleStyle, "门票价格（元）");
			POIUtil.setCellValue(row0.createCell(6), titleStyle, "所属公司");
			POIUtil.setCellValue(row0.createCell(7), titleStyle, "报名时间");
			POIUtil.setCellValue(row0.createCell(8), titleStyle, "报名状态");

			int rowIndex = 1;
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			 
			
			for(Map<String,Object> map :lst){
				HSSFRow row = sheet0.createRow(rowIndex++);
				POIUtil.setCellValue(row.createCell(0), cellStyle,rowIndex-1);
				POIUtil.setCellValue(row.createCell(1), cellStyle, map.get("titles"));
				POIUtil.setCellValue(row.createCell(2), cellStyle, map.get("classId"));
				POIUtil.setCellValue(row.createCell(3), cellStyle, map.get("userName"));
				POIUtil.setCellValue(row.createCell(4), cellStyle, map.get("phone"));
				POIUtil.setCellValue(row.createCell(5), cellStyle, map.get("price"));
				POIUtil.setCellValue(row.createCell(6), cellStyle, map.get("company"));
				String str=sdf.format((Date) map.get("createTime"));
				POIUtil.setCellValue(row.createCell(7), cellStyle, str);
				Integer sta= (Integer) map.get("status");
				if(sta==1){
					POIUtil.setCellValue(row.createCell(8), cellStyle, "待确认");
				}else if(sta==2){
					POIUtil.setCellValue(row.createCell(8), cellStyle, "用户取消");
				}else if(sta==3){
					POIUtil.setCellValue(row.createCell(8), cellStyle, "活动取消");
				}else if(sta==4){
					POIUtil.setCellValue(row.createCell(8), cellStyle, "已确认");
				}else if(sta==5){
					POIUtil.setCellValue(row.createCell(8), cellStyle, "已拒绝");
				}
			}
			//自动列宽
			POIUtil.autoSizeColumn(row0);
			POIUtil.export(response, wb, "用户报名列表");
	}
	/**
	 * 同意报名
	 * @param id
	 * @return
	 */
	@RequiresPermissions("activityuser:read")
	@RequestMapping("agree")
	public ModelAndView agree(String id) {

		return ajaxDone(activityUserService.updatestatusbyid(id, 4));
	}
	/**
	 * 拒绝报名
	 * @param id
	 * @return
	 */
	/*@RequiresPermissions("activityuser:read")
	@RequestMapping("refuse")
	public ModelAndView refuse(String id) {
			 ActivityUser user = activityUserService.getactivityUserByid(id);
			 *//**
			  * 报名被拒绝  发送微信  并且退款
			  * 尊敬的**，您所报名的****活动未通过主办人审核，望尽快于主办人进行沟通。
			  *//*
			 Activity activity = activityService.getActivityByid(user.getActivityId());
				String touser = user.getUserId();
				
				//添加微信退款记录   
				ActivityRefund refund = new ActivityRefund();
				refund.setAmount(user.getPayPrice());
				refund.setCreateTime(new Date());
				refund.setStatus(0);
				refund.setType(1);
				refund.setUserId(touser);
				refund.setOrderId(user.getOrderId());
				boolean bo = activityRefundService.insertRefund(refund);
				
				if(bo){
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(touser);
				messageInfo.setCreateTime(new Date());
				// 组装内容
				String content = "尊敬的"+user.getUserName()+",您所报名的"+activity.getTitles()+"活动未通过主办人审核，望尽快于主办人进行沟通。";
				String con = WxMsmUtil.getTextMessageContent(content);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("RECON_ADMIN");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setWxMsgType(1);
				messageInfoService.addMessageInfo(messageInfo);
				}
		return ajaxDone(activityUserService.updatestatusbyid(id, 5));
	}*/
}
