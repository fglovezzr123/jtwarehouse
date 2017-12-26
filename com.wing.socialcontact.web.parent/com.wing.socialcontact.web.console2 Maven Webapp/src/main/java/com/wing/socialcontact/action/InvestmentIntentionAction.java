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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IInvestmentClassService;
import com.wing.socialcontact.service.wx.api.IInvestmentIntentionService;
import com.wing.socialcontact.service.wx.bean.InvestmentClass;
import com.wing.socialcontact.service.wx.bean.InvestmentIntention;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.POIUtil;
import com.wing.socialcontact.util.ServletUtil;
/**
 * 
 * @author zhangzheng
 *
 */
@Controller
@RequestMapping("/investmentIntention ")
public class InvestmentIntentionAction extends BaseAction {

	@Autowired
	private IInvestmentIntentionService investmentIntentionService;
	@Autowired
	private IInvestmentClassService investmentClassService;
	
	/**
	 * 
	 * @return
	 */
	@RequiresPermissions("investmentIntention:read")
	@RequestMapping("load")
	public String load(ModelMap map){
		
		//获取所有分类
		List<InvestmentClass> cla = investmentClassService.getAllClass();
		map.addAttribute("cla", cla);
		return "system/investmentClass/intentionload";
	}
	/**
	 * 
	 * @param param
	 * @param investmentIntention
	 * @return
	 */
	@RequiresPermissions("investmentIntention:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,InvestmentIntention investmentIntention,Date stime,Date etime){
		return ajaxJsonEscape(investmentIntentionService.selectinvestmentintention(param, investmentIntention, stime, etime));
	}
	
	
	/**
	 * 投资顾问修改
	 */
	@RequiresPermissions("investmentIntention:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		InvestmentIntention dto = investmentIntentionService.getInvestmentIntentionByid(id);
		map.addAttribute("dto", dto);
		return "system/investmentClass/intentionupdate";
	}
	
	@RequiresPermissions("investmentIntention:update")
	@RequestMapping("update")
	public ModelAndView update( InvestmentIntention dto,Errors errors){
		dto.setUpdateTime(new Date());
		dto.setUpdateUserId(ServletUtil.getMember().getId());
		return ajaxDone(investmentIntentionService.updateInvestmentIntention(dto));
		
	}
	
	/**
	 * 
	 * @param response
	 * @param request
	 * @param className1
	 * @param status1
	 * @param stime1
	 * @param etime1
	 */
	@RequiresPermissions("investmentIntention:read")
	@RequestMapping("export")
	public void query(HttpServletResponse response, HttpServletRequest request,String className1,
			Integer status1,Date stime1,Date etime1) throws IOException{
		className1 = new String(className1.getBytes("ISO-8859-1"), "utf-8");
		List<Map<String,Object>> lst= investmentIntentionService.exportinvestmentintention(className1, status1, stime1, etime1);
		
		  HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet0 = wb.createSheet("投资意向");
			HSSFRow row0 = sheet0.createRow(0);

			HSSFCellStyle titleStyle = POIUtil.getTitleStyle(wb);
			HSSFCellStyle cellStyle = POIUtil.getCellStyle(wb);

			POIUtil.setCellValue(row0.createCell(0), titleStyle, "ID");
			POIUtil.setCellValue(row0.createCell(1), titleStyle, "投资兴趣");
			POIUtil.setCellValue(row0.createCell(2), titleStyle, "投资额度（元）");
			POIUtil.setCellValue(row0.createCell(3), titleStyle, "联系人");
			POIUtil.setCellValue(row0.createCell(4), titleStyle, "联系电话");
			POIUtil.setCellValue(row0.createCell(5), titleStyle, "创建时间");
			POIUtil.setCellValue(row0.createCell(6), titleStyle, "投资状态");
			POIUtil.setCellValue(row0.createCell(7), titleStyle, "投资意向留言");
			POIUtil.setCellValue(row0.createCell(8), titleStyle, "客服留言");

			int rowIndex = 1;
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			 
			
			for(Map<String,Object> map :lst){
				HSSFRow row = sheet0.createRow(rowIndex++);
				POIUtil.setCellValue(row.createCell(0), cellStyle,rowIndex-1);
				POIUtil.setCellValue(row.createCell(1), cellStyle, map.get("className"));
				POIUtil.setCellValue(row.createCell(2), cellStyle, map.get("position"));
				POIUtil.setCellValue(row.createCell(3), cellStyle, map.get("userName"));
				POIUtil.setCellValue(row.createCell(4), cellStyle, map.get("phone"));
				String str=sdf.format((Date) map.get("createTime"));
				POIUtil.setCellValue(row.createCell(5), cellStyle, str);
				Integer sta= (Integer) map.get("status");
				if(sta==1){
					POIUtil.setCellValue(row.createCell(6), cellStyle, "待联系");
				}else if(sta==2){
					POIUtil.setCellValue(row.createCell(6), cellStyle, "待考虑");
				}else if(sta==3){
					POIUtil.setCellValue(row.createCell(6), cellStyle, "已投资");
				}else if(sta==4){
					POIUtil.setCellValue(row.createCell(6), cellStyle, "已拒绝");
				}
				POIUtil.setCellValue(row.createCell(7), cellStyle, map.get("message"));
				POIUtil.setCellValue(row.createCell(8), cellStyle, map.get("answer"));
			}
			//自动列宽
			POIUtil.autoSizeColumn(row0);
			POIUtil.export(response, wb, "投资意向");
	}
	
	
}
